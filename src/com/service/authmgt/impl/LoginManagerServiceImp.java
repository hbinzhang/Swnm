package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.authmgt.IAccount;
import com.dao.authmgt.ILogin;
import com.dao.authmgt.IRole;
import com.dao.authmgt.impl.LoginImp;
import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.Login;
import com.entity.authmgt.Operation;
import com.entity.authmgt.OrgIdAndNames;
import com.entity.authmgt.Session;
import com.service.authmgt.ILoginManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.common.Cipher;
import com.service.authmgt.common.SecurityUtil;

public class LoginManagerServiceImp implements ILoginManagerService {

	private ILogin loginDao = null;

	private IAccount accountDao = null;

	private IRole roleDao = null;

	private IOrganizationManagerService organizationManagerService = null;

	private Log log = LogFactory.getLog(this.getClass());

	private static ILoginManagerService loginManagerServiceInstace = null;

	public Object checkAndCreate(Object object) {

		Session session = null;

		Login login = (Login) object;
		Map map = new HashMap();
		map.put("id", login.getId());
		map.put("password", login.getPassword());
		Integer count = (Integer) accountDao.getCountByIdAndPassword(map);
		if (count.intValue() > 0) {
			session = createSession(login);
		}
		return session;

	}

	//
	private Session createSession(Login login) {
		Session session = null;
		Account account = (Account) accountDao.getById(login.getId());
		if (account != null) {
			session = new Session();			
			session.setId(login.getId());
			session.setLoginHostIp(login.getLoginHostIp());
			session.setLoginHostName(login.getLoginHostName());
			session.setLoginTime(new Date(System.currentTimeMillis()));
			session.setOrganizationId(account.getOrgId());
			session.setType(account.getType());
			session.setUserIdForUI(account.getUserId());
			session.setUserNameForUI(account.getUserName());
			session.setOrgNmForUI((String)organizationManagerService.getOrgNmByOrgId(account.getOrgId()));
			long id = System.currentTimeMillis();
			session.setContextId(id);
			log.info("createSession-session--before: " + session);
			loginDao.deleteSessionByAccountId(login.getId());
			loginDao.create(session);
			log.info("createSession-session--after: " + session);
		}
		return session;
	}

	// result:2--不存在；0：成功；-1：失败
	public int deleteSession(long contextId) {
		int result = -1;
		Integer count = (Integer) loginDao.getCountByContextId(contextId);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			loginDao.delete(contextId);
			log.info("deleteSession-contextId: " + contextId);
			result = 0;
		}
		return result;
	}

	public List getAuthorizedOpIds(Session session) {
		String[] roles = getAuthorizedRoles(session.getId());
		List<String> authorizedOpIds = null;
		if (roles != null) {
			authorizedOpIds = new ArrayList();
			// 普通用户
			if (session.getType() == 1) {
				for (int i = 0; i < roles.length; i++) {
					String[] operations = getOperationsByRoleName(roles[i]);
					if (operations != null && operations.length != 0) {
						for (int j = 0; j < operations.length; j++) {
							if (!authorizedOpIds.contains(operations[j])) {
								authorizedOpIds.add(operations[j]);
							}
						}
					}
				}
				if(authorizedOpIds.size()==0){
					authorizedOpIds=null;
				}
			}
		} else {
			// 内置管理员
			if (session.getType() == 0) {
				authorizedOpIds = new ArrayList();
			}
		}
		return authorizedOpIds;
	}

	public List getAuthorizedOpIdsForUI(List<String> authorizedOpIds) {
		List<String> authorizedOpIdsForUI = null;
		if (authorizedOpIds != null) {
			authorizedOpIdsForUI = new ArrayList();
			// 内置管理员
			if (authorizedOpIds.size()==0) {
				List<Operation> operations = AuthorizationManager
						.getOperations();
				for (Operation operation : operations) {
					authorizedOpIdsForUI.add(operation.getOpDisplayName());
				}
			}
			// 普通用户
			else {
				authorizedOpIdsForUI = OperationAuthorizationImp
						.getOperationDisplayNames(authorizedOpIds);
			}
		}
		return authorizedOpIdsForUI;
	}

	private String[] getOperationsByRoleName(String roleName) {
		String[] operations = null;
		List<String> operationsList = new ArrayList();
		String operationsStr = (String) roleDao
				.getOperationsByRoleName(roleName);
		if (operationsStr != null)
			operations = operationsStr.split(",");
		return operations;
	}

	private String[] getAuthorizedRoles(String accountId) {
		String roles = (String) accountDao.getRolesByAccountId(accountId);
		if (roles != null && roles.length() > 0) {
			String[] rolesCol = roles.split(",");
			return rolesCol;
		}
		return null;
	}

	public ILogin getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(ILogin loginDao) {
		this.loginDao = loginDao;
	}

	public IAccount getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccount accountDao) {
		this.accountDao = accountDao;
	}

	public IRole getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRole roleDao) {
		this.roleDao = roleDao;
	}

	public Object getOrgIdAndNamesByAccountId(String accountId) {
		OrgIdAndNames orgIdAndNames = new OrgIdAndNames();
		String lev = (String) organizationManagerService
				.getLevByAccountId(accountId);
		if (lev != null) {
			switch (Integer.parseInt(lev)) {
			case 0:// 若用户属总公司，companys中放所在总公司、subCompanys中放所有分公司、managements中放所有管理处；
				CommonBean company = (CommonBean) organizationManagerService
						.getOrgIdAndOrgNmByAccountId(accountId);
				orgIdAndNames.setCompany(company);
				orgIdAndNames.setSubCompanys(organizationManagerService
						.getOrgIdAndOrgNmsByLev("1"));
				orgIdAndNames.setManagements(organizationManagerService
						.getOrgIdAndOrgNmsByLev("2"));
				break;
			case 1:
				// 用户属分公司，subCompanys中放这个分公司，managements中放其下所有管理处；
				List<CommonBean> subCompanys11 = new ArrayList();
				CommonBean commonBean11 = (CommonBean) organizationManagerService
						.getOrgIdAndOrgNmByAccountId(accountId);
				subCompanys11.add(commonBean11);
				orgIdAndNames.setSubCompanys(subCompanys11);
				orgIdAndNames.setManagements(organizationManagerService
						.getOrgIdAndOrgNmsFor2ByPOrgId(commonBean11.getId()));
				break;
			default:
				// 若用户属管理处，subCompanys中存该管理处所属的分公司，managements中放这个管理处
				List<CommonBean> subCompanys21 = new ArrayList();
				String pOrgId = (String) organizationManagerService
						.getPOrgIdByAccountId(accountId);
				String pOrgNm = (String) organizationManagerService
						.getOrgNmByOrgId(pOrgId);
				CommonBean commonBean21 = new CommonBean();
				commonBean21.setId(pOrgId);
				commonBean21.setName(pOrgNm);
				subCompanys21.add(commonBean21);
				List<CommonBean> managements22 = new ArrayList();
				CommonBean commonBean22 = (CommonBean) organizationManagerService
						.getOrgIdAndOrgNmByAccountId(accountId);
				managements22.add(commonBean22);
				orgIdAndNames.setSubCompanys(subCompanys21);
				orgIdAndNames.setManagements(managements22);
				break;
			}
		}
		return orgIdAndNames;
	}

	public String getLevByAccountId(String accountId) {
		String lev = (String) organizationManagerService
				.getLevByAccountId(accountId);
		return lev;
	}

	public IOrganizationManagerService getOrganizationManagerService() {
		return organizationManagerService;
	}

	public void setOrganizationManagerService(
			IOrganizationManagerService organizationManagerService) {
		this.organizationManagerService = organizationManagerService;
	}

}
