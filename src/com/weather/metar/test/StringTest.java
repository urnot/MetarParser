package com.weather.metar.test;

import java.util.regex.Pattern;

public class StringTest {
	public static void main(String[] args) {
//		String str="R18R/0325U";
//		System.out.println(str.substring(str.indexOf("/")+1,str.length()-1));
		//[A-Z]{4}/s+[0-9]{6}Z(.+?)
		Pattern pattern=Pattern.compile("(METAR|SPECI)/s?(.+?)");
		String str="METAR XXXX 020202Z wrfwvtebgt";
		System.out.println( pattern.matcher(str).matches());
	}

}
