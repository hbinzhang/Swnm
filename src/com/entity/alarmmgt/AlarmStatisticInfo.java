package com.entity.alarmmgt;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

public class AlarmStatisticInfo {
		
	/**
	 * 防区
	 */	
	private String zoneName;
	
	/**
	 * 管理处
	 */	
	private String departmentName;
	
	/**
	 * 分公司
	 */	
	private String branchName;
	
	/**
	 * 管理处
	 */	
	private String departmentId;
	
	/**
	 * 分公司
	 */	
	private String branchId;
	
	private Integer zoneId;
	
	/**
	 * 告警个数
	 */
	private Integer alarmNum;
	
	/**
	 * 设备类型
	 */
	private Integer deviceType;
	
	/**
	 * 告警类型
	 */
	private Integer type;

	/**
	 * 告警级别
	 * 1 轻微
	 * 2 一般
	 * 3 严重
	 * 4非常严重
	 */
	private Integer checkLevel;
	
	/**
	 * 告警级别
	 * 1 轻微
	 * 2 一般
	 * 3 严重
	 * 4非常严重
	 */
	private Integer alarmLevel;
	
	/**
	 * 开始时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	/**
	 * 结束时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	public String getDepartmentId() {
		if (departmentId == null ){
			return "";
		}
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getBranchId() {
		if (branchId == null ){
			return "";
		}
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(Integer alarmNum) {
		this.alarmNum = alarmNum;
	}

	public String getZoneName() {
		if (zoneName == null ){
			return "";
		}
		return zoneName;
	}

	public void setZoneName(String zoneName) {	
		this.zoneName = zoneName;
	}

	public String getDepartmentName() {
		if (departmentName == null ){
			return "";
		}
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBranchName() {
		if (branchName == null ){
			return "";
		}
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCheckLevel() {
		return checkLevel;
	}

	public void setCheckLevel(Integer checkLevel) {
		this.checkLevel = checkLevel;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Override
	public String toString() {
		return "AlarmStatisticInfo [zoneName=" + zoneName + ", departmentName="
				+ departmentName + ", branchName=" + branchName
				+ ", departmentId=" + departmentId + ", branchId=" + branchId
				+ ", zoneId=" + zoneId + ", alarmNum=" + alarmNum
				+ ", deviceType=" + deviceType + ", type=" + type
				+ ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", checkLevel=" + checkLevel + ", alarmLevel=" + alarmLevel
				+ "]";
	}

}
