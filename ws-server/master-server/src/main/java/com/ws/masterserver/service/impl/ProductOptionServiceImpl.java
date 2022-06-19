package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.masterserver.entity.ColorEntity;
import com.ws.masterserver.service.ProductOptionService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOptionServiceImpl implements ProductOptionService {

    private final WsRepository repository;

    @Override
    public ResData<String> findProductOptionId(ProductOptionIdReq req) {
        String productOptionId = repository.productOptionRepository.findBySizeAndColorId(req.getSize(), req.getColorId());
        return new ResData<>(productOptionId, WsCode.OK);
    }

    @Override
    public ResData<List<ColorResponse>> findColorNameBySize(SizeEnum size) {
        List<ColorResponse> color = repository.productOptionRepository.getListColorNameBySize(size);

        return new ResData<>(color, WsCode.OK);
    }
}
