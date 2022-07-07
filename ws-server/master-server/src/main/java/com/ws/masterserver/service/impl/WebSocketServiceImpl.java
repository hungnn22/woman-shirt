package com.ws.masterserver.service.impl;

import com.ws.masterserver.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate ws;

    @Override
    public Object test(String mess) {
        log.info("WebSocketServiceImpl test start with mess: {}", mess);
        try {
            ws.convertAndSend("/topic/admin", mess);
        } catch (Exception e) {
            log.error("WebSocketServiceImpl test error: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
