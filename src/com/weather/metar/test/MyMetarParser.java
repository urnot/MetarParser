package com.weather.metar.test;

import java.util.ArrayList;
import java.util.List;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.RunawayVisualRange;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;
import com.weather.metar.domain.Metar;
import com.weather.metar.exception.MetarParseException;
import com.weather.metar.parse.Regex;

public class MyMetarParser {

	public Metar parseMetar(String report) throws MetarParseException {
		Metar m = new Metar();
		if (report == null || report.length() <= 0) {
			throw new MetarParseException("null input");
		} else {
			report = report.toUpperCase().trim();
			String infos[] = report.split("\\s+");
			List<Cloud> cloud_group = new ArrayList<>();
			Wind w = new Wind();
			List<WeatherPhenomena> phenomenas = new ArrayList<>();
			boolean flag = false;

			for (int i = 0; i < infos.length; i++) {

				// 例行天气预报实况，特殊天气报告
				if (Regex.isMETAR(infos[i])) {
					System.out.println("格式：" + infos[i]);
					m.setReport_time(infos[i]);
					continue;
				}
				// CHINA only
				if (Regex.isChinaICAO(infos[i])) {
					m.setAirport_code(infos[i]);
					System.out.println("机场代码:" + infos[i] + ",");
					continue;

				}
				//TODO 时间格式
				if (Regex.isTimeZ(infos[i]) && infos[i].length() == 7) {
					m.setReport_time(infos[i]);
					System.out.println("世界时 " + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "时"
							+ infos[i].substring(4, 6) + "分预报，");
					continue;
				}
				// 风第一节
				if (infos[i].endsWith("MPS") || infos[i].endsWith("KT") || infos[i].endsWith("KMH")) {
					if (infos[i].startsWith("VRB")) {
						w.setVRB(true);

					}
					if (infos[i].contains("G")) {
						w.setGustwind(true);
						w.setMax_gustwind_speed(Regex.parseString(infos[i].substring(6, 8)));
					}
					// 单独处理VRB
					if (!infos[i].startsWith("VRB")) {
						w.setWind_direction(Regex.parseString(infos[i].substring(0, 3)));
					}
					w.setWind_speed(Regex.parseString(infos[i].substring(3, 5)));
					if (infos[i].endsWith("KT")) {
						w.setWind_unit("KT");
					} else {
						w.setWind_unit(infos[i].substring(infos[i].length() - 3, infos[i].length()));
					}

					// 风第二节 如果有 正则xxxVxxx
					if (infos[i + 1].length() == 7 && infos[i + 1].indexOf("V") == 3) {
						w.setContainsV(true);
						w.setMin_gustwind_direction(Regex.parseString(infos[i + 1].substring(0, 3)));
						w.setMax_gustwind_direction(Regex.parseString(infos[i + 1].substring(4, 7)));
					}
					m.setWind(w);
					System.out.println(w);
					continue;
				}
				//  能见度 四个数字 [带方向???]国内暂时按4个数字处理
				if (Regex.isFourNumber(infos[i])) {
					m.setVisibility(Regex.parseString(infos[i]));
					System.out.println("能见度" + Regex.parseString(infos[i]) + "米");
					//9999表示能见度大于10000米
					continue;
				}
				//  天气现象 
				if (Regex.isWeatherPhenomena(infos[i]) && i > 1 && !infos[i].startsWith("RE")) {
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
					continue;

				}
				// 跑道视程 RVR
				if (Regex.isRVR(infos[i])) {
					RunawayVisualRange rvr = new RunawayVisualRange();
					if (infos[i].contains("V")) {
						rvr.setContainsV(true);
					}
					rvr.setRunaway_number(infos[i].substring(1, 3));
					rvr.setViusal_range(
							Regex.parseString(infos[i].substring(infos[i].indexOf("/") + 1, infos[i].length() - 1)));
					// 第四位是字母 跑道方向，是数字 没方向
					if (Character.isLetter(infos[i].charAt(3))) {
						rvr.setRunaway_LCR(String.valueOf(infos[i].charAt(3)));
					}
					if (Character.isLetter(infos[i].charAt(infos[i].length() - 1))) {
						rvr.setRunaway_LCR("");
						rvr.setRunaway_change(String.valueOf(infos[i].charAt(infos[i].length() - 1)));
					}
					System.out.println(rvr);
					continue;

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
					continue;

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
					continue;

				}

				// 正则温度
				if (Regex.isTemperature(infos[i])) {
					m.setAir_temperature(infos[i].split("/")[0].contains("M")
							? Regex.parseString(infos[i].split("/")[0].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[0]));
					m.setDewpoint_temperature(infos[i].split("/")[1].contains("M")
							? Regex.parseString(infos[i].split("/")[1].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[1]));
					System.out.println("气温" + (infos[i].split("/")[0].contains("M")
							? Regex.parseString(infos[i].split("/")[0].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[0]) + "℃,"));
					System.out.println("露点温度" + (infos[i].split("/")[1].contains("M")
							? Regex.parseString(infos[i].split("/")[1].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[1]) + "℃,"));
					continue;

				}
				// TODO 北美A英寸汞柱
				if (infos[i].startsWith("Q")) {
					m.setPressure(Regex.parseString(infos[i].substring(1)));
					System.out.println("修正海平面气压" + Regex.parseString(infos[i].substring(1)) + "百帕");
					flag = true;
					continue;

				}
				// TODO 趋势预测 气压后面的 趋势预报暂时都break
				if (flag) {
					// NOSIG 无明显变化
					// TEMPO temperorary短时
					// BECMG becoming 逐渐转变
					break;
				}
				// TODO WS风切变
				// TODO RE近期天气

			}
		}

		return m;

	}


	public static void main(String[] args) {
		MyMetarParser parser = new MyMetarParser();
		try {
			parser.parseMetar("METAR ZUBJ 282100Z 28002MPS 1100 R26/1000D BR SCT026 18/18 Q1010 NOSIG=\r\n" + 
					"");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
	}

}
