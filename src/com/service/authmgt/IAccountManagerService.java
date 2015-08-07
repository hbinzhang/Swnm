package com.service.authmgt;

import java.util.List;
import java.util.Map;

import com.entity.authmgt.OrgLevAndIdNames;
import com.entity.authmgt.Role;
import com.entity.authmgt.Session;

public interface IAccountManagerService {	

	public OrgLevAndIdNames getOrgLevAndIdNamesByAccountId(String accountId);
	
	public List getAllowedRolesByAccountId();
	
	public List getAllowedAccountIdsByAccountId(String accountId);
	
	public Object queryAccountByAccountId(String accountId);
	
	public List queryAccountsByAccountId(String accountId);
	
	public int createAccount(Object object);
	
	public int updateAccount(Object object);
	
	public int deleteAccount(String name);
	
	public int updateAccountPassword(String name,String oldPassword,String password);
	
	public int resetAccountPassword(String accountId);
	
	public List getAccountIdAndUserNamesByOrgId (String orgId);	
	
	public List getAccountsByLev(String lev);
}
