package com.chakarapani.userentitlement.Controller;


import com.chakarapani.base.Enums.Roles;
import com.chakarapani.userentitlement.Service.UserEntitlementServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

import static com.chakarapani.base.Constants.Constants.GATEWAYURL;


//Declaring that this is a RestController
@RestController
// Declaring the request mapping with value so that after the address the path of the API is declare
@RequestMapping("/api/entitlement")
// Slf4j is a logger
@Slf4j
@CrossOrigin(origins = GATEWAYURL)
public class userEntitlementController {

	//Autowire the UsersEntitlement service here
	@Autowired
	private UserEntitlementServiceImpl userEntitlementService;

	// Declare the endpoint after /api/endpoints/**
	@PostMapping("/save")
	@ApiResponses(value = {
			@ApiResponse(description = "Success", useReturnTypeSchema = true, responseCode = "200",
					content = @Content(mediaType = "application/json"))
	})
	@Parameters(value = {
			@Parameter(name = "x-correlation-id", in = ParameterIn.HEADER, required = true,
					example = "c4690ee8-f3f5-4a68-b08d-a7ecdf8fb851"),
			@Parameter(name = "country", in = ParameterIn.HEADER, required = true, example = "SG"),
			@Parameter(name = "username", in = ParameterIn.QUERY, required = true, example = "chakarai1234")
	})
	public ResponseEntity<Object> setEntitlementForUser(@RequestHeader Map<String, String> headers,
	                                                    @RequestParam(name = "username") String username,
	                                                    @RequestBody ArrayList<Roles> roles) {
		return userEntitlementService.saveEntitlementForUser(headers, username, roles);
	}


	@GetMapping("/entitlement")
	@ApiResponses(value = {
			@ApiResponse(description = "Success", useReturnTypeSchema = true, responseCode = "200",
					content = @Content(mediaType = "application/json"))
	})
	@Parameters(value = {
			@Parameter(name = "x-correlation-id", in = ParameterIn.HEADER, required = true,
					example = "c4690ee8-f3f5-4a68-b08d-a7ecdf8fb851"),
			@Parameter(name = "country", in = ParameterIn.HEADER, required = true, example = "SG"),
			@Parameter(name = "username", in = ParameterIn.QUERY, required = true, example = "chakarai1234")
	})
	public ResponseEntity<Object> getEntitlementForUsername(@RequestHeader Map<String, String> headers,
	                                                        @RequestParam(name = "username") String username) {
		return userEntitlementService.getEntitlementForUsername(headers, username);
	}
}
