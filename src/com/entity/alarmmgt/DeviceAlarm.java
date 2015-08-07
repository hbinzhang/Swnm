package com.entity.alarmmgt;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceAlarm extends Alarm{
	
	/**
	 * 可能原因
	 */
	private String cause;
	
	/**
	 * 可能后果
	 */
	private String result;
	
	/**
	 * 建议操作
	 */
	private String operation;
		
	/**
	 * 是否影响业务,0 不影响,1 影响
	 */
	private Integer isAffect = 0;
	
	/**
	 * 告警码
	 */
	private Integer alarmCode;
	
	/**
	 * 告警名称
	 * 高压脉冲主机入侵告警
	 * 光纤振动告警
	 * 一体化探测跨越告警
	 * 一体化探测触碰
	 * 警戒线
	 * 高空抛物
	 * 渠道漂浮物
	 * 人迹追踪
	 */
	private String alarmName;

	/**
	 * 告警级别
	 * 1 轻微
	 * 2 一般
	 * 3 严重
	 * 4非常严重
	 */
	private Integer alarmLevel = -1;
	
	/**
	 * 设备类型
	 * 1 高压电子围栏
	 * 2 振动光纤
	 * 3 一体化
	 * 4 智能分析服务器
	 */
	private Integer deviceType = -1;
	
	/**
	 * 设备Id
	 */
	private String deviceId;
	
	/**
	 * 防区
	 */	
	private String zoneName;
	
	/**
	 * 防区Id
	 */	
	private Integer zoneId;
	
	/**
	 * 管理处
	 */	
	private String departmentName;
	
	/**
	 * 管理处Id
	 */	
	private String departmentId;
	
	/**
	 * 分公司
	 */	
	private String branchName;
	
	/**
	 * 附加信息
	 */
	private String info;
	
	/**
	 * 告警类型
	 * 1 安防告警
	 * 2 设备告警
	 */
	private Integer type = -1;
	
	/**
	 * 处理状态
	 * 0 未处理
	 * 1 已处理
	 */
	private Integer alarmStatus = 0;
	
	/**
	 * 处理时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date managerTime;
	
	/**
	 * 责任人Id
	 */
	private String peopleId;	

	/**
	 * 分公司Id
	 */	
	private String branchId;
	
	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getDepartmentId() {
		if (departmentId == null ){
			return "";
		}
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getManagerTime() {
		return managerTime;
	}

	public void setManagerTime(Date managerTime) {
		this.managerTime = managerTime;
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
	
	public String getCause() {
		if (cause == null ){
			return "";
		}
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
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

	public String getOperation() {
		if (operation == null ){
			return "";
		}
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Integer getIsAffect() {
		return isAffect;
	}

	public void setIsAffect(Integer isAffect) {
		this.isAffect = isAffect;
	}

	public Integer getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}

	public String getAlarmName() {
		if (alarmName == null ){
			return "";
		}
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
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

	public String getPeopleId() {
		if (peopleId == null ){
			return "";
		}
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
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

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		if (deviceId == null ){
			return "";
		}
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getInfo() {
		if (info == null ){
			return "";
		}
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return super.toString() + "DeviceAlarm [cause=" + cause + ", result=" + result
				+ ", operation=" + operation + ", isAffect=" + isAffect
				+ ", alarmCode=" + alarmCode + ", alarmName=" + alarmName
				+ ", alarmLevel=" + alarmLevel + ", deviceType=" + deviceType
				+ ", deviceId=" + deviceId + ", zoneName=" + zoneName
				+ ", departmentName=" + departmentName + ", departmentId="
				+ departmentId + ", branchName=" + branchName + ", info="
				+ info + ", type=" + type + ", alarmStatus=" + alarmStatus
				+ ", managerTime=" + managerTime + ", peopleId=" + peopleId
				+ ", branchId=" + branchId + ", zoneId=" + zoneId + "]";
	}
	
}
