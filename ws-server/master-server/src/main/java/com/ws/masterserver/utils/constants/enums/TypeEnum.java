package com.ws.masterserver.utils.constants.enums;

public enum TypeEnum {
    MALE(1, Boolean.TRUE, "Nam", "MALE"),
    FEMALE(0, Boolean.FALSE, "Nữ", "FEMALE"),
    NONE(-1, null, "Chưa xác định | Cả hai", "NONE")
    ;

    private int typeCode;
    private Boolean typeValue;
    private String viName;
    private String enName;

    TypeEnum(int typeCode, Boolean typeValue, String viName, String enName) {
    }

    public Boolean getTypeValue() {
        return typeValue;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getEnName() {
        return enName;
    }

    public String getViName() {
        return viName;
    }
}
