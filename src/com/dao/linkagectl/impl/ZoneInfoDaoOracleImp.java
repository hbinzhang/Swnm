package com.dao.linkagectl.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.IIpcZoneMapDao;
import com.dao.linkagectl.IZoneInfoDao;
import com.entity.linkagectl.IpcZoneMap;


public class ZoneInfoDaoOracleImp implements IZoneInfoDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) {
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteZoneInfo",o);
		return true;
	}

	public List findall() {
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllZoneInfo",null);
	}

	public Object findZoneInfo(Integer zoneID) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getZoneInfo",zoneID);
	}

	public boolean save(Object o) {
		// TODO Auto-generated method stub
		sqlmapclienttemplate.insert("insertZoneInfo",o);
		return true;
	}

	public boolean update(Object o) {
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateZoneInfo",o);
		return true;
	}
	

}
