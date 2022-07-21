package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType01;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType02;
import com.ws.masterserver.entity.DiscountCategoryEntity;
import com.ws.masterserver.entity.DiscountCustomerTypeEntity;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.DiscountProductEntity;
import com.ws.masterserver.service.DiscountService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.admin.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {
    private final WsRepository repository;

    @Override
    @Transactional
    public Object create(CurrentUser currentUser, DiscountDto payload) {
        AuthValidator.checkAdmin(currentUser);
        AdminDiscountValidator.validCreate(payload);
        var discount = new DiscountEntity();
        var discountId = UidUtils.generateUid();
        discount.setId(discountId);
        discount.setCode(payload.getCode().trim());

        //type
        var type = DiscountTypeEnum.valueOf(payload.getType());
        discount.setType(type.name());
        Object typeValues = payload.getTypeValues();
        discount.setTypeValue(JsonUtils.toJson(typeValues));

        //ap dung cho
        var applyType = ApplyTypeEnum.valueOf(payload.getApplyType());
        discount.setApplyType(applyType.name());
        payload.getApplyTypeIds().forEach(o -> {
            switch (applyType) {
                case CATEGORY:
                    repository.discountCategoryRepository.save(DiscountCategoryEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .categoryId(o)
                            .build());
                    break;
                case PRODUCT:
                    repository.discountProductRepository.save(DiscountProductEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .productId(o)
                            .build());
                    break;
                default:
                    break;
            }
        });

        //dieu kien ap dung
        var prerequisiteType = PrerequisiteTypeEnums.valueOf(payload.getPrerequisiteType());
        discount.setPrerequisiteType(prerequisiteType.name());
        if (!PrerequisiteTypeEnums.NONE.equals(prerequisiteType)) {
            switch (prerequisiteType) {
                case MIN_QTY:
                    discount.setPrerequisiteValue(JsonUtils.toJson(PrerequisiteType01.builder()
                            .minimumSaleTotalPrice(Long.valueOf(payload.getPrerequisiteTypeValue()))
                            .build()));
                    break;
                case MIN_TOTAL:
                    discount.setPrerequisiteValue(JsonUtils.toJson(PrerequisiteType02.builder()
                            .minimumQuantity(Long.valueOf(payload.getPrerequisiteTypeValue()))
                            .build()));
                    break;
                default:
                    break;
            }
        }

        //nhom KH
        var customerType = DiscountCustomerTypeEnums.valueOf(payload.getCustomerType());
        discount.setCustomerType(customerType.name());
        if (!DiscountCustomerTypeEnums.ALL.equals(customerType)) {
            payload.getCustomerTypeIds().forEach(o -> {
                repository.discountCustomerTypeRepository.save(DiscountCustomerTypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .discountId(discountId)
                        .customerTypeId(o)
                        .build());
            });
        }

        //gioi han su dung
        discount.setHasUsageLimit(payload.getHasUsageLimit());
        discount.setUsageLimit(Long.valueOf(payload.getUsageLimit()));
        discount.setOncePerCustomer(payload.getOncePerCustomer());

        var status = DiscountStatusEnums.PENDING;

        //thoi gian
        var startStr = payload.getStartDate() + " " + payload.getStartTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.F_DDMMYYYYHHMM);
        var start = LocalDateTime.parse(startStr, dateTimeFormatter);
        discount.setStartDate(start);
        if (payload.getHasEndsDate()) {
            var endStr = payload.getEndDate() + " " + payload.getEndTime();
            var end = LocalDateTime.parse(endStr, dateTimeFormatter);
            discount.setEndDate(end);
        }

        return ResData.ok(discount.getId());
    }
}
