package com.dao.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.IIpcZoneMapDao;
import com.entity.linkagectl.IpcZoneMap;


public class IpcZoneMapDaoOracleImp implements IIpcZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteIpcZoneMap",o.toString());
		return true;
	}

	public List findall(int ZoneID) throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllIpcZoneMapFromZoneID",ZoneID);
	}

	public Object findIpcZoneMap(Object o) throws SQLException{
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getIpcZoneMap",o.toString());
	}

	public boolean save(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.insert("insertIpcZoneMap",o);
		return true;
	}

	public boolean update(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateIpcZoneMap",o);
		return true;
	}
	public void delIpcZoneMapByZoneID(Integer zoneID) throws Exception {
		
		sqlmapclienttemplate.delete("delIpcZoneMapByZoneID", zoneID);
		
	}

	public void updateIpcZoneMap(IpcZoneMap ipcZoneMap) throws Exception {
		sqlmapclienttemplate.update("updateIpcZoneMap1", ipcZoneMap);
	}

	public List<IpcZoneMap> getIpcZoneMapsByZoneID(Integer zoneID)
			throws Exception {
		return (List<IpcZoneMap>)sqlmapclienttemplate.queryForList("getIpcZoneMapsByZoneID", zoneID);
	}
	@Override
	public void delIpcZoneMapByZoneIdAndIpcId(IpcZoneMap ipcZoneMap)
			throws Exception {
		sqlmapclienttemplate.delete("delIpcZoneMapByZoneIdAndIpcId", ipcZoneMap);
	}

	@Override
	public void delIpcMapInfoById(String id) throws Exception {
		sqlmapclienttemplate.delete("delIpcMapInfoById", id);
	}

	@Override
	public void delIpcMapByIpcId(String ipcID) throws Exception {
		
		sqlmapclienttemplate.delete("delIpcMapByIpcId", ipcID);
	}

	@Override
	public List<Integer> getPointByIpcId(String ipcID) throws Exception {
		return (List<Integer>)sqlmapclienttemplate.queryForList("getPointByIpcId",ipcID);
	}

}
