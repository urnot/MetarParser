package com.weather.metar.compoment;

import com.weather.metar.weatherenum.Unit;

public class RunawayVisualRange {
	// 跑道编号
	private String runaway_number;
	// 跑道视程
	private int viusal_range;
	// 左中右 哪个跑道
	private String runaway_LCR;
	// 变化范围 见Unit.java
	private String runaway_change;
	// 是否有V字符
	private boolean containsV;
	// 最小
	private String min_range;
	// 最小值的变化
	private String min_range_change;
	// 最大
	private int max_range;
	// 最大值的变化
	private String max_range_change;

	public String getRunaway_number() {
		return runaway_number;
	}

	public void setRunaway_number(String runaway_number) {
		this.runaway_number = runaway_number;
	}

	public int getViusal_range() {
		return viusal_range;
	}

	public void setViusal_range(int viusal_range) {
		this.viusal_range = viusal_range;
	}

	public String getRunaway_LCR() {
		return runaway_LCR;
	}

	public void setRunaway_LCR(String runaway_LCR) {
		this.runaway_LCR = runaway_LCR;
	}

	public boolean isContainsV() {
		return containsV;
	}

	public void setContainsV(boolean containsV) {
		this.containsV = containsV;
	}

	public String getRunaway_change() {
		return runaway_change;
	}

	public void setRunaway_change(String runaway_change) {
		this.runaway_change = runaway_change;
	}

	public String getMin_range() {
		return min_range;
	}

	public void setMin_range(String min_range) {
		this.min_range = min_range;
	}

	public String getMin_range_change() {
		return min_range_change;
	}

	public void setMin_range_change(String min_range_change) {
		this.min_range_change = min_range_change;
	}

	public int getMax_range() {
		return max_range;
	}

	public void setMax_range(int max_range) {
		this.max_range = max_range;
	}

	public String getMax_range_change() {
		return max_range_change;
	}

	public void setMax_range_change(String max_range_change) {
		this.max_range_change = max_range_change;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String change = "";
		String min_change = "";
		String max_change = "";

		if (this.runaway_change != null) {
			change += Unit.getDescriptionByCode(runaway_change);
		}

		if (this.containsV) {
			if (this.min_range_change != null) {
				min_change += Unit.getDescriptionByCode(min_range_change);
			}
			if (this.max_range_change != null) {
				max_change += Unit.getDescriptionByCode(max_range_change);
			}
			return this.runaway_number + Unit.getDescriptionByCode(runaway_LCR) + "跑道，最小跑道视程" + this.min_range + "米,"
					+ min_change + ",最大跑道视程" + this.max_range + "米," + max_change + ",";

		} else {

			return this.runaway_number + Unit.getDescriptionByCode(runaway_LCR) + "跑道，跑道视程" + this.viusal_range + "米,"+change;
		}
	}

}
