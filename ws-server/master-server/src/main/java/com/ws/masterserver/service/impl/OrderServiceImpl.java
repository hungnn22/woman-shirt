package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartDto;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartResponse;
import com.ws.masterserver.dto.customer.order.checkin.CheckinDto;
import com.ws.masterserver.dto.customer.order.checkin.CheckinResponse;
import com.ws.masterserver.dto.customer.order.me.MyOrderRequest;
import com.ws.masterserver.dto.customer.order.me.MyOrderResponse;
import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.dto.admin.order.search.OrderRes;
import com.ws.masterserver.entity.AddressEntity;
import com.ws.masterserver.service.OrderService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.validate.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final WsRepository repository;

    /**
     * KH tạo order khi check-in
     *      Nếu KH không có order nào hoặc có order có trạng thái != PROCESSING thì tạo order mới cho KH
     *      Nếu có thì add vào order cũ
     * Note: Chưa xử lý phần hình thức thanh toán
     */
    @Override
    @Transactional
    public ResData<CheckinResponse> checkin(CurrentUser currentUser, CheckinDto body) {
        /**
         * Nếu role là khách hàng thì mới dc tạo order
         * */
        if (currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER)) {
            validateOrderCreateDto(body);
            var address = repository.addressRepository.getById(body.getAddressId());

            /**
             * Nếu KH nhập địa chỉ khác địa chỉ đã có cua KH thì thêm d/chi mới cho KH
             * */
            if (address == null) {
                address = AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .isDefault(Boolean.FALSE).userId(currentUser.getId())
                        .build();
                repository.addressRepository.save(address);
            }

            /**
             * Kiểm tra KH đang có order nào đang trong trạng thái PROCESSING không
             *      1. Nếu chưa có thì tạo mới và set status = PROCESSING
             * */


            /**
             * Update giỏ hàng vào bảng order_detail
             * */
                /**
                 * Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                 *  1. Nếu có thì tăng số lượng = sl cũ + sl mới
                 *  2. Nếu không thì tạo mới
                 * */
            return null;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, WsConst.Messages.FORBIDDEN);
    }

    @Override
    public PageData<MyOrderResponse> getMyOrder(CurrentUser currentUser, MyOrderRequest request) {

        return null;
    }

    @Override
    public ResData<AddToCartResponse> addToCart(CurrentUser currentUser, AddToCartDto body) {
            log.info("START OrderServiceImpl-addToCart() with req: {}", JsonUtils.toJson(body));
        return null;
    }

    @Override
    public PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req) {
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STAFF);
        return repository.orderCustomRepository.search4Admin(currentUser, req);
    }

    @Override
    public ResData<DetailRes> detail4Admin(CurrentUser currentUser, String id) {
        if (!repository.orderRepository.existsById(id)) {
            return new ResData<>(true);
        }
        AuthValidator.checkRole(currentUser, RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_ADMIN);
        return repository.orderCustomRepository.detail4Admin(currentUser, id);
    }

    private void validateOrderCreateDto(CheckinDto body) {
        if (body.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, WsConst.Messages.CART_EMPTY_EXCEPTION);
        }
        body.getItems().forEach(item -> {
            var product = repository.productRepository.findById(item.getProductId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.PRODUCT_VI.toLowerCase(Locale.ROOT))));
            var qty = item.getQty();
            if (qty <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.QTY_VI));
            }
            /**
             * check số lượng đặt không được lớn hơn sl có trong kho
             * */

        });
    }
}
