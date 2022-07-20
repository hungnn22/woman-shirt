package com.ws.master_service.service;

import com.ws.master_service.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.master_service.dto.admin.order.detail.DetailRes;
import com.ws.master_service.dto.customer.order.add_to_cart.AddToCartDto;
import com.ws.master_service.dto.customer.order.add_to_cart.AddToCartResponse;
import com.ws.master_service.dto.customer.order.checkin.CheckinDto;
import com.ws.master_service.dto.customer.order.checkin.CheckinResponse;
import com.ws.master_service.dto.customer.order.me.MyOrderRequest;
import com.ws.master_service.dto.customer.order.me.MyOrderResponse;
import com.ws.master_service.dto.admin.order.search.OrderReq;
import com.ws.master_service.dto.admin.order.search.OrderRes;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;
import com.ws.master_service.utils.base.rest.PageData;

public interface OrderService {
    ResData<CheckinResponse> checkin(CurrentUser currentUser, CheckinDto body);
    PageData<MyOrderResponse> getMyOrder(CurrentUser currentUser, MyOrderRequest request);
    ResData<AddToCartResponse> addToCart(CurrentUser currentUser, AddToCartDto body);

    /**
     * For Admin
     * */
    PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req);
    ResData<DetailRes> detail4Admin(CurrentUser currentUser, String id);
    ResData<String> changeStatus4Admin(CurrentUser currentUser, ChangeStatusDto dto);
}
