package com.dao.alarmmgt.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.alarmmgt.IAlarmStatisticDao;
import com.entity.alarmmgt.AlarmStatisticCondition;

public class AlarmStatisticDaoImp implements IAlarmStatisticDao{
	
	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	/**
	 * 统计安防告警
	 * @param alarmStatisticConditon
	 * @return List<AlarmStatisticInfo>
	 */
	public List statisticSecurityAlarm(AlarmStatisticCondition alarmStatisticConditon) {
		return sqlmapclienttemplate.queryForList(
				"statisticSecurityAlarm", alarmStatisticConditon);
	}
	
	/**
	 * 统计设备告警
	 * @param alarmStatisticConditon
	 * @return List<AlarmStatisticInfo>
	 */
	public List statisticDeviceAlarm(AlarmStatisticCondition alarmStatisticConditon) {
		return sqlmapclienttemplate.queryForList("statisticDeviceAlarm", 
				alarmStatisticConditon);
	}
	
	@Override
	public int getStatisSecAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getStatisSecAlarmCount", 
				alarmStatisticCondition);
	}
	
	@Override
	public int getStatisDevAlarmCount(
			AlarmStatisticCondition alarmStatisticCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getStatisDevAlarmCount", 
				alarmStatisticCondition);
	}

}
