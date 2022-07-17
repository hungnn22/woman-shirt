package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.report.RevenueReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AdminReportService {
    Object revenue(CurrentUser currentUser, RevenueReq req);
}
