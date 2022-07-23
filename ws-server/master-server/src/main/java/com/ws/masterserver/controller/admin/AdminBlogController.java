package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.blog.BlogDto;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
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
@RequestMapping("/api/v1/admin/blog")
@RequiredArgsConstructor
@Slf4j
public class AdminBlogController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "API thêm mới blog")
    public ResponseEntity<ResData<String>> create(@RequestBody BlogDto dto){
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.create(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API sửa blog")
    public ResponseEntity<ResData<String>> update(@RequestBody BlogDto dto){
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.update(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa blog")
    public ResponseEntity<ResData<String>> delete(@RequestBody BlogDto dto){
        log.info("start api delele with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.delete(getCurrentUser(), dto));
    }
}
