package com.service.authmgt;

import java.util.List;

import com.entity.authmgt.Organization;

public interface IOrganizationManagerService {	
	
	public List queryOrganizationsByAccountId(String accountId);
	
	public int createOrganization(Object object);
	
	public int updateOrganization(Object object);
	
//	public int deleteOrganization(String id);
	
	public List getOrgIdAndOrgNmsByLev(String lev);
	
	public Object getOrgNmByOrgId(String orgId);
	
	public List getOrgIdAndOrgNmsFor2ByPOrgId(String pOrgId);
	
	public Object getOrgIdAndOrgNmByAccountId (String accountId);
	
	public Object getLevByAccountId (String accountId);
	
	public Object getPOrgIdByAccountId(String accountId);
	
	public boolean isParentCompanyByAccountId (String accountId);
	
	public boolean isCompanyByAccountId (String accountId);
	
	public boolean isManagementByAccountId (String accountId);
	
	public Object getOrganizationByOrgID(String orgId);
	
	public List getOrganizationsByLev(String lev);
	
	public List getOrganizationsFor1ByPOrgId(String pOrgId);
	
	public List getOrganizationsFor2ByPOrgId(String pOrgId);
	
	public Object getLevByOrgId (String orgId);
}
