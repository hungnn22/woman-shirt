package com.ws.master_service.utils.constants.enums;

public enum DiscountTypeEnum {
    PERCENT("Theo phần trăm"),
    PRICE("Theo số tiền"),
    SHIP("Miễn phí vận chuyển");

    private String name;

    DiscountTypeEnum(String name) {
        this.name = name;
    }
}
