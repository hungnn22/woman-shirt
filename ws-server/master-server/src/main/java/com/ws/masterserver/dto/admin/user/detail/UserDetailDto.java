package com.ws.masterserver.dto.admin.user.detail;

import com.ws.masterserver.dto.admin.user.search.UserRes;
import lombok.experimental.Accessors;

import javax.persistence.MappedSuperclass;

@Accessors(chain = true)
@MappedSuperclass
public class UserDetailDto extends UserRes {

}
