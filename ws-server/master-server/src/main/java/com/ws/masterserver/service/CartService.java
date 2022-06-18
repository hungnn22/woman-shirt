package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface CartService {
    ResData<String> addToCart(CurrentUser currentUser, CartRequest cart);
}
