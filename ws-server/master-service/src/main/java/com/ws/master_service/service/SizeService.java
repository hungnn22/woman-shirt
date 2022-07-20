package com.ws.master_service.service;

import com.ws.master_service.dto.customer.size.response.SizeResponse;
import com.ws.master_service.utils.base.rest.ResData;

import java.util.List;

public interface SizeService {
    ResData<List<SizeResponse>> getAllSize();
}
