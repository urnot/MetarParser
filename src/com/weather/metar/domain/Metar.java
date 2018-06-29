package com.weather.metar.domain;

import java.util.List;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.RunawayVisualRange;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;

/**
 * 机场天气报告->METAR报 SPECI报  for CHINA 
 * @author jlshen
 *
 */
public class Metar {
	// 预报类型 METAR实况 or SPECI特选报
	private String report_type;
	// 机场四字代码
	private String airport_code;
	// 预报时间 世界时 200300Z->20日03时00分Z
	private String report_time;
	// 风
	private Wind wind;
	// 能见度
	private int visibility;
	// VV垂直能见度
	private int vertical_visibility;
	// 跑道视程
	private List<RunawayVisualRange> rvr;
	// 天气现象 可能多组
	private List<WeatherPhenomena> phenomena;
	// 云组 可能多组
	private List<Cloud> cloud_group;
	// 气温
	private int air_temperature;
	// 露点温度
	private int dewpoint_temperature;
	// 修正海平面气压
	private int pressure;
	private String forecast;

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getAirport_code() {
		return airport_code;
	}

	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public List<RunawayVisualRange> getRvr() {
		return rvr;
	}

	public void setRvr(List<RunawayVisualRange> rvr) {
		this.rvr = rvr;
	}

	public List<WeatherPhenomena> getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(List<WeatherPhenomena> phenomena) {
		this.phenomena = phenomena;
	}

	public List<Cloud> getCloud_group() {
		return cloud_group;
	}

	public void setCloud_group(List<Cloud> cloud_group) {
		this.cloud_group = cloud_group;
	}

	public int getAir_temperature() {
		return air_temperature;
	}

	public void setAir_temperature(int air_temperature) {
		this.air_temperature = air_temperature;
	}

	public int getDewpoint_temperature() {
		return dewpoint_temperature;
	}

	public void setDewpoint_temperature(int dewpoint_temperature) {
		this.dewpoint_temperature = dewpoint_temperature;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

}
