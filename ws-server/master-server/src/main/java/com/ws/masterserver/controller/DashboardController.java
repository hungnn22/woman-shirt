package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DashboardController extends WsController {

    private final WsService service;

    @GetMapping("/dashboard")
    public ResponseEntity<Object> dashboard() {
        return ResponseEntity.ok(service.dashboardService.dashboard(getCurrentUser()));
    }

}
