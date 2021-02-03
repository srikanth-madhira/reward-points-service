package com.srikanthmadhira.portfolio.pointsservice.service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.srikanthmadhira.portfolio.pointsservice.dao.IGetPointsDao;

public class GetPointsServiceImplTests {
	
	private IGetPointsDao mockDao = mock(IGetPointsDao.class);
	
	private IGetPointsService service;
	
	@BeforeEach
	public void init() {
		service = new GetPointsServiceImpl(mockDao);
	}

	@Test
	public void getAllSuccessTest() {
		service.getAll(Pageable.unpaged());
	}

}
