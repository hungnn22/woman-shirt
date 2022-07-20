package com.ws.master_service.utils.common;

import com.ws.master_service.utils.base.enum_dto.TypeDto;
import com.ws.master_service.utils.constants.enums.TypeEnum;

/**
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
