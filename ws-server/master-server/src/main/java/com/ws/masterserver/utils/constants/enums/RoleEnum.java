package com.ws.masterserver.utils.constants.enums;

public enum RoleEnum {
    /**
     * Danh sách quyền
     */
    ROLE_ADMIN("Admin"),//chủ hệ thống
    ROLE_STAFF("Nhân viên"),//nhân viên
    ROLE_CUSTOMER("Khách hàng"),//khách hàng
    ROLE_GUEST(""),
    ;

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
