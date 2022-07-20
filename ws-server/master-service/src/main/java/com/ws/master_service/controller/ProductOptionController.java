package com.ws.master_service.controller;

import com.ws.master_service.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.constants.enums.SizeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-option")
@RequiredArgsConstructor
@Slf4j
public class ProductOptionController extends WsController {

    @PostMapping("/findId")
    public ResponseEntity<?> findProductOptionId(@RequestBody ProductOptionIdReq req){
        return ResponseEntity.status(HttpStatus.OK).body(service.productOptionService.findProductOptionId(req));
    }


    @GetMapping("/findColor")
    public ResponseEntity<?> findColor(@RequestParam String sizeId,@RequestParam String productId){
        return ResponseEntity.status(HttpStatus.OK).body(service.productOptionService.findColorNameBySize(sizeId,productId));
    }

    @GetMapping("/findSize")
    public ResponseEntity<?> findSize(@RequestParam String productId){
        return ResponseEntity.status(HttpStatus.OK).body(service.productOptionService.findSizeByProductId(productId));
    }

}
