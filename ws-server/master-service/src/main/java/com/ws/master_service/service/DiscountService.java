package com.ws.master_service.service;

import com.ws.master_service.dto.admin.discount.create.DiscountDto;
import com.ws.master_service.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface DiscountService {
    Object create(CurrentUser currentUser, DiscountDto payload);
}
