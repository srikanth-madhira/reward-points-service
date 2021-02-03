package com.srikanthmadhira.portfolio.pointsservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.srikanthmadhira.portfolio.pointsservice.dao.IPointsAccountingDao;
import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;

public class PointsAccountingServiceImplTests {
	
	private IPointsAccountingDao mockDao = mock(IPointsAccountingDao.class);
	
	private IPointsAccountingService service;
	
	@BeforeEach
	public void init() {
		service = new PointsAccountingServiceImpl(mockDao);
	}

	@Test
	public void earnPointsSuccessTest() {
		service.earnPoints(new Points(0, null, null, 0));
	}

	@Test
	public void redeemPointsSuccessTest() {
		
		List<RedeemResponse> responseBean = new ArrayList<>();
		RedeemResponse response = new RedeemResponse("now", "bobTheBuilder", 100);
		responseBean.add(response);
		when(mockDao.redeemPoints(any(RedeemPointsRequest.class))).thenReturn(responseBean);
		RedeemPointsRequest request = new RedeemPointsRequest(0, 0);
		List<RedeemResponse> result = service.redeemPoints(request);
		verify(mockDao,times(1)).redeemPoints(request);
		verifyNoMoreInteractions(mockDao);
		assertThat(result.size()).isEqualTo(1);
	}

}
