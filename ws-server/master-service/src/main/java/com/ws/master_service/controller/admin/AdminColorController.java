package com.ws.master_service.controller.admin;

import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.base.enum_dto.ColorDto;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/color")
@RequiredArgsConstructor
@Slf4j
public class AdminColorController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "API thêm mới màu sp")
    public ResponseEntity<ResData<String>> create(@RequestBody ColorDto dto) {
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.create(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API cập nhật màu sp")
    public ResponseEntity<ResData<String>> update(@RequestBody ColorDto dto){
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.update(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa màu sp")
    public ResponseEntity<ResData<String>> delete(@RequestBody ColorDto dto){
        log.info("start api delete with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.colorService.delete(getCurrentUser(), dto));
    }
}
