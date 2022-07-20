package com.ws.master_service.service;

import com.ws.master_service.dto.admin.user.search.UserReq;
import com.ws.master_service.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminUserService {
    Object search(CurrentUser currentUser, UserReq req);
    Object detail(CurrentUser currentUser, String id);

}
