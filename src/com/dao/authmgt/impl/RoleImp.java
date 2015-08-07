package com.dao.authmgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.authmgt.IRole;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Role;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

public class RoleImp implements IRole {

	private SqlMapClientTemplate sqlmapclienttemplate;

	public void setSqlmapclienttemplate(
			SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public List getAll() {
		List roles = (List) sqlmapclienttemplate.queryForList("getAllRoles",
				null);
		return roles;
	}

	public void create(Object object) {
		Role role = (Role) object;
		sqlmapclienttemplate.insert("createRole", role);
		return;
	}

//	public void createAccessorialOperations(List roleMap) {
//		List<Map> roleMaps = roleMap;
//		for (Map roleMap1 : roleMaps) {
//			sqlmapclienttemplate.insert("createRoleOperation", roleMap1);
//		}
//	}

	public void delete(String name) {
		sqlmapclienttemplate.delete("deleteRole", name);
		return;
	}

	public Object getOperationsByRoleName(String name) {
		return sqlmapclienttemplate.queryForObject(
				"getOperationsByRoleName", name);
	}
    

	public Object getCountByName(String name) {
		Integer countInteger=(Integer)sqlmapclienttemplate.queryForObject("getRoleCountByName", name);
		return countInteger;
	}

//	public void deleteAccessorialOperations(String name) {
//		sqlmapclienttemplate.delete("deleteRoleOperation", name);
//	}
	
	public void update(Object object) {
		Role role=(Role)object;	
		sqlmapclienttemplate.update("updateRole",role);
		return;
	}
//	public List getRolesByType(int roleType) {
//		List roles = (List) sqlmapclienttemplate.queryForList(
//				"getRoleNamesByType", roleType);
//		return roles;
//	}

	public Role queryRoleByName(String name) {
		return (Role) sqlmapclienttemplate.queryForObject("queryRoleByName", name);
	}

}
