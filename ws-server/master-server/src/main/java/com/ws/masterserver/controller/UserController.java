package com.ws.masterserver.controller;

import com.ws.masterserver.dto.admin.user.UserReq;
import com.ws.masterserver.dto.customer.user.ChangeProfileDto;
import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends WsController {

    /**
     * API KH đăng ký tài khoản
     */
    @PostMapping("/customer/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.userService.registerCustomer(body));
    }

    /**
     * API KH đổi thông tin cá nhân
     * */
    @PutMapping("/customer/change-profile")
    public ResponseEntity<Object> changeProfile(@RequestBody ChangeProfileDto body) {
        return ResponseEntity.status(HttpStatus.OK).body(service.userService.changeCustomerProfile(getCurrentUser(), body));
    }

    @PostMapping("/admin/search")
    public ResponseEntity<Object> search4Admin(@RequestBody UserReq req) {
        Object o = new Object();
        return ResponseEntity.ok(service.userService.search4Admin(getCurrentUser(), req));
    }
}
