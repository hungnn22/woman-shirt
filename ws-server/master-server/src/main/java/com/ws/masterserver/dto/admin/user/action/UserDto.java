package com.ws.masterserver.dto.admin.user.action;

import com.ws.masterserver.utils.constants.enums.RoleEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private RoleEnum role;
    private Boolean gender;
    private MultipartFile avatar;
    private Date dob;
}
