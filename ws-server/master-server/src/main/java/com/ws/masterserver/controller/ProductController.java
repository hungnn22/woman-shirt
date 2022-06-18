package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.dto.admin.product.ProductDto;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsService;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController extends WsController {

//    @PostMapping("/search")
//    public ResponseEntity<?> search(@RequestBody ProductReq req){
//        return ResponseEntity.status(HttpStatus.OK).body(service.productService.search(getCurrentUser(), req));
//    }
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody ProductReq req){
        return ResponseEntity.status(HttpStatus.OK).body(service.productService.search( req));
    }

    @Operation(summary = "ADMIN: API thêm sản phầm")
    @RequestMapping("/create")
    public ResponseEntity<ResData<String>> create(@RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.productService.create(getCurrentUser(), dto));
    }

    @Operation(summary = "API GET Product Detail")
    @GetMapping("/{id}")
    public ResponseEntity<ResData<ProductDetailResponse>> getProductDetail(@PathVariable("id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(service.productService.getProductDetail(id));
    }

    @Operation(summary = "API GET Product Detail")
    @PostMapping("/admin/search")
    public ResponseEntity<Object> search4Admin(@RequestBody ProductReq req) {
        log.info("start api /api/v1/product/admin/search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.status(HttpStatus.OK).body(service.productService.search4Admin(getCurrentUser(), req));
    }


}
