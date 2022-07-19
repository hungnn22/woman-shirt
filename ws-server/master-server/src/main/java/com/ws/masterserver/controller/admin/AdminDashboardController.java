package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController extends WsController {

    @Operation(summary = "bao cao so lieu nhanh: doanh thu hom nay, so nguoi dung moi...")
    @GetMapping("/report")
    public ResponseEntity<Object> dashboard() {
        return ResponseEntity.ok(service.dashboardService.getReport(getCurrentUser()));
    }

    @Operation(summary = "doanh thu theo danh muc san pham")
    @GetMapping("/category-revenue")
    public ResponseEntity<Object> categoryRevenue() {
        return ResponseEntity.ok(service.dashboardService.getCategoryRevenue(getCurrentUser()));
    }

    @Operation(summary = "doanh thu tuan truoc va tuan nay")
    @GetMapping("/week-revenue")
    public ResponseEntity<Object> weekRevenue() {
        return ResponseEntity.ok(service.dashboardService.getWeekRevenue(getCurrentUser()));
    }

}
