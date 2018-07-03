package com.weather.metar.weatherenum;

/**
 * 云量 枚举
 * 
 * @author jlshen
 *
 */
public enum CloudAmount {
	FEW("FEW", "少云"), SCT("SCT", "疏云"), BKN("BKN", "多云"), OVC("OVC", "满天云"), CAVOK("CAVOK",
			"天气状况良好:能见度≥10km，\r\n" + 
			"云高≥1500m"), SKC("SKC", "无云"), NSC("NSC", "5000英尺以下无云"), TCU("TCU", "浓积云"), CB("CB", "积雨云");
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
