package com.example.landrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LandRouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandRouterApplication.class, args);
    }

}
