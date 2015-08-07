package com.service.authmgt;

import java.util.List;

import com.entity.authmgt.OrgLevAndIdNames;

public interface ISessionManagerService {
	
//	public List getLevsByAccountId(String accountId);
//	
//	public List getOrgIdAndOrgNmsByLev(String lev);
	public OrgLevAndIdNames getOrgLevAndIdNamesByAccountId(String accountId);
	
	public List querySessionsByOrganizationId(String orgId);
	
	public List querySessionsByAccountId(String loginId,String id);
	
	public int deleteSession(long contextId);
	
//	public Object getOrgNmByAccountId(String id);
	
	//得到同级及下所有级别的用户工号	
//	public List getAllowedAccountIdsByAccountId(String accountId);

}
