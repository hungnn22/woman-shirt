package com.ws.master_service.service.impl;

import com.ws.master_service.dto.admin.report.RevenueReq;
import com.ws.master_service.service.AdminReportService;
import com.ws.master_service.utils.base.rest.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminReportServiceImpl implements AdminReportService {
    @Override
    public Object revenue(CurrentUser currentUser, RevenueReq req) {
        return null;
    }
}
