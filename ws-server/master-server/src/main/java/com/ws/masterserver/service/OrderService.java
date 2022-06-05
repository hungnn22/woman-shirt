package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.order.response.detail.OrderDetailRes;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartDto;
import com.ws.masterserver.dto.customer.order.add_to_cart.AddToCartResponse;
import com.ws.masterserver.dto.customer.order.checkin.CheckinDto;
import com.ws.masterserver.dto.customer.order.checkin.CheckinResponse;
import com.ws.masterserver.dto.customer.order.me.MyOrderRequest;
import com.ws.masterserver.dto.customer.order.me.MyOrderResponse;
import com.ws.masterserver.dto.admin.order.request.OrderReq;
import com.ws.masterserver.dto.admin.order.response.OrderRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.base.rest.PageData;

public interface OrderService {
    ResData<CheckinResponse> checkin(CurrentUser currentUser, CheckinDto body);
    PageData<MyOrderResponse> getMyOrder(CurrentUser currentUser, MyOrderRequest request);
    ResData<AddToCartResponse> addToCart(CurrentUser currentUser, AddToCartDto body);

    /**
     * For Admin
     * */
    PageData<OrderRes> search4Admin(CurrentUser currentUser, OrderReq req);
    PageData<OrderDetailRes> detail4Admin(CurrentUser currentUser, String id);
}
