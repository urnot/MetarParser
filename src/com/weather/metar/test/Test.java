package com.weather.metar.test;


import com.weather.metar.core.MetarDecode;
import com.weather.metar.core.TafDecode;
import com.weather.metar.domain.Metar;
import com.weather.metar.domain.Taf;
import com.weather.metar.exception.MetarParseException;

public class Test {

	
	public static void main(String[] args) {
		
		//todoVRB02G17MPS
		// TODO 含AUTO会有//缺测数据
		MetarDecode md = new MetarDecode();
		Metar m = new Metar();
		try {
			m=md.parseMetar("METAR ZSSH 092200Z 05001MPS 0400 R04/0500D FG VV003 24/24 Q1008 NOSIG=");
			System.out.println(m.getCloud_group());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Taf t  = new Taf();
		TafDecode td = new TafDecode();
		try {
			t=td.parseTaf("TAF ZBYC 100747Z 100918 12004MPS 6000 -SHRA NSC TX24/09Z TN17/18Z= ");
			System.out.println(t.getTxtDecode());
			System.out.println(t.getReport_time());

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
