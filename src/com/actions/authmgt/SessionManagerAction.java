package com.actions.authmgt;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.OrgLevAndIdNames;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.ISessionManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;

import common.page.AjaxObject;

public class SessionManagerAction extends ActionSupport {

	private static final java.lang.String LOGGER_NAME = SessionManagerAction.class
			.getName();

	private Log log = LogFactory.getLog(this.getClass());

	// 下面的属性值由页面传入//
	private String id;// 用户工号
	private String lev;// 组织机构级别
	private String organizationId;// 组织机构代码
	private long contextId;// 会话标识
	private int filter;// 1代表按机构查询，其他代表按照工号查询

	// 下面的属性值提供给页面
	private OrgLevAndIdNames orgLevAndIdNames;
	private List<Session> sessions;
	// 执行结果。用于页面显示
	private CommonBean commonBean;
	private AjaxObject ajaxObject;

	private ISessionManagerService sessionManagerService = null;

	private IOperationLogService operationLogService;

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String entrySession() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = "0";

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		filter = 1;
		if (session != null) {
			try {
				orgLevAndIdNames = sessionManagerService
						.getOrgLevAndIdNamesByAccountId(session.getId());
				if (orgLevAndIdNames != null) {
					sessions = sessionManagerService
							.querySessionsByOrganizationId(session
									.getOrganizationId());
				}
				// orgLevAndIdNames =
				// sessionManagerService.getOrgLevAndIdNamesByAccountId(id);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		// operationLogService.createOperationLog("querySessionsByOrganizationId",
		// "", resultForLog,
		// message);
		StringBuffer msg = new StringBuffer("进入会话管理");
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

	/**
	 * 查询session，当filter为1标示按照机构查询，否则标示按照账号来查询
	 * 
	 * @return
	 */
	public String searchSession() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		String opNameStr = "";
		boolean isSuccess = true;
		String result = "0";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				orgLevAndIdNames = sessionManagerService
						.getOrgLevAndIdNamesByAccountId(session.getId());
				if (filter == 1) {
					sessions = sessionManagerService
							.querySessionsByOrganizationId(organizationId);
					opNameStr = "querySessionsByOrganizationId";
				} else {
					sessions = sessionManagerService
							.querySessionsByAccountId(session.getId(),id);
					opNameStr = "querySessionsByAccountId";
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		StringBuffer msg = new StringBuffer("查询会话");
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

	// result:2--不存在；0：成功；-1：异常
	public String deleteSession() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;
		int resultForDel = -1;
		try {
			result = String.valueOf(sessionManagerService
					.deleteSession(contextId));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("deleteSession",
				String.valueOf(contextId), resultForLog, message);

		StringBuffer msg = new StringBuffer("删除会话").append(contextId);
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，会话已经不存在。");
			isSuccess = false;
			resultForDel = 0;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			resultForDel = 0;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			resultForDel = 1;
			break;
		}
		// commonBean = new CommonBean(result, msg.toString());

		log.info("deleteSession:contextId:"+contextId+"::"+result);
		ajaxObject = new AjaxObject(resultForDel, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public long getContextId() {
		return contextId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public ISessionManagerService getSessionManagerService() {
		return sessionManagerService;
	}

	public void setSessionManagerService(
			ISessionManagerService sessionManagerService) {
		this.sessionManagerService = sessionManagerService;
	}

	public OrgLevAndIdNames getOrgLevAndIdNames() {
		return orgLevAndIdNames;
	}

	public void setOrgLevAndIdNames(OrgLevAndIdNames orgLevAndIdNames) {
		this.orgLevAndIdNames = orgLevAndIdNames;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		this.filter = filter;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

}
