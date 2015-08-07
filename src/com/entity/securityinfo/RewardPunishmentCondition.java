package com.entity.securityinfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RewardPunishmentCondition {

	/**
	 * 开始时间，为null表示不限制开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间，为null表示不限制结束时间
	 */
	private Date endTime;
	
	/**
	 * 所属机构
	 */
	private String belongToOrgId;
	
	/**
	 * 奖惩名称
	 */
	private String name;
	
	/**
	 * 奖惩实施部门
	 */
	private String implementOrg;
	
	private String searchUri;

	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBelongToOrgId() {
		return belongToOrgId;
	}

	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImplementOrg() {
		return implementOrg;
	}

	public void setImplementOrg(String implementOrg) {
		this.implementOrg = implementOrg;
	}

	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("rewardPunishmentCondition.belongToOrgId=").append(this.belongToOrgId).append("&");	
		try {
			buffer.append("rewardPunishmentCondition.beginTime=").append(this.beginTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginTime)).append("&");
			buffer.append("rewardPunishmentCondition.endTime=").append(this.endTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime)).append("&");
		} catch (Exception e) {
		}
		buffer.append("rewardPunishmentCondition.name=").append(this.name==null ||this.name =="null"?"":this.name).append("&");
		buffer.append("rewardPunishmentCondition.implementOrg=").append(this.implementOrg==null ||this.implementOrg =="null"?"":this.implementOrg);
		this.searchUri = buffer.toString();
		return searchUri;
	}
	
	@Override
	public String toString() {
		return "RewardPunishmentCondition [beginTime=" + beginTime
				+ ", endTime=" + endTime + ", belongToOrgId=" + belongToOrgId
				+ ", name=" + name + ", implementOrg=" + implementOrg + "]";
	}
	
}
