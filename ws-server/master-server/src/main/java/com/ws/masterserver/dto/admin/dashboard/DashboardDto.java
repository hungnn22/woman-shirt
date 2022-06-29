package com.ws.masterserver.dto.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    /**
     * đơn hàng đang chờ xử lý
     */
    private String pending;

    /**
     * người dùng mới
     */
    private String user;

    /**
     * Doanh thu hôm nay
     */
    private String today;

    /**
     * Doanh thu trong tuần
     */
    private String week;

    /**
     * đơn hàng bị hủy
     */
    private String cancel;
}
