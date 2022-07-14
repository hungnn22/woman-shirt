package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.dto.admin.user.search.UserReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminUserService {
    Object search(CurrentUser currentUser, UserReq req);

    Object detail(CurrentUser currentUser, String id);

    Object create(CurrentUser currentUser, UserDto dto);
}
