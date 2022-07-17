package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.report.RevenueReq;
import com.ws.masterserver.service.AdminReportService;
import com.ws.masterserver.utils.base.rest.CurrentUser;
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
