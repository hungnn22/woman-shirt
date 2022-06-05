package com.ws.masterserver.dto.admin.product;

import com.ws.masterserver.dto.admin.product_option.ProductOptionDto;
import com.ws.masterserver.utils.constants.enums.TypeEnum;
import com.ws.masterserver.utils.constants.enums.MaterialEnum;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String categoryId;
    private MaterialEnum material;
    private TypeEnum type;
    private String des;
    private List<ProductOptionDto> productOptions;
}
