package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface ColorService {
    ResData<String> create(CurrentUser currentUser, ColorDto dto);

    ResData<String> delete(CurrentUser currentUser, ColorDto dto);

    ResData<String> update(CurrentUser currentUser, ColorDto dto);
}
