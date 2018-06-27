package com.weather.metar.parse;

import java.util.regex.Pattern;

public class Regex {
	/**
	 * 报文格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMETAR(String str) {
		Pattern pattern = Pattern.compile("[]");
		return pattern.matcher(str).matches();
	}

	
	/**
	 * 4位数字能见度正则
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFourNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]{4}");
		return pattern.matcher(str).matches();
	}

	/**
	 * 温度/露点温度正则  
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTemperature(String str) {
		Pattern pattern = Pattern.compile("M?[0-9]{2}[/]M?[0-9]{2}");
		return pattern.matcher(str).matches();
	}

	/**
	 * 天气现象
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isWeatherPhenomena(String str) {
		Pattern pattern = Pattern.compile("[-+]?[A-Z]{2,6}");
		return pattern.matcher(str).matches();
	}

	/**
	 * 跑道视程
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isRVR(String str) {
		Pattern pattern = Pattern.compile("R[0-9][0-9](.+?)");
		return pattern.matcher(str).matches();
	}
}
