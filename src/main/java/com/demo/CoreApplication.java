package com.demo;

import com.demo.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return args -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }
}