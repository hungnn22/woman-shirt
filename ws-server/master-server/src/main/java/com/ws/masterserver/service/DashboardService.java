package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;

/**
 * @author myname
 */
public interface DashboardService {
    Object getReport(CurrentUser currentUser);

    Object getCategoryRevenue(CurrentUser currentUser);

    Object getWeekRevenue(CurrentUser currentUser);
}
