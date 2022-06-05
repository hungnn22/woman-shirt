package com.ws.masterserver.utils.base.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnn22
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeDto {
    private String code;
    private String viName;
    private String enName;
}
