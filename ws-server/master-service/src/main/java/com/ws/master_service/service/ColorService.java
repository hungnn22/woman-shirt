package com.ws.master_service.service;

import com.ws.master_service.dto.customer.product.ColorResponse;
import com.ws.master_service.utils.base.enum_dto.ColorDto;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;

import java.util.List;

public interface ColorService {
    ResData<String> create(CurrentUser currentUser, ColorDto dto);

    ResData<String> delete(CurrentUser currentUser, ColorDto dto);

    ResData<String> update(CurrentUser currentUser, ColorDto dto);

    ResData<List<ColorResponse>> getListColor();
}
