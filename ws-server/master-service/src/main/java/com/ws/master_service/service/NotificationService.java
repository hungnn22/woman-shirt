package com.ws.master_service.service;

import com.ws.master_service.utils.base.rest.CurrentUser;

import java.util.List;

/**
 * @author myname
 */
public interface NotificationService {
    Object get4Admin(CurrentUser currentUser);

    Long readTop3Notification4Admin(CurrentUser currentUser, List<String> dto);

    Object search4Admin(CurrentUser currentUser, Integer pageSize);

    Long readAll4Admin(CurrentUser currentUser);

    void readById4Admin(CurrentUser currentUser, String id);
}
