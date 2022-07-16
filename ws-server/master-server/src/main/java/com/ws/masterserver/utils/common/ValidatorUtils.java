package com.ws.masterserver.utils.common;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.RoleEnum;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    private static final String MUST_LENGTH_LESS = " phải ít hơn ";
    private static final String MUST_LENGTH_MORE = " phải nhiều hơn ";
    private static final String CHARACTER = " ký tự ";
    private static final String MUST_MORE = " phải lớn hơn ";
    private static final String MUST_LESS = " phải nhỏ hơn ";

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


    public static void validLength(String fieldName, String value, int length, boolean type) {
        if (type && value.length() > length) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + length + CHARACTER);
        }

        if (!type && value.length() < length) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + length + CHARACTER);
        }
    }

    public static void validLength(String fieldName, String value, int minLength, int maxLength) {
        if (value.length() < minLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + minLength + CHARACTER);
        }
        if (value.length() > maxLength) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + maxLength + CHARACTER);
        }
    }

    public static void validLength(String fieldName, List<String> values, int minLength, int maxLength) {
        if (values == null || values.isEmpty()) {
            return;
        }
        for (var value : values) {
            if (value.length() < minLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_MORE + minLength + CHARACTER);
            }
            if (value.length() > maxLength) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LENGTH_LESS + maxLength + CHARACTER);
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

    public static void validOnlyCharacter(String fieldName, String value) {
        if (!StringUtils.isOnlyCharacter(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validEmail(String fieldName, String value) {
        if (!StringUtils.isValidEmail(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validPhone(String fieldName, String value) {
        if (!StringUtils.isValidPhone(value)) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validRole(String fieldName, String value) {
        validNullOrEmpty(fieldName, value);
        var role = RoleEnum.valueOf(value);
        if (role == null) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validBooleanType(String fieldName, Boolean value) {
        if (value == null) {
            throw new WsException(WsCode.BAD_REQUEST, fieldName + INVALID);
        }
    }

    public static void validAgeBetween(String fieldName, Date value, Integer minAge, Integer maxAge) {
        if (value == null) {
            throw new WsException(WsCode.BAD_REQUEST, NOT_BLANK + fieldName.toLowerCase());
        }
        var now = LocalDate.now();
        var dob = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dob.compareTo(now) > 0) {
            throw new WsException(WsCode.DOB_NOT_MORE_NOW);
        }
        if (null != minAge) {
            now = now.minusYears(minAge);
            if (dob.compareTo(now) > 0) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_MORE + minAge);
            }
        }
        if (null != maxAge) {
            now = now.minusYears(maxAge);
            if (dob.compareTo(now) < 0) {
                throw new WsException(WsCode.BAD_REQUEST, fieldName + MUST_LESS + maxAge);
            }
        }
    }
}
