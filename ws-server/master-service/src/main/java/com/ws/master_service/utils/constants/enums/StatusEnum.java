package com.ws.master_service.utils.constants.enums;

public enum StatusEnum {
    /**
     * Trạng thái đơn hàng
     */
    PROCESSING("Khách hàng đang mua - chưa checkin"),
    PENDING("Đơn hàng đang đợi xử lý!"),
    PAYED("Đã thanh toán"),
    CANCEL("Đã hủy"),
    REJECT("Bị từ chối"),
    ACCEPT("Được chấp nhận"),
    SHIPPING("Đang giao hàng"),
    EXCHANGE("Đổi hàng"),
    REFUND("Trả hàng"),
    RECEIVED("Đã nhận được hàng")

    ;

    private String name;

    StatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
