package com.ws.master_service.dto.customer.product.product_option;

import com.ws.master_service.utils.constants.enums.ColorEnum;
import com.ws.master_service.utils.constants.enums.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionRes {
    private String id;
    private ColorEnum color;
    private String image;
    private Long price;
    private Long qty;
    private SizeEnum size;
}
