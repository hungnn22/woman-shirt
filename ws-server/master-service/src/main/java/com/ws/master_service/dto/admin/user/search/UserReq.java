package com.ws.master_service.dto.admin.user.search;

import com.ws.master_service.utils.base.rest.PageReq;
import com.ws.master_service.utils.constants.enums.RoleEnum;
import lombok.Data;

@Data
public class UserReq {
    private String textSearch;
    private Boolean active;
    private RoleEnum role;
    private PageReq pageReq;
}
