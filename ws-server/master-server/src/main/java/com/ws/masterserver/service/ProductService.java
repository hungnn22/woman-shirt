package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.admin.product.ProductDetailResponse;
import com.ws.masterserver.dto.admin.product.ProductDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;

public interface ProductService {
    ResData<String> create(CurrentUser currentUser, ProductDto dto);
    ResData<ProductDetailResponse> getProductDetail(String id);
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq productReq);
    PageData<ProductResponse> search( ProductReq productReq);

    PageData<ProductRes> search4Admin(CurrentUser currentUser, ProductReq req);
}
