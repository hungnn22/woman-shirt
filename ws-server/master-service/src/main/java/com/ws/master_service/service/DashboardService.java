package com.ws.master_service.service;

import com.ws.master_service.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface DashboardService {
    Object getReport(CurrentUser currentUser);

    Object getCategoryRevenue(CurrentUser currentUser);

    Object getWeekRevenue(CurrentUser currentUser);
}
