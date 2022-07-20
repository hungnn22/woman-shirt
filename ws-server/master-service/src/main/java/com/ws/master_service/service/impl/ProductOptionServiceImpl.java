package com.ws.master_service.service.impl;

import com.ws.master_service.dto.customer.product.ColorResponse;
import com.ws.master_service.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.master_service.dto.customer.product.product_option.ProductOptionIdRes;
import com.ws.master_service.dto.customer.size.response.SizeResponse;
import com.ws.master_service.entity.ProductOptionEntity;
import com.ws.master_service.service.ProductOptionService;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOptionServiceImpl implements ProductOptionService {

    private final WsRepository repository;

    @Override
    public ResData<ProductOptionIdRes> findProductOptionId(ProductOptionIdReq req) {
        ProductOptionEntity productOption = repository.productOptionRepository.findBySizeAndColorId(req.getSizeId(),req.getColorId(),req.getProductId());
        if(productOption == null) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        ProductOptionIdRes res = ProductOptionIdRes.builder()
                .productOptionId(productOption.getId())
                .quantity(productOption.getQty())
                .price(productOption.getPrice())
                .build();
        return new ResData<>(res, WsCode.OK);
    }


    @Override
    public ResData<List<ColorResponse>> findColorNameBySize(String sizeId,String productId) {
        List<ColorResponse> color = repository.productOptionRepository.getListColorNameBySize(sizeId,productId);
        log.info("----------------" + color);
        return new ResData<>(color, WsCode.OK);
    }

    @Override
    public ResData<List<SizeResponse>> findSizeByProductId(String productId) {
        List<SizeResponse> size = repository.productOptionRepository.findListSizeByProductId(productId);
        return new ResData<>(size, WsCode.OK);
    }
}
