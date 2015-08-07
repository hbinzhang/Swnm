package com.entity.linkagectl;

import java.util.Date;

public class AlarmKnowledge /*implements java.io.Serializable*/{
	private Integer alarmCode;  	//告警码
	private String alarmName; 		//告警名称
	private Integer alarmType; 		//告警类型
	private Integer deviceType; 	//设备类型
	private Integer alarmLevel=2; 	//告警级别
	private String cause;      		//可能原因
	private String result;			//可能后果
	private String operation;		//建议操作
	private Integer isAffect;		//是否影响业务
	private String info;			//备注
	
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
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public Integer getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOperation() {
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
