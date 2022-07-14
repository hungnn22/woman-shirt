package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;

import java.util.List;

/**
 * @author myname
 */
public class ValidatorUtils {

    private ValidatorUtils() {
        super();
    }

    private static final String NOT_BLANK = "Không được để trống ";
    private static final String INVALID = " không hợp lệ";
    private static final String NOT_LENGTH_MORE = " không được dài hơn ";
    private static final String NOT_LENGTH_LESS = " không được ít hơn ";
    private static final String CHARACTER = "ký tự";

    public static void validNullOrEmpty(String fieldName, String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName.toLowerCase());
        }
    }

    public static void validNullOrEmpty(String fieldName, List<String> values) {
        if (values == null || values.isEmpty()) {
            fieldName = fieldName.charAt(0) + fieldName.substring(1);
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName.toLowerCase());
        }
        for (var value : values) {
            validNullOrEmpty(fieldName, value);
        }
    }

    public static void validNullOrEmptyList(String fieldName, List<Object> values) {
        if (values == null || values.isEmpty()) {
            fieldName = fieldName.charAt(0) + fieldName.substring(1);
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName.toLowerCase());
        }
    }

    public static void validOnlyCharacterAndNumber(String fieldName, String value) {
        if (!StringUtils.isOnlyCharacterAndNumber(value)) {
            fieldName = fieldName.charAt(0) + fieldName.substring(1);
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validOnlyCharacterAndNumber(String fieldName, List<String> values) {
        if (values.isEmpty()) {
            return;
        }
        for (var value : values) {
            if (!StringUtils.isOnlyCharacterAndNumber(value)) {
                fieldName = fieldName.charAt(0) + fieldName.substring(1);
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
        }
    }


    public static void validLength(String fieldName, String value, int limitLength, boolean type) {
        if (type && value.length() > limitLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_MORE + limitLength + CHARACTER);
        }

        if (value.length() < limitLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_LESS + limitLength + CHARACTER);
        }
    }

    public static void validLength(String fieldName, String value, int minLength, int maxLength) {
        if (value.length() < minLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_LESS + minLength + CHARACTER);
        }
        if (value.length() > maxLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_MORE + maxLength + CHARACTER);
        }
    }

    public static void validLength(String fieldName, List<String> values, int minLength, int maxLength) {
        if (values == null || values.isEmpty()) {
            return;
        }
        for (var value : values) {
            if (value.length() < minLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_LESS + minLength + CHARACTER);
            }
            if (value.length() > maxLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + NOT_LENGTH_MORE + maxLength + CHARACTER);
            }
        }
    }

    public static void validOnlyNumber(String fieldName, String value) {
        if (!StringUtils.isOnlyNumber(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validPrice(String fieldName, String value) {
        try {
            if (value.startsWith("0") || value.contains(" ")) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
            var price = Long.valueOf(value);
            if (price < 0) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
            }
        } catch (NumberFormatException e) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }
}
