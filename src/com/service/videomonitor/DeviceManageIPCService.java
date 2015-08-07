package com.service.videomonitor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIpcDTO;
import com.entity.videomonitor.TVmIpcExample;

public interface DeviceManageIPCService extends DeviceManageService{
	public int addIPC(TVmIpc ipc);
	public String deleteIPCs(List<String> ipcids);
	public int modifyIPC(TVmIpc ipc);
	public List<TVmIpc> search(TVmIpcExample example);
	public TVmIpc searchById(String id);
	public List<TVmIpc> searchByPage(Map<String,Object> cond);
	public int searchByPageRowCount(Map<String,Object> cond);
	public int testIpcExistById(String ipcid);
	public List<TVmIpc> getAllIpcByOrgIdNvrId(Map<String,Object> cond);
	public List<TVmIpcDTO> getAllIpcByIvaID(String ivaID);
}
