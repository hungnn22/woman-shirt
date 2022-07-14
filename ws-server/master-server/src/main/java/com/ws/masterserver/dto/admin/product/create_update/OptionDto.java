package com.ws.masterserver.dto.admin.product.create_update;

import lombok.Data;

@Data
public class OptionDto {
    private String id;
    private String colorId;
    private String price;
    private String qty;
    private String sizeId;
    private byte[] image;
}
