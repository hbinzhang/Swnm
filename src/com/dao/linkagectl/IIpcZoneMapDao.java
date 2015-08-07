package com.dao.linkagectl;

import java.sql.SQLException;
import java.util.List;

import com.entity.linkagectl.IpcZoneMap;

public interface IIpcZoneMapDao {
	
	public List findall(int ZoneID)throws SQLException;
	
	public Object findIpcZoneMap(Object o)throws SQLException;
	
	public boolean save(Object o)throws SQLException;
	
	public boolean update(Object o)throws SQLException;
	
	public boolean delete(Object o)throws SQLException;
	
	public void delIpcZoneMapByZoneID(Integer zoneID)throws Exception;

	public void updateIpcZoneMap(IpcZoneMap ipcZoneMap)throws Exception;

	public List<IpcZoneMap> getIpcZoneMapsByZoneID(Integer zoneID)throws Exception;
	public void delIpcZoneMapByZoneIdAndIpcId(IpcZoneMap ipcZoneMap)throws Exception;

	public void delIpcMapInfoById(String id)throws Exception;
	public void delIpcMapByIpcId(String ipcID)throws Exception;

	public List<Integer> getPointByIpcId(String ipcID)throws Exception;
}
