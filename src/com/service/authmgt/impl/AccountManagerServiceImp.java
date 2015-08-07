package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.authmgt.IAccount;
import com.dao.authmgt.IOrganization;
import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.OrgLevAndIdNames;
import com.entity.authmgt.Role;
import com.entity.authmgt.Session;
import com.service.authmgt.IAccountManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.IRoleManagerService;
import com.service.authmgt.ISessionManagerService;
import com.service.authmgt.common.Cipher;
import com.service.authmgt.common.SecurityUtil;

public class AccountManagerServiceImp implements IAccountManagerService {
	private IOrganization organizationDao = null;
	private IAccount accountDao = null;
	private ISessionManagerService sessionManagerService = null;
	private IOrganizationManagerService organizationManagerService = null;
	private IRoleManagerService roleManagerService = null;

	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 获取账号信息。如果是管理处的那只能看到这个管理处的，
	 * 如果是分公司的可以看到这个分公司及这个分公司下属的管理处，
	 * 如果是总公司可以看到全部。
	 * @param accountId 工号
	 * @return List
	 */
	public List queryAccountsByAccountId(String accountId) {
		List<Account> accounts = new ArrayList();
		List<Account> accountsForUI = new ArrayList();
		accounts = getAccountsByAccountId(accountId);
		accountsForUI = getAccountsForUI(accounts);

		return accountsForUI;
	}
	
	/**
	 * 查询账号。如果是管理处的那只能看到这个管理处的，
	 * 如果是分公司的可以看到这个分公司及这个分公司下属的管理处，
	 * 如果是总公司可以看到全部。
	 * @param accountId 工号
	 * @return List
	 */
	private List getAccountsByAccountId(String accountId) {
		List<Account> accounts = new ArrayList();

		String lev = (String) organizationManagerService
				.getLevByAccountId(accountId);
		List<String> allowedAccountIds = new ArrayList();
		if (lev != null) {
			switch (Integer.parseInt(lev)) {
			case 0:
				// 总公司所有用户
				accounts = accountDao.getAccountsByLev("0");
				// 分公司所有用户
				accounts.addAll(accountDao.getAccountsByLev("1"));
				// 管理处所有用户
				accounts.addAll(accountDao.getAccountsByLev("2"));
				break;
			case 1:
				// 这个分公司的用户
				accounts.addAll(accountDao.getAccountsByAccountIdForOrg(accountId));
				// 这个分公司下属管理处的用户
				accounts.addAll(accountDao.getAccountsByAccountIdForSubOrgs(accountId));
				break;
			default:
				// 这个管理处的用户
				accounts.addAll(accountDao.getAccountsByAccountIdForOrg(accountId));
				break;
			}
		}

		return accounts;
	}


	@Override
	public List getAccountsByLev(String lev) {
		return accountDao.getAccountsByLev(lev);
	}
	
	/**
	 * 构造ui需要的账号信息。
	 * @param List<Account> accounts 账号集
	 * @return List
	 */
	private List getAccountsForUI(List<Account> accounts) {
		List<Account> accountsForUI = new ArrayList();
		for (Account account : accounts) {
			account.setOrgNmForUI((String) organizationManagerService
					.getOrgNmByOrgId(account.getOrgId()));
			accountsForUI.add(account);
		}

		return accountsForUI;
	}
	
	/**
	 * 获取工号信息。如果是管理处的那只能看到这个管理处的，
	 * 如果是分公司的可以看到这个分公司及这个分公司下属的管理处，
	 * 如果是总公司可以看到全部。
	 * @param String 工号
	 * @return List
	 */
	public List getAllowedAccountIdsByAccountId(String accountId) {
		List<String> allowedAccountIds = new ArrayList();
		// 得到同级及下所有级别的用户
		List<Account> accounts = new ArrayList();
		accounts = getAccountsByAccountId(accountId);
		for (Account account : accounts) {
			allowedAccountIds.add(account.getId());
		}

		return allowedAccountIds;
	}
	
	/**
	 * 获取全部角色名。
	 * @return List
	 */
	public List getAllowedRolesByAccountId() {
		List<String> allowedRoles = new ArrayList();
		List<Role> roles = roleManagerService.getAllRoles();
		for (Role role : roles) {
			allowedRoles.add(role.getName());
		}
		
		return allowedRoles;
	}
	
	/**
	 * 获取当前组织ID下所有员工的工号和姓名。
	 * @param String 当前组织ID
	 * @return List
	 */
	public List getAccountIdAndUserNamesByOrgId(String orgId) {
		return accountDao.getIdAndUsernamesByOrgId(orgId);
	}
	
	/**
	 * 获取工号的账号信息。
	 * @param String 工号
	 * @return Object
	 */
	public Object queryAccountByAccountId(String accountId) {
		Account account = (Account) accountDao.getById(accountId);
		if (account != null) {
			account.setOrgNmForUI((String) organizationManagerService
					.getOrgNmByOrgId(account.getOrgId()));
		}
		return account;
	}
	
	/**
	 * 获取机构信息。
	 * 若用户属总公司，companys中放所在总公司、subCompanys中放所有分公司、managements中放所有管理处；
	 * 若用户属分公司，subCompanys中放所在分公司；managements中放所在分公司下的管理处，
	 * 若用户属管理处，subCompanys为空;managements中放所在管理处。
	 * @param accountId 工号
	 * @return OrgLevAndIdNames
	 */
	public OrgLevAndIdNames getOrgLevAndIdNamesByAccountId(String accountId) {
		return sessionManagerService.getOrgLevAndIdNamesByAccountId(accountId);
	}

	/**
	 * 创建账号。
	 * @param object 账号
	 * @return int 1--已存在；0：成功
	 */
	public int createAccount(Object object) {
		int result = -1;
		Account account = (Account) object;
		Integer count = (Integer) accountDao.getCountById(account.getId());
		if (count.intValue() > 0) {
			result = 1;
		} else {
			accountDao.create(account);
			result = 0;
		}
		return result;
	}
	
	/**
	 * 修改账号。
	 * @param Object 账号
	 * @return int 2--不存在；0：成功
	 */
	public int updateAccount(Object object) {
		int result = -1;
		Account account = (Account) object;
		Integer count = (Integer) accountDao.getCountById(account.getId());
		if (count.intValue() == 0) {
			result = 2;
		} else {
			accountDao.update(account);
			result = 0;
		}
		return result;
	}
	
	/**
	 * 修改密码。
	 * @param String id 工号
	 * @param String oldPassword 旧密码	
	 * @param String password 新密码
	 * @return int 2--不存在；0：成功
	 */
	public int updateAccountPassword(String id, String oldPassword,
			String password) {
		int result = -1;

		Map mapOld = new HashMap();
		mapOld.put("id", id);
		mapOld.put("password", oldPassword);
		Integer count = (Integer) accountDao.getCountByIdAndPassword(mapOld);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			Map map = new HashMap();
			map.put("id", id);
			map.put("password", password);
			accountDao.updateAccountPassword(map);
			result = 0;
		}
		return result;
	}
	
	/**
	 * 重置密码为工号。
	 * @param String accountId 工号
	 * @return int 2--不存在；0：成功
	 */
	public int resetAccountPassword(String accountId) {

		int result = -1;
		
		Integer count = (Integer) accountDao.getCountById(accountId);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			Map map = new HashMap();
			map.put("id", accountId);
			map.put("password", accountId);
			accountDao.updateAccountPassword(map);
			result = 0;
		}

		return result;
	}
	
	/**
	 * 删除账号。
	 * @param String 工号
	 * @return int 2--不存在；0：成功
	 */
	public int deleteAccount(String id) {
		int result = -1;
		Integer count = (Integer) accountDao.getCountById(id);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			accountDao.delete(id);
			result = 0;
		}
		return result;
	}
	
	public ISessionManagerService getSessionManagerService() {
		return sessionManagerService;
	}

	public void setSessionManagerService(
			ISessionManagerService sessionManagerService) {
		this.sessionManagerService = sessionManagerService;
	}
	
	public IOrganizationManagerService getOrganizationManagerService() {
		return organizationManagerService;
	}

	public void setOrganizationManagerService(
			IOrganizationManagerService organizationManagerService) {
		this.organizationManagerService = organizationManagerService;
	}

	public IRoleManagerService getRoleManagerService() {
		return roleManagerService;
	}

	public void setRoleManagerService(IRoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}

	public IAccount getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccount accountDao) {
		this.accountDao = accountDao;
	}

	public IOrganization getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(IOrganization organizationDao) {
		this.organizationDao = organizationDao;
	}

}
