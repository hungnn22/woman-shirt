package com.ws.masterserver.dto.customer.cart.response;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private String productOptionId;
    private List<CartItem> cartItems;
    private String totalPrice;
}
