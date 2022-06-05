package com.ws.masterserver.dto.admin.order.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderRes {
    /**
     * Thông tin đơn hàng
     */
    private String id;

    //ngày đặt hàng
    private String orderDate;

    //địa chỉ giao
    private String address;

    //tổng tiền
    private Long total;

    private String totalFmt;

    private Long discount;

    //note của khách hàng
    private String note;

    /**
     * status
     */
    private StatusDto status;

    /**
     * Thông tin khách hàng
     */
    private UserDto customer;

    //mã voucher
    private String voucherCode;

}
