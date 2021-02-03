package com.srikanthmadhira.portfolio.pointsservice.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;

/**
 * 
 * Dao layer interface containing all the methods used in the Points Service GET service layer.
 * 
 * list of methods: 
 * 		- {@link IGetPointsDao#getAll(Pageable)}.
 * 
 * @author SM053453
 *
 */
public interface IGetPointsDao {

	/**
	 * 
	 * Dao layer method for accessing Data for the Points Service GET endpoints.
	 * 
	 * @param pagination
	 * 		- an object of {@link Pageable} type
	 * 		 that limits the number of results a user would see to a limited size.
	 * @return
	 * 		- {@link List} of {@link PointsResponse} 
	 * 		containing the paginated results from aggregation execution.
	 */
	public List<PointsResponse> getAll(final Pageable pagination);

}
