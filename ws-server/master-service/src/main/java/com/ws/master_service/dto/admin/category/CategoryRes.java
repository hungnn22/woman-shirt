package com.ws.master_service.dto.admin.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRes {
    private String id;
    private String name;
    private String des;
    private Boolean active;
    private Date createdDate;
    private String createdBy;
    private String createdName;
}
