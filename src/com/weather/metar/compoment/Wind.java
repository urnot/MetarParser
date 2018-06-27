package com.weather.metar.compoment;

import com.weather.metar.weatherenum.Unit;

/**
 * 风
 * @author jlshen
 *
 */
public class Wind {
	// 风向
	private int wind_direction;
	// 风速
	private int wind_speed;
	// 风速单位
	private String wind_unit;
	// 是否可变风
	private boolean isVRB;
	// 是否阵风
	private boolean isGustwind;
	// 最大阵风风速
	private int max_gustwind_speed;
	// 最小阵风风向
	private int min_gustwind_direction;
	// 是否包含V代码
	private boolean containsV;
	// 最大阵风风向
	private int max_gustwind_direction;

	public int getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(int wind_direction) {
		this.wind_direction = wind_direction;
	}

	public int getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	public String getWind_unit() {
		return wind_unit;
	}

	public void setWind_unit(String wind_unit) {
		this.wind_unit = wind_unit;
	}

	public boolean isVRB() {
		return isVRB;
	}

	public void setVRB(boolean isVRB) {
		this.isVRB = isVRB;
	}

	public boolean isGustwind() {
		return isGustwind;
	}

	public void setGustwind(boolean isGustwind) {
		this.isGustwind = isGustwind;
	}

	public int isMax_gustwind_speed() {
		return max_gustwind_speed;
	}

	public void setMax_gustwind_speed(int max_gustwind_speed) {
		this.max_gustwind_speed = max_gustwind_speed;
	}

	public int getMin_gustwind_direction() {
		return min_gustwind_direction;
	}

	public void setMin_gustwind_direction(int min_gustwind_direction) {
		this.min_gustwind_direction = min_gustwind_direction;
	}

	public int getMax_gustwind_direction() {
		return max_gustwind_direction;
	}

	public void setMax_gustwind_direction(int max_gustwind_direction) {
		this.max_gustwind_direction = max_gustwind_direction;
	}

	public boolean isContainsV() {
		return containsV;
	}

	public void setContainsV(boolean containsV) {
		this.containsV = containsV;
	}

	public int getMax_gustwind_speed() {
		return max_gustwind_speed;
	}

	@Override
	public String toString() {
		String wind_range = "";
		if (this.containsV) {
			wind_range = "风向范围" + this.min_gustwind_direction + "-" + this.max_gustwind_direction;
		}
		if (this.isGustwind) {
			// TODO 阵风
			return "阵风风速" + this.wind_speed + Unit.getDescriptionByCode(this.wind_unit)
					+ (wind_range.length() > 5 ? wind_range : "") + "度,";
		} else if (this.isVRB) {
			return "可变风" + this.wind_speed + Unit.getDescriptionByCode(this.wind_unit) + ",";
		} else if (this.wind_speed == 0) {
			return "静风,";
		} else {
			return "风速" + this.wind_speed + Unit.getDescriptionByCode(this.wind_unit) + ",风向" + this.wind_direction
					+ "度," + (wind_range.length() > 6 ? wind_range : "");
		}
	}

}
