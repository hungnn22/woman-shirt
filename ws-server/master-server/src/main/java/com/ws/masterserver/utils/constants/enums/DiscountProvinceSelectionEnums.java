package com.ws.masterserver.utils.constants.enums;

public enum DiscountProvinceSelectionEnums {
    ALL("Tất cả"),
    SELECTION("Tỉnh.thành được chọn");

    private String name;

    DiscountProvinceSelectionEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name = name;
    }
}
