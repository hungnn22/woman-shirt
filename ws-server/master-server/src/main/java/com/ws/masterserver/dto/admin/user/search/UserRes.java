package com.ws.masterserver.dto.admin.user.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
