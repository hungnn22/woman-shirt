package com.ws.master_service.dto.customer.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String id;
    private String addressName;
    private String hotline;
    private String addressLink;

}
