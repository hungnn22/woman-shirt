package com.ws.masterserver.utils.constants.enums;

public enum PrerequisiteTypeEnums {
    NONE("Không"),
    MIN_TOTAL("Tổng giá trị đơn hàng tối thiểu"),
    MIN_QTY("Tổng số lượng sản phẩm được khuyến mãi tối thiếu");

    private String name;
    PrerequisiteTypeEnums(String name) {
        this.name = name;
    }
}
