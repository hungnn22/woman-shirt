package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class RoleController extends WsController {
}
