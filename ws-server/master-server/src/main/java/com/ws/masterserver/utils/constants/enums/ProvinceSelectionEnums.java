package com.ws.masterserver.utils.constants.enums;

public enum ProvinceSelectionEnums {
    ALL("Tất cả"),
    SELECTION("Tỉnh.thành được chọn");

    private String name;

    ProvinceSelectionEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name = name;
    }
}
