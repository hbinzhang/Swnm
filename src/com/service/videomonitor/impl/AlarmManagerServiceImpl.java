package com.service.videomonitor.impl;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.mgtserver.IServerDao;
import com.nsbd.fence.IFenceManager;
import com.service.videomonitor.AlarmManagerService;

public class AlarmManagerServiceImpl implements AlarmManagerService {
	
	private static Log log = LogFactory.getLog(AlarmManagerServiceImpl.class);
	
	/*private IServerDao serverDao;

	public IServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(IServerDao serverDao) {
		this.serverDao = serverDao;
	}*/

	@Override
	public void handleRemoteJmsMsg(String mngIp) throws Exception {
		//通过管理处ip获取管理处id
		/*String mgtID = serverDao.getMgtIdByMgtIP(mngIp);
		if(mgtID==null){
			log.info("handleRemoteJmsMsg:find not mngid by mngip.....");
			return;
		}*/
		
		/*IVideoAlarmManager videoAlarmManager = getRemoteEJB(mngIp);
		videoAlarmManager.startListen();*/
		log.info("handleRemoteJmsMsg:startListen success......");
	}

	/*private IVideoAlarmManager getRemoteEJB(String hostIP) {

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
			IVideoAlarmManager videoAlarmManager = (IVideoAlarmManager) ctx
					.lookup("VideoAlarmManager/remote");
			return videoAlarmManager;
		} catch (Exception e) {
			log.error("the manager connect failed,fail message:"+e.getMessage());
			return null;
		}

	}*/
}
