package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController extends WsController {

    private final WsService service;

    @GetMapping("/admin")
    public ResponseEntity<Object> getNotification4Admin()  {
        return ResponseEntity.ok(service.notificationService.get4Admin(getCurrentUser()));
    }


}
