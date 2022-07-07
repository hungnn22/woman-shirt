package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.enums.SizeEnum;

import java.util.List;

public interface ProductOptionService {
    ResData<String> findProductOptionId(ProductOptionIdReq req);
    ResData<List<ColorResponse>> findColorNameBySize(SizeEnum size);
}
