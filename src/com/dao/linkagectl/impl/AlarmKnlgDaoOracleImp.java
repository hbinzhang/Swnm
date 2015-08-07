package com.dao.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.IAlarmDao;
import com.dao.linkagectl.IAlarmKnlgDao;

//import com.entity.linkagectl.SecurityAlarm;;

public class AlarmKnlgDaoOracleImp implements IAlarmKnlgDao{
	
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) 
	{
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException 
	{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteAlarmKnowledge",o.toString());
		return true;
	}

	public List findall() throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllAlarmKnowledge",null);
	}

	public Object findAlarmKnowledge(String alarmCode) throws SQLException{
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getAlarmKnowledge",alarmCode);
	}

	public boolean save(Object o) throws SQLException {
		// TODO Auto-generated method stub
		sqlmapclienttemplate.insert("insertAlarmKnowledge",o);
		return true;
	}

	public boolean update(Object o) throws SQLException {
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateAlarmKnowledge",o);
		return true;
	}
	
	public Object findUIAlarm (Object o)throws SQLException
	{
		sqlmapclienttemplate.update("getUIAlarm",o);
		return true;
	}
}
