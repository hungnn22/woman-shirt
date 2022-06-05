package com.ws.masterserver.utils.constants.enums;

public enum StatusEnum {
    /**
     * Trạng thái đơn hàng
     */
    PROCESSING("Khách hàng đang mua - chưa checkin"),
    PENDING("Đơn hàng đang đợi xử lý!"),
    CANCEL("Đã hủy"),
    REJECT("Bị từ chối"),
    ACCEPTED("Được chấp nhận"),
    SHIPPING("Đang giao hàng")
    ;

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
