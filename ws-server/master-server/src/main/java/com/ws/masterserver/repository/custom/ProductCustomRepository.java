package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

public interface ProductCustomRepository {
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req);
    PageData<ProductResponse> search(ProductReq req);

    PageData<ProductRes> search4Admin(ProductReq req);

    Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req);
}
