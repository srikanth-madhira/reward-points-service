package com.srikanthmadhira.portfolio.pointsservice.dao;

import java.util.List;

import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;

/**
 * 
 * Dao Interface containing method definitions for the service layer class
 * containing the methods supporting the POST endpoints.
 * 
 * @author SM053453
 *
 */
public interface IPointsAccountingDao {

	/**
	 * Dao method for the Accounting controller for facilitating "earning" points.
	 * 
	 * @param request - an object of {@link Points} type containing the information
	 *                about accountId, userName and points being earned.
	 * 
	 */
	public void earnPoints(final Points request);

	/**
	 * Dao method for the Accounting controller for facilitating "redeeming" points.
	 * 
	 * @param request - an object of type {@link RedeemPointsRequest} containing the
	 *                accountId to redeem the points from, and the number of points
	 *                to redeem.
	 * 
	 * @return - {@link List} of {@link RedeemResponse} containing the details about
	 *         who contributed how many points for the final redemption.
	 */
	public List<RedeemResponse> redeemPoints(final RedeemPointsRequest request);

}
