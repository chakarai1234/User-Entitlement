package com.chakarapani.userentitlement.Service;

import com.chakarapani.base.Enums.Roles;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

public interface UserEntitlementService {

	ResponseEntity<Object> saveEntitlementForUser(Map<String, String> headers, String username, ArrayList<Roles> roles);

	ResponseEntity<Object> getEntitlementForUsername(Map<String, String> headers, String username);
}
