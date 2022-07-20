package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.report.RevenueReq;
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
@RequestMapping("/api/v1/admin/report")
@RequiredArgsConstructor
@Slf4j
public class AdminReportController extends WsController {

    @PostMapping("/revenue")
    @Operation(summary = "báo cáo doanh thu")
    public ResponseEntity<Object> revenue(@RequestBody RevenueReq req) {
        log.info("start api POST: /api/v1/admin/report/revenue with payload: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.adminReportService.revenue(getCurrentUser(), req));
    }

}
