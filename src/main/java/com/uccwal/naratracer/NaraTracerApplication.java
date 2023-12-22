package com.uccwal.naratracer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NaraTracerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaraTracerApplication.class, args);
    }

}
