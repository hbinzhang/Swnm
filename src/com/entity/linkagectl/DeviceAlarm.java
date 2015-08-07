package com.entity.linkagectl;

import java.sql.Timestamp;
import java.util.Date;

public class DeviceAlarm extends Alarm {
	private String info;//附加信息
	private String peopleID=" ";       //处理人ID
	
	public String getPeopleID() {
		if(peopleID==null){
			return "";
		}
		return peopleID;
	}

	public void setPeopleID(String peopleID) {
		this.peopleID = peopleID;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public DeviceAlarm() {
		super();		
		// TODO Auto-generated constructor stub
	}

	public DeviceAlarm(Integer alarmID, Integer alarmCode, Date alarmTime,
			String deviceID, Integer zoneID, String departmentID,
			String branchID, Integer alarmStatus, Date managerTime,
			String info, String peopleID) {
		super(alarmID, alarmCode, alarmTime, deviceID, zoneID, departmentID,
				branchID, alarmStatus, managerTime);
		this.info = info;
		this.peopleID = peopleID;
	}

	

	

	
	
}
