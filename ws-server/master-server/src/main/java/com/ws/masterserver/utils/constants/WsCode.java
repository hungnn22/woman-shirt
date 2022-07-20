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
    SIZE_SUGGEST_NOT_FOUND("400", "Không tìm thấy size phù hợp"),
    EMAIL_EXISTS("400", "Email đã tồn tại"),
    PHONE_EXISTS("400", "SDT đã tồn tại"),
    DOB_NOT_MORE_NOW("400", "Ngảy sinh không được lớn hơn hiện tại"),
    AGE_MUST_MORE("400", "Tuổi phải lớn hơn"),
    AGE_MUST_LESS("400", "Tuổi phải nhỏ hơn"),
    DONT_CHANGE_YOURSELF("400", "Không được thay đổi chính mình"),
    USER_LOCKED("500", "Tài khoản của bạn đã bị xóa hoặc vô hiệu hóa"),
    NEW_PASS_NOT_SAME_OLD("400", "Mật khẩu mới không được trùng mật khẩu cũ"),
    PASSWORD_WRONG("400", "Mật khẩu không chính xác"),
    PERCENT_MUST_BETWEEN_0_AND_100("400", "Giá trị khuyến mãi phải từ 0 - 100"),
    MUST_SELECT_PREREQUISITE("400", "Vui lòng chọn điều kiện áp dụng"),
    MUST_SELECT_CUSTOMER_TYPE("400", "Vui lòng chọn nhóm khách hàng"),
    MUST_SELECT_DISCOUNT_TYPE("400", "Vui lòng chọn lại khuyến mãi"),
    MUST_SELECT_APPLY_TYPE("400", "Vui lòng chọn loại áp dụng khuyến mãi"),
    DATE_FORMAT_INVALID("400", "Định dạng ngày tháng không hợp lê");
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
