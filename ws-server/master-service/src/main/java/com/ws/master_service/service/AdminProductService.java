package com.ws.master_service.service;

import com.ws.master_service.dto.admin.product.create_update.ProductDto;
import com.ws.master_service.dto.customer.product.ProductReq;
import com.ws.master_service.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminProductService {
    Object search(CurrentUser currentUser, ProductReq req);

    Object detail(CurrentUser currentUser, String id);

    Object create(CurrentUser currentUser, ProductDto dto);
}
