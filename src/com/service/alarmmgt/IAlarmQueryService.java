package com.service.alarmmgt;

import java.util.List;
import java.util.Map;

import com.entity.CommonBean;
import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;

public interface IAlarmQueryService {

	public List querySecurityAlarm(AlarmQueryCondition alarmQueryCondition);
	
	public List queryDeviceAlarm(AlarmQueryCondition alarmQueryCondition);	

	public int getSecurityAlarmCount(AlarmQueryCondition alarmQueryCondition);

	public int getDeviceAlarmCount(AlarmQueryCondition alarmQueryCondition);

	public List queryDeviceAlarmByStatus(DeviceAlarm deviceAlarm);

	public void updateDeviceAlarm(DeviceAlarm deviceAlarm);

	public List queryZoneByDepartId(String departmentId);

	public Object getZoneNameById(int zoneId);
	
	/**
	 * 查询未处理的安防告警，设备告警数量
	 * 如果用户是总公司用户，查询所有安防告警，设备告警数量
	 * 如果用户是分公司用户，查询分公司下的安防告警，设备告警数量
	 * 如果用户是管理处用户，查询管理处下的安防告警，设备告警数量
	 * list[0] 安防告警数量
	 * list[1] 设备告警数量
	 */
	public List monitorAlarmCount();

	public Object querySecAlarmById(int alarmId);

	public List queryZoneInfos();
	
	public Object queryDeviceAlarmById(int alarmId);

	public List queryBranchDevAlarmByStatus(DeviceAlarm dAlarm);

	public List queryDepDevAlarmByStatus(DeviceAlarm dAlarm);

	public List queryCompDevAlarmByStatus(DeviceAlarm dAlarm);

	/**
	 * 根据机构级别，机构id统计未处理的设备告警数量
	 * @param level
	 * @param orgId
	 * @returMap
	 */
	public List statDevAlarmCountUnHandled(String level, String orgId);
}
