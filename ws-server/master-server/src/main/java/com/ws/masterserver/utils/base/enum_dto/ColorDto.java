package com.ws.masterserver.utils.base.enum_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {
    private String id;
    private String name;
    private String hex;

}
