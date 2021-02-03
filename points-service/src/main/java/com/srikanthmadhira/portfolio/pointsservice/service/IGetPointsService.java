package com.srikanthmadhira.portfolio.pointsservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;

/**
 * 
 * Interface for the Service layer supporting the GET controller endpoint. This
 * interface contains the method definitions to be used in the GET controller
 * endpoint.
 * 
 * Contains the following methods:
 * 		- {@link IGetPointsService#getAll(Pageable)}
 * 
 * @author SM053453
 *
 */
public interface IGetPointsService {

	/**
	 * Service layer method for getting all points/regards history located on the
	 * database.
	 * 
	 * @param pagination - {@link Pageable} object to define pagination
	 * 
	 * @return {@link List} of {@link PointsResponse} - ArrayList containing the
	 *         paginated list of PointsResponse objects as JSON
	 */
	public List<PointsResponse> getAll(final Pageable pagination);

}
