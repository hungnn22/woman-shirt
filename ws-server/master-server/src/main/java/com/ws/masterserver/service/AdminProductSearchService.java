package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface AdminProductSearchService {
    Object search(CurrentUser currentUser, ProductReq req);

    Object detail(CurrentUser currentUser, String id);
}