package com.dao.authmgt;

import java.util.Map;

public interface ILogin {
	
	public void create(Object object);
	
//	public Object getSessionByContextId();
	
	public Object getCountByContextId(long contextId);
	
	public void delete(long contextId);
	
	public void deleteSessionByAccountId(String accountId);
	
}
