package com.weather.metar.weatherenum;

public enum SigmentCode {
	TEMPO("TEMPO","期间"),
	BECMG("BECMG","逐渐转变"),
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
	//惡劣天氣及大氣現象
	TS("TS","雷暴"),
	VA("VA","火山灰"),
	ICE("ICE","结冰"),
	IC("IC","积冰"),
	GR("GR","雹"),
	DS("DS","尘暴"),
	SS("SS","暴风沙"),
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
	LTD("LTD","范围")
	;
	private String code;
	private String description;
	private SigmentCode(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public static String getDescriptionByCode(String code) {
		for (SigmentCode sc : SigmentCode.values()) {
			if (sc.code.equals(code)) {
				return sc.description;
			}
		}
		return "未知sigment编码";
	}
}
