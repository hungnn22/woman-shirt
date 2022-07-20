package com.ws.master_service.service;

import com.ws.master_service.dto.customer.user.ProfileDto;
import com.ws.master_service.utils.base.rest.CurrentUser;

public interface UserInfoService {
    Object updateProfile(CurrentUser currentUser, ProfileDto dto);

    Object personal(CurrentUser currentUser);
}
