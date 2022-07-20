package com.ws.master_service.dto.admin.order.search;

import com.ws.master_service.utils.base.rest.PageReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReq {
    private String id;
    private String status;
    private String cusId;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String time;
    private String textSearch;
    private PageReq pageReq;
}
