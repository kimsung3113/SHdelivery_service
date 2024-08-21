package com.delivery.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        System.out.println("배달 플랫폼 시작");
        SpringApplication.run(ApiApplication.class, args);
    }

}
