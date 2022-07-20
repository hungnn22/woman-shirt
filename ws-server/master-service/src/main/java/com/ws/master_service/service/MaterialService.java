package com.ws.master_service.service;

import com.ws.master_service.utils.base.enum_dto.MaterialDto;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;

public interface MaterialService {
    ResData<String> create(CurrentUser currentUser, MaterialDto dto);

    ResData<String> delete(CurrentUser currentUser, MaterialDto dto);

    ResData<String> update(CurrentUser currentUser, MaterialDto dto);
}
