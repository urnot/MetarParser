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
			System.out.println(md.parseMetar("METAR ZLHZ 052200Z AUTO 06002MPS 0750 R07/0200V1200 // ////// 25/24 Q1004= "));
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		TafDecode td = new TafDecode();
		try {
			td.parseTaf("TAF ZSAM 052205Z 060009 20004G09MPS 8000 SCT030 TX34/06Z TN29/00Z TEMPO 0509 FEW026CB SCT026=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
		

	}
}
