package com.weather.metar.compoment;

import com.weather.metar.weatherenum.Phenomena;

/**
 * 天气现象描述
 * 
 * @author jlshen
 *
 */
public class WeatherPhenomena {
	// 强度
	private String itensity;
	// 形容词
	private String adjective;
	// 天气现象
	private String phenomena;

	public String getItensity() {
		return itensity;
	}

	public void setItensity(String itensity) {
		this.itensity = itensity;
	}

	public String getAdjective() {
		return adjective;
	}

	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}

	public String getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(String phenomena) {
		this.phenomena = phenomena;
	}

	@Override
	public String toString() {
		String itensity_result = "";
		switch (this.itensity) {
		case "-":
			itensity_result = "小";
			break;
		case "+":
			itensity_result = "大";
			break;

		}
		// TODO Auto-generated method stub
		return itensity_result + Phenomena.getDescriptionByCode(this.adjective)
				+ Phenomena.getDescriptionByCode(this.phenomena) ;
	}

}
