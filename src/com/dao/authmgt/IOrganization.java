package com.dao.authmgt;

import java.util.List;
import java.util.Map;

public interface IOrganization {	
	
	public List getOrganizationsByLev(String lev);
	
	public List getOrganizationsFor1ByPOrgId(String porgId);
	
	public List getOrganizationsFor2ByPOrgId(String porgId);
	
	public Object getCountByOrgId(String orgId);
	
	public Object getCountByOrgIdAndOrgNm(Map map);
	
	public void create(Object object);
	
	public void update(Object object);
	
	public void delete(String name);	
	
	public List getOrgIdAndOrgNmsByLev(String lev);
	
	public Object getOrgNmByOrgId(String orgId);
	
	public Object getOrgIdByOrgNm(Map map);
	
	public List getOrgIdAndOrgNmsFor1ByPOrgId(String porgId);
	
	public List getOrgIdAndOrgNmsFor2ByPOrgId(String porgId);
	
	public List getOrgIdsByPOrgId(String porgId);
	
	public Object getLevByOrgId (String orgId);
	
	public Object getPOrgIdByOrgId (String orgId);
	
	public Object getOrganizationByOrgID (String orgId);
	
	public List getOrgIdAndOrgNmsForSubCompany ();
	
	public List getOrgIdAndOrgNmsForManagement ();

}
