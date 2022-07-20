package com.ws.master_service.service.impl;

import com.ws.master_service.dto.customer.size.response.SizeResponse;
import com.ws.master_service.service.SizeService;
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
public class SizeServiceImpl implements SizeService {

    private final WsRepository repository;

    @Override
    public ResData<List<SizeResponse>> getAllSize() {
        List<SizeResponse> size = repository.sizeRepository.getAllSize();

        return new ResData<>(size,  WsCode.OK);
    }
}
