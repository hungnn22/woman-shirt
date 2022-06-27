package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.entity.CartEntity;
import com.ws.masterserver.service.CartService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final WsRepository repository;

    @Override
    public ResData<String> addToCart(CurrentUser currentUser, CartRequest cartRequest) {

        if (currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER)) {
            var productOptionEntity = repository.productOptionRepository.findById(cartRequest.getProductOptionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.PRODUCT_OPTION_VI.toLowerCase(Locale.ROOT))));

            if(productOptionEntity.getQty() < cartRequest.getQuantity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.QTY_VI));
            }

            Optional<CartEntity> cartOptional = repository.cartRepository.findByUserIdAndAndProductOptionId(currentUser.getId(), cartRequest.getProductOptionId());
            if(cartOptional.isPresent()){

                CartEntity cart = cartOptional.get();
                int updateQuantity = cart.getQuantity() + cartRequest.getQuantity();

                if(productOptionEntity.getQty() < updateQuantity){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.QTY_VI));
                }

                cart.setQuantity(updateQuantity);

                repository.cartRepository.save(cart);

                return new ResData<>("Thêm vào giỏ hàng thành công !!!", WsCode.OK);
            }else{

                CartEntity newCart = new CartEntity();
                newCart.setId(UidUtils.generateUid());
                newCart.setUserId(currentUser.getId());
                newCart.setProductOptionId(productOptionEntity.getId());
                newCart.setQuantity(cartRequest.getQuantity());
                repository.cartRepository.save(newCart);

                return new ResData<>("Thêm vào giỏ hàng thành công !!!", WsCode.OK);
            }

        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, WsConst.Messages.FORBIDDEN);
    }
}
