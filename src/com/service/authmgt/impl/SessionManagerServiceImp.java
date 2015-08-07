package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.authmgt.IAccount;
import com.dao.authmgt.IOrganization;
import com.dao.authmgt.ISession;
import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.OrgLevAndIdNames;
import com.entity.authmgt.Session;
import com.service.authmgt.IAccountManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.ISessionManagerService;

public class SessionManagerServiceImp implements ISessionManagerService {

	private IOrganizationManagerService organizationManagerService = null;
	private IAccountManagerService accountManagerService = null;
	private ISession sessionDao = null;

	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 获取机构信息。
	 * 若用户属总公司，companys中放所在总公司、subCompanys中放所有分公司、managements中放所有管理处；
	 * 若用户属分公司，subCompanys中放所在分公司；managements中放所在分公司下的管理处，
	 * 若用户属管理处，subCompanys为空;managements中放所在管理处。
	 * @param accountId 工号
	 * @return OrgLevAndIdNames
	 */
	public OrgLevAndIdNames getOrgLevAndIdNamesByAccountId(String accountId) {
		OrgLevAndIdNames orgLevAndIdNames = new OrgLevAndIdNames();
		orgLevAndIdNames.setLevs(getLevsByAccountId(accountId));
		String lev = getLevByAccountId(accountId);
		if (lev != null) {
			switch (Integer.parseInt(lev)) {
			case 0:
				// 若用户属总公司，companys中放所在总公司、subCompanys中放所有分公司、managements中放所有管理处；
				CommonBean company = getOrgIdAndOrgNmByAccountId(accountId);
				orgLevAndIdNames.setCompany(company);
				orgLevAndIdNames.setSubCompanys(getOrgIdAndOrgNmsByLev("1"));
				orgLevAndIdNames.setManagements(getOrgIdAndOrgNmsByLev("2"));
				break;
			case 1:
				// 若用户属分公司，subCompanys中放所在分公司；managements中放所在分公司下的管理处
				List<CommonBean> subCompanys = new ArrayList();
				subCompanys.add(getOrgIdAndOrgNmByAccountId(accountId));
				orgLevAndIdNames.setSubCompanys(subCompanys);
				orgLevAndIdNames
						.setManagements(getOrgIdAndOrgNmsFor2ByPOrgId(getOrgIdAndOrgNmByAccountId(
								accountId).getId()));
				break;
			default:
				// 若用户属管理处，subCompanys为空;managements中放所在管理处
				List<CommonBean> subManagements = new ArrayList();
				subManagements.add(getOrgIdAndOrgNmByAccountId(accountId));
				orgLevAndIdNames.setManagements(subManagements);
				break;
			}
		}
		return orgLevAndIdNames;

	}

	private String getLevByAccountId(String accountId) {
		String lev = (String) organizationManagerService
				.getLevByAccountId(accountId);
		return lev;
	}

	private List getLevsByAccountId(String accountId) {
		List<CommonBean> levs = new ArrayList();
		String lev = (String) organizationManagerService
				.getLevByAccountId(accountId);
		if (lev != null) {
			switch (Integer.parseInt(lev)) {
			case 0:
				levs.add(new CommonBean("0", "总公司"));
				levs.add(new CommonBean("1", "分公司"));
				levs.add(new CommonBean("2", "管理处"));
				break;
			case 1:
				levs.add(new CommonBean("1", "分公司"));
				levs.add(new CommonBean("2", "管理处"));
				break;
			default:
				levs.add(new CommonBean("2", "管理处"));
				break;
			}
		}
		return levs;
	}

	private CommonBean getOrgIdAndOrgNmByAccountId(String accountId) {
		CommonBean commonBean = (CommonBean) organizationManagerService
				.getOrgIdAndOrgNmByAccountId(accountId);
		return commonBean;
	}

	private List getOrgIdAndOrgNmsFor2ByPOrgId(String pOrgId) {
		return organizationManagerService.getOrgIdAndOrgNmsFor2ByPOrgId(pOrgId);
	}

	private List getOrgIdAndOrgNmsByLev(String lev) {
		List<CommonBean> commonBeans = organizationManagerService
				.getOrgIdAndOrgNmsByLev(lev);
		return commonBeans;
	}

	@Override
	public List querySessionsByOrganizationId(String orgId) {
		List<Session> sessionsForUI = new ArrayList();
		// String orgId = (String) organizationManagerService
		// .getOrgIdByOrgNm(orgNm);

		List<Session> sessions = sessionDao
				.querySessionsByOrganizationId(orgId);
		if (sessions != null) {
			for (Session session : sessions) {
				session.setOrgNmForUI((String) organizationManagerService
						.getOrgNmByOrgId(session.getOrganizationId()));
				Account account = (Account) accountManagerService
						.queryAccountByAccountId(session.getId());
				if (account != null) {
					session.setUserIdForUI(account.getUserId());
					session.setUserNameForUI(account.getUserName());
					String lev=(String)organizationManagerService.getLevByAccountId(account.getId());
					session.setLev(lev);
					String levForUI=getLevForUI(lev);	
					session.setLevForUI(levForUI);
				}
				else{
					session.setUserIdForUI("");
					session.setUserNameForUI("");
					session.setLev("");
					session.setLevForUI("");
				}
				sessionsForUI.add(session);
			}
		}
		return sessionsForUI;
	}

	private String getLevForUI(String lev) {
		String levForUI="";
		switch (Integer.parseInt(lev)) {
		case 0:
			levForUI="总公司";
			break;
		case 1:
			levForUI="分公司";
			break;
		default:
			levForUI="管理处";
			break;
		}					
		return levForUI;
	}

	@Override
	public List querySessionsByAccountId(String loginId,String id) {
		List<Session> sessionsForUI = new ArrayList();
		List<Session> sessions = null;
		List<String> accountIds = getAllowedAccountIdsByAccountId(loginId);
		if (accountIds != null) {
			if (accountIds.contains(id)) {
				sessions = sessionDao.querySessionsByAccountId(id);
				if (sessions != null) {
					for (Session session : sessions) {
						session.setOrgNmForUI((String) organizationManagerService
								.getOrgNmByOrgId(session.getOrganizationId()));
						Account account = (Account) accountManagerService
								.queryAccountByAccountId(session.getId());
						if (account != null) {
							session.setUserIdForUI(account.getUserId());
							String lev=(String)organizationManagerService.getLevByAccountId(account.getId());
							session.setLev(lev);
							String levForUI=getLevForUI(lev);	
							session.setLevForUI(levForUI);
						}
						else{
							session.setUserIdForUI("");
							session.setLev("");
							session.setLevForUI("");
						}
						sessionsForUI.add(session);
					}
				}
			} 
		}
		return sessionsForUI;
	}

	// 得到同级及下所有级别的用户工号	
	private List getAllowedAccountIdsByAccountId(String accountId) {
		return accountManagerService.getAllowedAccountIdsByAccountId(accountId);
	}

	// result:2--不存在；0：成功
	public int deleteSession(long contextId) {
		int result = -1;
		Integer count = (Integer) sessionDao.getCountByContextId(contextId);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			sessionDao.delete(contextId);
			log.info("deleteSession-contextId: " + contextId);
			result = 0;
		}
		return result;
	}

	public IOrganizationManagerService getOrganizationManagerService() {
		return organizationManagerService;
	}

	public void setOrganizationManagerService(
			IOrganizationManagerService organizationManagerService) {
		this.organizationManagerService = organizationManagerService;
	}

	public ISession getSessionDao() {
		return sessionDao;
	}

	public void setSessionDao(ISession sessionDao) {
		this.sessionDao = sessionDao;
	}

	public IAccountManagerService getAccountManagerService() {
		return accountManagerService;
	}

	public void setAccountManagerService(
			IAccountManagerService accountManagerService) {
		this.accountManagerService = accountManagerService;
	}

//	public Object getOrgNmByAccountId(String id) {
//		CommonBean commonBean = (CommonBean) organizationManagerService
//				.getOrgIdAndOrgNmByAccountId(id);
//		return commonBean.getName();
//	}

}
