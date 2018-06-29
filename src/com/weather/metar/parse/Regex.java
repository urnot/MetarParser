package com.weather.metar.parse;

import java.util.regex.Pattern;

public class Regex {
	
	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @return
	 */
	public static int parseString(String str) {
		return Integer.valueOf(str);
	}
	
	/**
	 * 报文格式 是否以METAR或者SPECI开头
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMETAR(String str) {
		Pattern pattern = Pattern.compile("(METAR|SPECI).+?\",Pattern.CASE_INSENSITIVE");
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
	 * 4位字母机场代码正则 暂时限制中国全境
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinaICAO(String str) {
		Pattern pattern = Pattern.compile("(Z|V|R)(B|G|H|L|P|S|U|W|Y|C|H|M)[A-Z][A-Z]");
		return pattern.matcher(str).matches();
	}
	/**
	 * 4位数字能见度正则
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTimeZ(String str) {
		Pattern pattern = Pattern.compile("[0-9]{6}Z");
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
	/**
	 * 是否有效时间格式 hhmm/hhmm
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValidTime(String str) {
		Pattern pattern = Pattern.compile("[0-9]{6}");
		return pattern.matcher(str).matches();
	}
}
