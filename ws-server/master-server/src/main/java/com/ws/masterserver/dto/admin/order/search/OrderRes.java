package com.ws.masterserver.dto.admin.order.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

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

    //tien ship
//    private Long shipPrice;
//
//    private String shipPriceFmt;


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

    private String payed;

    private String type;

//    private List<PromotionDto> promotions;


}
