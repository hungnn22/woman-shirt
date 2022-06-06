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

    private List<PromotionDto> promotions;

    private Boolean payed;

    private Long shipPrice;

    private String shipPriceFmt;

    private String type;


}
