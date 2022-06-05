package com.ws.masterserver.utils.constants.enums;

public enum MaterialEnum {
    MTR01("Vải")
    ;

    private final String name;

    MaterialEnum(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
