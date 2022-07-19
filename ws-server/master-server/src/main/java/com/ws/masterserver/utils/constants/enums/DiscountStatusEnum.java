package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum DiscountStatusEnum {
    PENDING("Chưa áp dụng"),
    ACTIVE("Đang áp dụng"),
    DE_ACTIVE("Ngừng áp dụng");

    private String name;

    DiscountStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
