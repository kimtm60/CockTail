package com.knj.cocktail.domain;

public class Custom {

	
	private String userId;
	private String sectorId;
	private int brightness;
	private int modeId;
	private int callId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public Custom() {
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public int getBrightness() {
		return brightness;
	}
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	public int getModeId() {
		return modeId;
	}
	public void setModeId(int modeId) {
		this.modeId = modeId;
	}
	public int getCallId() {
		return callId;
	}
	public void setCallId(int callId) {
		this.callId = callId;
	}
}
