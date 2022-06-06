package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.enum_dto.SizeDto;
import com.ws.masterserver.utils.constants.enums.SizeEnum;

/**
 * @author hungnn22
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
