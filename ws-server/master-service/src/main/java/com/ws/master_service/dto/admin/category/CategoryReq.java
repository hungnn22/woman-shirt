package com.ws.master_service.dto.admin.category;

import com.ws.master_service.utils.base.rest.PageReq;
import lombok.Data;

@Data
public class CategoryReq {
    private String id;
    private Boolean active;
    private String textSearch;
    private PageReq pageReq;

}
