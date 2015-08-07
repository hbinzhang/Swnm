package com.actions.authmgt;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		log.info("intercept:httpSession:"+httpSession);
//		if (httpSession != null) {
			Session session = (Session) httpSession.getAttribute("session");
			log.info("intercept:session:"+session);
			if (session != null) {
				return invocation.invoke();
			}
			else {

				StringBuffer msg = new StringBuffer("本次会话失效，请刷新页面重新登录！");
				String type = request.getHeader("X-Requested-With");

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
					return "nologin";
				}	
			}
		}
//		else
//			return "nologin";

//	}

}
