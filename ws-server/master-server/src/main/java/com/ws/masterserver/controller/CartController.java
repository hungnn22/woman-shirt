package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartDto;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartResponse;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController extends WsController {

    @Operation(summary = "API thêm sản phẩm vào giỏ hàng")
    @PostMapping("/add-to-cart")
    public ResponseEntity<ResData<String>> addToCart(@RequestBody CartRequest cartRequest) {
        log.info("START API /api/v1/order/add-to-cart");
        return ResponseEntity.status(HttpStatus.OK).body(service.cartService.addToCart(getCurrentUser(), cartRequest));
    }


}
