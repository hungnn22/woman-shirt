package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.location.LocationDto;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.service.LocationService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
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
