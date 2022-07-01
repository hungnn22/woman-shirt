package com.ws.masterserver.dto.admin.dashboard;

import lombok.Data;

@Data
public class ReportDto {
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
