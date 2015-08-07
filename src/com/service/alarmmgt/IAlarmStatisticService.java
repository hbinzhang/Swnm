package com.service.alarmmgt;

import java.util.List;

import com.entity.alarmmgt.AlarmStatisticCondition;

public interface IAlarmStatisticService {

	public List statisticSecurityAlarm(AlarmStatisticCondition alarmStatisticCondition);
	
	public List statisticDeviceAlarm(AlarmStatisticCondition alarmStatisticCondition);

	public int getStatisSecAlarmCount(AlarmStatisticCondition alarmStatisticCondition);

	public int getStatisDevAlarmCount(AlarmStatisticCondition alarmStatisticCondition);
}
