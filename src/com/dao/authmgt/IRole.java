package com.dao.authmgt;

import java.util.List;

import com.entity.authmgt.Role;

public interface IRole {
		
//	public List getRolesByType(int roleType);
	
	public List getAll();
	
	public Object getOperationsByRoleName(String name);	
	
	public Object getCountByName(String name);
	
	public void create(Object object);
	
	public void update(Object object);
	
//	public void createAccessorialOperations(List roleMap);
//	
//	public void deleteAccessorialOperations(String name);
	
	public void delete(String name);

	public Role queryRoleByName(String name);

}
