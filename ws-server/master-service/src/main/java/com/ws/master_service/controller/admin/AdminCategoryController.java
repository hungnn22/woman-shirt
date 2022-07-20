package com.ws.master_service.controller.admin;

import com.ws.master_service.dto.admin.category.CategoryDto;
import com.ws.master_service.dto.admin.category.CategoryReq;
import com.ws.master_service.dto.admin.category.CategoryRes;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.JsonUtils;
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
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController extends WsController {

    @PostMapping("/search")
    @Operation(summary = "API tìm kiếm danh sách loại sp")
    public ResponseEntity<PageData<CategoryRes>> search(@RequestBody CategoryReq req) {
        log.info("start api search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.search(getCurrentUser(), req));
    }

    @PostMapping("/create")
    @Operation(summary = "API thêm mới loại sp")
    public ResponseEntity<ResData<String>> create(@RequestBody CategoryDto dto) {
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.create(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa loại sản phẩm")
    public ResponseEntity<ResData<String>> delete(@RequestBody CategoryDto dto) {
        log.info("start api delete with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.delete(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API update loại sản phẩm")
    public ResponseEntity<ResData<String>> update(@RequestBody CategoryDto dto) {
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.update(getCurrentUser(), dto));
    }

    @PostMapping("/detail")
    @Operation(summary = "API chi tiết loại sản phẩm")
    public ResponseEntity<ResData<CategoryRes>> detail(@RequestBody CategoryDto dto) {
        log.info("start api detail with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryService.detail(getCurrentUser(), dto));
    }
}
