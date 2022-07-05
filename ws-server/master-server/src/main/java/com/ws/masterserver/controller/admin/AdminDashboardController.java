package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminDashboardController extends WsController {

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Object> dashboard() {
        return ResponseEntity.ok(service.dashboardService.dashboard(getCurrentUser()));
    }

}
