package com.srikanthmadhira.portfolio.pointsservice.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.srikanthmadhira.portfolio.pointsservice.dao.GetPointsDaoImpl;
import com.srikanthmadhira.portfolio.pointsservice.dao.IGetPointsDao;

public class GetPointsDaoImplTests {

	private IGetPointsDao dao;
	
	@BeforeEach
	public void init() {
		dao = new GetPointsDaoImpl();
	}
	
	@Test
	@Disabled
	public void getAllSuccess() {
		dao.getAll(Pageable.unpaged());
	}

}
