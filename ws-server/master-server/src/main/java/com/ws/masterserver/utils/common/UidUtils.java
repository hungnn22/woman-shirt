package com.ws.masterserver.utils.common;

import java.util.UUID;

public class UidUtils {

    private UidUtils() {}

    public static String generateUid() {
        return UUID.randomUUID().toString();
    }

    public static String cleanUid(String uid) {
        return uid.replace("\"", "").trim();
    }
}
