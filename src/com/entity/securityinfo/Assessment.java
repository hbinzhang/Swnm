package com.entity.securityinfo;

import java.util.List;

/**
 * 考核指标
 * @author liyinghui
 *
 */
public class Assessment {
	
	/**
	 * 考核机构
	 */
	private String orgId;

	private String orgName;
	
	/**
	 * 指标名称
	 */
	private String name;
	
	/**
	 * 考核机构责任人工号
	 */
	private String orgPerson;
	
	/**
	 * 考核机构责任人名字
	 */
	private String orgPersonName;
	
	/**
	 * 指标目标
	 */
	private String target;	
	
	/**
	 * 指标权重
	 */
	private Integer percent;
		
	/**
	 * 考评结果
	 */
	private Float result;
	
	/**
	 * 指标检查情况
	 */
	private List<CheckInfo> checkInfoList;	
	
	/**
	 * 考核人员工号
	 */
	private String reviewer;
	
	/**
	 * 考核人员名字
	 */
	private String reviewerName;
	
	/**
	 * 评价说明
	 */
	private String note;

	public String getOrgId() {
		if (orgId == null ){
			return "";
		}
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public String getOrgPerson() {
		if (orgPerson == null ){
			return "";
		}
		return orgPerson;
	}

	public void setOrgPerson(String orgPerson) {
		this.orgPerson = orgPerson;
	}

	public String getOrgPersonName() {
		if (orgPersonName == null ){
			return "";
		}
		return orgPersonName;
	}

	public void setOrgPersonName(String orgPersonName) {
		this.orgPersonName = orgPersonName;
	}

	public String getTarget() {
		if (target == null ){
			return "";
		}
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public Float getResult() {
		return result;
	}

	public void setResult(Float result) {
		this.result = result;
	}

	public List<CheckInfo> getCheckInfoList() {
		return checkInfoList;
	}

	public void setCheckInfoList(List<CheckInfo> checkInfoList) {
		this.checkInfoList = checkInfoList;
	}

	public String getReviewer() {
		if (reviewer == null ){
			return "";
		}
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
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

	public String getOrgName() {
		if (orgName == null ){
			return "";
		}
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public String toString() {
		return "Assessment [orgId=" + orgId + ", name=" + name + ", orgPerson="
				+ orgPerson + ", orgPersonName=" + orgPersonName + ", target="
				+ target + ", percent=" + percent + ", result=" + result
				+ ", checkInfoList=" + checkInfoList + ", reviewer=" + reviewer
				+ ", orgName=" + orgName 
				+ ", reviewerName=" + reviewerName + ", note=" + note + "]";
	}
	
}
