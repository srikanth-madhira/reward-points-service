package com.srikanthmadhira.portfolio.pointsservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedeemResponse {

	private String createTime;
	private String userName;
	private int points;
	/**
	 * default noargs constructor
	 * 
	 */
	public RedeemResponse() {
		super();
	}

	
}
