package com.ws.master_service.repository.custom;

import com.ws.master_service.dto.customer.product.ProductReq;
import com.ws.master_service.dto.customer.product.ProductResponse;
import com.ws.master_service.utils.base.rest.PageData;

public interface ProductCustomRepository {
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq req);
    PageData<ProductResponse> search(ProductReq req);

    Object searchV2(com.ws.master_service.dto.customer.product.search.ProductReq req);
}
