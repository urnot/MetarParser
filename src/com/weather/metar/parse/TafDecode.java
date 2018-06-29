package com.weather.metar.parse;

import java.util.ArrayList;
import java.util.List;

import com.weather.metar.compoment.Cloud;
import com.weather.metar.compoment.TafChange;
import com.weather.metar.compoment.WeatherPhenomena;
import com.weather.metar.compoment.Wind;
import com.weather.metar.domain.Taf;
import com.weather.metar.exception.MetarParseException;
import com.weather.metar.weatherenum.SigmentCode;

public class TafDecode {
	public void parseTaf(String report) throws MetarParseException {
		Taf t = new Taf();

		if (report == null || report.length() <= 0) {
			throw new MetarParseException("null input");
		} else {
			report = report.toUpperCase().trim().replace("=", "");
			String infos[] = report.split("\\s+");
			List<Cloud> cloud_group = new ArrayList<>();
			Wind w = new Wind();
			List<WeatherPhenomena> phenomenas = new ArrayList<>();
			boolean flag = false;
			List<TafChange> changes;

			for (int i = 0; i < infos.length; i++) {
				// System.out.println("parsing----"+infos[i]+"-----");
				if (infos[i].equals("TAF")) {
					t.setTaf_type("TAF");
					continue;
				}
				if (Regex.isChinaICAO(infos[i])) {
					t.setAirport_code(infos[i]);
					System.out.println("ICAO:" + infos[i]);
					continue;
				}
				if (Regex.isTimeZ(infos[i])) {
					t.setReport_time(infos[i]);
					System.out.println("世界时 " + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "时"
							+ infos[i].substring(4, 6) + "分预报，");
					continue;
				}
				if (Regex.isValidTime(infos[i])) {
					t.setBegin_time(infos[i]);
					t.setEnd_time(infos[i]);
					System.out.println("有效至:" + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "时"
							+ infos[i].substring(4, 6) + "分");
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
					t.setWind(w);
					System.out.println(w);
					continue;
				}
				// 能见度 四个数字 [带方向???]国内暂时按4个数字处理
				if (Regex.isFourNumber(infos[i])) {
					t.setVisibility(Regex.parseString(infos[i]));
					System.out.println("能见度" + Regex.parseString(infos[i]) + "米");
					// 9999表示能见度大于10000米
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
					t.setColud(cloud_group);
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
					t.setColud(cloud_group);
					continue;

				}
				// 天气现象
				if (Regex.isWeatherPhenomena(infos[i]) && i > 1 && !infos[i].startsWith("RE")
						&& !infos[i].startsWith("TEMPO")&& !infos[i].startsWith("BECMG")) {
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
					System.out.println(phenomena);
					continue;

				}

				if (Regex.isValidTime(infos[i])) {
					t.setEnd_time(infos[i]);
					System.out.println("有效至世界时" + infos[i].substring(0, 2) + "日" + infos[i].substring(2, 4) + "日"
							+ infos[i].substring(4, 6) + "分");
				}
				if (infos[i].startsWith("T") && infos[i].length() == 8) {
					if (infos[i].charAt(1) == 'X') {
						t.setMax_temp(infos[i].substring(2, 4));
						t.setMax__temp_time(infos[i].substring(5, 7));
						System.out.println("max temp ： " + infos[i].substring(2, 4) + "max temp time :"
								+ infos[i].substring(5, 7));
					} else {
						t.setMin_temp(infos[i].substring(2, 4));
						t.setMin__temp_time(infos[i].substring(5, 7));
						System.out.println("min temp ： " + infos[i].substring(2, 4) + "min temp time :"
								+ infos[i].substring(5, 7));
					}
					continue;
				}
				if (infos[i].startsWith("BECMG") || infos[i].startsWith("TEMPO")) {
					System.out.println(SigmentCode.getDescriptionByCode(infos[i]));
					continue;
				}
				if (flag == true) {
					TafChange tc = new TafChange();
					System.out.println("new record......" + infos[i]);
					flag = false;
					// do sth, flag=false
					continue;
				}

			}
		}

	}

	public static void main(String[] args) {
		TafDecode t = new TafDecode();
		try {
			t.parseTaf(
					"TAF ZBCF 290424Z 290615 24004MPS 9999 FEW033 TX32/06Z TN25/15Z TEMPO 0607 02007G12MPS -TSRA FEW033CB FEW033 BECMG 1606/1608 SCT015CB BKN020 FM161200 15015KT 9999 NSW BKN020 BKN100\r\n"
							+ "");
		} catch (MetarParseException e) {
			e.printStackTrace();
		}

	}
}
