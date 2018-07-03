package com.weather.metar.test;


import com.weather.metar.core.MetarDecode;
import com.weather.metar.core.TafDecode;
import com.weather.metar.exception.MetarParseException;

public class Test {

	
	public static void main(String[] args) {
		
		//todoVRB02G17MPS
		// TODO 含AUTO会有//缺测数据
		MetarDecode md = new MetarDecode();
		try {
			System.out.println(md.parseMetar("METAR ZLXY 030600Z 06004MPS 3500 BR FEW005 SCT015 OVC050 21/19 Q1002 NOSIG="));
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		TafDecode td = new TafDecode();
		try {
			td.parseTaf("TAF ZLXY 030424Z 030615 06003MPS 3000 BR FEW004 SCT015 OVC050 TX25/08Z TN23/15Z BECMG 1112 2000 -RA BR=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		

	}
}
