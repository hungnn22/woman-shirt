package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.dto.admin.discount.search.DiscountResponse;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.service.AdminDiscountService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.admin.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDiscountServiceImpl implements AdminDiscountService {
    private final WsRepository repository;

    @Override
    public Object create(CurrentUser currentUser, DiscountDto payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminDiscountValidator.validCreate(payload);
        return null;
    }

    @Override
    public Object search(CurrentUser currentUser, DiscountRequest payload) {
        try {
            log.info("AdminDiscountServiceImpl search() with payload: {}", JsonUtils.toJson(payload));
            AuthValidator.checkAdmin(currentUser);
            var pageable = PageableUtils.getPageable(payload.getPageReq());
            if (StringUtils.isNullOrEmpty(payload.getTextSearch())) {
                payload.setTextSearch("");
            }
            payload.setTextSearch("%" + payload.getTextSearch()
                                               .toUpperCase(Locale.ROOT)
                                               .trim() + "%");
            if (!StringUtils.isNullOrEmpty(payload.getStatus())) {
                DiscountStatusEnums status = DiscountStatusEnums.valueOf(payload.getStatus());
                if (status == null) {
                    throw new WsException(WsCode.INTERNAL_SERVER);
                }
            }
            Page<DiscountEntity> discountEntityPage = repository.discountRepository
                    .search(payload.getTextSearch(), payload.getStatus(), pageable);
            if (discountEntityPage.isEmpty()) {
                return PageData.setEmpty(payload.getPageReq());
            }
            return PageData.setResult(discountEntityPage.getContent()
                                                        .stream()
                                                        .map(o -> DiscountResponse.builder()
                                                                                  .id(o.getId())
                                                                                  .code(o.getCode())
                                                                                  .des(ListUtils.convert4Menu(o.getDes(), "\\|"))
                                                                                  .status(o.getStatus())
                                                                                  .statusValue(DiscountStatusEnums.valueOf(o.getStatus())
                                                                                                                  .getName())
                                                                                  .start(DateUtils.toStr(o.getStartDate(), DateUtils.F_DDMMYYYYHHMM))
                                                                                  .end(o.getEndDate() == null ? "-" : DateUtils.toStr(o.getEndDate(), DateUtils.F_DDMMYYYYHHMM))
                                                                                  .used(this.getUsedNumber(o.getHasUsageLimit(), o.getUsageLimit(), repository.discountRepository.findUsedNumber(o.getId())))
                                                                                  .build())
                                                        .collect(Collectors.toList()),
                    discountEntityPage.getNumber(),
                    discountEntityPage.getSize(),
                    discountEntityPage.getTotalElements());
        } catch (Exception e) {
            log.error("AdminDiscountServiceImpl search() error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private String getUsedNumber(Boolean hasUsageLimit, Long usageLimit, Long usedNumber) {
        if (!hasUsageLimit && usageLimit == null) {
            return usedNumber.toString();
        }
        return usedNumber + "/" + usageLimit;
    }
}
