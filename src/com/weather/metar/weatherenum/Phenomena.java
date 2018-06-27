package com.weather.metar.weatherenum;

/**
 * 天气现象 修饰词及描述枚举
 * @author jlshen
 *
 */
public enum Phenomena {
	VC("VC","附近"),
	MI("MI", "浅的"), BC("BC", "散片状"), DR("DR", "低吹的"), BL("BL", "高吹的"), SH("SH", "阵"), TS("TS", "雷暴的"), FZ("FZ",
			"冻"), PR("PR", "部分范围的"),

	DZ("DZ", "毛毛雨"), RA("RA", "雨"), SN("SN", "雪"), SG("SG", "米雪"), IC("IC", "冰针"), PE("PE", "冰粒"), GR("GR",
			"冰雹"), GS("GS", "小冰雹"),

	BR("BR", "轻雾"), FG("FG", "雾"), FU("FU", "烟"), VA("VA", "火山灰"), DU("DU", "大范围浮沉"), SA("SA", "沙"), HZ("HZ", "霾"),

	PO("BR", "尘/尘卷风"), SQ("FG", "飑"), FC("FU", "漏斗云"), SS("VA", "沙暴"), DS("DU", "尘暴");
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
