package com.entity.videomonitor;

public class IpcConfigDTO {
	
	private String ip;
	
	private Integer port;
	
	private String devName;
	
	private Integer preset;
	private String userID;
	
	private String password;
	
	private Integer cordon;
	
	private Integer highTossAct;
	
	private Integer flotage;
	
	private Integer travelToTrack;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCordon() {
		return cordon;
	}
	public void setCordon(Integer cordon) {
		this.cordon = cordon;
	}
	public Integer getHighTossAct() {
		return highTossAct;
	}
	public void setHighTossAct(Integer highTossAct) {
		this.highTossAct = highTossAct;
	}
	public Integer getFlotage() {
		return flotage;
	}
	public void setFlotage(Integer flotage) {
		this.flotage = flotage;
	}
	public Integer getTravelToTrack() {
		return travelToTrack;
	}
	public void setTravelToTrack(Integer travelToTrack) {
		this.travelToTrack = travelToTrack;
	}
	public Integer getPreset() {
		return preset;
	}
	public void setPreset(Integer preset) {
		this.preset = preset;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}

}
