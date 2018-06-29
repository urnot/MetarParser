package com.weather.metar.test;

import java.util.regex.Pattern;

public class StringTest {
	public static void main(String[] args) {
		String str="TX34/07Z";
		System.out.println(str.startsWith("T") );
		System.out.println(str.endsWith("Z"));
		System.out.println(str.length() == 7);
		//[A-Z]{4}/s+[0-9]{6}Z(.+?)
		//(METAR|SPECI)/s?(.+?)
//		Pattern pattern=Pattern.compile("?.*(Z|V|R)^?(B|G|H|L|P|S|U|W|Y|C|H|M)[A-Z][A-Z]");
//		String str="VMMC";
//		System.out.println( pattern.matcher(str).matches());
	}

}
