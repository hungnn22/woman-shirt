package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.order.response.detail.OrderDetailRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

/**
 * @author hungnn22
 */
public interface OrderDetailCustomRepository {
    PageData<OrderDetailRes> findByOrderId4Admin(CurrentUser currentUser, String id);
}
