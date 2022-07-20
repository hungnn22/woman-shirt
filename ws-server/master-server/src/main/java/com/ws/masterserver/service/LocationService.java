package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.location.LocationDto;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface LocationService {

    ResData<List<LocationDto>> findLocations();

}
