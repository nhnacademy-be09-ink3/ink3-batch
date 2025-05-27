package com.nhnacademy.ink3batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class Ink3BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ink3BatchApplication.class, args);
    }

}
