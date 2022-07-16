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
    private String role;
    private Boolean gender;
    private String avatar;
    private Date dob;
}
