package com.dao.alarmmgt;

import java.util.List;

import com.entity.alarmmgt.AlarmStatisticCondition;

public interface IAlarmStatisticDao {

	public List statisticSecurityAlarm(AlarmStatisticCondition 
			alarmStatisticConditon);
	
	public List statisticDeviceAlarm(AlarmStatisticCondition 
			alarmStatisticConditon);

	public int getStatisSecAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition);

	public int getStatisDevAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition);
}
