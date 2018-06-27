package com.weather.metar.weatherenum;

/**
 * 单位 方向等 枚举
 * 
 * @author jlshen
 *
 */
public enum Unit {
	MPS("MPS", "m/s"), KT("KT", "kt"), KMH("KMH", "km/h"),
	// 跑道视程趋势
	U("U", "上升趋势"), D("D", "下降趋势"), N("N", "没有显著变化"), P("P", "最大"), M("M", "最小"),
	// 跑道方向
	L("L", "左边"), C("C", "中间"), R("R", "右边");

	private String unit_code;
	private String description;

	private Unit(String unit_code, String description) {
		this.unit_code = unit_code;
		this.description = description;
	}

	public static String getDescriptionByCode(String code) {
		for (Unit u : Unit.values()) {
			if (u.unit_code.equals(code)) {
				return u.description;
			}
		}
		if (code.equals("")) {
			return "";
		} else {
			return "未知符号";
		}
	}

}
