package com.ws.master_service.service;

import com.ws.master_service.dto.admin.report.RevenueReq;
import com.ws.master_service.utils.base.rest.CurrentUser;

public interface AdminReportService {
    Object revenue(CurrentUser currentUser, RevenueReq req);
}
