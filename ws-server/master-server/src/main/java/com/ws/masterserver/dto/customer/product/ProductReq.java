package com.ws.masterserver.dto.customer.product;

import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.constants.enums.ColorEnum;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.Data;

@Data
public class ProductReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private String priceMin;
    private String priceMax;
    private SizeEnum size;
    private ColorEnum color;
    private PageReq pageReq;
}
