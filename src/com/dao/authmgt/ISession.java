package com.dao.authmgt;

import java.util.List;

public interface ISession {	
	
	public List querySessionsByAccountId(String id);
	
	public List querySessionsByOrganizationId(String orgId);
	
	public Object getCountByContextId(long contextId);	
	
	public void delete(long contextId);

}
