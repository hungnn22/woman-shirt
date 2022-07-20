package com.ws.master_service;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

//@SpringBootTest
class MasterServiceApplicationTests {

    @Test
    void contextLoads() {
        try {
            var random = SecureRandom.getInstanceStrong();
            IntStream.range(1, 20).forEach(i -> random.nextInt(3));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
