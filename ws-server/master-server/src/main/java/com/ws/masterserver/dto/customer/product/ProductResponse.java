package com.ws.masterserver.dto.customer.product;

import com.ws.masterserver.dto.customer.product.product_option.ProductOptionRes;
import com.ws.masterserver.utils.constants.enums.MaterialEnum;
import com.ws.masterserver.utils.constants.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private Boolean active;
    private String categoryName;
    private String des;
    private MaterialEnum material;
    private TypeEnum type;
    private Date createdDate;
    private String createdBy;
    private String createdName;
//    private List<ProductOptionRes> productOptionResList;
}
