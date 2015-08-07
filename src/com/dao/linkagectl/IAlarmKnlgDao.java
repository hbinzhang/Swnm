package com.dao.linkagectl;

import java.sql.SQLException;
import java.util.List;

public interface IAlarmKnlgDao {
	
	public List findall()throws SQLException ;
	
	public Object findAlarmKnowledge(String alarmCode)throws SQLException ;
	
	public boolean save(Object o)throws SQLException ;
	
	public boolean update(Object o)throws SQLException ;
	
	public boolean delete(Object o)throws SQLException ;
	
	public Object findUIAlarm(Object o)throws SQLException ;
}
