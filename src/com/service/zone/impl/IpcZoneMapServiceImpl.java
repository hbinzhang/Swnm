package com.service.zone.impl;

import java.util.List;

import com.dao.linkagectl.IIpcZoneMapDao;
import com.service.zone.IIpcZoneMapService;

public class IpcZoneMapServiceImpl implements IIpcZoneMapService {
	
	private IIpcZoneMapDao ipcZoneMapDao;

	public List findall(int ZoneID) {
		return null;
	}

	public Object findIpcZoneMap(Object o) {
		return null;
	}

	public void save(Object o) throws Exception {

	}

	public void update(Object o) throws Exception {

	}

	public void delete(Object o) throws Exception {

	}

	@Override
	public List<Integer> getPointByIpcId(String ipcID) throws Exception {
		return ipcZoneMapDao.getPointByIpcId(ipcID);
	}

	public IIpcZoneMapDao getIpcZoneMapDao() {
		return ipcZoneMapDao;
	}

	public void setIpcZoneMapDao(IIpcZoneMapDao ipcZoneMapDao) {
		this.ipcZoneMapDao = ipcZoneMapDao;
	}

}
