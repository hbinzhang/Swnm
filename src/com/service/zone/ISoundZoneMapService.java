package com.service.zone;

import java.util.List;

public interface ISoundZoneMapService {
	
	public List findall(int ZoneID);
	
	public List findallSoundID(int ZoneID);
	
	public Object findSoundZoneMap(Object o);	
	
	public void save(Object o)throws Exception;
	
	public void update(Object o)throws Exception;
	
	public void delete(Object o)throws Exception;

}
