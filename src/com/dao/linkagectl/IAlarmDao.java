package com.dao.linkagectl;

import java.sql.SQLException;
import java.util.List;

import com.entity.alarmmgt.DeviceAlarm;
import com.entity.linkagectl.AlarmResult;


public interface IAlarmDao {
	
	public List findall()throws SQLException ;
	
	public Object findAlarm(Object o) throws SQLException;
	
	public int save(Object o) throws SQLException;
	
	public boolean update(Object o)throws SQLException;
	public boolean updateSimple(Object o)throws SQLException;
	
	public boolean delete(Object o)throws SQLException;
	
	public List findallUIAlarm(String orgID,String orgLvl)throws SQLException;
	
	public DeviceAlarm findUIDevAlarmByID(Integer alarmID) throws SQLException;

	public int getActiveAlarmCountByDevId(String devID) throws SQLException;

	public List<AlarmResult> getActiveAlarmCountByOrgid(String orgLev,String orgID) throws SQLException;

}
