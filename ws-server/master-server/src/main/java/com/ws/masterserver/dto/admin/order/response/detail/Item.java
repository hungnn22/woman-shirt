package com.ws.masterserver.dto.admin.order.response.detail;

import com.ws.masterserver.utils.base.enums.ColorDto;
import com.ws.masterserver.utils.base.enums.MaterialDto;
import com.ws.masterserver.utils.base.enums.SizeDto;
import com.ws.masterserver.utils.base.enums.TypeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author hungnn22
 */

@Data
@Builder
public class Item {
    //productOptionId
    private String id;

    //productId
    private String productId;

    //product name
    private String name;

    private ColorDto color;

    private SizeDto size;

    private String img;

    private MaterialDto material;

    private TypeDto type;

    private Long price;

    private String priceFmt;

    private Long qty;

    private String des;

    private List<String> des4Menu;

    private String category;
}
