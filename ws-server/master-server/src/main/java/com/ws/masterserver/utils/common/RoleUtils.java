package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.constants.enums.RoleEnum;

public class RoleUtils {
    public static RoleEnum getRoleFromStr(String value) {
        try {
            return RoleEnum.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
