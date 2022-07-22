package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface DiscountDetailService {
    Object search(CurrentUser currentUser, DiscountRequest payload);
}
