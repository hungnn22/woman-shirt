package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.discount.create.DiscountDto;
import com.ws.master_service.service.DiscountService;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.validator.AuthValidator;
import com.ws.master_service.utils.validator.admin.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {
    private final WsRepository repository;

    @Override
    public Object create(CurrentUser currentUser, DiscountDto payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminDiscountValidator.validCreate(payload);
        return null;
    }
}
