package com.ws.masterserver.utils.constants.enums;

/**
 * @author myname
 */
public enum PromotionTypeEnum {

    /**
     * @code TYPE1
     * @tittle SHIP
     * @name Vận chuyển
     * */
    TYPE1("SHIP", "Vận chuyển"),

    /**
     * @code TYPE2
     * @tittle SHOP
     * @name Mua sắm
     * */
    TYPE2("SHOP", "Mua sắm")
        ;

    private final String tittle;
    private final String name;

    PromotionTypeEnum(String id, String name) {
        this.tittle = id;
        this.name = name;
    }

    public String getTittle() {
        return tittle;
    }

    public String getName() {
        return name;
    }
}
