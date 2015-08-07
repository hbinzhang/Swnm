package com.dao.linkagectl;

import java.sql.SQLException;
import java.util.List;

import com.entity.linkagectl.SoundZoneMap;

public interface ISoundZoneMapDao {
	
	public List findall(int ZoneID)throws SQLException;
	
	public List findallSoundID(int ZoneID)throws SQLException;
	
	public Object findSoundZoneMap(Object o)throws SQLException;	
	
	public boolean save(Object o)throws SQLException;
	
	public boolean update(Object o)throws SQLException;
	
	public boolean delete(Object o)throws SQLException;
	
	public void delSoundZoneMapByZoneID(Integer zoneID)throws Exception;

	public void updateSoundZoneMap(SoundZoneMap soundZoneMap)throws Exception;
	void deleteSoundZoneMapByZoneIdAndAudioID(SoundZoneMap soundZoneMap)throws Exception;
	public void delSoundZoneMapByAudioID(String audioID)throws Exception;
}
