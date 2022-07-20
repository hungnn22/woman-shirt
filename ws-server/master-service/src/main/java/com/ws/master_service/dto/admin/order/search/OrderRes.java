package com.ws.master_service.dto.admin.order.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderRes {
    private String id;

    private String code;

    private String customer;

    private String phone;

    private String orderDate;

    private String address;

    private String total;

    private String note;

    private String type;

    private String status;

    private String customerId;

    private List<OptionDto> options;
}
