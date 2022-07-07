package com.ws.masterserver.dto.customer.cart.response;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String cartId;
    private String productOptionId;
    private String productName;
    private SizeEnum sizeName;
    private String colorName;
    private Integer quantity;
    private Long price;
    private String image;
    private Long subtotal;
}
