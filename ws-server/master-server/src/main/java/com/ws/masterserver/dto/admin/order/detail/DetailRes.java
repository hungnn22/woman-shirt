package com.ws.masterserver.dto.admin.order.detail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DetailRes {
    private String id;
    private List<ItemDto> items;
    private List<PromotionDto> promotions;
    private ResultDto result;
}
