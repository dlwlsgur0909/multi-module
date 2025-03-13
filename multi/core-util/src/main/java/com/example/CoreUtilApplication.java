package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class CoreUtilApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CoreUtilApplication.class);
        app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/application-core.yml"));
        app.run(args);
//        SpringApplication.run(CoreUtilApplication.class, args);
    }

}