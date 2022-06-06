package com.ws.masterserver.dto.admin.order.search;

import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusDto {
    private StatusEnum status;
    private String note;
    private String createdDate;
    private String combination;
    private UserDto user;
}
