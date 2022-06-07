package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum PromotionTypeEnum {
    TYPE1("SHIP", "Vận chuyển"),
    TYPE2("SHOP", "Mua sắm")
        ;

    private final String id;
    private final String name;

    PromotionTypeEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
