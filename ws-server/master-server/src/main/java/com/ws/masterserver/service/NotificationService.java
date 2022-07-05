package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author myname
 */
public interface NotificationService {
    Object get4Admin(CurrentUser currentUser);

    Long read4admin(CurrentUser currentUser, List<String> dto);

    Object search4Admin(CurrentUser currentUser, Integer pageSize);

    Long readAll4Admin(CurrentUser currentUser);
}
