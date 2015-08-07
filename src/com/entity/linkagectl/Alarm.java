package com.entity.linkagectl;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Alarm implements java.io.Serializable {
	
	private Integer alarmID; //告警编号
	private Integer alarmCode; //告警码
	private Date  alarmTime ; //告警时间
	private String deviceID; //设备ID
	private Integer zoneID;      //防区ID
	private String departmentID; //管理处ID
	private String branchID;     //分公司ID
	private Integer alarmStatus; //处理状态
	private Date managerTime ;//处理时间
	
	public Alarm(Integer alarmID, Integer alarmCode, Date alarmTime,
			String deviceID, Integer zoneID, String departmentID,
			String branchID, Integer alarmStatus, Date managerTime) {
		super();
		this.alarmID = alarmID;
		this.alarmCode = alarmCode;
		this.alarmTime = alarmTime;
		this.deviceID = deviceID;
		this.zoneID = zoneID;
		this.departmentID = departmentID;
		this.branchID = branchID;
		this.alarmStatus = alarmStatus;
		this.managerTime = managerTime;
	}
	public Alarm() {
		super();
				
		// TODO Auto-generated constructor stub
	}
	public Integer getAlarmID() {
		
		return alarmID;
	}
	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}
	public Integer getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}
	
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public Integer getZoneID() {
		return zoneID;
	}
	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getBranchID() {
		return branchID;
	}
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	public Integer getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public Date getManagerTime() {
		return managerTime;
	}
	public void setManagerTime(Date managerTime) {
		this.managerTime = managerTime;
	}
	
	
	
}
