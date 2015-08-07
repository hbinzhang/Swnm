package com.entity.alarmmgt;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class AlarmStatisticCondition {
	
	/**
	 * 告警类型
	 * 1 安防告警
	 * 2 设备告警
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
	 * 统计粒度
	 * 1 分公司
	 * 2 管理处
	 * 3 防区
	 * 4 设备类型
	 * 5 告警级别
	 */
	private List<Integer> staticGranularity;
	
	/**
	 * 时间粒度
	 * DD 天
	 * DY 周
	 * MM 月
	 * Q  季度
	 * YY 年
	 */
	private String timeGranularity;
	
	private String departmentId;
	
	private int zoneId = -1;
	private int deviceTypeId = -1;
	private int levelId = -1;
	
	private String branchId;
	
	private String groupByStr;
	
	/**
	 * 查询 的结果列
	 */
	private String columnStr;
	
	/**
	 * where 条件
	 */
	private String whereStr;
	
	private String sqlStr;
	
	/**
	 * 分页起始条数
	 */
	private int start;
	
	/**
	 * 分页结束条数
	 */
	private int end;


	private int offset;
	
	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getWhereStr() {
		return whereStr;
	}

	public void setWhereStr(String whereStr) {
		this.whereStr = whereStr;
	}

	public String getColumnStr() {
		return columnStr;
	}

	public void setColumnStr(String columnStr) {
		this.columnStr = columnStr;
	}

	public String getGroupByStr() {
		return groupByStr;
	}

	public void setGroupByStr(String groupByStr) {
		this.groupByStr = groupByStr;
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

	public List<Integer> getStaticGranularity() {
		return staticGranularity;
	}

	public void setStaticGranularity(List<Integer> staticGranularity) {
		this.staticGranularity = staticGranularity;
	}

	public String getTimeGranularity() {
		return timeGranularity;
	}

	public void setTimeGranularity(String timeGranularity) {
		this.timeGranularity = timeGranularity;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
		return "AlarmStatisticCondition [type=" + type + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", staticGranularity="
				+ staticGranularity + ", timeGranularity=" + timeGranularity
				+ ", departmentId=" + departmentId + ", zoneId=" + zoneId
				+ ", deviceTypeId=" + deviceTypeId + ", levelId=" + levelId
				+ ", branchId=" + branchId + ", groupByStr=" + groupByStr
				+ ", columnStr=" + columnStr + ", whereStr=" + whereStr
				+ ", sqlStr=" + sqlStr + ", start=" + start + ", end=" + end
				+ ", offset=" + offset + "]";
	}
	
}
