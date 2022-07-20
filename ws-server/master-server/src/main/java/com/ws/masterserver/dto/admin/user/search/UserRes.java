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
    private String combinationName;
    private String email;
    private String phone;
    private String roleValue;
    private Boolean active;
    private String createdDateValue;
    private String createdByValue;
    private String avatar;
}
