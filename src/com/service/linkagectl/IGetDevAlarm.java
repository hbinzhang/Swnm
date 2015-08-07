package com.service.linkagectl;

import com.entity.linkagectl.DeviceAlarm;

public interface IGetDevAlarm {
	
	public String findUIMcDevAlarmInfo(Integer alarmID);
	public int  getActiveAlarmCountByDevId(String devID);
}