package com.ws.masterserver.dto.admin.order.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author hungnn22
 */

@Data
@Builder
public class PriceDto {
    private Long total;
    private String totalFmt;
    private Long ship;
    private String shipFmt;
    private List<PromotionDto> promotions;
}
