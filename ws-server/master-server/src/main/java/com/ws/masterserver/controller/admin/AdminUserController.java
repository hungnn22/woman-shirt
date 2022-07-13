package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController extends WsController {

    @Operation(summary = "API search product cho admin")
    @PostMapping("/search")
    public ResponseEntity<Object> search4Admin(@RequestBody UserReq req) {
        log.info("start api /api/v1/admin/user/search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.adminUserSearchService.search(getCurrentUser(), req));
    }

}
