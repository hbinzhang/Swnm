package com.dao.authmgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.authmgt.IAccount;
import com.entity.authmgt.Account;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Role;

public class AccountImp implements IAccount {

	private SqlMapClientTemplate sqlmapclienttemplate = null;

	public void setSqlmapclienttemplate(
			SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	@Override
	public List getAccountsByLev(String lev) {
		List accounts = new ArrayList();
		accounts=(List)sqlmapclienttemplate.queryForList(
				"getAccountsByLev", lev);
		return accounts;
	}
	
	public List queryAccountsByAccountId(String accountId) {
		List accounts = (List) sqlmapclienttemplate.queryForList(
				"queryAccountsByAccountId", accountId);
		return accounts;
	}

	public void create(Object object) {
		Account account = (Account) object;
		sqlmapclienttemplate.insert("createAccount", account);
		return;
	}

	public void delete(String id) {
		sqlmapclienttemplate.delete("deleteAccount", id);
		return;
	}

	public Object getRolesByAccountId(String id) {
		return sqlmapclienttemplate.queryForObject("getRolesByAccountId",
				id);
	}

	public Object getCountById(String id) {
		return sqlmapclienttemplate.queryForObject("getAccountCountById",id);
	}

	public void updateAccountPassword(Map map) {
		sqlmapclienttemplate.update("updateAccountPassword",map);
		return;
	}

	public Object getCountByIdAndPassword(Map map) {
		return sqlmapclienttemplate.queryForObject("getAccountCountByIdAndPassword",map);
	}

	public Object getById(String id) {
		return sqlmapclienttemplate.queryForObject("getAccountById",id);
	}

	public Object getOrgIdById(String id) {
		return sqlmapclienttemplate.queryForObject("getOrgIdById",id);
	}

	public List getIdsByPOrgId(String orgId) {
		return (List) sqlmapclienttemplate.queryForList(
				"getIdsByPOrgId", orgId);
	}

	public List getIdAndUsernamesByOrgId(String orgId) {
		return (List) sqlmapclienttemplate.queryForList(
				"getIdAndUsernamesByOrgId", orgId);
	}

	public void update(Object object) {
		Account account=(Account)object;	
		sqlmapclienttemplate.update("updateAccount",account);
		
	}

	public void updateAccountRoles(Map map) {
		sqlmapclienttemplate.update("updateAccountRoles",map);
		
	}

	@Override
	public List getAccountsByAccountIdForOrg(String id) {
		List accounts = new ArrayList();
		accounts=(List)sqlmapclienttemplate.queryForList(
				"getAccountsByAccountIdForOrg", id);
		return accounts;
	}

	@Override
	public List getAccountsByAccountIdForSubOrgs(String id) {
		List accounts = new ArrayList();
		accounts=(List)sqlmapclienttemplate.queryForList(
				"getAccountsByAccountIdForSubOrgs", id);
		return accounts;
	}
}
