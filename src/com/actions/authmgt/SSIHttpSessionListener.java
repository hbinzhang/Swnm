package com.actions.authmgt;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dao.authmgt.impl.LoginImp;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionContext;
import com.service.authmgt.ILoginManagerService;
import com.service.authmgt.impl.AccountManagerServiceImp;
import com.service.authmgt.impl.AuthenticationManager;
import com.service.authmgt.impl.LoginManagerServiceImp;
import com.service.authmgt.impl.OrganizationManagerServiceImp;

public class SSIHttpSessionListener implements HttpSessionListener {

	// private LoginImp loginImp;

	private Log log = LogFactory.getLog(this.getClass());

	public void sessionCreated(HttpSessionEvent event) {
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession currentHttpSession = event.getSession();
		log.info("sessionDestroyed1:currentHttpSession: "+currentHttpSession);
//		log.info("sessionDestroyed1: currentHttpSession:"+currentHttpSession.getAttributeNames().toString());
		Session session = (Session) currentHttpSession.getAttribute("session");
		log.info("sessionDestroyed1:session: "+session);
		if (session != null) {
			String accountId = session.getId();
			if (LoginAction.accountIdAndHttpSessions.keySet().contains(
					accountId)) {
				LoginAction.accountIdAndHttpSessions.remove(accountId);
			}
		}
		log.info("sessionDestroyed2:accountIdAndHttpSessions: "+LoginAction.accountIdAndHttpSessions);
	}

}
