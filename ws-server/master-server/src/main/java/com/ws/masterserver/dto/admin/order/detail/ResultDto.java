package com.ws.masterserver.dto.admin.order.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnn22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private Long total;
    private String totalFmt;
    private Long ship;
    private String shipFmt;
    private Long shipDiscount;
    private String shipDiscountFmt;
    private Long shop;
    private String shopFmt;
    private Long shopDiscount;
    private String shopDiscountFmt;
}
