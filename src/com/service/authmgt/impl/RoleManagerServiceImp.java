package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.dao.authmgt.IRole;
import com.entity.authmgt.Role;
import com.entity.authmgt.Role;
import com.service.authmgt.impl.AuthorizationManager;
import com.service.authmgt.IRoleManagerService;

public class RoleManagerServiceImp implements IRoleManagerService {

	private IRole roleDao = null;

	public IRole getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRole roleDao) {
		this.roleDao = roleDao;
	}

	public List getAllRoles() {
		List roles = roleDao.getAll();
		Role role = null;
		if (roles != null) {
			for (int i = 0; i < roles.size(); i++) {
				role = (Role) roles.get(i);
				// getAccessorialOperations(role);
			}
		}
		return roles;
	}

	// private void getAccessorialOperations(Role role) {
	// if (role != null) {
	// String name = role.getName();
	// List<String> authorizedOpIds = (List)
	// roleDao.getOperationsByRoleName(name);
	// if (authorizedOpIds != null) {
	// role.setAuthorizedOpIds(authorizedOpIds);
	// }
	// }
	// }

	// result:1--已存在；0：成功
	public int createRole(Object object) {
		int result = -1;
		Role role = (Role) object;
		Integer count = (Integer) roleDao.getCountByName(role.getName());
		if (count.intValue() > 0) {
			result = 1;
		} else {
			roleDao.create(role);
			// createAccessorialOperations(role);
			result = 0;
		}
		return result;
	}

	// private void createAccessorialOperations(Role role) {
	// List<Map> roleMap = (List<Map>) new ArrayList<Map>();
	// for (int i = 0; i < role.getAuthorizedOpIds().size(); i++) {
	// Map map = new HashMap();
	// map.put("roleName", role.getName());
	// map.put("operationName", (String) role.getAuthorizedOpIds().get(
	// i));
	// roleMap.add(map);
	// }
	// roleDao.createAccessorialOperations(roleMap);
	// }

	// result:2--不存在；0：成功
	public int updateRole(Object object) {
		int result = -1;
		Role role = (Role) object;
		Integer count = (Integer) roleDao.getCountByName(role.getName());
		if (count.intValue() == 0) {
			result = 2;
		} else {
			roleDao.update(object);
			result = 0;
		}
		return result;
	}

	// result:2--不存在；0：成功
	public int deleteRole(String name) {
		int result = -1;
		Integer count = (Integer) roleDao.getCountByName(name);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			roleDao.delete(name);
			// roleDao.deleteAccessorialOperations(name);
			result = 0;
		}
		return result;
	}

	// public List getRoleNamesByType(int roleType) {
	// List<Role> roles=roleDao.getRolesByType(roleType);
	// List<String> namesOfRole =new ArrayList<String>(roles.size());
	// for (Role role:roles){
	// namesOfRole.add(role.getName());
	// }
	// // TODO Auto-generated method stub
	// return roles;
	// }

	public String getOperationNames(List<String> displayNames) {
		String operationNamesStr = null;
		if (displayNames != null) {
			List<String> opNames = new ArrayList();
			Properties displayAndNames = AuthorizationManager
					.getOperationDisplayAndName();
			Set<Object> displays = displayAndNames.keySet();
			Iterator<Object> displayIterator = displays.iterator();
			String display = null;
			String name = null;
			while (displayIterator.hasNext()) {
				display = (String) displayIterator.next();
				name = (String) displayAndNames.get(display);
				for (int i = 0; i < displayNames.size(); i++) {
					if (display.compareToIgnoreCase(displayNames.get(i)) == 0)
						opNames.add(name);
				}
			}
			operationNamesStr = transListToString(opNames);
		}
		return operationNamesStr;
	}

	private String transListToString(List<String> opNames) {
		StringBuffer authorizedOpIdsTmp = new StringBuffer();
		for (int i = 0; i < opNames.size(); i++) {
			authorizedOpIdsTmp.append(opNames.get(i));
			if (i < (opNames.size() - 1)) {
				authorizedOpIdsTmp.append(",");
			}
		}
		return authorizedOpIdsTmp.toString();
	}

	public Role getRoleByName(String name) {
		Role role = roleDao.queryRoleByName(name);
		return role;
	}

}
