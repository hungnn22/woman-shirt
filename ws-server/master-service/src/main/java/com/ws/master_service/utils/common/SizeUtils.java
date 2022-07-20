package com.ws.master_service.utils.common;

import com.ws.master_service.utils.base.enum_dto.SizeDto;
import com.ws.master_service.utils.constants.enums.SizeEnum;

/**
 */
public class SizeUtils {
    public static SizeDto getSizeDto(String input) {
        var size = SizeEnum.valueOf(input);
        if (size == null) return new SizeDto();
        return SizeDto.builder()
                .code(size.name())
                .name(size.getName())
                .build();
    }
}
