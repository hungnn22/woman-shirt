package com.ws.masterserver.utils.validator.admin;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeDto;
import com.ws.masterserver.dto.admin.discount.type.DiscountTypeValue01;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.DiscountTypeEnum;
import com.ws.masterserver.utils.constants.field.DiscountFields;

/**
 * @author myname
 */
public class AdminDiscountValidator {


    public static void validCreate(DiscountDto payload) {
        ValidatorUtils.validNullOrEmpty(DiscountFields.CODE, payload.getCode());
        ValidatorUtils.validLength(DiscountFields.CODE, payload.getCode(), 4, 50);
        ValidatorUtils.validNullOrEmpty(DiscountFields.TYPE, payload.getType());
        var type = DiscountTypeEnum.valueOf(payload.getType());
        switch (type) {
            case PERCENT:
                validPercentType(payload.getTypeValues());
                break;

        }
    }

    private static void validPercentType(DiscountTypeDto typeValues) {
        if (typeValues instanceof DiscountTypeValue01) {
            DiscountTypeValue01 dto = (DiscountTypeValue01) typeValues;
            ValidatorUtils.validNullOrEmpty(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validOnlyNumber(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue());
            ValidatorUtils.validLongValueBetween(DiscountFields.AMOUNT_VALUE, dto.getPercentageValue(), 0L, 100L);
            if (!StringUtils.isNullOrEmpty(dto.getValueLimitAmount())) {
                ValidatorUtils.validOnlyNumber(DiscountFields.VALUE_LIMIT_AMOUNT, dto.getValueLimitAmount());
                ValidatorUtils.validLongValueMustBeMore(DiscountFields.VALUE_LIMIT_AMOUNT, dto.getValueLimitAmount(), 0L);
            }
        } else {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }

    }

}
