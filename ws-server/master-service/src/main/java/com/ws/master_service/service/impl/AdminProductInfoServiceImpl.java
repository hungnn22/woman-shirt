package com.ws.master_service.service.impl;

import com.ws.master_service.utils.base.WsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductInfoServiceImpl {
    private final WsRepository repository;
}
