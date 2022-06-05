package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

public interface ProductCustomRepository {
    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req);
}
