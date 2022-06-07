package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

/**
 * @author hungnn22
 */
public interface OrderDetailCustomRepository {
    PageData<DetailRes> findByOrderId4Admin(CurrentUser currentUser, String id);
}
