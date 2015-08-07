package com.dao.linkagectl;

import java.util.List;

public interface IZoneInfoDao {
	
	public List findall();
	
	public Object findZoneInfo(Integer zoneID);
	
	public boolean save(Object o);
	
	public boolean update(Object o);
	
	public boolean delete(Object o);
}
