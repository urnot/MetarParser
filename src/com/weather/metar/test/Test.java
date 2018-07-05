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
			System.out.println(md.parseMetar("METAR ZSSS 050230Z 13004MPS 100V170 9999 BKN013 OVC040 28/26 Q0999 BECMG TL0330 BKN015="));
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		TafDecode td = new TafDecode();
		try {
			td.parseTaf("TAF ZSYN 050105Z 050312 09005MPS 2500 -SHRA BR SCT004 BKN008 TX28/06Z TN23/12Z TEMPO 0408 1200 TSRA BR BKN004 BKN008 FEW030CB=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		

	}
}
