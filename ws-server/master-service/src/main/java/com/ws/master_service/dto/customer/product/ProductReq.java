package com.ws.master_service.dto.customer.product;

import com.ws.master_service.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class ProductReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private String minPrice;
    private String maxPrice;
    private PageReq pageReq;
}
