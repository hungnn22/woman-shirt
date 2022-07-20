package com.ws.masterserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

//@SpringBootTest
class MasterServerApplicationTests {

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
