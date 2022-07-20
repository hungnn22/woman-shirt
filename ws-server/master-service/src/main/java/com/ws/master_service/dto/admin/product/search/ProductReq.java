package com.ws.master_service.dto.admin.product.search;

import com.ws.master_service.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class ProductReq {
    private String id;
    private String textSearch;
    private String status;
    private String type;
    private String categoryId;
    private PageReq pageReq;
}
