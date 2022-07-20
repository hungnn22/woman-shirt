package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.order.detail.DetailRes;
import com.ws.masterserver.dto.admin.order.search.OrderReq;
import com.ws.masterserver.dto.admin.order.search.OrderRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;

public interface OrderCustomRepository {
    PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req);
    ResData<DetailRes> detail4Admin(CurrentUser currentUser, String id);
}
