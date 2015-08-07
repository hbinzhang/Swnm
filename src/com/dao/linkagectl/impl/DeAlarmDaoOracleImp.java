package com.dao.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.IAlarmDao;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.linkagectl.AlarmResult;
import com.entity.linkagectl.SecurityAlarm;;

public class DeAlarmDaoOracleImp implements IAlarmDao {
	
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteDeAlarm",o.toString());
		return true;
	}

	public List findall() throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllDeAlarm",null);
	}

	public Object findAlarm(Object o) throws SQLException{
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getDeAlarm",o.toString());
	}

	public int save(Object o) throws SQLException{
		// TODO Auto-generated method stub
		int id =(Integer )sqlmapclienttemplate.insert("insertDeAlarm",o);
		return id;
	}

	public boolean update(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateDeAlarm",o);
		return true;
	}
	
	public boolean updateSimple(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateDeAlarmSimple",o);
		return true;
	}
	
	public List findallUIAlarm(String orgID,String orgLvl) throws SQLException
	{
		return (List)sqlmapclienttemplate.queryForList("getAllUIDeAlarm",orgID);
		
	}
	
	public DeviceAlarm findUIDevAlarmByID(Integer alarmID) throws SQLException
	{
		return (DeviceAlarm) sqlmapclienttemplate.queryForObject("queryDeviceAlarmByID",alarmID);
		
	}
	
	public int getActiveAlarmCountByDevId(String devID) throws SQLException
	{
		return (Integer) sqlmapclienttemplate.queryForObject(

				"getActiveDevAlarmCount", devID);
	}

	@Override
	public List<AlarmResult> getActiveAlarmCountByOrgid(String orgLev,String orgID)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
