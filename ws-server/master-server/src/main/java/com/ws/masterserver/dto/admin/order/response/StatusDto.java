package com.ws.masterserver.dto.admin.order.response;

import com.ws.masterserver.utils.constants.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author hungnn22
 */
@Data
@Builder
public class StatusDto {
    private StatusEnum status;

    private String note;

    private String createdDate;

    private String combination;

    private UserDto user;

}
