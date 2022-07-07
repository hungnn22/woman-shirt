package com.ws.masterserver.controller.admin;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/ws")
public class AdminWebSocketController extends WsController {

    @GetMapping("/test")
    public Object test(String mess) {
        return service.webSocketService.test(mess);
    }
}
