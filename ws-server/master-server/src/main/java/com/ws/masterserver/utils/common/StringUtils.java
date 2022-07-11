package com.ws.masterserver.utils.common;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {}

    public static boolean isNullOrEmpty(String email) {
        return email == null || email.isBlank();
    }

    /*Chuyen doi ten tieng Viet co dau sang khong co dau*/
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("")
                .replace("Đ", "D")
                .replace("đ", "")
                .replace("'", " ")
                .replace("-", " ");

    }

    public static boolean isNumber(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        return value.chars().allMatch(Character::isDigit);
    }
}
