package com.service.authmgt;

import java.util.List;

import com.entity.authmgt.Session;

public interface ILoginManagerService {
	
	public Object checkAndCreate(Object object);
	
	public int deleteSession(long contextId);
	
	public List getAuthorizedOpIds(Session session);
	
	public List getAuthorizedOpIdsForUI(List<String> authorizedOpIds);
	
	public Object getOrgIdAndNamesByAccountId(String accountId);
	
	public String getLevByAccountId(String accountId);
}
