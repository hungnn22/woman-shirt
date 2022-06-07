package com.ws.masterserver.utils.constants.enums;

/**
 * @author hungnn22
 */
public enum OrderTypeEnum {
    CASH("CASH", "Tiền mặt", "Thanh toán bằng tiền mặt"),
    VNP("VNPAY", "VN Pay", "Thanh toán qua VN-Pay"),
    ZLP("ZALOPAY", "Zalo Pay", "Thanh toán qua Zalo-Pay")
    ;

    private final String code;
    private final String tittle;
    private final String name;

    OrderTypeEnum(String code, String tittle, String name) {
        this.code = code;
        this.tittle = tittle;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getTittle() {
        return tittle;
    }

    public String getName() {
        return name;
    }
}
