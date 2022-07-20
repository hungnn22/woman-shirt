package com.ws.master_service.dto.admin.order.detail;

import com.ws.master_service.utils.constants.enums.RoleEnum;
import com.ws.master_service.utils.constants.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private StatusEnum status;
    private Date createdDate;
    private RoleEnum role;
    private String fullName;
}
