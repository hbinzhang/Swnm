package com.service.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmNvr;
import com.entity.videomonitor.TVmNvrExample;

public interface DeviceManageNVRService extends DeviceManageService {
	public int addNVR(TVmNvr nvr,List<String> ipcchecked,List<String> ipcnochecked) throws Exception;
	public int deleteNVRs(List<String> nvrids);
	public int modifyNVR(TVmNvr nvr,List<String> ipcchecked,List<String> ipcnochecked) throws Exception;
	public List<TVmNvr> search(TVmNvrExample example);
	public TVmNvr searchById(String id);
	public List<TVmNvr> searchByPage(Map<String,Object> cond);
	public int searchByPageRowCount(Map<String,Object> cond);
	public List<TVmNvr> selectNvrByRole(Map<String,Object> cond);
	public int testNvrExistById(String nvrid);
}
