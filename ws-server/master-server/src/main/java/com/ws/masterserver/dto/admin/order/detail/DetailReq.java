package com.ws.masterserver.dto.admin.order.detail;

import com.ws.masterserver.utils.base.rest.PageReq;
import lombok.Data;

/**
 * @author hungnn22
 */
@Data
public class DetailReq {
    private String id;
    private PageReq pageReq;
}
