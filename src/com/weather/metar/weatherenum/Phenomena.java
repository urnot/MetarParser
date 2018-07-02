package com.weather.metar.weatherenum;

/**
 * 天气现象 修饰词及描述枚举
 * @author jlshen
 *
 */
public enum Phenomena {
	VC("VC","附近"),
	MI("MI", "浅"), BC("BC", "散片状"), DR("DR", "低吹"), BL("BL", "高吹"), SH("SH", "阵"), TS("TS", "雷"), FZ("FZ",
			"冻"), PR("PR", "部分范围"),

	DZ("DZ", "毛毛雨"), RA("RA", "雨"), SN("SN", "雪"), SG("SG", "米雪"), IC("IC", "冰晶"), PL("PL", "冰粒"), GR("GR",
			"冰雹"), GS("GS", "小冰雹"),

	BR("BR", "轻雾"), FG("FG", "雾"), FU("FU", "烟"), VA("VA", "火山灰"), DU("DU", "大范围浮尘"), SA("SA", "扬沙"), HZ("HZ", "霾"),

	PO("PO", "尘/尘卷风"), SQ("SQ", "飑"), FC("FC", "漏斗云"), SS("SS", "沙暴"), DS("DS", "尘暴");
	private String code;
	private String description;

	private Phenomena(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public static String getDescriptionByCode(String phenomena) {
		for (Phenomena p : Phenomena.values()) {
//			System.out.println(p.code);
			if (p.code.equals(phenomena)) {
				return p.description;
			}
		}
		if(phenomena.equals("")) {
			return "";
		}else {
			return "未知天气现象";
		}

	}

}
