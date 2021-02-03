package com.srikanthmadhira.portfolio.pointsservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;
import com.srikanthmadhira.portfolio.pointsservice.service.IGetPointsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PointsServiceGetController {
	IGetPointsService service;
	
    /**
     * Autowired Constructor supporting dependency injection 
     * for point service query controller.
     *
     * @param service
     *            - {@link IGetPointsService} instance
     */
	@Autowired
	public PointsServiceGetController(IGetPointsService service) {
		this.service = service;
	}

	

    /**
     * Endpoint for getting all points/regards history located on the database.
     * 
     * @param pagination
     *            - Pageable object to define pagination
     * 
     * @return {@link ResponseEntity} of {@link List} of {@link PointsResponse} 
     *      - ResponseEntity object consisting of paginated list of PointsResponse objects as JSON
     */
    @Operation(description = "Earn reward points")
	@GetMapping(value= "/points", produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameter(name = "page", in = ParameterIn.QUERY, description = 
    "Results page you want to retrieve (0..N)", example = "1", schema = @Schema(type = "int"))
	@Parameter(name = "size", in = ParameterIn.QUERY, description = 
    "Number of records per page.", example = "1", schema = @Schema(type = "int"))
	public ResponseEntity<List<PointsResponse>> getAll(
			@Parameter(hidden = true) @PageableDefault(size = 50) final Pageable pagination){
		log.debug("Entered getAll method in the controller at [{}]", System.currentTimeMillis());
		List<PointsResponse> result = service.getAll(pagination);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
