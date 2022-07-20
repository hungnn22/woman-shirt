package com.ws.master_service.utils.constants.enums;

public enum PrerequisiteTypeEnum {
    NONE("Không"),
    MIN_TOTAL("Tổng giá trị đơn hàng tối thiểu"),
    MIN_QTY("Tổng số lượng sản phẩm được khuyến mãi tối thiếu");

    private String name;
    PrerequisiteTypeEnum(String name) {
        this.name = name;
    }
}
