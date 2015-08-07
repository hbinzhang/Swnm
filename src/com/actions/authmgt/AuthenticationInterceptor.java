package com.actions.authmgt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.dao.authmgt.ILogin;
import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.service.authmgt.impl.AuthenticationManager;

public class AuthenticationInterceptor extends AbstractInterceptor {

	private ILogin loginDao = null;
	private ApplicationContext ac;

	private Log log = LogFactory.getLog(this.getClass());

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		boolean isSuccess = true;
		int result = 0;
		// ActionContext ctx = actionInvocation.getInvocationContext();
		HttpServletRequest request = ServletActionContext.getRequest();

		// 获得请求类型
		String type = request.getHeader("X-Requested-With");

		ServletContext sc = request.getSession().getServletContext();
		ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		log.info("AuthenticationInterceptor:session:" + session);
		String opName = actionInvocation.getInvocationContext().getName();
		if (session != null) {
//			if (checkSession(session.getContextId()) == 0) {
				if (AuthenticationManager.isAuthorizedId(session.getId(),
						opName)) {
					log.info("AuthenticationInterceptor:actionInvocation.invoke()");
					return actionInvocation.invoke();
				}
//			}
		}
		StringBuffer msg = new StringBuffer("无操作权限。");
		// ctx.put("tip", msg.toString());

		if ("XMLHttpRequest".equalsIgnoreCase(type)) {
			ServletActionContext.getResponse().setContentType(
					"application/json; charset=utf-8");
			PrintWriter printWriter = ServletActionContext.getResponse()
					.getWriter();
			printWriter.print("{\"result\":\"0\",\"message\":\"" + msg
					+ "\",\"data\":\"null\"}");
			printWriter.flush();
			printWriter.close();

			return null;
		} else {
			return "autherror";
		}

	}

	// result:2--不存在；0：成功
	private int checkSession(long contextId) {
		int result = -1;
		loginDao = (ILogin) ac.getBean("LoginImp");
		log.info("checkSession:loginDao**" + loginDao);
		Integer count = null;
		try {
			count = (Integer) loginDao.getCountByContextId(contextId);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		if (count != null) {
			log.info("checkSession:count**" + count.intValue());
			if (count.intValue() == 0) {
				result = 2;
			} else {
				result = 0;
			}
		}
		return result;
	}

	public ILogin getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(ILogin loginDao) {
		this.loginDao = loginDao;
	}

	public ApplicationContext getAc() {
		return ac;
	}

	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}

}
