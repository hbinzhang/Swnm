package com.dao.alarmmgt;

import java.util.List;
import java.util.Map;

import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;

public interface IAlarmQueryDao {
	
	public List querySecurityAlarm(AlarmQueryCondition alarmQueryConditon);
	
	public List queryDeviceAlarm(AlarmQueryCondition alarmQueryConditon);

	public int getSecurityAlarmCount(AlarmQueryCondition alarmQueryCondition);

	public int getDeviceAlarmCount(AlarmQueryCondition alarmQueryCondition);

	public List queryDeviceAlarmByStatus(DeviceAlarm deviceAlarm);

	public void updateDeviceAlarm(DeviceAlarm deviceAlarm);

	public List queryZoneByDepartId(String departmentId);

	public Object getZoneNameById(int zoneId);

	public int monitorSecurityAlarmCount(SecurityAlarm sAlarm);

	public int monitorDeviceAlarmCount(DeviceAlarm dAlarm);

	public Object querySecAlarmById(int alarmId);

	public List queryZoneInfos();
	
	public Object queryDeviceAlarmById(int alarmId);

	public List queryDepDevAlarmByStatus(DeviceAlarm dAlarm);

	public List queryCompDevAlarmByStatus(DeviceAlarm dAlarm);

	public List queryBranchDevAlarmByStatus(DeviceAlarm dAlarm);

	public List statDAlarmCountUnHandled4Branch(String orgId);

	public List statDAlarmCountUnHandled4Dep(String orgId);

}
