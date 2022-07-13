package com.ws.masterserver.dto.admin.user.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRes {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String type;
    private Boolean active;
    private String createdDate;
    private String createdBy;
}
