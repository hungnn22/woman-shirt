package com.ws.masterserver.utils.validate;

import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
import com.ws.masterserver.utils.constants.WsConst;

public class MaterialValidator {

    public MaterialValidator(){

    }

    public static void validCreate(MaterialDto dto){
        ValidateUtils.validBlank(WsConst.MaterialFields.NAME_VAL, dto.getName());
        ValidateUtils.validBlank((WsConst.MaterialFields.DES_VAL), dto.getDes());
        ValidateUtils.isValidRangeLength(WsConst.MaterialFields.NAME_VAL, dto.getName(), 4, 250);
        ValidateUtils.isValidRangeLength(WsConst.MaterialFields.DES_VAL, dto.getDes(), 4, 250);
    }
}
