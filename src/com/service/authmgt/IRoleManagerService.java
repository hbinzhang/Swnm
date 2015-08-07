package com.service.authmgt;

import java.util.List;

import com.entity.authmgt.Role;

public interface IRoleManagerService {	
	
	public List getAllRoles();
	
	public int createRole(Object object);
	
	public int updateRole(Object object);
	
	public int deleteRole(String name);
	
//	public List getRoleNamesByType(int roleType);
	
	public Object getOperationNames(List<String> displayNames);

	public Role getRoleByName(String name);
}
