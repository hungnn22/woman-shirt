package com.ws.masterserver.dto.customer.suggest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {
    private String categoryId;
    private Long height;
    private Long weight;
}
