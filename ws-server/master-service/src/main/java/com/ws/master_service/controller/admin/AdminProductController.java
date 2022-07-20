package com.ws.master_service.controller.admin;

import com.ws.master_service.dto.admin.product.create_update.ProductDto;
import com.ws.master_service.dto.customer.product.ProductReq;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@Slf4j
public class AdminProductController extends WsController {

    @Operation(summary = "API search product cho admin")
    @PostMapping("/search")
    public ResponseEntity<Object> search4Admin(@RequestBody ProductReq req) {
        log.info("start api /api/v1/product/admin/search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.adminProductService.search(getCurrentUser(), req));
    }

    @Operation(summary = "API detail product cho admin")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detail4Admin(@PathVariable String id) {
        log.info("start api /api/v1/product/admin/detail with id: {}", id);
        return ResponseEntity.ok(service.adminProductService.detail(getCurrentUser(), id));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(service.adminProductService.create(getCurrentUser(), dto));
    }
}
