package com.weather.metar.compoment;

import java.util.List;

public class TafChange {
	private String trend;
	private String beginTime;
	private String endTime;
	private List<Cloud> cloud;
	private Wind wind;
	private String verichle_visibility;
	private List<WeatherPhenomena> phenomena;
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<Cloud> getCloud() {
		return cloud;
	}
	public void setCloud(List<Cloud> cloud) {
		this.cloud = cloud;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public String getVerichle_visibility() {
		return verichle_visibility;
	}
	public void setVerichle_visibility(String verichle_visibility) {
		this.verichle_visibility = verichle_visibility;
	}
	public List<WeatherPhenomena> getPhenomena() {
		return phenomena;
	}
	public void setPhenomena(List<WeatherPhenomena> phenomena) {
		this.phenomena = phenomena;
	}

}
