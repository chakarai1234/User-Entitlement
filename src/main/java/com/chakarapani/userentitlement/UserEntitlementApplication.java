package com.chakarapani.userentitlement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// Enabling the Eureka Client
@EnableEurekaClient
@SuppressWarnings("unused")
public class UserEntitlementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserEntitlementApplication.class, args);
    }

    // Creating a bean so that can Autowire it later
    // And Load Balanced for the gateway to access
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Creating a ObjectMapper bean so that later used to convert the response json from resttemplate to object
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper newObjMapper = new ObjectMapper();
        newObjMapper.registerModule(new JavaTimeModule());
        return newObjMapper;
    }

}
