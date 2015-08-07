package com.service.videomonitor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.dao.linkagectl.IIpcZoneMapDao;
import com.dao.videomonitor.TVmIpcMapper;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIpcDTO;
import com.entity.videomonitor.TVmIpcExample;
import com.service.videomonitor.DeviceAdaptor;
import com.service.videomonitor.DeviceManageIPCService;

public class DeviceManageIPCServiceImpl extends DeviceManageServiceImpl implements DeviceManageIPCService {
	
	private TVmIpcMapper ipcDao;

	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}

	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}

	public int addIPC(TVmIpc ipc) {
		// TODO Auto-generated method stub
		int res = 0;
		try {
			//ipc.setIpcid(UUID.randomUUID().toString());
			res = ipcDao.insertSelective(ipc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return res;
	}

	public String deleteIPCs(List<String> ipcids) {
		// TODO Auto-generated method stub
		String res = "";
		for(String id:ipcids){
			if(ipcDao.checkIpcUsedByAudio(id)==1){
				res += id + ",";
				continue;
			}
			ipcDao.deleteByPrimaryKey(id);
		}
		if(res != ""){
			res = res.substring(0,res.length() - 1);
		}
		return res;
	}

	@Override
	public int modifyIPC(TVmIpc ipc) {
		// TODO Auto-generated method stub
		int res = ipcDao.updateByPrimaryKeySelective(ipc);
		return res;
	}

	@Override
	public List<TVmIpc> search(TVmIpcExample example) {
		// TODO Auto-generated method stub
		List<TVmIpc> res = ipcDao.selectByExample(example);
		return res;
	}

	@Override
	public TVmIpc searchById(String ipcid) {
		// TODO Auto-generated method stub
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
		return ipc;
	}

	@Override
	public List<TVmIpc> searchByPage(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		return ipcDao.selectByPage(cond);
	}

	@Override
	public int searchByPageRowCount(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		return ipcDao.selectByPageRowCount(cond);
	}

	@Override
	public int testIpcExistById(String ipcid) {
		// TODO Auto-generated method stub
		return ipcDao.testIpcExistById(ipcid);
	}

	@Override
	public List<TVmIpc> getAllIpcByOrgIdNvrId(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return ipcDao.getAllIpcByOrgIdNvrId(cond);
	}

	@Override
	public List<TVmIpcDTO> getAllIpcByIvaID(String ivaID) {
		return ipcDao.getAllIpcByIvaID(ivaID);
	}
	
}
