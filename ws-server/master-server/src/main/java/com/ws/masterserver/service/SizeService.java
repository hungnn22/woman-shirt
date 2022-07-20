package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface SizeService {
    ResData<List<SizeResponse>> getAllSize();
}
