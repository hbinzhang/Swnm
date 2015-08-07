package com.service.zone.impl;

import java.util.List;

import com.dao.linkagectl.ISoundZoneMapDao;
import com.service.zone.ISoundZoneMapService;

public class SoundZoneMapServiceImpl implements ISoundZoneMapService {
	
	private ISoundZoneMapDao soundZoneMapDao;

	public List findall(int ZoneID) {
		return null;
	}

	public List findallSoundID(int ZoneID) {
		return null;
	}

	public Object findSoundZoneMap(Object o) {
		return null;
	}

	public void save(Object o) throws Exception {

	}

	public void update(Object o) throws Exception {

	}

	public void delete(Object o) throws Exception {

	}

	public ISoundZoneMapDao getSoundZoneMapDao() {
		return soundZoneMapDao;
	}

	public void setSoundZoneMapDao(ISoundZoneMapDao soundZoneMapDao) {
		this.soundZoneMapDao = soundZoneMapDao;
	}

}
