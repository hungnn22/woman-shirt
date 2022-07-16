package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminUserInfoService {
    Object create(CurrentUser currentUser, UserDto dto);

}
