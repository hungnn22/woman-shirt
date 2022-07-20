package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.service.DiscountService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.admin.AdminDiscountValidator;
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
