package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.service.AdminProductService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, ProductReq req) {
        List<ProductRes.ProductSubRes> productSubRes = repository.productRepository.searchProductSubRes4Admin(req);
        return null;
    }
}
