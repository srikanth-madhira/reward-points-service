package com.srikanthmadhira.portfolio.pointsservice.model;

import lombok.Data;

@Data
public class AppStatus {

	public static final String UP = "Application is UP!";
	public static final String DOWN = "Application is DOWN!!";

    private String appStatus;

    public AppStatus() {
        super();
        this.appStatus = UP;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(final String appStatus) {
        this.appStatus = appStatus;
    }

}
