package com.service.alarmmgt.impl;

import java.util.List;

import com.dao.alarmmgt.IAlarmStatisticDao;
import com.entity.alarmmgt.AlarmStatisticCondition;
import com.service.alarmmgt.IAlarmStatisticService;

public class AlarmStatisticServiceImp implements IAlarmStatisticService{
	
	private IAlarmStatisticDao alarmStatisticDao = null;

	public void setAlarmStatisticDao(IAlarmStatisticDao alarmStatisticDao) {
		this.alarmStatisticDao = alarmStatisticDao;
	}

	@Override
	public List statisticSecurityAlarm(
			AlarmStatisticCondition alarmStatisticCondition) {
		return alarmStatisticDao.statisticSecurityAlarm(alarmStatisticCondition);
	}

	@Override
	public List statisticDeviceAlarm(
			AlarmStatisticCondition alarmStatisticCondition) {
		return alarmStatisticDao.statisticDeviceAlarm(alarmStatisticCondition);
	}

	@Override
	public int getStatisSecAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition) {
		return alarmStatisticDao.getStatisSecAlarmCount(alarmStatisticCondition);
	}

	@Override
	public int getStatisDevAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition) {
		return alarmStatisticDao.getStatisDevAlarmCount(alarmStatisticCondition);
	}

}
