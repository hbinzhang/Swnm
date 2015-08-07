/**
 * 
 */
package com.actions.gis;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import common.page.AjaxObject;

/**
 * @author wyj
 *
 * 
 */
public class QueryOrganizationAction extends ActionSupport {

	private static final long serialVersionUID = 4395188312500108659L;
	private Log log = LogFactory.getLog(this.getClass());

	private IOrganizationManagerService orgService;//

	private InputStream resultJsonStream;//
	
	private AjaxObject resultAjaxObj;
	
	private List<Organization> orgList4Ajax;
	
	private Organization org4Ajax;
	
	private String orgId;

	// private AjaxObject errorObj;

	/**
	 * 
	 */
	public QueryOrganizationAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 通过用户id，查询组织结构信息
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public String queryOrganizationsByAccountId() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				orgList4Ajax = orgService.queryOrganizationsByAccountId(session.getId());
				return SUCCESS;
			} catch (Exception ex) {
				orgList4Ajax = new ArrayList<Organization>();
				log.error(ex.getMessage(), ex);
				return ERROR;
			}
		}
		return ERROR;
	}


	public InputStream getResultJsonStream() {
		return resultJsonStream;
	}

	public IOrganizationManagerService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganizationManagerService orgService) {
		this.orgService = orgService;
	}

	public List<Organization> getOrgList4Ajax() {
		return orgList4Ajax;
	}

	public void setOrgList4Ajax(List<Organization> orgList4Ajax) {
		this.orgList4Ajax = orgList4Ajax;
	}

	//
	// public AjaxObject getErrorObj() {
	// return errorObj;
	// }
	//
	// public void setErrorObj(AjaxObject errorObj) {
	// this.errorObj = errorObj;
	// }

	public void setResultJsonStream(InputStream resultJsonStream) {
		this.resultJsonStream = resultJsonStream;
	}

	/**
	 * 通过用户id，查询组织结构信息
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String queryOrganizationsInfoById() {
		resultAjaxObj = new AjaxObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				org4Ajax = (Organization) orgService.getOrganizationByOrgID(orgId);
				resultAjaxObj= new AjaxObject(1,org4Ajax);
				return SUCCESS;
			} catch (Exception ex) {
				resultAjaxObj= new AjaxObject(0);
				log.error(ex.getMessage(), ex);
				return ERROR;
			}
		}
		else{//未登录
			resultAjaxObj= new AjaxObject(-100);
		}
		return ERROR;
	}

	public Organization getOrg4Ajax() {
		return org4Ajax;
	}

	public void setOrg4Ajax(Organization org4Ajax) {
		this.org4Ajax = org4Ajax;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public AjaxObject getResultAjaxObj() {
		return resultAjaxObj;
	}

	public void setResultAjaxObj(AjaxObject resultAjaxObj) {
		this.resultAjaxObj = resultAjaxObj;
	}
}
