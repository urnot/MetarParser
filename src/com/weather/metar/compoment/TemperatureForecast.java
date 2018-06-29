package com.weather.metar.compoment;

/**
 * 气温预报时间 温度 for TAF
 * 
 * @author jlshen
 *
 */
public class TemperatureForecast {
	// taf 时间
	private String time;
	// 最大还是最小
	private int max_or_min; // 1 max 0 min
	// 气温数值
	private String tempareture;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMax_or_min() {
		return max_or_min;
	}

	public void setMax_or_min(int max_or_min) {
		this.max_or_min = max_or_min;
	}

	public String getTempareture() {
		return tempareture;
	}

	public void setTempareture(String tempareture) {
		this.tempareture = tempareture;
	}

}
