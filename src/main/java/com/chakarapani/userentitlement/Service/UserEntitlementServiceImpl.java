package com.chakarapani.userentitlement.Service;

import com.chakarapani.base.Converter.RequestToJsonStringConverter;
import com.chakarapani.base.Entity.Entitlement;
import com.chakarapani.base.Entity.Users;
import com.chakarapani.base.Enums.Message;
import com.chakarapani.base.Enums.Roles;
import com.chakarapani.base.Response.Response;
import com.chakarapani.userentitlement.Repository.UserEntitlementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static com.chakarapani.base.Constants.Constants.*;

//@formatter:on
@Service
@Slf4j
public class UserEntitlementServiceImpl implements UserEntitlementService {

	//Autowired the UsersEntitlement repository
	@Autowired
	private UserEntitlementRepository userEntitlementRepository;

	// Autowired the bean RestTemplate so that this is singleton
	@Autowired
	private RestTemplate restTemplate;

	// Autowired the bean ObjectMapper so that this is singleton
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public ResponseEntity<Object> saveEntitlementForUser(@NotNull Map<String, String> headers,
	                                                     @NotNull String username, Roles roles) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (username.equals("")) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please enter the username");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST,
					HEADERSMISSINGVALUE);
		} else {
			RequestEntity<Void> request = RequestEntity.get(
							URI.create("http://ms-users/api/users/user?username=" + username))
					.header(HEADERCORRELEATIONTITLE, xCorrId).header(HEADERCOUNTRYTITLE, country)
					.build();

			try {
				String json = new RequestToJsonStringConverter(objectMapper,
						restTemplate).generateStringResponseFromRequest(request, "data");
				Users users1 = objectMapper.readValue(json, Users.class);
				Entitlement entitlementFromRepository =
						userEntitlementRepository.findByUsername(users1.getUsername());
				if (entitlementFromRepository == null) {
					Entitlement usersEntitlement1 = userEntitlementRepository.save(
							Entitlement.builder().userId(users1.getId())
									.username(users1.getUsername()).roles(roles).build());
					return Response.generateResponse(Message.SUCCESS, headers, HttpStatus.OK,
							usersEntitlement1);
				} else {
					return Response.generateResponse(Message.FAILURE, headers,
							HttpStatus.BAD_REQUEST, "User already has entitlement");
				}

			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
				return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST,
						USERNOTEXISTS);
			}

		}
	}

	@Override
	public ResponseEntity<Object> getEntitlementForUsername(@NotNull Map<String, String> headers,
	                                                        @NotNull String username) {
		String xCorrId = headers.get(HEADERCORRELEATIONTITLE);
		String country = headers.get(HEADERCOUNTRYTITLE);
		if (username.equals("")) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.NOT_ACCEPTABLE,
					"Please enter the username");
		} else if (xCorrId == null || country == null) {
			return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST,
					HEADERSMISSINGVALUE);
		} else {

			RequestEntity<Void> request = RequestEntity.get(
							URI.create("http://ms-users/api/users/user?username=" + username))
					.header(HEADERCORRELEATIONTITLE, xCorrId).header(HEADERCOUNTRYTITLE, country)
					.build();

			try {
				String json = new RequestToJsonStringConverter(objectMapper,
						restTemplate).generateStringResponseFromRequest(request, "data");
				Users users1 = objectMapper.readValue(json, Users.class);
				assert users1 != null;
				Entitlement userEntitlement = userEntitlementRepository.findByUsername(username);
				return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST,
						userEntitlement);

			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
				return Response.generateResponse(Message.FAILURE, headers, HttpStatus.BAD_REQUEST,
						USERNOTEXISTS);
			}

		}
	}
}
