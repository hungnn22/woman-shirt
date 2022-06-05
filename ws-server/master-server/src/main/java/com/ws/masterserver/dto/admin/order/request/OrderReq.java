package com.ws.masterserver.dto.admin.order.request;

import com.ws.masterserver.utils.base.rest.PageReq;
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
