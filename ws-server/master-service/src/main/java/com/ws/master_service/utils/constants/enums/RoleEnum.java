package com.ws.master_service.utils.constants.enums;

public enum RoleEnum {
    /**
     * Danh sách quyền
     */
    ROLE_ADMIN("Admin"),//chủ hệ thống
    ROLE_STAFF("Nhân viên"),//nhân viên
    ROLE_CUSTOMER("Khách hàng"),//khách hàng
    ROLE_GUEST(""),
    ;

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
