package com.entity.linkagectl;

import java.util.Date;

public class UIGisSecurityAlarm extends UIMCSecurityAlarm{
	private String  alarmLongitude; 		//告警经度
	private String  alarmLatitude; 			//告警纬度
	//private String 	alarmInfo; 				//告警详细信息
	public String getAlarmLongitude() {
		return alarmLongitude;
	}
	public void setAlarmLongitude(String alarmLongitude) {
		this.alarmLongitude = alarmLongitude;
	}
	public String getAlarmLatitude() {
		return alarmLatitude;
	}
	public void setAlarmLatitude(String alarmLatitude) {
		this.alarmLatitude = alarmLatitude;
	}
	/*public String getAlarmInfo() {
		return alarmInfo;
	}
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}*/

	public UIGisSecurityAlarm (UIMCSecurityAlarm seAlarm)
	{
		super(seAlarm.getAlarmID(), seAlarm.getAlarmTime(),seAlarm.getAlarmDuringTime(),
				seAlarm.getAlarmZoneID(), seAlarm.getAlarmZoneName(),seAlarm.getAlarmName(),seAlarm.getDevType(),
				seAlarm.getDevID(),seAlarm.getSeverityLvl(),seAlarm.getAlarmCnt(),seAlarm.getMgtName(),
				seAlarm.getBranchName(),seAlarm.getInfo());
			}
}