package com.entity.linkagectl;

public class AlarmResult  {
	
	private Integer level1Count; //告警级别为1的告警数
	private Integer level2Count; //告警级别为2的告警数
	private Integer level3Count; //告警级别为3的告警数
	private Integer level4Count; //告警级别为4的告警数
	private String orgID; //机构ID
	

	public Integer getLevel1Count() {
		
		return level1Count;
	}
	public void setLevel1Count(Integer level1Count) {
		this.level1Count = level1Count;
	}
	
	public Integer getLevel2Count() {
		
		return level2Count;
	}
	public void setLevel2Count(Integer level2Count) {
		this.level2Count = level2Count;
	}
	
	
	public Integer getLevel3Count() {
		
		return level3Count;
	}
	public void setLevel3Count(Integer level3Count) {
		this.level3Count = level3Count;
	}
	
	
	public Integer getLevel4Count() {
		
		return level4Count;
	}
	public void setLevel4Count(Integer level4Count) {
		this.level4Count = level4Count;
	}
	
	public String getOrgID() {
		return orgID;
	}
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
}
