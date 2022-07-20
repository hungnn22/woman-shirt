package com.ws.master_service.dto.customer.order.me;

import com.ws.master_service.utils.constants.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyOrderResponse {
    /**order*/
    private String id;
    private Long createdAt;
    private String note;
    private String address;
    private Long total;

    private StatusEnum status;

}
