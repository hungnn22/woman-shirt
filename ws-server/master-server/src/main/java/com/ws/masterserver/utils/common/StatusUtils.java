package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.constants.enums.StatusEnum;

public class StatusUtils {
    public static StatusEnum getFromStr(String value) {
        try {
            return StatusEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
