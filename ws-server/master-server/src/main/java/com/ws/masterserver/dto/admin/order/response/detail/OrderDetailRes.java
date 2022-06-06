package com.ws.masterserver.dto.admin.order.response.detail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailRes {
    private String id;
    private Long price;
    private String priceFmt;
    private Long qty;
    private Long total;
    private String totalFmt;
    private ItemDto item;
}
