package com.weather.metar.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.RunawayVisualRange;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;
import com.weather.metar.domain.Metar;
import com.weather.metar.weatherenum.Phenomena;

public class Test {

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
	 * 天气现象
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isRVR(String str) {
		Pattern pattern = Pattern.compile("R[0-9][0-9](.+?)");
		return pattern.matcher(str).matches();
	}

	public static void main(String[] args) {
		// TODO 含AUTO会有//缺测数据
		String metar = "METAR ZWTL 270700Z VRB02MPS CAVOK 39/09 Q1001 NOSIG=\r\n" + "";
		metar = metar.toUpperCase();
		String infos[] = metar.split("\\s+");
		Metar m = new Metar();
		List<Cloud> cloud_group = new ArrayList<>();
		Wind w = new Wind();
		List<WeatherPhenomena> phenomenas = new ArrayList<>();
		boolean flag = false;

		for (int i = 0; i < infos.length; i++) {

			// 例行天气预报实况，特殊天气报告
			if ((infos[i].equals("METAR") || infos[i].equals("SPECI")) && i == 0) {
				System.out.println("格式：" + infos[i]);
				m.setReport_time(infos[i]);
			}
			// TODO 正则 4位字母 (but四位大写字母可能是其他数据)
			if (i == 1 && infos[i].length() == 4) {
				m.setAirport_code(infos[i]);
				System.out.println("机场代码:" + infos[i] + ",");
			}

			if (infos[i].endsWith("Z") && infos[i].length() == 7) {
				m.setReport_time(infos[i]);
				System.out.println("世界时 " + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "时"
						+ infos[i].substring(4, 6) + "分预报，");
				continue;
			}
			// 风第一节
			if (infos[i].endsWith("MPS") || infos[i].endsWith("KT") || infos[i].endsWith("KMH")) {
				if (infos[i].startsWith("VRB")) {
					w.setVRB(true);

				} else if (infos[i].contains("G")) {
					w.setGustwind(true);
					w.setMax_gustwind_speed(parseString(infos[i].substring(6, 8)));
				}
				// 单独处理VRB
				if (!infos[i].startsWith("VRB")) {
					w.setWind_direction(parseString(infos[i].substring(0, 3)));
				}
				w.setWind_speed(parseString(infos[i].substring(3, 5)));
				if (infos[i].endsWith("KT")) {
					w.setWind_unit("KT");
				} else {
					w.setWind_unit(infos[i].substring(infos[i].length() - 3, infos[i].length()));
				}

				// 风第二节 如果有 正则xxxVxxx
				if (infos[i + 1].length() == 7 && infos[i + 1].indexOf("V") == 3) {
					w.setContainsV(true);
					w.setMin_gustwind_direction(parseString(infos[i + 1].substring(0, 3)));
					w.setMax_gustwind_direction(parseString(infos[i + 1].substring(4, 7)));
				}
				m.setWind(w);
				System.out.println(w);
				continue;
			}
			// TODO 能见度 四个数字 [带方向]国内暂时按4个数字处理
			if (isFourNumber(infos[i])) {
				m.setVisibility(parseString(infos[i]));
				System.out.println("能见度" + parseString(infos[i]) + "米");
				continue;
			}
			// TODO 天气现象 VC not defined
			if (isWeatherPhenomena(infos[i]) && i > 1 && !infos[i].startsWith("RE")) {
				WeatherPhenomena phenomena = new WeatherPhenomena();
				// VC开头的情况
				if (infos[i].startsWith("VC")) {
					phenomena.setItensity("VC");
					// 方便处理 去掉VC
					infos[i] = infos[i].replace("VC", "");
				}
				// 按每2个拆分拼字符串
				switch (infos[i].substring(0, 1)) {
				case "-":
					phenomena.setItensity("-");
					infos[i] = infos[i].substring(1);
					break;
				case "+":
					phenomena.setItensity("+");
					infos[i] = infos[i].substring(1);
					break;
				default:
					phenomena.setItensity("");
				}
				if (infos[i].length() == 4) {
					phenomena.setAdjective(infos[i].substring(0, 2));
					phenomena.setPhenomena(infos[i].substring(2, 4));

				} else if (infos[i].length() == 2) {
					phenomena.setAdjective("");
					phenomena.setPhenomena(infos[i].substring(0, 2));
				} else {
					phenomena.setAdjective("");
					phenomena.setPhenomena("");
				}
				phenomenas.add(phenomena);
				m.setPhenomena(phenomenas);
				System.out.println(phenomena);

			}
			// 跑道状况 RVR
			if (isRVR(infos[i])) {
				RunawayVisualRange rvr = new RunawayVisualRange();
				if (infos[i].contains("V")) {
					rvr.setContainsV(true);
				}
				rvr.setRunaway_number(infos[i].substring(1, 3));
				rvr.setViusal_range(parseString(infos[i].substring(infos[i].indexOf("/") + 1, infos[i].length() - 1)));
				// 第四位是字母 跑道方向，是数字 没方向
				if (Character.isLetter(infos[i].charAt(3))) {
					rvr.setRunaway_LCR(String.valueOf(infos[i].charAt(3)));
				}
				if (Character.isLetter(infos[i].charAt(infos[i].length() - 1))) {
					rvr.setRunaway_change(String.valueOf(infos[i].charAt(infos[i].length() - 1)));
				}
				System.out.println(rvr);
			}

			// 云量
			if (infos[i].startsWith("FEW") || infos[i].startsWith("BKN") || infos[i].startsWith("SCT")
					|| infos[i].startsWith("OVC")) {
				Cloud c = new Cloud();
				c.setCloud_amount(infos[i].substring(0, 3));
				if (infos[i].endsWith("CB")) {
					c.setCB(true);
				}
				if (infos[i].endsWith("TCU")) {
					c.setTCU(true);
				}
				c.setCloud_height(Integer.parseInt(infos[i].substring(3, 6)) * 100);
				cloud_group.add(c);
				System.out.println(c);
				m.setCloud_group(cloud_group);
			}
			// 云能见度等另一种 简写
			if (infos[i].equals("CAVOK") || infos[i].equals("SKC") || infos[i].equals("NSC")) {
				Cloud c = new Cloud();
				c.setCloud_amount(infos[i]);
				switch (infos[i]) {
				case "CAVOK":
					c.setCAVOK(true);
					break;
				case "SKC":
					c.setSKC(true);
					break;
				case "NSC":
					c.setNSC(true);
					break;
				}
				cloud_group.add(c);
				System.out.println(c);
				m.setCloud_group(cloud_group);
			}

			// 正则温度
			if (isTemperature(infos[i])) {
				m.setAir_temperature(
						infos[i].split("/")[0].contains("M") ? parseString(infos[i].split("/")[0].replace("M", "-"))
								: parseString(infos[i].split("/")[0]));
				m.setDewpoint_temperature(
						infos[i].split("/")[1].contains("M") ? parseString(infos[i].split("/")[1].replace("M", "-"))
								: parseString(infos[i].split("/")[1]));
				System.out.println("气温"
						+ (infos[i].split("/")[0].contains("M") ? parseString(infos[i].split("/")[0].replace("M", "-"))
								: parseString(infos[i].split("/")[0]) + "℃,"));
				System.out.println("露点温度"
						+ (infos[i].split("/")[1].contains("M") ? parseString(infos[i].split("/")[1].replace("M", "-"))
								: parseString(infos[i].split("/")[1]) + "℃,"));
			}
			// 气压
			if (infos[i].startsWith("Q")) {
				m.setPressure(parseString(infos[i].substring(1)));
				System.out.println("修正海平面气压" + parseString(infos[i].substring(1)) + "百帕");
				flag = true;
			}
			// TODO 气压后面的 趋势预报暂时都break
			if (flag) {
				break;
			}
			// TODO WS风切变
			// TODO RE近期天气

		}

	}
}
