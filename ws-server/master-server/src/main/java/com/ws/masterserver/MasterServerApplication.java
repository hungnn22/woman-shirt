package com.ws.masterserver;

import com.ws.masterserver.utils.seeder.BatchSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableJpaAuditing
public class MasterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterServerApplication.class, args);
//        try {
//            new BatchSeeder().seed();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }
}
