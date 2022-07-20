package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum DiscountCustomerTypeEnums {
    ALL("Tất cả"),
    GROUP("Nhóm khách hàng"),
    CUSTOMER("Khách hàng");

    private String name;

    DiscountCustomerTypeEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
