package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class NotificationController extends WsController {

    private final WsService service;

    @GetMapping("/admin/notification")
    public ResponseEntity<Object> getNotification4Admin()  {
        return ResponseEntity.ok(service.notificationService.get4Admin(getCurrentUser()));
    }

    @PostMapping("/admin/notification/read")
    public ResponseEntity<Object> readNotification4Admin(@RequestBody List<String> dto) {
        return ResponseEntity.ok(service.notificationService.read4admin(getCurrentUser(), dto));
    }

    @GetMapping("/admin/notification/search")
    public ResponseEntity<Object> search(@RequestParam(required = false, defaultValue = "1") Integer pageSize) {
        return ResponseEntity.ok(service.notificationService.search4Admin(getCurrentUser(), pageSize));
    }

    @GetMapping("/admin/notification/read-all")
    public ResponseEntity<Object> readAll() {
        return ResponseEntity.ok(service.notificationService.readAll4Admin(getCurrentUser()));
    }


}
