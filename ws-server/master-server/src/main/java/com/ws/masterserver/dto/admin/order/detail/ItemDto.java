package com.ws.masterserver.dto.admin.order.detail;

import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.enum_dto.MaterialDto;
import com.ws.masterserver.utils.base.enum_dto.SizeDto;
import com.ws.masterserver.utils.base.enum_dto.TypeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author hungnn22
 */

@Data
@Builder
public class ItemDto {
    //productOptionId
    private String id;

    //productId
    private String productId;

    //product name
    private String name;

    private String color;

    private String size;

    private String img;

    private String material;

    private String type;

    private Long price;

    private String priceFmt;

    private Long qty;

    private String des;

    private List<String> des4Menu;

    private String category;
}
