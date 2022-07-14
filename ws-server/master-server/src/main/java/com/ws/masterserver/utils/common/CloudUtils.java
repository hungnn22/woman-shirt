package com.ws.masterserver.utils.common;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

/**
 * @author myname
 */
public class CloudUtils {
    @Value("${cloud.name}")
    private static String name;

    @Value("${cloud.api.key}")
    private static String apiKey;

    @Value("${cloud.api.secret}")
    private static String apiSecret;

    public static Cloudinary getCloudinary() {
        var config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
