package com.ws.master_service.dto.customer.order.checkin;

import lombok.Data;

@Data
public class ItemDto {
    private String productId;
    private Integer qty;
    private Long price;
}
