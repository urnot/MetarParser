package com.weather.metar.parse;

import com.weather.metar.domain.Metar;
import com.weather.metar.exception.MetarParseException;

public class MetarDecode {
	Metar m = new Metar();

	void parseType(String str) throws MetarParseException {
		if (Regex.isMETAR(str)) {
			m.setReport_type(str);
		} else {
			throw new MetarParseException("input string should be starts with 'METAR' or 'SPECI'");
		}
	};

	void parseICAO(String str) throws MetarParseException {
		if (Regex.isChinaICAO(str)) {
			m.setAirport_code(str);
		} else {
			throw new MetarParseException("only support CHINA ICAO code now");
		}
	};

	void parseTime(String str) throws MetarParseException {
		if (Regex.isTimeZ(str) && str.length() == 7) {
			m.setReport_time(
					"世界时 " + str.substring(0, 2) + "日" + str.substring(2, 4) + "时" + str.substring(4, 6) + "分预报");
		} else {
			throw new MetarParseException("exception in time decode");
		}
	};

	void parseWind(String str) {
		
	};

	void parseVisibility(String str) {
	};

	void parseCloud(String str) {
	};

	void parseRVR(String str) {
	};

	void parseTemp(String str) {
	};

	void parsePressure(String str) {
	};

}
