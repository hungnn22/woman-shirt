package com.ws.master_service.service;

import com.ws.master_service.dto.customer.location.LocationDto;
import com.ws.master_service.utils.base.rest.ResData;

import java.util.List;

public interface LocationService {

    ResData<List<LocationDto>> findLocations();

}
