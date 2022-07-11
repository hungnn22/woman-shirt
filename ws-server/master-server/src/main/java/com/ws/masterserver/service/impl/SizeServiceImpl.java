package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.service.SizeService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.WsCode;
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
