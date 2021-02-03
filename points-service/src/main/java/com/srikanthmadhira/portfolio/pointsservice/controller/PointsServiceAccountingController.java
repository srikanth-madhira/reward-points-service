package com.srikanthmadhira.portfolio.pointsservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;
import com.srikanthmadhira.portfolio.pointsservice.model.response.ResponseModel;
import com.srikanthmadhira.portfolio.pointsservice.service.IPointsAccountingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * REST controller for POST endpoints for Points Service.
 * 
 * @author SM053453
 *
 */
@Slf4j
@RestController
public class PointsServiceAccountingController {

	IPointsAccountingService service;

	/**
	 * 
	 * Autowired constructor for {@link PointsServiceAccountingController} class
	 * that supports dependency injection.
	 * 
	 * @param service - an object of type {@link IPointsAccountingService}
	 */
	@Autowired
	public PointsServiceAccountingController(final IPointsAccountingService service) {
		this.service = service;
	}

	/**
	 * 
	 * This is a POST endpoint on the Points Service to facilitate "earning" of
	 * reward points.
	 * 
	 * @param request - an object of type {@link Points} containing the accountId,
	 *                userName and points being earned.
	 * @return - an object of {@link ResponseEntity} of {@link String} that tells us
	 *         that the points history has successfully been added.
	 */
	@Operation(description = "Earn reward points")
	@PostMapping(value = "/points/earn", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseModel> earnPoints(@Valid @RequestBody Points request) {
		Assert.notNull(request, "Request body cannot be null.");
		Assert.notNull(request.getAccountId(), "accountId cannot be null.");
		Assert.notNull(request.getUserName(), "userName cannot be null.");
		Assert.notNull(request.getPoints(), "points cannot be null.");
		log.debug("Entered earnPoints method with accoundtId [{}] and userName [{}]", request.getAccountId(),
				request.getUserName());
		service.earnPoints(request);
		return new ResponseEntity<>(new ResponseModel("Added " + request.getPoints() + " points."), HttpStatus.OK);
	}

	/**
	 * 
	 * This is a POST endpoint on the Points Service to facilitate "redeeming" of
	 * reward points.
	 * 
	 * @param request - an object of type {@link Points} containing the accountId
	 *                and total number of points to be redeemed.
	 * @return - an object of {@link ResponseEntity} of {@link List} of
	 *         {@link RedeemResponse} that tells us how many points have been
	 *         redeemed from which user.
	 */
	@Operation(description = "Redeem reward points")
	@PostMapping(value = "/points/redeem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RedeemResponse>> spendPoints(@Valid @RequestBody RedeemPointsRequest request) {
		Assert.notNull(request, "Request body cannot be null.");
		Assert.notNull(request.getAccountId(), "accountId cannot be null.");
		Assert.notNull(request.getAccountId(), "points cannot be null.");
		log.debug("Entered redeemPoints method with accoundtId [{}]", request.getAccountId());
		return new ResponseEntity<>(service.redeemPoints(request), HttpStatus.OK);
	}

}
