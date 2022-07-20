package com.ws.master_service.controller;

import com.ws.master_service.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.master_service.dto.admin.order.search.OrderReq;
import com.ws.master_service.dto.admin.order.search.OrderRes;
import com.ws.master_service.dto.admin.order.detail.DetailRes;
import com.ws.master_service.dto.customer.order.add_to_cart.AddToCartDto;
import com.ws.master_service.dto.customer.order.add_to_cart.AddToCartResponse;
import com.ws.master_service.dto.customer.order.checkin.CheckinDto;
import com.ws.master_service.dto.customer.order.checkin.CheckinResponse;
import com.ws.master_service.dto.customer.order.me.MyOrderRequest;
import com.ws.master_service.dto.customer.order.me.MyOrderResponse;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController extends WsController {

    @Operation(summary = "API checkin")
    @PostMapping("/checkin")
    public ResponseEntity<ResData<CheckinResponse>> createCustomerOrder(@RequestBody CheckinDto body) {
        log.info("START API /api/v1/order/checkin");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.orderService.checkin(getCurrentUser(), body));
    }

    @Operation(summary = "API xem lịch sử đơn hàng của mình, mặc định sắp xếp theo ngày tạo từ mới nhất\n" +
            "đến cũ nhất")
    @GetMapping("/me")
    public ResponseEntity<PageData<MyOrderResponse>> getMyOrders(MyOrderRequest request) {
        log.info("START API /api/v1/order/me");
        return ResponseEntity.status(HttpStatus.OK).body(service.orderService.getMyOrder(getCurrentUser(), request));
    }

    @Deprecated(forRemoval = true)
    @Operation(summary = "API thêm sản phẩm vào giỏ hàng",
            deprecated = true)
    @PostMapping("/add-to-cart")
    public ResponseEntity<ResData<AddToCartResponse>> addToCart(@RequestBody AddToCartDto body) {
        log.info("START API /api/v1/order/add-to-cart");
        return ResponseEntity.status(HttpStatus.OK).body(service.orderService.addToCart(getCurrentUser(), body));
    }

}
