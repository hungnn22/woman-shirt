package com.ws.master_service.utils.constants.enums;

/**
 * @author myname
 */
public enum ObjectTypeEnum {
    ORDER("order"),
    USER("user");

    String value;

    public String getValue() {
        return value;
    }

    ObjectTypeEnum(String value) {
    }
}
