package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController extends WsController {

    @GetMapping("/report")
    public ResponseEntity<Object> dashboard() {
        return ResponseEntity.ok(service.dashboardService.getReport(getCurrentUser()));
    }

    @GetMapping("/category-revenue")
    public ResponseEntity<Object> categoryRevenue() {
        return ResponseEntity.ok(service.dashboardService.getCategoryRevenue(getCurrentUser()));
    }

    @GetMapping("/week-revenue")
    public ResponseEntity<Object> weekRevenue() {
        return ResponseEntity.ok(service.dashboardService.getWeekRevenue(getCurrentUser()));
    }

}
