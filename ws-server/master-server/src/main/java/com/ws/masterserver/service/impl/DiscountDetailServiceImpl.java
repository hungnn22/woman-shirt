package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.dto.admin.discount.search.DiscountResponse;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.service.DiscountDetailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import com.ws.masterserver.utils.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountDetailServiceImpl implements DiscountDetailService {
    private final WsRepository repository;


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
                                    .used(this.getUsageNumber(o.getHasUsageLimit(), o.getUsageLimit(), repository.discountRepository.findUsedNumber(o.getId())))
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

    private String getUsageNumber(Boolean hasUsageLimit, Long usageLimit, Long usedNumber) {
        if (!hasUsageLimit && usageLimit == null) {
            return usedNumber.toString();
        }
        return usedNumber + "/" + usageLimit;
    }
}
