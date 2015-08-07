package com.actions.authmgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.entity.CommonBean;
import com.entity.authmgt.Login;
import com.entity.authmgt.OrgIdAndNames;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.ILoginManagerService;
import com.service.authmgt.impl.AuthenticationManager;
import com.service.logmgt.ISecurityLogService;
import com.util.alarmmgt.AlarmConstants;
import common.page.AjaxObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginAction extends ActionSupport {

	private static final java.lang.String LOGGER_NAME = LoginAction.class
			.getName();

	private static Log log = LogFactory.getLog(LoginAction.class);
	// 下面的属性值由页面传入
	// private Login login;
	private String id;
	private String password;
	// private String loginHostName;
	// private String loginHostIp;

	// 下面的属性值提供给页面
	// 执行结果。用于页面显示
	private Session session;
	private CommonBean commonBean;
	// private AjaxObject ajaxObject;
	//
	// public AjaxObject getAjaxObject() {
	// return ajaxObject;
	// }
	//
	// public void setAjaxObject(AjaxObject ajaxObject) {
	// this.ajaxObject = ajaxObject;
	// }

	private ILoginManagerService loginManagerService = null;

	private ISecurityLogService securityLogService;

	public static Map<String, HttpSession> accountIdAndHttpSessions = new ConcurrentHashMap<String, HttpSession>();

	public String userLogin() {
		String retStr = SUCCESS;
		commonBean = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request != null) {
			HttpSession httpSession = request.getSession();
			log.info("userLogin:httpSession:" + httpSession);
			if (httpSession != null) {
				Session customSession = (Session) httpSession
						.getAttribute("session");
				log.info("userLogin:customSession:" + customSession);
				if (customSession != null) {
					retStr = "haslogin";
				}
			}
		}

		// ajaxObject = new AjaxObject(0, "本次会话失效，请点击其它按钮回到登陆页面！");
		return retStr;
	}

	// result:2--不存在；0：成功；-1：异常
	public String login() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		String result = "0";
		boolean isSuccess = true;
		Login login = new Login();
		login.setId(id);
		login.setPassword(password);

		HttpServletRequest request = ServletActionContext.getRequest();
		String loginHostName = request.getRemoteHost();
		String loginHostIp = getIpAddr(request);
		login.setLoginHostIp(loginHostIp);
		login.setLoginHostName(loginHostName);
		try {
			session = checkAndCreate(login);
		} catch (Exception e) {
			log.error("login() error!", e);
			result = "-1";
			// message = "数据库异常";
			// resultForLog = AlarmConstants.RESULT_FAIL;
		}

		StringBuffer msg = new StringBuffer("登录");
		if (session != null) {
			List authorizatedOpsForAuth = getAuthorizedOpIds(session);
			AuthenticationManager.initializeData(id, authorizatedOpsForAuth);
			List authorizatedOpsForSession = getAuthorizedOpIdsForUI(getAuthorizedOpIds(session));
			session.setAuthorizatedOps(authorizatedOpsForSession);// .setAuthorizatedOps(authorizatedOpsForSession);
			OrgIdAndNames orgIdAndNames = (OrgIdAndNames) loginManagerService
					.getOrgIdAndNamesByAccountId(id);
			session.setOrgIdAndNames(orgIdAndNames);
			session.setLev(loginManagerService.getLevByAccountId(id));
			InetAddress netAddress = getInetAddress();
			String serverHostIp = getHostIp(netAddress);
			session.setServerHostIp(serverHostIp);
			writeSession(request, session);
			msg.append("成功。");
			securityLogService.createSecurityLog("login", "", resultForLog,
					msg.toString());
			result = "0";
		} else {
			result = "2";
		}
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，账户名或密码错误！");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			// msg.append("成功。");
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

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error("unknown host!", e);
		}
		return null;
	}

	private String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String name = netAddress.getHostName();
		return name;
	}

	private String getHostIp(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String ip = netAddress.getHostAddress();
		return ip;
	}

	private List getAuthorizedOpIds(Session session) {
		List authorizedOpIds = null;

		try {
			authorizedOpIds = (List) loginManagerService
					.getAuthorizedOpIds(session);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return authorizedOpIds;
	}

	private List getAuthorizedOpIdsForUI(List<String> authorizedOpIds) {
		List authorizedOpIdsForUI = null;

		try {
			authorizedOpIdsForUI = (List) loginManagerService
					.getAuthorizedOpIdsForUI(authorizedOpIds);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return authorizedOpIdsForUI;
	}

	private void writeSession(HttpServletRequest request, Session session) {
		log.info("writeSession1:accountIdAndHttpSessions.keySet():"
				+ accountIdAndHttpSessions.keySet());
		log.info("writeSession1:accountIdAndHttpSessions:"
				+ accountIdAndHttpSessions);
		String accountId = session.getId();
		ActionContext ctx = ActionContext.getContext();
		HttpSession httpSession = request.getSession();

		String httpSessionId = httpSession.getId();
		ctx.getSession().put("session", session);
		log.info("writeSession1:session: " + session);
		if (accountIdAndHttpSessions.keySet() != null) {
			if (accountIdAndHttpSessions.keySet().contains(accountId)) {
				HttpSession cacheHttpSession = accountIdAndHttpSessions.get(accountId);
				if (cacheHttpSession != null&& cacheHttpSession.getAttribute("session") != null) {
					String cacheHttpSessionId = cacheHttpSession.getId();
					log.info("writeSession1:cacheHttpSession:"+ cacheHttpSessionId);
					log.info("writeSession1:httpSession:" + httpSessionId);
					if (cacheHttpSessionId.compareTo(httpSessionId) != 0) {
						cacheHttpSession.invalidate();
					}
				}
			}
		}
		accountIdAndHttpSessions.put(accountId, httpSession);
		log.info("writeSession2:accountIdAndHttpSessions.keySet():"
				+ accountIdAndHttpSessions.keySet());
		log.info("writeSession2:accountIdAndHttpSessions:"
				+ accountIdAndHttpSessions);
	}

	private Session checkAndCreate(Login login) {
		Session session1 = null;
		try {
			session1 = (Session) loginManagerService.checkAndCreate(login);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return session1;
	}

	public String logout() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = "0";
		StringBuffer msg = new StringBuffer("注销成功。");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			Session session = (Session) httpSession.getAttribute("session");
			log.info("logout:session: " + session);
			if (session != null) {
				AuthenticationManager.finalizeData(session.getId());
				deleteSession(session.getContextId());
				// if(accountIdAndHttpSessions.keySet()!=null){
				// if
				// (accountIdAndHttpSessions.keySet().contains(session.getId()))
				// {
				// accountIdAndHttpSessions.remove(session.getId());
				// }
				// }
				// if (deleteSession(session.getContextId()) != -1) {
				// isSuccess = true;
				// msg.append("成功。");
				// } else {
				// isSuccess = false;
				// resultForLog = AlarmConstants.RESULT_FAIL;
				// msg.append("失败。");
				// }
				securityLogService.createSecurityLog("logout", "",
						resultForLog, msg.toString());
				httpSession.invalidate();
			} else {
				// isSuccess = false;
				// msg.append("失败。");
			}
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:2--不存在；0：成功；-1：失败
	private int deleteSession(long contextId) {
		int result = 2;
		try {
			result = loginManagerService.deleteSession(contextId);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = -1;
		}

		return result;
	}

	public ILoginManagerService getLoginManagerService() {
		return loginManagerService;
	}

	public void setLoginManagerService(ILoginManagerService loginManagerService) {
		this.loginManagerService = loginManagerService;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public ISecurityLogService getSecurityLogService() {
		return securityLogService;
	}

	public void setSecurityLogService(ISecurityLogService securityLogService) {
		this.securityLogService = securityLogService;
	}

}
