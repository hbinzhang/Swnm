package com.entity.securityinfo;

import java.util.Date;

/**
 * 检查情况
 * @author liyinghui
 *
 */
public class CheckInfo {
	
	/**
	 * 检查时间
	 */
	private Date time;
	
	/**
	 * 录入人员工号
	 */
	private String recordPerson;
	
	/**
	 * 录入人员名字
	 */
	private String recordPersonName;
	
	/**
	 * 检查情况
	 */
	private String note;
	
	/**
	 * 考核机构
	 */
	private String orgId;
	
	/**
	 * 考核机构
	 */
	private String orgName;
	
	/**
	 * 关联指标名称
	 */
	private String indexName;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRecordPerson() {
		if (recordPerson == null ){
			return "";
		}
		return recordPerson;
	}

	public void setRecordPerson(String recordPerson) {
		this.recordPerson = recordPerson;
	}

	public String getRecordPersonName() {
		if (recordPersonName == null ){
			return "";
		}
		return recordPersonName;
	}

	public void setRecordPersonName(String recordPersonName) {
		this.recordPersonName = recordPersonName;
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

	public String getOrgId() {
		if (orgId == null ){
			return "";
		}
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		if (orgName == null ){
			return "";
		}
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIndexName() {
		if (indexName == null ){
			return "";
		}
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public String toString() {
		return "CheckInfo [time=" + time + ", recordPerson=" + recordPerson
				+ ", recordPersonName=" + recordPersonName + ", note=" + note
				+ ", orgId=" + orgId + ", orgName=" + orgName + ", indexName="
				+ indexName + "]";
	}	

}
