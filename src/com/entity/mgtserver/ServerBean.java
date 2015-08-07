package com.entity.mgtserver;

public class ServerBean {
	
	private String orgID;
	private Integer devID;
	private Integer type;
	private String efenceIP;
	
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	public Integer getDevID() {
		return devID;
	}
	public void setDevID(Integer devID) {
		this.devID = devID;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getEfenceIP() {
		return efenceIP;
	}
	public void setEfenceIP(String efenceIP) {
		this.efenceIP = efenceIP;
	}

}
