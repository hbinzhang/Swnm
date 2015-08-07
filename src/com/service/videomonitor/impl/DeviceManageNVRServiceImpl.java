package com.service.videomonitor.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.videomonitor.TVmIpcMapper;
import com.dao.videomonitor.TVmNvrMapper;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmNvr;
import com.entity.videomonitor.TVmNvrExample;
import com.service.videomonitor.DeviceManageNVRService;

public class DeviceManageNVRServiceImpl extends DeviceManageServiceImpl implements DeviceManageNVRService {

	private TVmNvrMapper nvrDao;

	public TVmNvrMapper getNvrDao() {
		return nvrDao;
	}

	public void setNvrDao(TVmNvrMapper nvrDao) {
		this.nvrDao = nvrDao;
	}
	
	private TVmIpcMapper ipcDao;	

	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}

	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}

	public int addNVR(TVmNvr nvr,List<String> ipcchecked,List<String> ipcnochecked) throws Exception {
		// TODO Auto-generated method stub
		//nvr.setNvrid(UUID.randomUUID().toString());
		int res = nvrDao.insertSelective(nvr);
		if(res == 1){
			updateIpcByNvrId(nvr.getNvrid(), ipcchecked,ipcnochecked);
		}
		return res;
	}
	
	private void updateIpcByNvrId(String nvrId,List<String> ipcchecked,List<String> ipcnochecked) throws Exception{
		try {
			String sipcs = "'";
			for(String ipc:ipcchecked){
				if(ipc == null || ipc.equals("")){
					continue;
				}
				sipcs += (ipc + "','");
			}
			if(sipcs.length() > 2){
				sipcs = sipcs.substring(0,sipcs.length() - 2);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("nvrid", nvrId);
				params.put("ipcids", sipcs);
				Map<String,Object> cond = new HashMap<String,Object>();
				cond.put("cond", params);
				ipcDao.updateNvrByIpcIds(cond);
			}
			String sipcn = "'";
			for(String ipc:ipcnochecked){
				if(ipc == null || ipc.equals("")){
					continue;
				}
				sipcn += (ipc + "','");
			}
			if(sipcn.length() > 2){
				sipcn = sipcn.substring(0,sipcn.length() - 2);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("nvrid", null);
				params.put("ipcids", sipcn);
				Map<String,Object> cond = new HashMap<String,Object>();
				cond.put("cond", params);
				ipcDao.updateNvrByIpcIds(cond);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public int deleteNVRs(List<String> nvrids) {
		int res = 0;
		try {
			updateIpcByNvrIds(nvrids);
			for(String id:nvrids){
				res += nvrDao.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private void updateIpcByNvrIds(List<String> nvrids) throws Exception {
		try {
			String snvrs = "'";
			for(String nvr:nvrids){
				if(nvr == null || nvr.equals("")){
					continue;
				}
				snvrs += (nvr + "','");
			}
			if(snvrs.length() > 2){
				snvrs = snvrs.substring(0,snvrs.length() - 2);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("nvrids", snvrs);
				Map<String,Object> cond = new HashMap<String,Object>();
				cond.put("cond", params);
				ipcDao.clearIpcByNvrIds(cond);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public int modifyNVR(TVmNvr nvr,List<String> ipcchecked,List<String> ipcnochecked) throws Exception {
		// TODO Auto-generated method stub
		int res = 0;
		updateIpcByNvrId(nvr.getNvrid(), ipcchecked,ipcnochecked);
		res = nvrDao.updateByPrimaryKeySelective(nvr);
		return res;
	}

	@Override
	public List<TVmNvr> search(TVmNvrExample example) {
		// TODO Auto-generated method stub
		List<TVmNvr> res = nvrDao.selectByExample(example);
		return res;
	}

	@Override
	public TVmNvr searchById(String id) {
		// TODO Auto-generated method stub
		TVmNvr res = null;
		try {
			res = nvrDao.selectByPrimaryKey(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<TVmNvr> searchByPage(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		List<TVmNvr> tns = nvrDao.selectByPage(cond);
		for(TVmNvr tn : tns){
			Map<String,String> state = new HashMap<String,String>();
			state.put("id", "ok");
			state.put("name", "正常");
			tn.setState(state);
		}
		return tns;
	}

	@Override
	public int searchByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return nvrDao.selectByPageRowCount(cond);
	}

	@Override
	public List<TVmNvr> selectNvrByRole(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		return nvrDao.selectNvrByRole(cond);
	}

	@Override
	public int testNvrExistById(String nvrid) {
		// TODO Auto-generated method stub
		return nvrDao.testNvrExistById(nvrid);
	}
}
