package com.entity.securityinfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventCondition {

	/** 开始时间，为null表示不限制开始时间
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
	 * 事件名称
	 */
	private String name;
	
	/**
	 * 发生地点
	 */
	private String position;
	
	/**
	 * 责任部门
	 */
	private String inChargeOrg;
	
	/**
	 * 事件定级
	 */
	private String eventLevel;
	
	private String searchUri;

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getInChargeOrg() {
		return inChargeOrg;
	}

	public void setInChargeOrg(String inChargeOrg) {
		this.inChargeOrg = inChargeOrg;
	}

	public String getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}
	
	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}

	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("eventCondition.belongToOrgId=").append(this.belongToOrgId).append("&");	
		try {
			buffer.append("eventCondition.beginTime=").append(this.beginTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginTime)).append("&");
			buffer.append("eventCondition.endTime=").append(this.endTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime)).append("&");
		} catch (Exception e) {
		}
		buffer.append("eventCondition.name=").append(this.name==null ||this.name =="null"?"":this.name).append("&");
		buffer.append("eventCondition.position=").append(this.position==null ||this.position =="null"?"":this.position).append("&");
		buffer.append("eventCondition.eventLevel=").append(this.eventLevel==null ||this.eventLevel =="null"?"":this.eventLevel).append("&");
		buffer.append("eventCondition.inChargeOrg=").append(this.inChargeOrg==null ||this.inChargeOrg =="null"?"":this.inChargeOrg);
		this.searchUri = buffer.toString();
		return searchUri;
	}

	@Override
	public String toString() {
		return "EventCondition [beginTime=" + beginTime + ", endTime="
				+ endTime + ", belongToOrgId=" + belongToOrgId + ", name="
				+ name + ", position=" + position + ", inChargeOrg="
				+ inChargeOrg + ", eventLevel=" + eventLevel + "]";
	}
	
}
