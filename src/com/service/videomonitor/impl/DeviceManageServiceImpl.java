package com.service.videomonitor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.authmgt.IOrganization;
import com.dao.videomonitor.TVmManufacturerMapper;
import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.videomonitor.TVmManufacturer;
import com.service.videomonitor.DeviceManageService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class DeviceManageServiceImpl implements DeviceManageService{
	
	private TVmManufacturerMapper manufactureDao;
	
	public TVmManufacturerMapper getManufactureDao() {
		return manufactureDao;
	}

	public void setManufactureDao(TVmManufacturerMapper manufactureDao) {
		this.manufactureDao = manufactureDao;
	}
	
	private IOrganization orgDao;

	public IOrganization getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(IOrganization orgDao) {
		this.orgDao = orgDao;
	}

	@Override
	public List<TVmManufacturer> getManufactures() {
		// TODO Auto-generated method stub
		return manufactureDao.selectAll();
	}

	@Override
	public List<Map<String, String>> getBranches() {
		List<Map<String,String>> bhs = new ArrayList<Map<String,String>>();
		Session session = AlarmUtil.getLoginSession();
		List<CommonBean> subCompanys = session.getOrgIdAndNames().getSubCompanys();
		for(CommonBean cb:subCompanys){
			Map<String,String> branch = new HashMap<String,String>();
			branch.put("id", cb.getId());
			branch.put("name", cb.getName());
			bhs.add(branch);
		}
		return bhs;
	}

	@Override
	public Map<String,List<Map<String, String>>> getManagementagencies() {
		Map<String,List<Map<String, String>>> res = new HashMap<String,List<Map<String, String>>>();
		Session session = AlarmUtil.getLoginSession();
		String lev = session.getLev();
		List<CommonBean> subCompanys = session.getOrgIdAndNames().getSubCompanys();
		List<CommonBean> managements = session.getOrgIdAndNames().getManagements();
		if(lev.equals(AlarmConstants.USER_DEPARTMENT)){
			CommonBean scb = subCompanys.get(0);
			CommonBean mcb = managements.get(0);
			Map<String,String> management = new HashMap<String,String>();
			management.put("id",mcb.getId());
			management.put("name",mcb.getName());
			List<Map<String, String>> allManagements = new ArrayList<Map<String,String>>();
			allManagements.add(management);
			res.put(scb.getId(), allManagements);
		}else{
			for(CommonBean cb:subCompanys){
				List mids = orgDao.getOrgIdsByPOrgId(cb.getId());
				List<Map<String,String>> allManagements = new ArrayList<Map<String,String>>();
				for(Object s:mids){
					Map<String,String> management = new HashMap<String,String>();
					management.put("id",s.toString());
					management.put("name", null);
					allManagements.add(management);
				}
				res.put(cb.getId(), allManagements);
			}

			for(CommonBean cb:managements){
				String mid = cb.getId();
				String mname = cb.getName();
				for(Entry<String,List<Map<String, String>>> entry:res.entrySet()){
					for(Map<String, String> m:entry.getValue()){
						if(m.get("id").equals(mid)){
							m.put("name", mname);
							break;
						}
					}
				}
			}
		}
		
		return res;
	}

}
