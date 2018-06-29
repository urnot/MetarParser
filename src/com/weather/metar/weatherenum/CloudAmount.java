package com.weather.metar.weatherenum;

/**
 * 云量 枚举
 * 
 * @author jlshen
 *
 */
public enum CloudAmount {
	FEW("FEW", "一至两分云"), SCT("SCT", "三至四分云"), BKN("BKN", "五至七分云"), OVC("OVC", "八分云"), CAVOK("CAVOK",
			"天气及能见度良好"), SKC("SKC", "无云"), NSC("NSC", "5000英尺以上无云"), TCU("TCU", "浓积云"), CB("CB", "积雨云");
	private String cloud_amount;
	private String description;

	private CloudAmount(String cloud_amount, String description) {
		this.cloud_amount = cloud_amount;
		this.description = description;
	}

	public static String getDescriptionByCloud(String cloud_amount) {
		for (CloudAmount c : CloudAmount.values()) {
			if (c.cloud_amount.equals(cloud_amount)) {
				return c.description;
			}
		}
		return "未知云量";
	}

}
