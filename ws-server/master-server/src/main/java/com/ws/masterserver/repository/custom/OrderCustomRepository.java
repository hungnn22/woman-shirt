package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.order.request.OrderReq;
import com.ws.masterserver.dto.admin.order.response.OrderRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

public interface OrderCustomRepository {
    PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req);
}
