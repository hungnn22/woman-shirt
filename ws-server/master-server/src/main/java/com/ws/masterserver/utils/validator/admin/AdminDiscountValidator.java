package com.ws.masterserver.utils.validator.admin;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeDto;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue01;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue02;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue03;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.constants.field.DiscountFields;

import java.util.Collections;
import java.util.List;
/**
 * @author myname
 */
public class AdminDiscountValidator {

    public static void validCreate(DiscountDto payload) throws WsException {
        ValidatorUtils.validNullOrEmpty(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validLength(DiscountFields.CODE, payload.getCode(), 4, 50);

        //loai khuyen mai
        validDiscountType(payload);

        //dieu kien ap dung
        validPrerequisite(payload.getPrerequisiteType(), payload.getPrerequisiteTypeValue());

        //nhoimm KH
        validCustomerType(payload.getCustomerType(), payload.getCustomerTypeIds());

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

    private static void validCustomerType(String customerType, List<String> customerTypeIds) {
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

    private static void validPrerequisite(String prerequisiteType, String prerequisiteTypeValue) {
        var preType = PrerequisiteTypeEnums.valueOf(prerequisiteType);
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

    private static void validDiscountType(DiscountDto payload) {
        var type = DiscountTypeEnums.valueOf(payload.getType());
        switch (type) {
            case PERCENT:
                validPercentType(payload.getTypeValues());
                break;
            case PRICE:
                validPriceType(payload.getTypeValues());
                break;
            case SHIP:
                validShipType(payload.getTypeValues());
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

    private static void validShipType(DiscountTypeDto typeValues) {
        if (typeValues instanceof DiscountTypeValue03) {
            DiscountTypeValue03 dto = (DiscountTypeValue03) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, dto.getValueLimitAmount());
            ValidatorUtils.validLongValueBetween(DiscountFields.VALUE_LIMIT_AMOUNT_SHIP, dto.getValueLimitAmount(), 0L, 999999999999999L);
            var selectionType = ProvinceSelectionEnums.valueOf(dto.getProvinceSelection());
            if (null == selectionType) {
                throw new WsException(WsCode.INTERNAL_SERVER);
            }
            if (ProvinceSelectionEnums.SELECTION.equals(selectionType)) {
                ValidatorUtils.validNullOrEmptyList(DiscountFields.ADDRESS_LIST, Collections.singletonList(dto.getProvinceIds()));
            }
            if (dto.getHasMaximumShippingRate()) {
                ValidatorUtils.validNullOrEmpty(DiscountFields.MAXIMUM_SHIPPING_RATE, dto.getMaximumShippingRate());
                ValidatorUtils.validLongValueBetween(DiscountFields.MAXIMUM_SHIPPING_RATE, dto.getMaximumShippingRate(), 0L, 999999999999999L);
            }
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPriceType(DiscountTypeDto typeValues) {
        if (typeValues instanceof DiscountTypeValue02) {
            DiscountTypeValue02 dto = (DiscountTypeValue02) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getAmountValue());
            ValidatorUtils.validLongValueMustBeMore(DiscountFields.AMOUNT_VALUE, dto.getAmountValue(), 0L);
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    private static void validPercentType(DiscountTypeDto typeValues) {
        if (typeValues instanceof DiscountTypeValue01) {
            DiscountTypeValue01 dto = (DiscountTypeValue01) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validOnlyNumber(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validLongValueBetween(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue(), 0L, 100L);
            if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
                ValidatorUtils.validOnlyNumber(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount());
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.VALUE_LIMIT_AMOUNT_PERCENT, dto.getValueLimitAmount(), 0L);
            }
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }

    }

}
