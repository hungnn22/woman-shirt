package com.ws.master_service.controller;

import com.ws.master_service.dto.customer.user.ProfileDto;
import com.ws.master_service.utils.base.WsController;
import com.ws.master_service.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController extends WsController {

    @Operation(summary = "update thông tin cá nhân")
    @PostMapping("/update-profile")
    public ResponseEntity<Object> updateProfile(@RequestBody ProfileDto dto) {
        log.info("start api /api/v1/user/update-profile with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.ok(service.userInfoService.updateProfile(getCurrentUser(), dto));
    }

    @Operation(summary = "thông tin cá nhân")
    @GetMapping("/personal")
    public ResponseEntity<Object> personal() {
        log.info("start api /api/v1/user/personal");
        return ResponseEntity.ok(service.userInfoService.personal(getCurrentUser()));
    }
}
