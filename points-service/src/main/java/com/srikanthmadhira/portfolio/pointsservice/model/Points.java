package com.srikanthmadhira.portfolio.pointsservice.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Points {
	
	private long accountId;
	@Hidden
	private Date createTime;
	private String userName;
	private int points;
	/**
	 *  no args constructor.
	 */
	public Points() {
		super();
	}
	
	

}
