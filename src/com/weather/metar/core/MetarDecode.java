package com.weather.metar.core;

import java.util.ArrayList;
import java.util.List;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.RunawayVisualRange;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;
import com.weather.metar.domain.Metar;
import com.weather.metar.exception.MetarParseException;
import com.weather.metar.util.Regex;
import com.weather.metar.util.TimeParser;
import com.weather.metar.weatherenum.Phenomena;

public class MetarDecode {

	public Metar parseMetar(String report) throws MetarParseException {
		Metar m = new Metar();
		String result = "";
		int pos = 0;// 记录游标，天气现象变化之后的四位数字 非能见度
		if (report == null || report.length() <= 0) {
			throw new MetarParseException("null input");
		} else {
			report = report.toUpperCase().trim();
			if (report.contains("TKOF")) {
				m.setWind_shear("起飞跑道有风切变");
			} else if (report.contains("LDG")) {
				m.setWind_shear("着陆跑道有风切变");
			} else if (report.contains("ALL")) {
				m.setWind_shear("所有跑道的起飞或将近航道有风切变");
			}
			String infos[] = report.toUpperCase().trim().replace("=", "").split("\\s+");
			List<Cloud> cloud_group = new ArrayList<>();
			Wind w = new Wind();
			List<WeatherPhenomena> phenomenas = new ArrayList<>();

			for (int i = 0; i < infos.length; i++) {
				/*
				 * 第一组 电报名称
				 */
				// 例行天气预报实况，特殊天气报告
				if (Regex.isMETAR(infos[i])) {
					// System.out.println("格式：" + infos[i]);
					m.setReport_time(infos[i]);
					result += "类型：" + infos[i] + "\r\n";
					continue;
				}
				/*
				 * 第2组 地名代码组
				 */
				// CHINA only
				if (Regex.isChinaICAO(infos[i])) {
					m.setAirport_code(infos[i]);
					// System.out.println("机场:" + infos[i] + ",");
					result += "机场:" + infos[i] + "\r\n";
					continue;

				}
				/*
				 * 第3组 预报时间组 UTC
				 */
				if (Regex.isTimeZ(infos[i]) && infos[i].length() == 7) {
					m.setReport_time(TimeParser.getBJT(infos[i]));
					// System.out.println("观测时间: " + TimeParser.getBJT(infos[i]) + "(BJT)");
					result += "观测时间: " + TimeParser.getBJT(infos[i]) + "(BJT)" + "\r\n";
					continue;
				}
				/*
				 * 第4组 预报的风
				 */
				// 风第一节 eg.VRB09G15MPS
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
					result += "地面风:" + w + "\r\n";
					// System.out.println("地面风:" + w);
					continue;
				}
				/*
				 * 第5组 能见度
				 */
				// 能见度 四个数字 [带方向???]国内暂时按4个数字处理
				if (Regex.isFourNumber(infos[i]) && i != pos) {
					int visibility = Regex.parseString(infos[i]);
					m.setVisibility(Regex.parseString(infos[i]));
					// System.out.println("能见度:" + Regex.parseString(infos[i]) + "m");
					// 9999表示能见度大于10000米
					result += "能见度:" + (visibility == 9999 ? ">10k" : Regex.parseString(infos[i]) + "") + "m" + "\r\n";
					continue;
				}

				/*
				 * 第6组 跑道视程
				 */
				// 跑道视程 RVR 暂时注释
				if (Regex.isRVR(infos[i])) {
/*					RunawayVisualRange rvr = new RunawayVisualRange();
					rvr.setRunaway_number(infos[i].substring(1, 3));
					if (infos[i].contains("V")) {
						rvr.setContainsV(true);
						rvr.setMin_range(Regex
								.parseString(infos[i].substring(infos[i].indexOf("/")+1, infos[i].indexOf("V"))));
						rvr.setMax_range(Regex
								.parseString(infos[i].substring(infos[i].indexOf("V")+1, infos[i].indexOf("V")+5)));
					}else {
						
						// R30/P2000N   R07/0200V1200
						if (Character.isLetter(infos[i].charAt(3))) {
							rvr.setRunaway_LCR(String.valueOf(infos[i].charAt(3)));
							rvr.setViusal_range(Regex
									.parseString(infos[i].substring(infos[i].indexOf("/") + 2, infos[i].length() - 1)));
						}
						if (Character.isLetter(infos[i].charAt(infos[i].length() - 1))) {
							// 第5位
							if (Character.isLetter(infos[i].charAt(4))) {
								rvr.setRunaway_LCR("");
								rvr.setViusal_range(Regex
										.parseString(infos[i].substring(infos[i].indexOf("/") + 2, infos[i].length() - 1)));
								rvr.setRunaway_change(String.valueOf(infos[i].charAt(infos[i].length() - 1)));
							} else {
								rvr.setRunaway_LCR("");
								rvr.setViusal_range(Regex
										.parseString(infos[i].substring(infos[i].indexOf("/") + 1, infos[i].length() - 1)));
								rvr.setRunaway_change(String.valueOf(infos[i].charAt(infos[i].length() - 1)));
							}
						}else {
							rvr.setViusal_range(Regex
									.parseString(infos[i].substring(infos[i].indexOf("/") + 1, infos[i].length() - 1)));
						}
					}
					result += rvr + "\r\n";
*/
					continue;

				}
				/*
				 * 第7组 天气现象
				 */
				// 天气现象
				if (Regex.isWeatherPhenomena(infos[i]) && !infos[i].startsWith("TEMPO") && !infos[i].startsWith("BECMG")
						&& !infos[i].startsWith("NOSIG") && !infos[i].startsWith("AUTO") && i > 1) {
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
					result += "天气:" + phenomena + "\r\n";
					// System.out.println(phenomena);
					continue;
				}
				/*
				 * 第8组 云组
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
					m.setCloud_group(cloud_group);
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
					m.setCloud_group(cloud_group);
					result += c + "\r\n";

					continue;

				}
				/*
				 * 第10组 温度/露点
				 */
				// 正则温度
				if (Regex.isTemperature(infos[i])) {
					m.setAir_temperature(infos[i].split("/")[0].contains("M")
							? Regex.parseString(infos[i].split("/")[0].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[0]));
					m.setDewpoint_temperature(infos[i].split("/")[1].contains("M")
							? Regex.parseString(infos[i].split("/")[1].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[1]));
					// System.out.println("温度：" + (infos[i].split("/")[0].contains("M")
					// ? Regex.parseString(infos[i].split("/")[0].replace("M", "-"))
					// : Regex.parseString(infos[i].split("/")[0]) + "℃,"));
					// System.out.println("露点温度：" + (infos[i].split("/")[1].contains("M")
					// ? Regex.parseString(infos[i].split("/")[1].replace("M", "-"))
					// : Regex.parseString(infos[i].split("/")[1]) + "℃,"));
					result += "温度：" + (infos[i].split("/")[0].contains("M")
							? Regex.parseString(infos[i].split("/")[0].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[0]) + "℃") + "\r\n";
					result += "露点温度：" + (infos[i].split("/")[1].contains("M")
							? Regex.parseString(infos[i].split("/")[1].replace("M", "-"))
							: Regex.parseString(infos[i].split("/")[1]) + "℃") + "\r\n";
					continue;

				}
				/*
				 * 第11组 气压
				 */
				// TODO 北美A英寸汞柱
				if (infos[i].startsWith("Q")) {
					m.setPressure(Regex.parseString(infos[i].substring(1)));
					// System.out.println("修正海压：" + Regex.parseString(infos[i].substring(1)) +
					// "hPa");
					result += "修正海压：" + Regex.parseString(infos[i].substring(1)) + "hPa" + "\r\n";

					continue;

				}
				/*
				 * 第12组 补充 近时天气
				 */

				/*
				 * 第13组 风切变
				 */

				/*
				 * 第14组 趋势
				 */

				// TODO 趋势预测 气压后面的 趋势预报暂时都break
				if (infos[i].equals("NOSIG") || infos[i].equals("TEMPO") || infos[i].equals("BECMG")) {
					pos = i + 1;
					// System.out.println(Phenomena.getDescriptionByCode(infos[i]) + "\r\n");
					// NOSIG 无明显变化
					// TEMPO temperorary短时
					// BECMG becoming 逐渐转变
					result += Phenomena.getDescriptionByCode(infos[i]) + "\r\n";
					if (!infos[i].equals("NOSIG")) {
						if (Character.isDigit(infos[i].charAt(0))) {
							result += Phenomena.getDescriptionByCode(infos[i + 1].substring(0, 2))
									+ infos[i + 1].substring(2, 4) + ":" + infos[i + 1].substring(4, 6) + "\r\n";
						} else {
							result += "从" + infos[i + 1].substring(0, 2) + ":" + infos[i + 1].substring(2, 4) + "\r\n";
						}
					}
					continue;
					// break;
				}
				/*
				 * 第15组 变化时间
				 */

				/*
				 * 第16组 变化的气象要素
				 */
			}
//			System.out.println(result);
			if(m.getWind_shear()!=null) {
				result += m.getWind_shear();
			}
			m.setTxtDecode(result);

		}

		return m;

	}

	public static void main(String[] args) {
		MetarDecode parser = new MetarDecode();
		try {
			parser.parseMetar("METAR ZLLL 030200Z 33002MPS 4000 -RA BR OVC043 15/14 Q1012 NOSIG=");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}
	}

}
