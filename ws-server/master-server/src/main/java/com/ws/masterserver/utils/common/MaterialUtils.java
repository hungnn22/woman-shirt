package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.enums.MaterialDto;
import com.ws.masterserver.utils.constants.enums.MaterialEnum;

/**
 * @author hungnn22
 */
public class MaterialUtils {

    public static MaterialDto getMaterialDto(String input) {
        var material = MaterialEnum.valueOf(input);
        if (material == null) return new MaterialDto();
        return MaterialDto.builder()
                .code(material.name())
                .name(material.getName())
                .build();
    }
}
