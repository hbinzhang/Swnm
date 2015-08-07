package com.entity.securityinfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskBookCondition {

	/**
	 * 开始时间，为null表示不限制开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间，为null表示不限制结束时间
	 */
	private Date endTime;
	
	/**
	 * 任务责任机构
	 */
	private String inChargeOrgId;
	
	/**
	 * 任务类型
	 * 0: 月度任务 1: 季度任务 2: 年度任务
	 */
	private int type = -1;	
	
	/**
	 * 1按任务发布时间查
	 * 2 按任务启动时间查
	 * 3 按任务结束时间查
	 */
	private int queryTaskBookFlag;
	
	/**
	 * 任务状态
	 * 0:未发布 1:已发布2:已关闭
	 */
	private int state = -1;
	
	private String searchUri;
	
	/**
	 * 任务制定机构
	 */
	private String planOrgId;
	
//	private String subPlanOrgId;
	

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
	
	public boolean beginTimeIsValid() {
		if (beginTime == null) {
			return false;
		}
		return true;
	}

	public boolean endTimeIsValid() {
		if (endTime == null) {
			return false;
		}
		return true;
	}
	
	public String getInChargeOrgId() {
		return inChargeOrgId;
	}

	public void setInChargeOrgId(String inChargeOrgId) {
		this.inChargeOrgId = inChargeOrgId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQueryTaskBookFlag() {
		return queryTaskBookFlag;
	}

	public void setQueryTaskBookFlag(int queryTaskBookFlag) {
		this.queryTaskBookFlag = queryTaskBookFlag;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}
	
	
	public String getPlanOrgId() {
		return planOrgId;
	}

	public void setPlanOrgId(String planOrgId) {
		this.planOrgId = planOrgId;
	}

	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("taskBookCondition.queryTaskBookFlag=").append(this.queryTaskBookFlag).append("&");	
		try {
			buffer.append("taskBookCondition.beginTime=").append(this.beginTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginTime)).append("&");
			buffer.append("taskBookCondition.endTime=").append(this.endTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime)).append("&");
		} catch (Exception e) {
		}
		buffer.append("taskBookCondition.planOrgId=").append(this.planOrgId).append("&");
		buffer.append("taskBookCondition.inChargeOrgId=").append(this.inChargeOrgId).append("&");
//		buffer.append("taskBookCondition.subPlanOrgId=").append(this.subPlanOrgId).append("&");
		buffer.append("taskBookCondition.type=").append(this.type).append("&");
		buffer.append("taskBookCondition.state=").append(this.state);
		this.searchUri = buffer.toString();
		return searchUri;
	}

	/*public String getSubPlanOrgId() {
		return subPlanOrgId;
	}

	public void setSubPlanOrgId(String subPlanOrgId) {
		this.subPlanOrgId = subPlanOrgId;
	}*/

	@Override
	public String toString() {
		return "TaskBookCondition [beginTime=" + beginTime + ", endTime="
				+ endTime + ", inChargeOrgId=" + inChargeOrgId + ", type="
				+ type + ", queryTaskBookFlag=" + queryTaskBookFlag
				+ ", planOrgId = " + planOrgId +", state=" + state 
				+ ", searchUri=" + searchUri + "]";
	}

}
