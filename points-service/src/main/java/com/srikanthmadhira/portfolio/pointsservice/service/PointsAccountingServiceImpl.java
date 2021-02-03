package com.srikanthmadhira.portfolio.pointsservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanthmadhira.portfolio.pointsservice.dao.IPointsAccountingDao;
import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;

/**
 * Service layer class implementing the methods defined in
 * {@link IPointsAccountingService} interface.
 * 
 * @author SM053453
 *
 */
@Service
public class PointsAccountingServiceImpl implements IPointsAccountingService {

	IPointsAccountingDao dao;

	/**
	 * Autowired constructor for {@link PointsAccountingServiceImpl} class
	 * supporting dependency injection.
	 * 
	 * @param dao - an instance of {@link IPointsAccountingDao} object used for
	 *            interaction with data layer.
	 */
	@Autowired
	public PointsAccountingServiceImpl(IPointsAccountingDao dao) {
		this.dao = dao;
	}

	@Override
	public void earnPoints(Points points) {

		dao.earnPoints(points);

	}

	@Override
	public List<RedeemResponse> redeemPoints(RedeemPointsRequest request) {

		return dao.redeemPoints(request);
	}

}
