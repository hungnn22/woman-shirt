package com.ws.master_service.dto.admin.notification;

import com.ws.master_service.utils.constants.enums.ObjectTypeEnum;
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
    private ObjectTypeEnum objectType;
    private String objectTypeId;
}
