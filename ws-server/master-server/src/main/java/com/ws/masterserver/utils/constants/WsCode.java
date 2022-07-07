package com.ws.masterserver.utils.constants;

public enum WsCode {
    OK("200", "Thành công!"),
    CREATED("201", "Thành công!"),
    FORBIDDEN("403", "Không có quyền"),

    REQUIRED_FIELD("400", "Không được để trống %s!"),
    INVALID_LENGTH("400", "Độ dài %s không hợp lệ!"),
    USER_NOT_FOUND("404", "Không tìm thấy người dùng!"),


    CATEGORY_NOT_FOUND("400", "Không tìm thấy loại sản phẩm!"),
    CATEGORY_EXISTS_NAME("400", "Tên loại sản phẩm đã tồn tại!"),

    COLOR_NOT_FOUND("400", "Không tìm thấy màu sản phẩm!"),
    MATERIAL_NOT_FOUND("400", "Không tìm thấy chất liệu sản phẩm!"),
    PRODUCT_NOT_FOUND("400", "Không tìm thấy chất liệu sản phẩm!"),
    INTERNAL_SERVER("500", "Hệ thống đang bị gián đoạn! Xin vui lòng thử lại sau"),
    BAD_REQUEST("400", "Dữ liệu không hợp lệ!"),
    MUST_LOGIN("400", "Vui lòng đăng nhập"),
    SIZE_SUGGEST_NOT_FOUND("400", "Không tìm thấy size phù hợp");



    private final String code;
    private final String message;

    WsCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
