package com.ws.masterserver.utils.validator;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Luôn dùng class này trong các api phân quyền
 */
@Slf4j
public class AuthValidator {
    private AuthValidator() {
    }

    public static void checkAdmin(CurrentUser currentUser) {
        log.info("AuthValidator checkAdmin: {}", JsonUtils.toJson(currentUser));
        if (!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkStaff(CurrentUser currentUser) {
        log.info("AuthValidator checkStaff: {}", JsonUtils.toJson(currentUser));
        if (!currentUser.getRole().equals(RoleEnum.ROLE_STAFF)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkCustomer(CurrentUser currentUser) {
        log.info("AuthValidator checkCustomer: {}", JsonUtils.toJson(currentUser));
        if (!currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum roleAvailable) {
        log.info("AuthValidator checkRole: {}, roleAvailable: {}", JsonUtils.toJson(currentUser), JsonUtils.toJson(roleAvailable));
        if (!currentUser.getRole().equals(roleAvailable)) {
            throw new WsException(WsCode.FORBIDDEN);
        }
    }

    public static void checkRole(CurrentUser currentUser, RoleEnum... roleAvailableList) {
        log.info("AuthValidator checkRole: {}, roleAvailableList: {}", JsonUtils.toJson(currentUser), JsonUtils.toJson(roleAvailableList));
        if (!Arrays.stream(roleAvailableList).anyMatch(role -> role.equals(currentUser.getRole())))
            throw new WsException(WsCode.FORBIDDEN);

    }
}
