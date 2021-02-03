package com.srikanthmadhira.portfolio.pointsservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanthmadhira.portfolio.pointsservice.model.AppStatus;

import io.swagger.v3.oas.annotations.Operation;

/**
 * This is a GET controller class supporting "health check" operations on this
 * API.
 * 
 * @author SM053453
 *
 */
@RestController
public class HealthCheckController {

	/**
	 * This is an endpoint that is supposed to serve as a "health check" resource.
	 * 
	 * Consumers will know of the availability of the API by calling this endpoint
	 * without having to perform any resource intensive CRUD uperations.
	 * 
	 * @return - {@link ResponseEntity} of {@link AppStatus} containing the current
	 *         status of the application.
	 */
	@Operation(description = "Get application health")
	@GetMapping(value = "/health")
	public ResponseEntity<AppStatus> getHealth() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// if we can reach this method, the status will be set to "up"
		// otherwise, the user receives a 404.
		AppStatus appStatus = new AppStatus();
		return new ResponseEntity<>(appStatus, headers, HttpStatus.OK);
	}

}
