package com.srikanthmadhira.portfolio.pointsservice.model.response;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PointsResponse {
	
	@Id
	private String id;
	@Field("createTime")
	private Date createTime;
	@Field("accountId")
	private long accountId;
	@Field("userName")
	private String userName;
	@Field("points")
	private int points;

	public PointsResponse(Date createTime, long accountId, String userName, int points) {
		this.createTime = createTime;
		this.accountId = accountId;
		this.userName = userName;
		this.points = points;
	}
	
}
