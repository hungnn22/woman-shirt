package com.ws.masterserver.utils.validator;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.base.rest.CurrentUser;

import java.util.Arrays;

/**
 * Luôn dùng class này trong các api phân quyền
 */
public class AuthValidator {
    private AuthValidator() {
    }

    public static void checkAdmin(CurrentUser user) {
        if (!user.getRole().equals(RoleEnum.ROLE_ADMIN)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkStaff(CurrentUser user) {
        if (!user.getRole().equals(RoleEnum.ROLE_STAFF)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkCustomer(CurrentUser user) {
        if (!user.getRole().equals(RoleEnum.ROLE_CUSTOMER)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum roleAvailable) {
        if (!currentUser.getRole().equals(roleAvailable)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum... roleAvailableList) {
        if (!Arrays.stream(roleAvailableList).anyMatch(role -> role.equals(currentUser.getRole())))
            throw new WsException(WsCode.FORBIDDEN);

    }
}
