package com.ws.master_service.service;

import com.ws.master_service.dto.admin.user.info.UserDto;
import com.ws.master_service.utils.base.rest.CurrentUser;

public interface AdminUserInfoService {
    Object create(CurrentUser currentUser, UserDto dto);

    /**
     * chỉ update 1 số trường nhất định(xem class dto)
     */
    Object update(CurrentUser currentUser, UserDto dto);

    Object changeStatus(CurrentUser currentUser, String id);
}
