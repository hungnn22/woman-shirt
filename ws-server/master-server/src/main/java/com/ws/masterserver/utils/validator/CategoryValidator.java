package com.ws.masterserver.utils.validator;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.utils.constants.WsConst;

public class CategoryValidator {
    private CategoryValidator() {}

    public static void validCreate(CategoryDto dto) {
        ValidateUtils.validBlank(WsConst.CategoryFields.NAME_VAL, dto.getName());
        ValidateUtils.validBlank(WsConst.CategoryFields.DES_VAL, dto.getDes());
        ValidateUtils.isValidRangeLength(WsConst.CategoryFields.NAME_VAL, dto.getName(), 4, 250);
        ValidateUtils.isValidRangeLength(WsConst.CategoryFields.DES_VAL, dto.getDes(), 10, 250);
    }
}
