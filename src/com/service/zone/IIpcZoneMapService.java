package com.service.zone;

import java.util.List;

public interface IIpcZoneMapService {
	
	public List findall(int ZoneID);
	
	public Object findIpcZoneMap(Object o);
	
	public void save(Object o)throws Exception;
	
	public void update(Object o)throws Exception;
	
	public void delete(Object o)throws Exception;

	public List<Integer> getPointByIpcId(String ipcID)throws Exception;

}
