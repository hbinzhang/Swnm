package com.actions.authmgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Role;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;

public class OrganizationManagerAction extends ActionSupport {

	private static final java.lang.String LOGGER_NAME = OrganizationManagerAction.class
			.getName();

	private Log log = LogFactory.getLog(this.getClass());

	// 下面的属性值由页面传入
	private Organization organization;
	private String accountId;// 工号
	private String orgId;// 工号

	// 下面的属性值提供给页面
	// Organization集
	private List<Organization> organizations;
	// 执行结果。用于页面显示
	private CommonBean commonBean;

	private IOrganizationManagerService organizationManagerService = null;

	private IOperationLogService operationLogService;

	public IOrganizationManagerService getOrganizationManagerService() {
		return organizationManagerService;
	}

	public void setOrganizationManagerService(
			IOrganizationManagerService organizationManagerService) {
		this.organizationManagerService = organizationManagerService;
	}

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String queryOrganizationsByAccountId() {
		String message = "";
		int resultForLog = AlarmConstants.RESULT_SUC;
		boolean isSuccess = true;
		String result = "0";
		List<Organization> organizationsTmp = null;

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				organizationsTmp = organizationManagerService
						.queryOrganizationsByAccountId(session.getId());
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		// operationLogService.createOperationLog("queryOrganizationsByAccountId",
		// "",
		// resultForLog, message);
		if (organizationsTmp != null) {
			organizations = new ArrayList();
			for (int i = 0; i < organizationsTmp.size(); i++) {
				Organization organizationTmp = organizationsTmp.get(i);
				int orgId = organizationTmp.getOrgId().hashCode();
				String pOrgIdStr = organizationTmp.getPorgId();
				int pOrgId = -1;
				if (pOrgIdStr != null) {
					pOrgId = organizationTmp.getPorgId().hashCode();
				}
				organizationTmp.setOrgIdForUI(orgId);
				organizationTmp.setPorgIdForUI(pOrgId);
				organizations.add(organizationTmp);
			}
		}
		StringBuffer msg = new StringBuffer("查询机构");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String addOranization() {
		return SUCCESS;
	}

	// result:1--已存在；0：成功；-1：异常
	public String createOrganization() {
		String message = "";
		int resultForLog = AlarmConstants.RESULT_SUC;
		boolean isSuccess = true;
		String result = null;

		String id=organization.getOrgId();
		String orgNm=organization.getOrgNm();
		String remark=organization.getRemark();
		organization.setOrgId(id.trim());
		organization.setOrgNm(orgNm.trim());
		organization.setRemark(remark.trim());

		try {
			result = String.valueOf(organizationManagerService
					.createOrganization(organization));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("createOrganization",
				organization.getOrgId(), resultForLog, message);
		StringBuffer msg = new StringBuffer("创建机构").append(organization
				.getOrgId());
		switch (Integer.parseInt(result)) {
		case 1:
			msg.append("失败，相同代码或名称的机构已经存在。");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String editOrganization() {
		boolean isSuccess = true;
		String result = "0";
		try {
			organization = (Organization) organizationManagerService
					.getOrganizationByOrgID(orgId);
		} catch (Exception ex) {
			// LoggerOperator.err(LOGGER_NAME, "addUser error", e);
			Logger log = Logger.getLogger(LOGGER_NAME);
			log.error(ex.getMessage(), ex);
			result = "-1";
		}
		String pOrgIdStr = organization.getPorgId();
		int pOrgId = -1;
		if (pOrgIdStr != null) {
			pOrgId = organization.getPorgId().hashCode();
		}
		organization.setOrgIdForUI(organization.getOrgId().hashCode());
		organization.setPorgIdForUI(pOrgId);
		StringBuffer msg = new StringBuffer("查询机构");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:1--相同名称的机构已存在；2--不存在；0：成功；-1：异常
	public String updateOrganization() {
		String message = "";
		int resultForLog = AlarmConstants.RESULT_SUC;
		boolean isSuccess = true;
		String result = null;

		String id=organization.getOrgId();
		String orgNm=organization.getOrgNm();
		String remark=organization.getRemark();
		organization.setOrgId(id.trim());
		organization.setOrgNm(orgNm.trim());
		organization.setRemark(remark.trim());
		
		try {
			result = String.valueOf(organizationManagerService
					.updateOrganization(organization));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("updateOrganization",
				organization.getOrgId(), resultForLog, message);

		StringBuffer msg = new StringBuffer("修改机构").append(organization
				.getOrgId());
		switch (Integer.parseInt(result)) {
		case 1:
			msg.append("失败，相同名称的机构已经存在。");
			isSuccess = false;
			break;
		case 2:
			msg.append("失败，机构已经不存在。");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:2--不存在；0：成功；-1：异常
	// public String deleteOrganization() {
	// boolean isSuccess=true;
	// String result = null;
	// try {
	// result =
	// String.valueOf(organizationManagerService.deleteOrganization(organization.getOrgId()));
	// } catch (Exception ex) {
	// // LoggerOperator.err(LOGGER_NAME, "addUser error", e);
	// Logger log = Logger.getLogger(LOGGER_NAME);
	// log.error(ex.getMessage(), ex);
	// result = "-1";
	// }
	// StringBuffer msg = new
	// StringBuffer("删除机构").append(organization.getOrgId());
	// switch (Integer.parseInt(result)) {
	// case 2:
	// msg.append("失败，机构已经不存在。");
	// isSuccess=false;
	// break;
	// case -1:
	// msg.append("失败，数据库异常！");
	// isSuccess=false;
	// break;
	// default:
	// msg.append("成功。");
	// break;
	// }
	// commonBean = new CommonBean(result, msg.toString());
	// if(isSuccess){
	// return SUCCESS;
	// }
	// else{
	// this.addActionError(msg.toString());
	// return ERROR;
	// }
	// }

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

}
