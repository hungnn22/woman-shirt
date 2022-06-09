package com.ws.masterserver.dto.admin.order.search;

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

    //note của khách hàng
    private String note;

    /**
     * status
     */
    private String status;

    /**
     * Thông tin khách hàng
     */
    private String customer;

    private String phone;

    private String payed;

    private String type;

}
