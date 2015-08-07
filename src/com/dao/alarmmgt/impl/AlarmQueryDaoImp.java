package com.dao.alarmmgt.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.alarmmgt.IAlarmQueryDao;
import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;

public class AlarmQueryDaoImp implements IAlarmQueryDao{
	
	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	
	/**
	 * 查询安防告警
	 * @param alarmQueryConditon
	 * @return List<SecurityAlarm>
	 */
	public List querySecurityAlarm(AlarmQueryCondition alarmQueryConditon) {
		return sqlmapclienttemplate.queryForList("querySecurityAlarm", 
				alarmQueryConditon);
	}
	
	/**
	 * 查询设备告警
	 * @param alarmStatisticConditon
	 * @return List<AlarmStatisticInfo>
	 */
	public List queryDeviceAlarm(AlarmQueryCondition alarmQueryConditon) {
		return sqlmapclienttemplate.queryForList("queryDeviceAlarm", 
				alarmQueryConditon);
	}

	public int getSecurityAlarmCount(AlarmQueryCondition alarmQueryCondition) {
		return (Integer) sqlmapclienttemplate.queryForObject(
				"getSecurityAlarmCount", alarmQueryCondition);
	}

	public int getDeviceAlarmCount(AlarmQueryCondition alarmQueryCondition) {
		return (Integer) sqlmapclienttemplate.queryForObject(
				"getDeviceAlarmCount", alarmQueryCondition);
	}

	public List queryDeviceAlarmByStatus(DeviceAlarm deviceAlarm) {
		return sqlmapclienttemplate.queryForList("queryDeviceAlarmByStatus", deviceAlarm);
	}

	public void updateDeviceAlarm(DeviceAlarm deviceAlarm) {
		sqlmapclienttemplate.update("updateDeviceAlarm", deviceAlarm);	
	}

	public List queryZoneByDepartId(String departmentId) {
		return sqlmapclienttemplate.queryForList("queryZoneByDepartId", 
				departmentId);		
	}

	public Object getZoneNameById(int zoneId) {
		return sqlmapclienttemplate.queryForObject("getZoneNameById", zoneId);
	}
	
	public int monitorSecurityAlarmCount(SecurityAlarm sAlarm) {
		return (Integer) sqlmapclienttemplate.queryForObject("monitorSecurityAlarmCount", 
				sAlarm);
	}

	public int monitorDeviceAlarmCount(DeviceAlarm dAlarm) {
		return (Integer) sqlmapclienttemplate.queryForObject("monitorDeviceAlarmCount", 
				dAlarm);
	}

	@Override
	public Object querySecAlarmById(int alarmId) {
		return sqlmapclienttemplate.queryForObject("querySecAlarmById", 
				alarmId);
	}

	@Override
	public List queryZoneInfos() {
		return sqlmapclienttemplate.queryForList("queryZoneInfos");		
	}

	@Override
	public Object queryDeviceAlarmById(int alarmId) {
		return sqlmapclienttemplate.queryForObject("queryDeviceAlarmById", 
				alarmId);
	}

	@Override
	public List queryDepDevAlarmByStatus(DeviceAlarm dAlarm) {
		return sqlmapclienttemplate.queryForList("queryDepDevAlarmByStatus", 
				dAlarm);
	}

	@Override
	public List queryCompDevAlarmByStatus(DeviceAlarm dAlarm) {
		return sqlmapclienttemplate.queryForList("queryCompDevAlarmByStatus", 
				dAlarm);
	}

	@Override
	public List queryBranchDevAlarmByStatus(DeviceAlarm dAlarm) {
		return sqlmapclienttemplate.queryForList("queryBranchDevAlarmByStatus", 
				dAlarm);
	}

	@Override
	public List statDAlarmCountUnHandled4Branch(String orgId) {
		return sqlmapclienttemplate.queryForList("statDAlarmCountUnHandled4Branch", 
				orgId);
	}

	@Override
	public List statDAlarmCountUnHandled4Dep(String orgId) {
		return sqlmapclienttemplate.queryForList("statDAlarmCountUnHandled4Dep", 
				orgId);
	}

	
}
