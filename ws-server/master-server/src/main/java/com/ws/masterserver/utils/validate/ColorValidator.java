package com.ws.masterserver.utils.validate;

import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.constants.WsConst;

public class ColorValidator {

    private ColorValidator(){

    }

    public static void validCreate(ColorDto dto){
        ValidateUtils.validBlank(WsConst.ColorFields.NAME_VAL, dto.getName());
        ValidateUtils.validBlank(WsConst.ColorFields.HEX_VAL, dto.getHex());
        ValidateUtils.isValidRangeLength(WsConst.ColorFields.NAME_VAL, dto.getName(), 4, 250);
        ValidateUtils.isValidRangeLength(WsConst.ColorFields.HEX_VAL, dto.getHex(), 4 ,250);
    }
}
