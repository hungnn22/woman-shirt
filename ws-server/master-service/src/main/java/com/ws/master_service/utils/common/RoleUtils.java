package com.ws.master_service.utils.common;

import com.ws.master_service.dto.admin.order.search.RoleDto;
import com.ws.master_service.utils.constants.enums.RoleEnum;

public class RoleUtils {
    public static RoleDto getRoleDto(String value) {
        try {
            var role = RoleEnum.valueOf(value);
            return RoleDto.builder()
                    .code(role.name())
                    .name(role.getName())
                    .build();
        } catch (Exception e) {
            return new RoleDto();
        }
    }

    public static String getRoleNameFromCode(String code) {
        try {
            var role = RoleEnum.valueOf(code);
            return role.getName();
        } catch (Exception e) {
            return "";
        }
    }
}
