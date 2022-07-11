package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
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
        return ResponseEntity.ok(service.adminProductSearchService.search(getCurrentUser(), req));
    }

    @Operation(summary = "API detail product cho admin")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detail4Admin(@PathVariable String id) {
        log.info("start api /api/v1/product/admin/detail with id: {}", id);
        return ResponseEntity.ok(service.adminProductSearchService.detail(getCurrentUser(), id));
    }

}
