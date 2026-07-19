package com.example.demo.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketBookingAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Ticket Booking API")
                        .description("REST APIs for Ticket Booking System")
                        .version("1.0"));
    }
}
