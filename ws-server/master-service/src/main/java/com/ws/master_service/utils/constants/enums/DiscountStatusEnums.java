package com.ws.master_service.utils.constants.enums;

/**
 * @author myname
 */
public enum DiscountStatusEnums {
    PENDING("Chưa áp dụng"),
    ACTIVE("Đang áp dụng"),
    DE_ACTIVE("Ngừng áp dụng");

    private String name;

    DiscountStatusEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
