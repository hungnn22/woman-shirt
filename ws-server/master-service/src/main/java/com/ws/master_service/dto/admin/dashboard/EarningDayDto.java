package com.ws.master_service.dto.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EarningDayDto {

    private Integer dayOfWeek;

    private Long total;

}
