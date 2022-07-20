package com.ws.master_service.utils.common;

import com.ws.master_service.dto.admin.order.search.StatusDto;
import com.ws.master_service.utils.constants.enums.StatusEnum;

public class StatusUtils {
    public static StatusEnum getFromStr(String value) {
        try {
            return StatusEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static StatusDto getStatusDto(StatusEnum statusEnum) {
        return StatusDto.builder()
                .code(statusEnum.name())
                .name(statusEnum.getName())
                .build();
    }
}
