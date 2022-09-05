package com.chakarapani.userentitlement;


import com.chakarapani.base.Converter.RequestToJsonStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

// Suppress the raw types warning
@SuppressWarnings("rawtypes")
//Declaring that this is a RestController
@RestController
// Declaring the request mapping with value so that after the address the path of the API is declare
@RequestMapping("/api/entitlement")
// Slf4j is a logger
@Slf4j
public class userEntitlementController {

    // Autowired the bean RestTemplate so that this is singleton
    @Autowired
    private RestTemplate restTemplate;

    // Autowired the bean ObjectMapper so that this is singleton
    @Autowired
    private ObjectMapper objectMapper;

    // Declare the endpoint after /api/endpoints/**
    @GetMapping("/")
    public List getEntitlement() throws IOException {
        RequestEntity<Void> request = RequestEntity.get(URI.create("http://USERS/api/users/all")).header("x" + "-correlation-id", UUID.randomUUID().toString()).header("country", "Singapore").build();

        String json = new RequestToJsonStringConverter(objectMapper, restTemplate).generateJsonNode(request, "data");
        String correlation = new RequestToJsonStringConverter(objectMapper, restTemplate).generateJsonNode(request, "x-correlation-id");

        String CorrelationId = objectMapper.readValue(correlation, String.class);
        List users1 = objectMapper.readValue(json, List.class);
        log.info(CorrelationId);
        return users1;

    }

}
