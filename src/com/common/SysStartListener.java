package com.common;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dao.mgtserver.impl.ServerDaoImpl;
import com.entity.mgtserver.ServerBean;
import com.nsbd.fence.IFenceManager;
import com.util.ConfigUtil;

public class SysStartListener implements ServletContextListener {

	private final static Log log = LogFactory.getLog(SysStartListener.class);

	public void contextDestroyed(ServletContextEvent event) {

		String path = event.getServletContext().getRealPath(
				"Context-action.xml");
	    if(path == null){       
	    	path = this.getClass().getClassLoader().getResource("/").getPath();        
	    	path = path.substring(0,path.indexOf("WEB-INF"));    
	    }else{
	    	path = path.substring(0, path.lastIndexOf("\\") + 1);
	    }
		// 从容器获取dao对象
		ServerDaoImpl serverDao = getDaoObject(path);

		List<ServerBean> list = null;
		try {
			list = serverDao.getAllServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 系统启动时执行管理处的初始化工作
		log.info("nuInit get remote FenceManager object...");
		for (ServerBean s : list) {
			// 获取每个管理处的IP
			IFenceManager fenceManager = getRemoteEJB(s.getEfenceIP());
			
			if(fenceManager==null){
				log.info("manager is not exsit...");
				return;
			}

			log.info("system uninit start...");
			int flag = fenceManager.unInit();
			if (flag == 0) {
				log.info("system uninit success....");
			} else {
				log.info("system uninit failed....");
			}
		}

	}

	public void contextInitialized(ServletContextEvent event) {
		String path = event.getServletContext().getRealPath(
				"Context-action.xml");
	    if(path == null){       
	    	path = this.getClass().getClassLoader().getResource("/").getPath();        
	    	path = path.substring(0,path.indexOf("WEB-INF"));    
	    }else{
	    	path = path.substring(0, path.lastIndexOf("\\") + 1);
	    }
		System.out.println(path);
		// 从容器获取dao对象
		ServerDaoImpl serverDao = getDaoObject(path);

		List<ServerBean> list = null;
		try {
			list = serverDao.getAllServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 系统启动时执行管理处的初始化工作
		log.info("init get remote FenceManager object...");
		if (list != null) {
			for (ServerBean s : list) {
				// 获取每个管理处的IP
				IFenceManager fenceManager = getRemoteEJB(s.getEfenceIP());

				try {
					if(fenceManager==null){
						log.info("the manager not exsit....");
						return;
					}
					log.info("system init start...");
					int flag = fenceManager.init();
					if (flag == 0) {
						log.info("system init success....");
					} else {
						log.info("system init failed....");
					}

				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	private ServerDaoImpl getDaoObject(String path) {
		ApplicationContext ac = new FileSystemXmlApplicationContext(path
				+ "WEB-INF/Context-*.xml");
		// ApplicationContext ac = new
		// FileSystemXmlApplicationContext(path+"Context-*.xml");
		// ac = new
		// FileSystemXmlApplicationContext("src/applicationContext.xml");
		ServerDaoImpl serverDao = (ServerDaoImpl) ac.getBean("serverDaoImpl");
		return serverDao;

	}

	private IFenceManager getRemoteEJBObj(String hostIP) {

		// 此处为一固定管理处主机IP?
		// String hostIP = "127.0.0.1";

		// 动态连接jboss的ejb
		ConfigUtil.write("remote.connection.default.host", hostIP,
				"jboss-ejb-client.properties");
		try {

			System.out.println("remote.connection.default.host:"
					+ ConfigUtil.getPropertyValue(
							"remote.connection.default.host",
							"jboss-ejb-client.properties"));
			System.out.println("remote.connection.default.port:"
					+ ConfigUtil.getPropertyValue(
							"remote.connection.default.port",
							"jboss-ejb-client.properties"));

			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES,
					"org.jboss.ejb.client.naming");
			Context ctx = new InitialContext(properties);

			final String appName = ConfigUtil.getPropertyValue("appName",
					"EJBConfig.properties");
			final String moduleName = ConfigUtil.getPropertyValue("moduleName",
					"EJBConfig.properties");
			final String distinctName = ConfigUtil.getPropertyValue(
					"distinctName", "EJBConfig.properties");

			String addr = "ejb:" + appName + "/" + moduleName + "/"
					+ distinctName
					+ "/FenceManager!com.nsbd.fence.IFenceManager";

			log.info("lookup FenceManager:addr==>" + addr);
			IFenceManager fenceManager = (IFenceManager) ctx.lookup(addr);
			return fenceManager;
		} catch (Exception e) {
			log.info("lookup FenceManager failed:" + e);
			return null;
		}
	}

	private IFenceManager getRemoteEJB(String hostIP) {
		
		try {
			log.info("=====================remote manager ip:  "+hostIP+" ======================");
			if(hostIP==null){
				log.info("manager ip is null.....");
				return null;
			}
			Properties properties = new Properties();
			properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.URL_PKG_PREFIXES,
					"org.jboss.naming:org.jnp.interfaces");
			properties.setProperty(Context.PROVIDER_URL, hostIP + ":1099");
			Context ctx = new InitialContext(properties);
			IFenceManager fenceManager = (IFenceManager) ctx
					.lookup("FenceManager/remote");
			return fenceManager;
		} catch (Exception e) {
			log.error("the manager connect failed......");
			return null;
		}

	}

}
