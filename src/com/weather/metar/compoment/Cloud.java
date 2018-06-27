package com.weather.metar.compoment;

import com.weather.metar.weatherenum.CloudAmount;

/**
 * 云
 * @author jlshen
 *
 */
public class Cloud {
	// 云量
	private String cloud_amount;
	// 云层高度 英尺
	private int cloud_height;
	// 是否CB积雨云
	private boolean isCB;
	// 是否TCU浓积云
	private boolean isTCU;
	//是否是CAVOK 天气及能见度良好
	private boolean isCAVOK;
	//没有对飞行有重要影响的云
	private boolean isNSC;
	//天空无云
	private boolean isSKC;

	public String getCloud_amount() {
		return cloud_amount;
	}

	public void setCloud_amount(String cloud_amount) {
		this.cloud_amount = cloud_amount;
	}

	public int getCloud_height() {
		return cloud_height;
	}

	public void setCloud_height(int cloud_height) {
		this.cloud_height = cloud_height;
	}

	public boolean isCB() {
		return isCB;
	}

	public void setCB(boolean isCB) {
		this.isCB = isCB;
	}

	public boolean isTCU() {
		return isTCU;
	}

	public void setTCU(boolean isTCU) {
		this.isTCU = isTCU;
	}
	
	public boolean isCAVOK() {
		return isCAVOK;
	}

	public void setCAVOK(boolean isCAVOK) {
		this.isCAVOK = isCAVOK;
	}

	public boolean isNSC() {
		return isNSC;
	}

	public void setNSC(boolean isNSC) {
		this.isNSC = isNSC;
	}

	public boolean isSKC() {
		return isSKC;
	}

	public void setSKC(boolean isSKC) {
		this.isSKC = isSKC;
	}

	@Override
	public String toString() {
		
		if (this.isCB) {
			return this.cloud_height + "英尺有" + CloudAmount.getDescriptionByCloud(this.cloud_amount) +CloudAmount.getDescriptionByCloud("CB") + ",";
		} else if (this.isTCU) {
			return this.cloud_height + "英尺有" + CloudAmount.getDescriptionByCloud(this.cloud_amount) + CloudAmount.getDescriptionByCloud("TCU") + ",";
		} else if(this.isCAVOK||this.isNSC||this.isSKC) {
			return CloudAmount.getDescriptionByCloud(this.cloud_amount);
		}else{
			return this.cloud_height + "英尺有" + CloudAmount.getDescriptionByCloud(this.cloud_amount) + ",";
		}
	}
}
