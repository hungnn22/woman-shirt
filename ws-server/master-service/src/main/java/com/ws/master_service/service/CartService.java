package com.ws.master_service.service;

import com.ws.master_service.dto.customer.cart.request.CartRequest;
import com.ws.master_service.dto.customer.cart.response.CountCartItem;
import com.ws.master_service.dto.customer.cart.response.ListCartResponse;
import com.ws.master_service.utils.base.rest.CurrentUser;
import com.ws.master_service.utils.base.rest.ResData;


public interface CartService {
    ResData<String> addToCart(CurrentUser currentUser, CartRequest cart);

    ResData<ListCartResponse> getListCart(CurrentUser currentUser);

    ResData<String> updateCart(CurrentUser currentUser, CartRequest cart);

    ResData<String> deleteItemInCart(CurrentUser currentUser,String productOptionId);

    ResData<CountCartItem> countCartItem(CurrentUser currentUser);
}
