package com.ws.master_service.dto.admin.dashboard;

import lombok.Data;

@Data
public class ReportDto {
    /**
     * đơn hàng đang chờ xử lý
     */
    private Long pending;

    /**
     * người dùng mới
     */
    private Long user;

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
    private Long cancel;
}
