package com.humidty.arge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition

public class ArgeApplication {

    public static void main(String[] args) {
        System.out.println("Setting thea timezone" + TimeZone.getTimeZone("GMT+3:00").getID());
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3:00"));
        SpringApplication.run(ArgeApplication.class, args);
    }

}
