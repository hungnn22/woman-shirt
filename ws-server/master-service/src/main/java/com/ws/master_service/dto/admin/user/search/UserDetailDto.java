package com.ws.master_service.dto.admin.user.search;

import lombok.experimental.Accessors;

import javax.persistence.MappedSuperclass;

@Accessors(chain = true)
@MappedSuperclass
public class UserDetailDto extends UserRes {

}
