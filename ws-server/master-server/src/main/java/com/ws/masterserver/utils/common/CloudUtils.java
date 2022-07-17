package com.ws.masterserver.utils.common;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

/**
 * @author myname
 */
public class CloudUtils {
    @Value("${cloud.name}")
    private String name;

    @Value("${cloud.api.key}")
    private String apiKey;

    @Value("${cloud.api.secret}")
    private String apiSecret;

    public static Cloudinary getCloudinary() {
//        var config = new HashMap<>();
//        config.put("cloud_name", name);
//        config.put("api_key", apiKey);
//        config.put("api_secret", apiSecret);
        return new Cloudinary("cloudinary://384426152519968:WjFBrrrKlAs1vU42mePi4FJLPW0@hungnn22cloudinary");
    }
}
