package com.ws.masterserver.dto.admin.order.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author myname
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
    private String code;
    private String tittle;
    private String name;
}
