package com.ws.master_service.utils.constants.enums;

/**
 * @author myname
 */
public enum NotificationTypeEnum {
    NORMAL("icon-circle bg-primary", "fas fa-file-alt text-white"),
    SUCCESS("icon-circle bg-success", "fas fa-donate text-white"),
    WARNING("icon-circle bg-warning", "fas fa-exclamation-triangle text-white");

    private String div;
    private String icon;

    NotificationTypeEnum(String div, String icon) {
        this.div = div;
        this.icon = icon;
    }

    public String getDiv() {
        return div;
    }

    public String getIcon() {
        return icon;
    }
}
