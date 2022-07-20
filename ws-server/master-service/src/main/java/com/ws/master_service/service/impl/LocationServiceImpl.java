package com.ws.master_service.service.impl;

import com.ws.master_service.dto.customer.location.LocationDto;
import com.ws.master_service.service.LocationService;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final WsRepository repository;
    @Override
    public ResData<List<LocationDto>> findLocations() {
        List<LocationDto> data = repository.locationRepository.findAllLocations();
        return new ResData<>(data, WsCode.OK);

    }

}
