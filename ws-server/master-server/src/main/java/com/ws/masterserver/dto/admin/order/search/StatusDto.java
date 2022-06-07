package com.ws.masterserver.dto.admin.order.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusDto {
    private String code;
    private String note;
    private String createdDate;
    private String combination;
    private UserDto user;
}
