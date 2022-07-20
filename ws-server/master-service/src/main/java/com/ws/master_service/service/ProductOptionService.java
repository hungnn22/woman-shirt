package com.ws.master_service.service;

import com.ws.master_service.dto.customer.product.ColorResponse;
import com.ws.master_service.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.master_service.dto.customer.product.product_option.ProductOptionIdRes;
import com.ws.master_service.dto.customer.size.response.SizeResponse;
import com.ws.master_service.utils.base.rest.ResData;

import java.util.List;

public interface ProductOptionService {
    ResData<ProductOptionIdRes> findProductOptionId(ProductOptionIdReq req);
    ResData<List<ColorResponse>> findColorNameBySize(String sizeId,String productId);
    ResData<List<SizeResponse>> findSizeByProductId(String productId);
}
