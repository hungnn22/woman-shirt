package com.ws.master_service.service;

import com.ws.master_service.dto.customer.product.ProductRelatedRes;
import com.ws.master_service.dto.customer.product.ProductReq;
import com.ws.master_service.dto.customer.product.ProductResponse;
import com.ws.master_service.dto.admin.product.ProductDetailResponse;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.ResData;

import java.util.List;

public interface ProductService {
    ResData<ProductDetailResponse> getProductDetail(String id);
//    PageData<ProductResponse> search(CurrentUser currentUser, ProductReq productReq);
    PageData<ProductResponse> search( ProductReq productReq);

    Object searchV2(com.ws.master_service.dto.customer.product.search.ProductReq req);

    ResData<List<ProductRelatedRes>> getRelatedProduct(String productId);
}
