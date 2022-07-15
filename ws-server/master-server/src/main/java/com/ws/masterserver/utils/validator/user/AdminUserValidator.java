package com.ws.masterserver.utils.validator.user;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.utils.common.ValidatorUtils;

/**
 * @author myname
 */
public class AdminUserValidator {
    public static void validDto(UserDto dto) {
        ValidatorUtils.validNullOrEmpty("Họ", dto.getFirstName());
//        ValidatorUtils.validLength("Ho", dto.getFirstName(), );
    }
}
