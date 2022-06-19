package com.ws.masterserver.dto.customer.product.product_option;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ProductOptionIdReq {
    @NotNull
    private String size;
    @NotNull
    private String colorId;
}
