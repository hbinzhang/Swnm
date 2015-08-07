package com.entity.securityinfo;

import java.util.Date;

public class RewardPunishment {
	
	/**
	 * 所属机构
	 */
	private String belongToOrgId;
	
	/**
	 * 所属机构
	 */
	private String belongToOrgName;
	
	/**
	 * 奖惩名称
	 */
	private String name;	
	
	/**
	 * 奖惩部门
	 */
	private String rpOrg;
	
	/**
	 * 奖惩描述
	 */
	private String description;
	
	/**
	 * 奖惩日期
	 */
	private Date time;
	
	/**
	 * 奖惩级别
	 */
	private String rplevel;
	
	/**
	 * 奖惩实施部门
	 */
	private String implementOrg;

	/**
	 * 奖惩备注
	 */
	private String note;

	public String getBelongToOrgId() {
		if (belongToOrgId == null ){
			return "";
		}
		return belongToOrgId;
	}

	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	public String getBelongToOrgName() {
		if (belongToOrgName == null ){
			return "";
		}
		return belongToOrgName;
	}

	public void setBelongToOrgName(String belongToOrgName) {
		this.belongToOrgName = belongToOrgName;
	}

	public String getName() {
		if (name == null ){
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRpOrg() {
		if (rpOrg == null ){
			return "";
		}
		return rpOrg;
	}

	public void setRpOrg(String rpOrg) {
		this.rpOrg = rpOrg;
	}

	public String getDescription() {
		if (description == null ){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRplevel() {
		if (rplevel == null ){
			return "";
		}
		return rplevel;
	}

	public void setRplevel(String rplevel) {
		this.rplevel = rplevel;
	}

	public String getImplementOrg() {
		if (implementOrg == null ){
			return "";
		}
		return implementOrg;
	}

	public void setImplementOrg(String implementOrg) {
		this.implementOrg = implementOrg;
	}

	public String getNote() {
		if (note == null ){
			return "";
		}
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "RewardPunishment [belongToOrgId=" + belongToOrgId
				+ ", belongToOrgName=" + belongToOrgName + ", name=" + name
				+ ", rpOrg=" + rpOrg + ", description="
				+ description + ", time=" + time + ", rplevel=" + rplevel
				+ ", implementOrg=" + implementOrg + ", note=" + note + "]";
	}
	
}
