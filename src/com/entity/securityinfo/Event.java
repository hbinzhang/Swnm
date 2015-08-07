package com.entity.securityinfo;

import java.util.Date;

public class Event {
	
	/**
	 * 所属机构
	 */
	private String belongToOrgId;
	
	/**
	 * 所属机构
	 */
	private String belongToOrgName;
	
	/**
	 * 事件名称
	 */
	private String name;
	
	/**
	 * 发生时间
	 */
	private Date time;
	
	/**
	 * 发生地点
	 */
	private String position;
	
	/**
	 * 所属区域和管理部门
	 */
	private String eventBelongToOrg;
	
	/**
	 * 事件定级
	 */
	private String eventLevel;

	/**
	 * 事件原因
	 */
	private String reason;

	/**
	 * 事件过程
	 */
	private String process;

	/**
	 * 事件结果
	 */
	private String result;

	/**
	 * 影响范围
	 */
	private String influenceRange;
	
	/**
	 * 责任部门
	 */
	private String inChargeOrg;
	
	/**
	 * 责任部门责任人
	 */
	private String inChargePerson;
	
	/**
	 * 补救措施
	 */
	private String remedialMeasures;
	
	/**
	 * 补救结果
	 */
	private String remedialResult;
	
	/**
	 * 事后评估
	 */
	private String postEvaluation;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPosition() {
		if (position == null ){
			return "";
		}
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEventBelongToOrg() {
		if (eventBelongToOrg == null ){
			return "";
		}
		return eventBelongToOrg;
	}

	public void setEventBelongToOrg(String eventBelongToOrg) {
		this.eventBelongToOrg = eventBelongToOrg;
	}

	public String getEventLevel() {
		if (eventLevel == null ){
			return "";
		}
		return eventLevel;
	}

	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getReason() {
		if (reason == null ){
			return "";
		}
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProcess() {
		if (process == null ){
			return "";
		}
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getResult() {
		if (result == null ){
			return "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInfluenceRange() {
		if (influenceRange == null ){
			return "";
		}
		return influenceRange;
	}

	public void setInfluenceRange(String influenceRange) {
		this.influenceRange = influenceRange;
	}

	public String getInChargeOrg() {
		if (inChargeOrg == null ){
			return "";
		}
		return inChargeOrg;
	}

	public void setInChargeOrg(String inChargeOrg) {
		this.inChargeOrg = inChargeOrg;
	}

	public String getInChargePerson() {
		if (inChargePerson == null ){
			return "";
		}
		return inChargePerson;
	}

	public void setInChargePerson(String inChargePerson) {
		this.inChargePerson = inChargePerson;
	}

	public String getRemedialMeasures() {
		if (remedialMeasures == null ){
			return "";
		}
		return remedialMeasures;
	}

	public void setRemedialMeasures(String remedialMeasures) {
		this.remedialMeasures = remedialMeasures;
	}

	public String getRemedialResult() {
		if (remedialResult == null ){
			return "";
		}
		return remedialResult;
	}

	public void setRemedialResult(String remedialResult) {
		this.remedialResult = remedialResult;
	}

	public String getPostEvaluation() {
		if (postEvaluation == null ){
			return "";
		}
		return postEvaluation;
	}

	public void setPostEvaluation(String postEvaluation) {
		this.postEvaluation = postEvaluation;
	}

	@Override
	public String toString() {
		return "Event [belongToOrgId=" + belongToOrgId + ", belongToOrgName="
				+ belongToOrgName + ", name=" + name + ", time=" + time
				+ ", position=" + position + ", eventBelongToOrg="
				+ eventBelongToOrg + ", eventLevel=" + eventLevel + ", reason="
				+ reason + ", process=" + process + ", result=" + result
				+ ", influenceRange=" + influenceRange + ", inChargeOrg="
				+ inChargeOrg + ", inChargePerson=" + inChargePerson
				+ ", remedialMeasures=" + remedialMeasures
				+ ", remedialResult=" + remedialResult + ", postEvaluation="
				+ postEvaluation + "]";
	}
	
}
