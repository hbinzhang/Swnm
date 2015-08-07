package com.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dao.efence.IFenceDao;
import com.dao.mgtserver.IServerDao;
import com.dao.zone.IZoneDao;
import com.entity.mgtserver.ServerBean;
import com.nsbd.fence.IFenceManager;



public class TimerManager extends QuartzJobBean {
	
	private final static Log log = LogFactory.getLog(TimerManager.class);
	
	private IServerDao serverDao;
	private IFenceDao fenceDao;
	private IZoneDao zoneDao;
	
	@Override
	public void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {

	}

	/**
	 * 在这里定义任务
	 */
	public void start() {
		
		//获取所有管理处IP
		List<ServerBean> list = null;
		try {
			list = serverDao.getAllServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//轮巡管理处是否存活
		log.info("Perform managerment heartbeat detection...");
		for (ServerBean s : list) {
			// 获取每个管理处的IP
			IFenceManager fenceManager = getRemoteEJB(s.getEfenceIP());
			
			//如果管理处连接不上，尝试连接3次
			if(fenceManager==null){
				log.error(s.getEfenceIP()+" connect exception!~");
				for(int i=0;i<3;i++){
					fenceManager = getRemoteEJB(s.getEfenceIP());
					if(fenceManager!=null){
						break;
					}
				}
			}
			//管理处断开连接或管理处服务器故障
			if(fenceManager==null){
				log.error(s.getEfenceIP()+" connect exception!~");
				//处理措施(围栏，防区)
				Map paramMap = new HashMap();
				paramMap.put("mngID", s.getOrgID());
				paramMap.put("fenceStatus", 3);
				paramMap.put("zoneStatus", 0);
				try {
					//fenceDao.updateFenceStatusByMngID(paramMap);
					//zoneDao.updateZoneStatusByMngID(paramMap);
				} catch (Exception e) {
					log.error("updateFenceStatusByMngID failed:"+e.getMessage());
				}
				
			}else{
				int result =fenceManager.getMngStatus();
				if(result!=0){
					//处理措施(围栏，防区)
					Map paramMap = new HashMap();
					paramMap.put("mngID", s.getOrgID());
					paramMap.put("fenceStatus", 3);
					paramMap.put("zoneStatus", 0);
					try {
						//fenceDao.updateFenceStatusByMngID(paramMap);
						//zoneDao.updateZoneStatusByMngID(paramMap);
					} catch (Exception e) {
						log.error("updateFenceStatusByMngID failed:"+e.getMessage());
					}
				}else{
					log.info(s.getOrgID()+":"+s.getEfenceIP()+" connect ok!");
				}
			}

		}
		
	}
	
	private IFenceManager getRemoteEJB(String hostIP) {
		try {
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
			log.error("remote connect error:"+e.getMessage());
			return null;
		}

	}

/*####################### getter setter分割 #############################*/
	public IServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(IServerDao serverDao) {
		this.serverDao = serverDao;
	}

	public IFenceDao getFenceDao() {
		return fenceDao;
	}

	public void setFenceDao(IFenceDao fenceDao) {
		this.fenceDao = fenceDao;
	}

	public IZoneDao getZoneDao() {
		return zoneDao;
	}

	public void setZoneDao(IZoneDao zoneDao) {
		this.zoneDao = zoneDao;
	}
	
}