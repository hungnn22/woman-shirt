package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.enums.TypeDto;
import com.ws.masterserver.utils.constants.enums.TypeEnum;

/**
 * @author hungnn22
 */
public class TypeUtils {

    public static TypeDto getTypeDto(String input) {
        var type = TypeEnum.valueOf(input);
        if (type == null) return new TypeDto();
        return TypeDto.builder()
                .code(type.name())
                .code(String.valueOf(type.getTypeCode()))
                .viName(type.getViName())
                .enName(type.getEnName())
                .build();
    }
}
