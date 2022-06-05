package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.enums.ColorDto;
import com.ws.masterserver.utils.constants.enums.ColorEnum;
import com.ws.masterserver.utils.constants.enums.StatusEnum;

/**
 * @author hungnn22
 */
public class ColorUtils {

    public static String getString(String statusEnum) {

        return "";
    }

    public static ColorDto getColorDto(String input) {
        var color = ColorEnum.valueOf(input);
        if (color == null) return new ColorDto();
        return ColorDto.builder()
                .code(input)
                .viName(color.getViName())
                .enName(color.getEnName())
                .hex(color.getHex())
                .build();
    }
}
