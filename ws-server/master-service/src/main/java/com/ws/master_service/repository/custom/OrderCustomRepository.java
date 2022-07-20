package com.ws.master_service.repository.custom;

import com.ws.master_service.dto.admin.order.detail.DetailRes;
import com.ws.master_service.dto.admin.order.search.OrderReq;
import com.ws.master_service.dto.admin.order.search.OrderRes;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.PageData;
import com.ws.master_service.utils.base.rest.ResData;

public interface OrderCustomRepository {
    PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req);
    ResData<DetailRes> detail4Admin(CurrentUser currentUser, String id);
}
