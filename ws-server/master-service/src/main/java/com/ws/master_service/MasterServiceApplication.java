package com.ws.master_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MasterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterServiceApplication.class, args);
//        try {
//            new BatchSeeder().seed();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }
}
