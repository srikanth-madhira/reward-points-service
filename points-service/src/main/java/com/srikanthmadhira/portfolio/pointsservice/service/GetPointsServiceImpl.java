package com.srikanthmadhira.portfolio.pointsservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.srikanthmadhira.portfolio.pointsservice.dao.IGetPointsDao;
import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;

/**
 * 
 * Service layer class for the points service GET endpoint.
 * @author SM053453
 *
 */
@Service
public class GetPointsServiceImpl implements IGetPointsService{

	IGetPointsDao dao;
	
	/**
	 * Autowired constructor for the service layer for the points service GET endpoint.
	 * @param dao
	 * 		- an instance of {@link IGetPointsDao}.
	 */
	@Autowired
	public GetPointsServiceImpl(final IGetPointsDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<PointsResponse> getAll(final Pageable pagination) {
		return dao.getAll(pagination);
	}

}
