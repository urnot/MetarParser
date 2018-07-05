package com.weather.metar.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;
import com.weather.metar.domain.Taf;
import com.weather.metar.exception.MetarParseException;
import com.weather.metar.util.Regex;
import com.weather.metar.util.TimeParser;
import com.weather.metar.weatherenum.Phenomena;

public class TafDecode {

	public void parseTaf(String report) throws MetarParseException {
		Taf t = new Taf();
		String result = "";
		int pos = 0;// 记录游标，天气现象变化之后的四位数字 非能见度
		if (report == null || report.length() <= 0) {
			throw new MetarParseException("null input");
		} else {
			report = report.toUpperCase().trim().replace("=", "");
			if (report.contains("TKOF")) {
				t.setWind_shear("起飞跑道有风切变");
			} else if (report.contains("LDG")) {
				t.setWind_shear("着陆跑道有风切变");
			} else if (report.contains("ALL")) {
				t.setWind_shear("所有跑道的起飞或将近航道有风切变");
			}
			String infos[] = report.split("\\s+");
			List<Cloud> cloud_group = new ArrayList<>();
			Wind w = new Wind();
			List<WeatherPhenomena> phenomenas = new ArrayList<>();
			boolean flag = false;

			for (int i = 0; i < infos.length; i++) {
				// System.out.println("parsing----"+infos[i]+"-----");
				/*
				 * 第一组 电报名称
				 */
				if (infos[i].equals("TAF")) {
					t.setTaf_type("TAF");
					result += "类型:" + infos[i] + "\r\n";
					continue;
				}
				/*
				 * 第2组 地名代码组
				 */
				if (Regex.isChinaICAO(infos[i])) {
					t.setAirport_code(infos[i]);
					// System.out.println("ICAO:" + infos[i]);
					result += "机场:" + infos[i] + "\r\n";
					continue;
				}
				/*
				 * 第3组 预报时间组 UTC
				 */
				if (Regex.isTimeZ(infos[i])) {
					t.setReport_time(TimeParser.getBJT(infos[i]));
					// System.out.println("观测时间: " + TimeParser.getBJT(infos[i]) + "(BJT)");
					result += "观测时间: " + TimeParser.getBJT(infos[i]) + "(BJT)" + "\r\n";
					continue;
				}
				/*
				 * 第4组 预报有效时间
				 */
				if (Regex.isValidTime(infos[i])) {
					t.setBegin_time(infos[i]);
					t.setEnd_time(infos[i]);
					// System.out.println("有效至:" + infos[i].substring(0, 2) + "日" +
					// infos[i].substring(2, 4) + "时"
					// + infos[i].substring(4, 6) + "分");
					result += "有效时间: "
							+ TimeParser.getValidBJT(infos[i], infos[i].substring(0, 2), infos[i].substring(2, 4)) + "至"
							+ TimeParser.getValidBJT(infos[i], infos[i].substring(0, 2), infos[i].substring(4, 6))
							+ "(BJT)\r\n";

					continue;
				}
				/*
				 * 第5组 预报的风
				 */
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
					t.setWind(w);
					result += "地面风:" + w + "\r\n";
					// System.out.println(w);
					continue;
				}
				/*
				 * 第5组 预报能见度
				 */
				// 能见度 四个数字 [带方向???]国内暂时按4个数字处理
				if (Regex.isFourNumber(infos[i]) && i != pos) {
					int visibility = Regex.parseString(infos[i]);
					t.setVisibility(Regex.parseString(infos[i]));
					// System.out.println("能见度" + Regex.parseString(infos[i]) + "米");
					result += "能见度:" + (visibility == 9999 ? ">10k" : Regex.parseString(infos[i]) + "") + "m" + "\r\n";
					// 9999表示能见度大于10000米
					continue;
				}
				/*
				 * 第7组 重要天气现象
				 */
				// 天气现象
				if (Regex.isWeatherPhenomena(infos[i]) && !infos[i].startsWith("TEMPO") && !infos[i].startsWith("BECMG")
						&& i > 1) {
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
					t.setPhenomenas(phenomenas);
					result += "天气:" + phenomena + "\r\n";
					// System.out.println(phenomena);
					continue;
				}
				/*
				 * 第8组 预报的云
				 */
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
					// System.out.println(c);
					t.setColud(cloud_group);
					result += "云:" + c + "\r\n";

					continue;

				}
				/*
				 * 第9组 CAVOK
				 */
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
					// System.out.println(c);
					result += c + "\r\n";
					t.setColud(cloud_group);
					continue;

				}

				/*
				 * 第10组 预报的气温
				 */
				if (infos[i].startsWith("T") && infos[i].length() == 8) {
					if (infos[i].charAt(1) == 'X') {
						t.setMax_temp(infos[i].substring(2, 4));
						t.setMax__temp_time(infos[i].substring(5, 7));
						result+="最高温度： " + infos[i].substring(2, 4) + "℃，最高温度时间:"
								+ Regex.parseString(infos[i].substring(5, 7)) + "时\r\n";
//						System.out.println("最高温度： " + infos[i].substring(2, 4) + "℃，最高温度时间:"
//								+ Regex.parseString(infos[i].substring(5, 7)) + "时");
					} else {
						t.setMin_temp(infos[i].substring(2, 4));
						t.setMin__temp_time(infos[i].substring(5, 7));
						result+="最低温度： " + infos[i].substring(2, 4) + "℃，最低温度时间 :"
								+ Regex.parseString(infos[i].substring(5, 7)) + "时\r\n";
//						System.out.println("最低温度： " + infos[i].substring(2, 4) + "℃，最低温度时间 :"
//								+ Regex.parseString(infos[i].substring(5, 7)) + "时");
					}
					continue;
				}
				/*
				 * 第11组 积冰
				 */
				if (Regex.isFrozen(infos[i])) {
					// TODO
				}
				/*
				 * 第12组 颠簸
				 */
				if (Regex.isTurbulence(infos[i])) {
					// TODO
				}

				if (Regex.isValidTime(infos[i])) {
					t.setEnd_time(infos[i]);
					System.out.println("有效至" + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "日"
							+ infos[i].substring(4, 6) + "分UTC");
				}
				/*
				 * 第13组 气象要素变化
				 */
				if (infos[i].equals("NOSIG") || infos[i].equals("TEMPO") || infos[i].equals("BECMG")) {
					pos = i + 1;
					// System.out.println(Phenomena.getDescriptionByCode(infos[i]) + "\r\n");
					// NOSIG 无明显变化
					// TEMPO temperorary短时
					// BECMG becoming 逐渐转变
					result += Phenomena.getDescriptionByCode(infos[i]) + "\r\n";
					if (!infos[i].equals("NOSIG")) {
						result += "从世界时" + infos[i + 1].substring(0, 2) + "时至" + infos[i + 1].substring(2, 4) + "时\r\n";
					}
					continue;
					// break;
				}

			}
			System.out.println(report+"\r\n"+result);
			result += t.getWind_shear();
			// t.setTxtDecode(result);
		}

	}

	public static void main(String[] args) {
		TafDecode t = new TafDecode();
		try {
			t.parseTaf("TAF ZLLL 030107Z 030312 03003MPS 5000 -RA BR SCT016 OVC040 TX16/06Z TN14/12Z TEMPO 0307 RA=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}

	}
}
