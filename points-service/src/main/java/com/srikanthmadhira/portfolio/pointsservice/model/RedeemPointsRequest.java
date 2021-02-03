package com.srikanthmadhira.portfolio.pointsservice.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedeemPointsRequest {
	
	@Field("accountId")
	private long accountId;
	@Field("points")
	private int points;

}
