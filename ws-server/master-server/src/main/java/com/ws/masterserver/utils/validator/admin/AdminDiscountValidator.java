package com.ws.masterserver.utils.validator.admin;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue01;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue03;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.constants.field.DiscountFields;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author myname
 */
@Slf4j
public class AdminDiscountValidator {
    public static void validCreateDtoData(DiscountDto payload) throws WsException {
        ValidatorUtils.validNullOrEmpty(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validNotContainSpace(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validLength(DiscountFields.CODE, payload.getCode(), 4, 50);
        ValidatorUtils.validOnlyCharacterAndNumber(DiscountFields.CODE, payload.getCode());

        //loai khuyen mai
        ValidatorUtils.validNullOrEmpty(DiscountFields.TYPE, payload.getType());
        validDiscountTypeValue(payload);

        //dieu kien ap dung
        ValidatorUtils.validNullOrEmpty(DiscountFields.PREREQUISITE, payload.getPrerequisiteType());
        validPrerequisiteValue(payload.getPrerequisiteType(), payload.getPrerequisiteTypeValue());

        //nhoimm KH
        ValidatorUtils.validNullOrEmpty(DiscountFields.APPLY_CUSTOMER_TYPE, payload.getCustomerType());
        validCustomerTypeValue(payload.getCustomerType(), payload.getCustomerIds());

        //gioi han su dung
        if (payload.getHasUsageLimit()) {
            ValidatorUtils.validNullOrEmpty(DiscountFields.USAGE_LIMIT, payload.getUsageLimit());
            ValidatorUtils.validLongValueMustBeMore(DiscountFields.USAGE_LIMIT, payload.getUsageLimit(), 1L);
        }

        //thoi gian
        validTime(payload.getStartDate(), payload.getStartTime(), payload.getHasEndsDate(), payload.getEndDate(), payload.getEndTime());
    }

    private static void validTime(String startDate, String startTime, Boolean hasEndsDate, String endDate, String endTime) {
        ValidatorUtils.validNullOrEmpty(DiscountFields.START_DATE, startDate);
        ValidatorUtils.validDateFormat(DiscountFields.START_DATE, startDate);
        ValidatorUtils.validNullOrEmpty(DiscountFields.START_TIME, startTime);
        ValidatorUtils.validTimeFormat(DiscountFields.START_TIME, startTime);
        if (hasEndsDate) {
            ValidatorUtils.validNullOrEmpty(DiscountFields.END_DATE, endDate);
            ValidatorUtils.validDateFormat(DiscountFields.END_DATE, endDate);
            ValidatorUtils.validNullOrEmpty(DiscountFields.END_TIME, endTime);
            ValidatorUtils.validTimeFormat(DiscountFields.END_TIME, endTime);
            ValidatorUtils.validDateMoreDateAndTimeMoreTime(
                    DiscountFields.START_DATE, startDate,
                    DiscountFields.END_DATE, endDate,
                    DiscountFields.START_TIME, startTime,
                    DiscountFields.END_TIME, endTime);
        }
    }

    private static void validCustomerTypeValue(String customerType, List<String> customerTypeIds) {
        var type = DiscountCustomerTypeEnums.valueOf(customerType);
        if (null == type) {
            throw new WsException(WsCode.MUST_SELECT_CUSTOMER_TYPE);
        }
        switch (type) {
            case GROUP:
                ValidatorUtils.validNullOrEmptyList(DiscountFields.GROUP_CUSTOMER, Collections.singletonList(customerTypeIds));
                break;
            case CUSTOMER:
                ValidatorUtils.validNullOrEmptyList(DiscountFields.CUSTOMER, Collections.singletonList(customerTypeIds));
                break;
        }
    }

    private static void validPrerequisiteValue(String prerequisiteType, String prerequisiteTypeValue) {
        var preType = DiscountPrerequisiteTypeEnums.valueOf(prerequisiteType);
        if (null == preType) {
            throw new WsException(WsCode.MUST_SELECT_PREREQUISITE);
        }
        switch (preType) {
            case MIN_TOTAL:
                ValidatorUtils.validNullOrEmpty(DiscountFields.MINIMUM_SUB_TOTAL_PRICE, prerequisiteTypeValue);
                ValidatorUtils.validLongValueBetween(DiscountFields.MINIMUM_SUB_TOTAL_PRICE, prerequisiteTypeValue, 0L, 999999999999999L);
                break;
            case MIN_QTY:
                ValidatorUtils.validNullOrEmpty(DiscountFields.MINIMUM_QTY, prerequisiteTypeValue);
                ValidatorUtils.validLongValueBetween(DiscountFields.MINIMUM_QTY, prerequisiteTypeValue, 0L, 100L);
                break;
            default:
                break;
        }
    }

    private static void validDiscountTypeValue(DiscountDto payload) {
        var type = DiscountTypeEnums.valueOf(payload.getType());
        if (type == null) {
            throw new WsException(WsCode.MUST_SELECT_DISCOUNT_TYPE);
        }
        switch (type) {
            case PERCENT:
                validPercentType(payload.getTypeValue());
                break;
            case PRICE:
                validPriceType(payload.getTypeValue());
                break;
            case SHIP:
                validShipType(payload.getTypeValue());
                break;
            default:
                throw new WsException(WsCode.MUST_SELECT_DISCOUNT_TYPE);
        }

        if (!DiscountTypeEnums.SHIP.equals(type)) {
            var applyType = ApplyTypeEnums.valueOf(payload.getApplyType());
            if (null == applyType) {
                throw new WsException(WsCode.MUST_SELECT_APPLY_TYPE);
            }
            switch (applyType) {
                case CATEGORY:
                    ValidatorUtils.validNullOrEmptyList(DiscountFields.CATEGORY_LIST, Collections.singletonList(payload.getApplyTypeIds()));
                    break;
                case PRODUCT:
                    ValidatorUtils.validNullOrEmptyList(DiscountFields.PRODUCT_LIST, Collections.singletonList(payload.getApplyTypeIds()));
                    break;
                case ALL_PRODUCT:
                default:
                    break;
            }
        }
    }

    private static void validShipType(Object typeValues) {
        if (typeValues instanceof DiscountTypeValue03) {
            DiscountTypeValue03 dto = (DiscountTypeValue03) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, dto.getValueLimitAmount());
            ValidatorUtils.validLongValueBetween(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, dto.getValueLimitAmount(), 0L, 999999999999999L);
            var selectionType = DiscountProvinceSelectionEnums.valueOf(dto.getProvinceSelection());
            if (null == selectionType) {
                throw new WsException(WsCode.INTERNAL_SERVER);
            }
            if (DiscountProvinceSelectionEnums.SELECTION.equals(selectionType)) {
                ValidatorUtils.validNullOrEmptyList(DiscountFields.ADDRESS_LIST, Collections.singletonList(dto.getProvinceIds()));
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaximumShippingRate())) {
                ValidatorUtils.validLongValueBetween(DiscountFields.MAXIMUM_SHIPPING_RATE, dto.getMaximumShippingRate(), 0L, 999999999999999L);
            }
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPriceType(Object typeValues) {
        if (typeValues instanceof DiscountTypeValue02) {
            DiscountTypeValue02 dto = (DiscountTypeValue02) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getAmountValue());
            ValidatorUtils.validLongValueMustBeMore(DiscountFields.AMOUNT_VALUE, dto.getAmountValue(), 0L);
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPercentType(Object typeValues) {
        try {
            DiscountTypeValue01 dto = (DiscountTypeValue01) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validOnlyNumber(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validLongValueBetween(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue(), 0L, 100L);
            if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
                ValidatorUtils.validOnlyNumber(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount());
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount(), 0L);
            }
        } catch (Exception e) {
            log.error("validPercentType() error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    public static void validCreateDtoConstraint(DiscountDto payload, WsRepository repository) {
        if (repository.discountRepository.existsByCodeIgnoreCaseAndStatusNot(payload.getCode().trim(), DiscountStatusEnums.DE_ACTIVE.name())) {
            throw new WsException(WsCode.DISCOUNT_CODE_EXISTS);
        }
    }
}
