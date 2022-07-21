package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType01;
import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteType02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue01;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue03;
import com.ws.masterserver.entity.DiscountCategoryEntity;
import com.ws.masterserver.entity.DiscountCustomerTypeEntity;
import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.entity.DiscountProductEntity;
import com.ws.masterserver.service.DiscountService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.AuthValidator;
import com.ws.masterserver.utils.validator.admin.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        List<String> des = new ArrayList<>();

        discount.setId(discountId);

        discount.setCode(payload.getCode().trim());
        des.add(payload.getCode());

        //type
        var type = DiscountTypeEnum.valueOf(payload.getType());
        discount.setType(type.name());
        this.saveTypeValueAndGetDesItem(payload, discount);

        /**
         * ap dung cho
         * */
        var applyType = ApplyTypeEnum.valueOf(payload.getApplyType());

        //5. applyType
        discount.setApplyType(applyType.name());
        this.saveApplyTypeValue(payload, discountId, applyType);

        //dieu kien ap dung
        var prerequisiteType = PrerequisiteTypeEnums.valueOf(payload.getPrerequisiteType());

        //6: prerequisiteType
        discount.setPrerequisiteType(prerequisiteType.name());
        this.savePrerequisiteValue(payload, discount, prerequisiteType);

        //nhom KH
        var customerType = DiscountCustomerTypeEnums.valueOf(payload.getCustomerType());

        //8: customerType
        discount.setCustomerType(customerType.name());
        this.saveDiscountCustomerType(payload, discountId, customerType);

        //gioi han su dung

        //9: hasUsageLimit
        discount.setHasUsageLimit(payload.getHasUsageLimit());
        //10: usageLimit
        discount.setUsageLimit(Long.valueOf(payload.getUsageLimit()));
        //11: oncePerCustomer
        discount.setOncePerCustomer(payload.getOncePerCustomer());

        var status = DiscountStatusEnums.PENDING;

        //thoi gian
        var startStr = payload.getStartDate() + " " + payload.getStartTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.F_DDMMYYYYHHMM);
        var start = LocalDateTime.parse(startStr, dateTimeFormatter);

        //12: startDate
        discount.setStartDate(start);
        if (start.isBefore(LocalDateTime.now())) {
            status = DiscountStatusEnums.ACTIVE;
        }
        if (payload.getHasEndsDate()) {
            var endStr = payload.getEndDate() + " " + payload.getEndTime();
            var end = LocalDateTime.parse(endStr, dateTimeFormatter);

            //13: endDate
            discount.setEndDate(end);
            if (end.isBefore(LocalDateTime.now())) {
                status = DiscountStatusEnums.DE_ACTIVE;
            }
        }

        //14: status
        discount.setStatus(status.name());

        //15: isApplyAcross
        discount.setIsApplyAcross(payload.getIsApplyAcross());

        //16: name
        discount.setDes("");

        return ResData.ok(discount.getId());
    }

    private String saveTypeValueAndGetDesItem(DiscountDto payload, DiscountEntity discount) {
        Object typeValues = payload.getTypeValue();
        discount.setTypeValue(JsonUtils.toJson(typeValues));
        var superDto = payload.getTypeValue();
        var result = "";
        if (superDto instanceof DiscountTypeValue01) {
            DiscountTypeValue01 dto = (DiscountTypeValue01) superDto;
            result = "Giảm " + dto.getPercentageValue() + "% ";
            if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
                result = "tối đa " + MoneyUtils.format(Long.valueOf(dto.getValueLimitAmount())) + " VND ";
            }
        } else if (superDto instanceof DiscountTypeValue02) {
            DiscountTypeValue02 dto = (DiscountTypeValue02) superDto;
            result = "Giảm " + MoneyUtils.format(Long.valueOf(dto.getAmountValue())) + " VND ";
        } else {
            DiscountTypeValue03 dto = (DiscountTypeValue03) superDto;
            result = "Miễn phí vận chuyện ";
            if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
                result += "tối đa " + MoneyUtils.format(Long.valueOf(dto.getValueLimitAmount())) + " VND ";
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaximumShippingRate())) {
                result += "với đơn hàng có phí vận chuyển <= " + MoneyUtils.format(Long.valueOf(dto.getMaximumShippingRate())) + "VND cho ";
            }
            var provinceSelectionEnums = ProvinceSelectionEnums.valueOf(dto.getProvinceSelection());
            switch (provinceSelectionEnums) {
                case ALL:
                    result += "toàn quốc ";
                    break;
                case SELECTION:
                    result += dto.getProvinceIds().size() + " khu vực ";
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    private void savePrerequisiteValue(DiscountDto payload, DiscountEntity discount, PrerequisiteTypeEnums prerequisiteType) {
        if (!PrerequisiteTypeEnums.NONE.equals(prerequisiteType)) {
            //7: prerequisiteValue
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
    }

    private void saveDiscountCustomerType(DiscountDto payload, String discountId, DiscountCustomerTypeEnums customerType) {
        if (!DiscountCustomerTypeEnums.ALL.equals(customerType)) {
            payload.getCustomerIds().forEach(o -> {
                repository.discountCustomerTypeRepository.save(DiscountCustomerTypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .discountId(discountId)
                        .customerTypeId(o)
                        .build());
            });
        }
    }

    private void saveApplyTypeValue(DiscountDto payload, String discountId, ApplyTypeEnum applyType) {
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
    }
}
