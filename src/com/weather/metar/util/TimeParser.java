package com.weather.metar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeParser {

	/**
	 * @param time
	 *            ddhhmm日时分
	 * @return
	 */
	public static String getBJT(String time) {
		time = time.replace("Z", "");
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		String report_time = cal.get(Calendar.YEAR) + ((month < 10) ? ("0" + month) : month + "") + time;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		try {
			date = sdf.parse(report_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 8);// 得到yyyy-MM-dd HH:mm格式的bj时间

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String bjtime = format.format(cal.getTime());
		return bjtime;
	}
	/**
	 * @param time
	 *            ddhhhh 日时~時
	 * @return
	 */
	public static String getValidBJT(String time,String day,String hour) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		String report_time = cal.get(Calendar.YEAR) + ((month < 10) ? ("0" + month) : month + "") + day+hour;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		try {
			date = sdf.parse(report_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 8);// 得到yyyy-MM-dd HH:mm格式的bj时间

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String bjtime = format.format(cal.getTime());
		return bjtime;
	}


	public static void main(String[] args) {
		System.out.println(getValidBJT("030312","03","03"));
	}
}
