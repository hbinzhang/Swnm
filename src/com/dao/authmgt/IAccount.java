package com.dao.authmgt;
import java.util.List;
import java.util.Map;

public interface IAccount {
	
	public List queryAccountsByAccountId(String accountId);	
	
	public List getAccountsByLev(String lev);
	
	public List getAccountsByAccountIdForOrg(String id);
	
	public List getAccountsByAccountIdForSubOrgs(String id);
	
	public Object getById(String id);	
	
	public Object getRolesByAccountId(String id);	
	
	public Object getCountById(String id);	
	
	public Object getCountByIdAndPassword(Map map);
	
	public void create(Object object);
	
	public void update(Object object);
	
	public void delete(String id);
	
	public void updateAccountPassword(Map map);
	
	public void updateAccountRoles(Map map);
	
	public Object getOrgIdById(String id);	
	
	public List getIdsByPOrgId(String orgId);
	
	public List getIdAndUsernamesByOrgId(String orgId);

}
