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
	U("U", "观测前十分钟有上升趋势"), D("D", "观测前十分钟有下降趋势"), N("N", "观测前十分钟有没有显著变化"), P("P", "大于"), M("M", "低于"),
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
