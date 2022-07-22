package com.ws.masterserver.utils.constants.enums;

public enum DiscountTypeEnums {
    PERCENT("Theo phần trăm"),
    PRICE("Theo số tiền"),
    SHIP("Miễn phí vận chuyển");

    private String name;

    DiscountTypeEnums(String name) {
        this.name = name;
    }
}
