package com.ws.masterserver.utils.constants.enums;

public enum ApplyTypeEnums {
    ALL_PRODUCT("Tất cả sản phẩm"),
    CATEGORY("Loại sản phẩm"),
    PRODUCT("Sản phẩm");

    private String name;

    ApplyTypeEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
