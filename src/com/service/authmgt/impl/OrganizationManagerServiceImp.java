package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.authmgt.IAccount;
import com.dao.authmgt.IOrganization;
import com.entity.CommonBean;
import com.entity.authmgt.Organization;
import com.service.authmgt.IOrganizationManagerService;

public class OrganizationManagerServiceImp implements
		IOrganizationManagerService {
	private IOrganization organizationDao = null;
	private IAccount accountDao = null;

	/**
	 * 获取机构信息。总公司用户，得到总公司、所有分公司、所有管理处，
	 * 分公司用户，得到总公司、该分公司及其下所有管理处，
	 * 管理处用户，得到总公司、所在分公司及该管理处。
	 * @param accountId 工号
	 * @return List
	 */
	public List queryOrganizationsByAccountId(String accountId) {
		String lev = null;
		List<Organization> organizations = new ArrayList();
		String orgId = (String) accountDao.getOrgIdById(accountId);
		if (orgId != null) {
			lev = (String) organizationDao.getLevByOrgId(orgId);
			if (lev != null) {
				switch (Integer.parseInt(lev)) {
				case 0:
					//总公司用户，得到总公司、所有分公司、所有管理处
					Organization organizationFor00 = (Organization) organizationDao
							.getOrganizationByOrgID(orgId);
					organizations.add(organizationFor00);//总公司
					List<Organization> organizationsFor01 = organizationDao
							.getOrganizationsFor1ByPOrgId(orgId);
					if (organizationsFor01 != null
							&& organizationsFor01.size() != 0) {
						organizations.addAll(organizationsFor01);
						for (Organization organization : organizationsFor01) {
							List<Organization> organizationsFor02 = organizationDao
									.getOrganizationsFor2ByPOrgId(organization
											.getOrgId());
							organizations.addAll(organizationsFor02);
						}
					}
					break;
				case 1:
					//分公司用户，得到总公司、该分公司及其下所有管理处
					String orgIdFor10 = (String) organizationDao.getPOrgIdByOrgId(orgId);
					Organization organizationFor10 = (Organization) organizationDao.getOrganizationByOrgID(orgIdFor10);
					organizations.add(organizationFor10);//总公司
					Organization organizationFor11 = (Organization) organizationDao.getOrganizationByOrgID(orgId);
					organizations.add(organizationFor11);//分公司
					List<Organization> organizationsFor12 = organizationDao
							.getOrganizationsFor2ByPOrgId(orgId);
					organizations.addAll(organizationsFor12);//管理处
					break;
				case 2:
					//管理处用户，得到总公司、所在分公司及该管理处
					String orgIdFor21 = (String) organizationDao.getPOrgIdByOrgId(orgId);
					Organization organizationFor21 = (Organization) organizationDao.getOrganizationByOrgID(orgIdFor21);
					organizations.add(organizationFor21);//分公司
					String orgIdFor20 = (String) organizationDao.getPOrgIdByOrgId(orgIdFor21);
					Organization organizationFor20 = (Organization) organizationDao.getOrganizationByOrgID(orgIdFor20);
					organizations.add(organizationFor20);//总公司
					Organization organizationFor22 = (Organization) organizationDao.getOrganizationByOrgID(orgId);
					organizations.add(organizationFor22);//管理处
					break;
				}
				// }
			}
		}
		return organizations;
	}
	
	/**
	 * 获取机构标识和名称。
	 * @param lev 机构级别
	 * @return List<CommonBean>
	 */	
	public List getOrgIdAndOrgNmsByLev(String lev) {
		return organizationDao.getOrgIdAndOrgNmsByLev(lev);
	}
	
	/**
	 * 获取机构名称。
	 * @param orgId 机构标识
	 * @return Object
	 */	
	public Object getOrgNmByOrgId(String orgId) {
		return organizationDao.getOrgNmByOrgId(orgId);
	}
	
	/**
	 * 获取管理处的机构标识和名称。
	 * @param pOrgId 分公司机构标识
	 * @return List<CommonBean>
	 */	
	public List getOrgIdAndOrgNmsFor2ByPOrgId(String pOrgId) {		
		return organizationDao.getOrgIdAndOrgNmsFor2ByPOrgId(pOrgId);
	}


	@Override
	public List getOrganizationsFor1ByPOrgId(String pOrgId) {
		return organizationDao.getOrganizationsFor1ByPOrgId(pOrgId);
	}

	@Override
	public List getOrganizationsFor2ByPOrgId(String pOrgId) {
		return organizationDao.getOrganizationsFor2ByPOrgId(pOrgId);
	}
	/**
	 * 获取机构标识和名称。
	 * @param accountId 工号
	 * @return Object
	 */	
	public Object getOrgIdAndOrgNmByAccountId(String accountId) {
		CommonBean commonBean = null;
		String orgId = (String) accountDao.getOrgIdById(accountId);
		if (orgId != null) {
			String orgNm = (String) organizationDao.getOrgNmByOrgId(orgId);
			commonBean = new CommonBean(orgId, orgNm);
		}
		return commonBean;
	}
	
	/**
	 * 获取机构级别。
	 * @param accountId 工号
	 * @return Object
	 */	
	public Object getLevByAccountId(String accountId) {
		String lev = null;
		String orgId = (String) accountDao.getOrgIdById(accountId);
		if (orgId != null) {
			lev = (String) organizationDao.getLevByOrgId(orgId);
		}
		return lev;
	}
	
	/**
	 * 获取上级机构的标识。
	 * @param accountId 工号
	 * @return Object
	 */	
	public Object getPOrgIdByAccountId(String accountId) {
		String pOrgId = null;
		String orgId = (String) accountDao.getOrgIdById(accountId);
		if (orgId != null) {
			pOrgId = (String) organizationDao.getPOrgIdByOrgId(orgId);
		}
		return pOrgId;
	}


	@Override
	public Object getLevByOrgId(String orgId) {
		return organizationDao.getLevByOrgId(orgId);
	}
	/**
	 * 是否属于总公司。
	 * @param accountId 工号
	 * @return boolean
	 */	
	public boolean isParentCompanyByAccountId(String accountId) {
		boolean isParentCompany = false;
		String lev = (String) getLevByAccountId(accountId);
		if (lev != null) {
			if (lev.compareToIgnoreCase("0") == 0) {
				isParentCompany = true;
			} else
				isParentCompany = false;
		}
		return isParentCompany;
	}
	
	/**
	 * 是否属于分公司。
	 * @param accountId 工号
	 * @return boolean
	 */	
	public boolean isCompanyByAccountId(String accountId) {
		boolean isCompany = false;
		String lev = (String) getLevByAccountId(accountId);
		if (lev != null) {
			if (lev.compareToIgnoreCase("1") == 0) {
				isCompany = true;
			} else
				isCompany = false;
		}
		return isCompany;
	}
	
	/**
	 * 是否属于管理处。
	 * @param accountId 工号
	 * @return boolean
	 */	
	public boolean isManagementByAccountId(String accountId) {
		boolean isManagement = false;
		String lev = (String) getLevByAccountId(accountId);
		if (lev != null) {
			if (lev.compareToIgnoreCase("2") == 0) {
				isManagement = true;
			} else
				isManagement = false;
		}
		return isManagement;
	}

	public Object getOrganizationByOrgID(String orgId) {
		return (Organization) organizationDao.getOrganizationByOrgID(orgId);
	}

	/**
	 * 创建机构。
	 * @param object 机构
	 * @return int 1--已存在；0：成功
	 */
	public int createOrganization(Object object) {
		int result = -1;
		Organization organization = (Organization) object;		
		Map map = new HashMap();
		map.put("orgId", organization.getOrgId());
		map.put("orgNm", organization.getOrgNm());
		
		Integer count = (Integer) organizationDao.getCountByOrgIdAndOrgNm(map);
		if (count.intValue() > 0) {
			result = 1;
		} else {
			organizationDao.create(organization);
			result = 0;
		}
		return result;
	}

	/**
	 * 修改机构。
	 * @param object 机构
	 * @return int 1--相同名称的机构已存在；2--不存在；0：成功
	 */
	public int updateOrganization(Object object) {
		int result = -1;
		Organization organization = (Organization) object;
		Integer count = (Integer) organizationDao.getCountByOrgId(organization
				.getOrgId());
		if (count.intValue() == 0) {
			result = 2;
		} else {		
			Map map = new HashMap();
			map.put("orgId", organization.getOrgId());
			map.put("orgNm", organization.getOrgNm());	
			String orgId = (String) organizationDao.getOrgIdByOrgNm(map);
			if (orgId!=null) {
				result = 1;
			} else {
				organizationDao.update(organization);
				result = 0;
			}
		}

		return result;
	}

//	 result:2--不存在；0：成功
	public int deleteOrganization(String orgId) {
		int result = -1;
		Integer count = (Integer) organizationDao.getCountByOrgId(orgId);
		if (count.intValue() == 0) {
			result = 2;
		} else {
			organizationDao.delete(orgId);
			result = 0;
		}
		return result;
	}
	@Override
	public List getOrganizationsByLev(String lev) {
		return organizationDao.getOrganizationsByLev(lev);
	}

	public IOrganization getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(IOrganization organizationDao) {
		this.organizationDao = organizationDao;
	}

	public IAccount getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccount accountDao) {
		this.accountDao = accountDao;
	}

}
