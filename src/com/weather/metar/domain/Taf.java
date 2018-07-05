package com.weather.metar.domain;

import java.util.List;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;

public class Taf {
	private String airport_code;
	private String taf_type;
	private String report_time;
	private String begin_time;
	private String end_time;
	private int visibility;
	private Wind wind;
	private List<Cloud>colud;
	private String max_temp;
	private String max__temp_time;
	private String min_temp;
	private String min__temp_time;
	private String type;
	List<WeatherPhenomena> phenomenas;
	private String wind_shear;
	public String getAirport_code() {
		return airport_code;
	}
	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}
	public String getTaf_type() {
		return taf_type;
	}
	public void setTaf_type(String taf_type) {
		this.taf_type = taf_type;
	}
	public String getReport_time() {
		return report_time;
	}
	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public List<Cloud> getColud() {
		return colud;
	}
	public void setColud(List<Cloud> colud) {
		this.colud = colud;
	}
	public String getMax_temp() {
		return max_temp;
	}
	public void setMax_temp(String max_temp) {
		this.max_temp = max_temp;
	}
	public String getMax__temp_time() {
		return max__temp_time;
	}
	public void setMax__temp_time(String max__temp_time) {
		this.max__temp_time = max__temp_time;
	}
	public String getMin_temp() {
		return min_temp;
	}
	public void setMin_temp(String min_temp) {
		this.min_temp = min_temp;
	}
	public String getMin__temp_time() {
		return min__temp_time;
	}
	public void setMin__temp_time(String min__temp_time) {
		this.min__temp_time = min__temp_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public List<WeatherPhenomena> getPhenomenas() {
		return phenomenas;
	}
	public void setPhenomenas(List<WeatherPhenomena> phenomenas) {
		this.phenomenas = phenomenas;
	}
	public String getWind_shear() {
		return wind_shear;
	}
	public void setWind_shear(String wind_shear) {
		this.wind_shear = wind_shear;
	}
	
	

}
