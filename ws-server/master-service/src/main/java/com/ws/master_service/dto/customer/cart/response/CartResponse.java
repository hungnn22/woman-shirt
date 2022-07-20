package com.ws.master_service.dto.customer.cart.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private String productOptionId;
    private String productName;
    private String sizeName;
    private String colorName;
    private Integer quantity;
    private Long price;
    private String image;
    private Long subtotal;
}
