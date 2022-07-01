package com.ws.masterserver.dto.admin.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private String id;
    private String content;
    private Boolean isRead;
    private String createdDate;
    private String div;
    private String icon;
}
