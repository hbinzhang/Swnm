package com.entity.alarmmgt;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class AlarmQueryCondition {
	
	/**
	 * 告警类型
	 * 1安防告警
	 * 2设备告警
	 */
	private int type = -1;
	
	/**
	 * 开始时间，为null表示不限制开始时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	/**
	 * 结束时间，为null表示不限制结束时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/**
	 * 设备Id
	 */
	private String deviceId;
	
	/**
	 * 告警名称
	 */
	private String alarmName;
	
	/**
	 * 是否有事件视频
	 * 0 没有
	 * 1 有
	 */
	private int hasEventVideo = 0;
	
	/**
	 * 处理状态
	 * 0 未处理
	 * 1 已处理
	 */
	private int alarmStatus = -1;
	
	/**
	 * 分页起始条数
	 */
	private int start;
	
	/**
	 * 分页结束条数
	 */
	private int end;
	
	private String departmentId;
	
	private int zoneId = -1;
	private int deviceTypeId = -1;
	private int levelId = -1;
	
	private String branchId;
	
	
	private int offset;
	

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public int getHasEventVideo() {
		return hasEventVideo;
	}

	public int getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(int alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public void setHasEventVideo(int hasEventVideo) {
		this.hasEventVideo = hasEventVideo;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "AlarmQueryCondition [type=" + type + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", deviceId=" + deviceId
				+ ", alarmName=" + alarmName + ", hasEventVideo="
				+ hasEventVideo + ", alarmStatus=" + alarmStatus + ", start="
				+ start + ", end=" + end + ", departmentId=" + departmentId
				+ ", zoneId=" + zoneId + ", deviceTypeId=" + deviceTypeId
				+ ", levelId=" + levelId + ", branchId=" + branchId
				+ ", offset=" + offset + "]";
	}
	

}
