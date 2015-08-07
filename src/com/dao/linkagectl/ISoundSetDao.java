package com.dao.linkagectl;

import java.sql.SQLException;
import java.util.List;

public interface ISoundSetDao {
	
	public List findall()throws SQLException;
	
	public Object findSoundSetFromAlarmCode(Object o)throws SQLException;
	public Object findSoundSet(Object o)throws SQLException;
	
	public boolean save(Object o)throws SQLException;
	
	public boolean update(Object o)throws SQLException;
	
	public boolean delete(Object o)throws SQLException;
}
