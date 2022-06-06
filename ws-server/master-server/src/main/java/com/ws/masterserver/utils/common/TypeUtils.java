package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.enum_dto.TypeDto;
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
                .name(type.getViName())
                .build();
    }
}
