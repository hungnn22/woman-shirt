package com.ws.masterserver.utils.base.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnn22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {
    private String code;
    private String viName;
    private String enName;
    private String hex;

}
