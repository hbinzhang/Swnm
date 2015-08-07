package com.entity.securityinfo;

import java.util.Date;

/**
 * 任务书
 * @author liyinghui
 *
 */
public class TaskBook {
	
	/**
	 * 任务名称
	 */
	private String name;
	
	/**
	 * 任务目标
	 */
	private String target;

	/**
	 * 任务类型
	 */
	private Integer type;	
	
	/**
	 * 任务内容
	 */
	private String content;
	
	/**
	 * 任务制定机构
	 */
	private String planOrgId;
	
	private String planOrgName;
	
	/**
	 * 任务制定机构负责人
	 */
	private String planOrgPerson;
	
	private String planOrgPersonName;

	/**
	 * 任务责任机构
	 */
	private String inChargeOrgId;
	
	private String inChargeOrgName;
	
	/**
	 * 任务责任机构负责人
	 */
	private String inChargeOrgPerson;
	
	private String inChargeOrgPersonName;

	/**
	 * 任务状态
	 */
	private Integer state;
	
	/**
	 * 任务制定时间
	 */
	private Date planTime;
	
	/**
	 * 任务发布时间
	 */
	private Date issueTime;
	
	/**
	 * 任务启动时间
	 */
	private Date startTime;
	
	/**
	 * 任务结束时间
	 */
	private Date endTime;
	
	/**
	 * 任务备注
	 */
	private String note;
	
	private String stateStr;

	public String getPlanOrgPersonName() {
		return planOrgPersonName;
	}

	public void setPlanOrgPersonName(String planOrgPersonName) {
		this.planOrgPersonName = planOrgPersonName;
	}

	public String getInChargeOrgPersonName() {
		return inChargeOrgPersonName;
	}

	public void setInChargeOrgPersonName(String inChargeOrgPersonName) {
		this.inChargeOrgPersonName = inChargeOrgPersonName;
	}

	public String getPlanOrgName() {
		return planOrgName;
	}

	public void setPlanOrgName(String planOrgName) {
		this.planOrgName = planOrgName;
	}

	public String getInChargeOrgName() {
		return inChargeOrgName;
	}

	public void setInChargeOrgName(String inChargeOrgName) {
		this.inChargeOrgName = inChargeOrgName;
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

	public String getTarget() {
		if (target == null ){
			return "";
		}
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		if (content == null ){
			return "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlanOrgId() {
		if (planOrgId == null ){
			return "";
		}
		return planOrgId;
	}

	public void setPlanOrgId(String planOrgId) {
		this.planOrgId = planOrgId;
	}

	public String getPlanOrgPerson() {
		if (planOrgPerson == null ){
			return "";
		}
		return planOrgPerson;
	}

	public void setPlanOrgPerson(String planOrgPerson) {
		this.planOrgPerson = planOrgPerson;
	}

	public String getInChargeOrgId() {
		if (inChargeOrgId == null ){
			return "";
		}
		return inChargeOrgId;
	}

	public void setInChargeOrgId(String inChargeOrgId) {
		this.inChargeOrgId = inChargeOrgId;
	}

	public String getInChargeOrgPerson() {
		if (inChargeOrgPerson == null ){
			return "";
		}
		return inChargeOrgPerson;
	}

	public void setInChargeOrgPerson(String inChargeOrgPerson) {
		this.inChargeOrgPerson = inChargeOrgPerson;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	@Override
	public String toString() {
		return "TaskBook [name=" + name + ", target=" + target + ", type="
				+ type + ", content=" + content + ", planOrgId=" + planOrgId
				+ ", planOrgName=" + planOrgName + ", planOrgPerson="
				+ planOrgPerson + ", planOrgPersonName=" + planOrgPersonName
				+ ", inChargeOrgId=" + inChargeOrgId + ", inChargeOrgName="
				+ inChargeOrgName + ", inChargeOrgPerson=" + inChargeOrgPerson
				+ ", inChargeOrgPersonName=" + inChargeOrgPersonName
				+ ", state=" + state + ", planTime=" + planTime
				+ ", issueTime=" + issueTime + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", note=" + note + ", stateStr="
				+ stateStr + "]";
	}

}
