package com.service.videomonitor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.videomonitor.TVmIpcMapper;
import com.entity.CommonBean;
import com.entity.videomonitor.TVmIpc;
import com.service.authmgt.IOrganizationManagerService;
import com.service.videomonitor.IGetCamerasByUserID;

public class GetCamerasByUserIDImpl implements IGetCamerasByUserID {
    
	private TVmIpcMapper ipcDao;
	private IOrganizationManagerService userOrg;

	
	public IOrganizationManagerService getUserOrg() {
		return userOrg;
	}
	public void setUserOrg(IOrganizationManagerService userOrg) {
		this.userOrg = userOrg;
	}
	
	public IOrganizationManagerService getUsrOrg() {
		return userOrg;
	}
	public void setUsrOrg(IOrganizationManagerService userOrg) {
		this.userOrg = userOrg;
	}
	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}
	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}
	
	private List<TVmIpc> getIPCs(String lev, CommonBean orgID) {
		// TODO Auto-generated method stub
		Map<String, String> org = new HashMap<String, String>();
		if (lev.equals("2")) {
		    org.put("managementAgency", orgID.getId());
		    org.put("branch", null);
		} else if (lev.equals("1")){
			org.put("managementAgency", null);
			org.put("branch", orgID.getId());
		} else if (lev.equals("0")) {
			org.put("managementAgency", null);
		    org.put("branch", null);
		}
		return ipcDao.getIPCsByOrg(org);
	}
	
	@Override
	public List<TVmIpc> getCamerasByUserID(String userID) {
		// TODO Auto-generated method stub
		List<TVmIpc> ipcList = new ArrayList<TVmIpc>();
		String lev = null;
		CommonBean orgID = null;
		
		if (userID == null || userID.isEmpty()) {
	        return ipcList;
		}
		
		lev = (String)userOrg.getLevByAccountId(userID);
		orgID = (CommonBean)userOrg.getOrgIdAndOrgNmByAccountId(userID);
		if (lev != null && orgID.getId() != null 
			&& !lev.isEmpty() && !orgID.getId().isEmpty()) {
			
            ipcList = getIPCs(lev, orgID);			
		} 
		return ipcList;
	}


}
