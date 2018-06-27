package com.weather.metar.test;

import com.weather.metar.domain.Metar;
import com.weather.metar.exception.MetarParseException;

public class MyMetarParser {

	public Metar parseMetar(String report) throws MetarParseException {
		if (report == null || report.length() <= 0) {
			throw new MetarParseException("illeagle input");
		}
		if (!report.startsWith("METAR") || !report.startsWith("SPECI")) {
			throw new MetarParseException("the report should be starts with 'METAR' or 'SPECI'");
		}
		return new Metar();

	}
	// 判断格式
	// 转换大写
	// 拆分
	// 按条件parse

	public static void main(String[] args) {
		MyMetarParser parser = new MyMetarParser();
		try {
			parser.parseMetar(" ZLHZ 270100Z VRB01MPS 9999 FEW023 OVC066 26/25 Q1007 NOSIG=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
	}

}
