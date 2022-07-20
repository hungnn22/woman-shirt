package com.ws.masterserver.utils.constants.enums;

public enum ApplyTypeEnum {
    ALL_PRODUCT("Tất cả sản phẩm"),
    CATEGORY("Loại sản phẩm"),
    PRODUCT("Sản phẩm");

    private String name;

    ApplyTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
