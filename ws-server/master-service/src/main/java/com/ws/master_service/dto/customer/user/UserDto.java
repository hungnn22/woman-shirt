package com.ws.master_service.dto.customer.user;

import com.ws.master_service.utils.constants.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private RoleEnum role;
}
