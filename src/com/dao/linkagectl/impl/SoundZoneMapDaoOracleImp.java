package com.dao.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.ISoundZoneMapDao;
import com.entity.linkagectl.SoundZoneMap;


public class SoundZoneMapDaoOracleImp implements ISoundZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteSoundZoneMap",o.toString());
		return true;
	}

	public List findall(int ZoneID) throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllSoundZoneMapFromZoneID",ZoneID);
	}
	public List findallSoundID(int ZoneID)throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllSoundIDFromZoneID",ZoneID);
	}
	public Object findSoundZoneMap(Object o) throws SQLException{
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getSoundZoneMap",o.toString());
	}

	public boolean save(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.insert("insertSoundZoneMap",o);
		return true;
	}

	public boolean update(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateSoundZoneMap",o);
		return true;
	}

	public void delSoundZoneMapByZoneID(Integer zoneID) throws Exception {
		
		sqlmapclienttemplate.delete("delSoundZoneMapByZoneID", zoneID);
		
	}

	public void updateSoundZoneMap(SoundZoneMap soundZoneMap) throws Exception {
		sqlmapclienttemplate.update("updateSoundZoneMap1", soundZoneMap);
	}
	@Override
	public void deleteSoundZoneMapByZoneIdAndAudioID(SoundZoneMap soundZoneMap){
		
		sqlmapclienttemplate.delete("deleteSoundZoneMapByZoneIdAndAudioID", soundZoneMap);
		
	}

	@Override
	public void delSoundZoneMapByAudioID(String audioID) throws Exception {
		
		sqlmapclienttemplate.delete("delSoundZoneMapByAudioID", audioID);
		
	}
	

}
