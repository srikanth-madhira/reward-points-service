package com.srikanthmadhira.portfolio.pointsservice.service;

import java.util.List;

import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;

/**
 * 
 * Interface class containing the method definitions/signatures for supporting
 * the REST AccountingController on the Points Service API.
 * 
 * @author SM053453
 *
 */
public interface IPointsAccountingService {

	/**
	 * Service layer method supporting "earn points" operation carried out in the
	 * POST Accounting controller.
	 * 
	 * @param points - an object of type {@link Points} containing the accountId,
	 *               userName to which points are to be added and the quantity of
	 *               points to be added.
	 */
	public void earnPoints(final Points points);

	/**
	 * Service layer method supporting "redeem points" operation carried out in the
	 * POST Accounting controller.
	 * 
	 * @param request - an object of type {@link RedeemPointsRequest} containing the
	 *                accountId from which points are to be redeemed and the
	 *                quantity of points to be redeemed.
	 * @return - {@link List} of {@link RedeemResponse} object containing the
	 *         redemption statistics.
	 */
	public List<RedeemResponse> redeemPoints(final RedeemPointsRequest request);

}
