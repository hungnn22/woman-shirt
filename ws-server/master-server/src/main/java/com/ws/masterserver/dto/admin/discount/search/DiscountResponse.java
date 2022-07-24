package com.ws.masterserver.dto.admin.discount.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiscountResponse {
    private String id;
    private String code;
    private List<String> des;
    private String status;
    private String statusValue;
    private String usageNumber;
    private String start;
    private String end;
}
