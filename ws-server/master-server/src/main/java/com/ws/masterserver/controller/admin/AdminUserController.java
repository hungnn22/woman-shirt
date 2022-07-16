package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController extends WsController {
    @Operation(summary = "API search user cho admin")
    @PostMapping("/search")
    public ResponseEntity<Object> search(@RequestBody UserReq req) {
        log.info("start api /api/v1/admin/user/search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.adminUserService.search(getCurrentUser(), req));
    }

    @Operation(summary = "API get detail user cho admin")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detail(@PathVariable String id) {
        log.info("start api /api/v1/admin/user/detail with id: {}", id);
        return ResponseEntity.ok(service.adminUserService.detail(getCurrentUser(), id));
    }

    @Operation(summary = "API create user cho admin")
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserDto dto) {
        log.info("start api /api/v1/admin/user/create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.adminUserInfoService.create(getCurrentUser(), dto));
    }

    @Operation(summary = "API update user cho admin")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody UserDto dto) {
        log.info("start api /api/v1/admin/user/update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.adminUserInfoService.update(getCurrentUser(), dto));
    }
}
