package com.weather.metar.weatherenum;

/**
 * 天气现象 修饰词及描述枚举
 * @author jlshen
 *
 */
public enum Phenomena {
	VC("VC","附近"),
	MI("MI", "浅"), BC("BC", "散片状"), DR("DR", "低吹"), BL("BL", "高吹"), SH("SH", "阵"), TS("TS", "雷暴"), FZ("FZ",
			"冻"), PR("PR", "部分范围"),

	DZ("DZ", "毛毛雨"), RA("RA", "雨"), SN("SN", "雪"), SG("SG", "米雪"), IC("IC", "冰晶"), PL("PL", "冰粒"), GR("GR",
			"冰雹"), GS("GS", "小冰雹"),

	BR("BR", "轻雾"), FG("FG", "雾"), FU("FU", "烟"), VA("VA", "火山灰"), DU("DU", "大范围浮尘"), SA("SA", "扬沙"), HZ("HZ", "霾"),

	PO("PO", "尘/尘卷风"), SQ("SQ", "飑"), FC("FC", "漏斗云"), SS("SS", "沙暴"), DS("DS", "尘暴"),
	NOSIG("NOSIG","无显著变化"),
	TEMPO("TEMPO","以下天气现象预计会暂时出现:"),
	BECMG("BECMG","以下天气现象预计会很快出现："),
	PROB30("PROB30","有30%概率"),
	PROB40("PROB40","有40%概率"),
	FM("FM","从。。开始"),
	TL("TL","直到"),
	AT("AT","在。。时"),
	S("S","南纬"),
	N("N","北纬"),
	E("E","东经"),
	W("W","西经"),
	TO("TO","到"),
	CLD("CLD","云"),
	ABV("ABV","在..之上"),
	BTN("BTN","在..之间"),
	FCTS("FCTS","预报"),
	DEGE("DEGE","东经"),
	DEGN("DEGN","北纬"),
	HVY("HVY","严重的"),
	INTSF("INTSF","正在加强"),
	MOD("MOD","中等强度"),
	OBSC("OBSC","模糊的"),
	SER("SER","强烈"),
 	INTST("INTST","强度"),
 	EMBD("EMBD","内含"),

	ICE("ICE","结冰"),

	TURB("TURB","颠簸"),
	MTW("MTW","山脉波动"),
	STNR("STNR","稳定"),
	TC("TC","热带气旋"),
	QUASISTNR("QUASISTNR","准静止"),
	SQL("SQL","狂风"),
	APRX("APRX","大约"),
	RDOACT("RDOACT","辐射"),
	NC("NC","没有变化"),
	OBS("OBS","观察到"),
	OTLK("OTLK","预测"),
	RAPID("RAPID","快速地"),
	MOV("MOV","移动"),
	SEV("SEV","强烈地"),
	TOP("TOP","顶"),
	WDSPR("WDSPR","范围扩大"),
	WKN("WKN","正在减弱"),
	LTD("LTD","范围")	;
	
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
