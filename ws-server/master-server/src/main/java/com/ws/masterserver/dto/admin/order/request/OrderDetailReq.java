package com.ws.masterserver.dto.admin.order.request;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

/**
 * @author hungnn22
 */
@Data
public class OrderDetailReq {
    private String id;
    private PageReq pageReq;
}
