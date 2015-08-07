package com.service.efence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.common.page.Page;
import com.dao.authmgt.IOrganization;
import com.dao.efence.IFenceDao;
import com.dao.mgtserver.IServerDao;
import com.dao.zone.IDefenceZoneMapDao;
import com.dao.zone.IIntegrationZoneMapDao;
import com.dao.zone.IPositionZoneMapDao;
import com.dao.zone.IPulseZoneMapDao;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.authmgt.Organization;
import com.entity.efence.FenceBean;
import com.entity.zone.ZoneBean;
import com.nsbd.fence.FenceInfo;
import com.nsbd.fence.IFenceManager;
import com.service.efence.IFenceService;
import com.service.linkagectl.impl.OperationUIPushImp;
import com.service.zone.IZoneService;
import com.util.ConfigUtil;
import com.util.fence.FenceConvertUtil;

public class FenceServiceImpl implements IFenceService {
	
	private IFenceDao fenceDao;
	
	private IServerDao serverDao;
	
	private OperationUIPushImp operationUIPushImp;
	
	private IDefenceZoneMapDao defenceZoneMapDao;
	private IIntegrationZoneMapDao integrationZoneMapDao;
	private IPositionZoneMapDao positionZoneMapDao;
	private IPulseZoneMapDao pulseZoneMapDao;
	private IOrganization organization;
	
	private IZoneService zoneManagerService;
	
	private final static Log log = LogFactory
			.getLog(FenceServiceImpl.class);
	
	@Override
	public int updateFenceStatus(FenceInfo fenceInfo){
		
		try {
			//1.更新数据库状态
			log.info("excute updateFenceStatus method:FenceInfo status:"+fenceInfo.getstatus()+" fenceInfo hostID:"+fenceInfo.getHostID());
			fenceDao.updateFenceStatus(fenceInfo);
			//2.查询
			FenceBean fenceBean = fenceDao.getFenceByHostID(fenceInfo.getHostID());
			
			String messageBody=fenceBean.getHostID()+":"+fenceBean.getFenceStatus();
			//查询总公司机构ID
			List l = organization.getOrganizationsByLev("0");
			Organization cb = (Organization)l.get(0);
			String orgid = cb.getOrgId();
			//3.向主界面推送消息
			try {
				operationUIPushImp.pullOperationEvent2MC(fenceBean.getMntMentID(), "change", "fence", messageBody);
				operationUIPushImp.pullOperationEvent2MC(fenceBean.getSubComID(), "change", "fence", messageBody);
				operationUIPushImp.pullOperationEvent2MC(orgid, "change", "fence", messageBody);
			} catch (Exception e) {
				log.info("pullOperationEvent2MC failed when updateFenceStatus:"+e.getMessage());
			}
			
			//4.向gis界面推送消息
			Map map = new HashMap();
			map.put("fenceID", fenceBean.getHostID());
			String statusMsg=null;
			switch(fenceBean.getFenceStatus()){
			case 1:
				statusMsg="ready";
				break;
			case 2:
				statusMsg="fault";
				break;
			case 3:
				statusMsg="offline";
				break;
				
			}
			map.put("stat", statusMsg);
			String jsonStr = JSON.toJSONString(map);
			try {
				operationUIPushImp.pullOperationEvent2GIS(fenceBean.getMntMentID(), "change", "fence", jsonStr);
			} catch (Exception e) {
				log.info("pullOperationEvent2GIS failed when updateFenceStatus:"+e.getMessage());
			}
			
			
		} catch (Exception e) {
			log.error("updateFenceStatus:hostId="+fenceInfo.getHostID()
					    +" status="+fenceInfo.getstatus()+" failed!"+e.getMessage());
			return -1;
		}
		
		return 0;
	}
	
	@Override
	public int addFence(FenceBean fi) throws Exception{
		
		fenceDao.addFence(fi);
		
		log.info("remote invoke addFence,fence hostID is:"+fi.getHostID());
		if (remoteAddFence(fi,null) == 0) {
			//向gis界面推送消息
			String jsonStr = JSON.toJSONString(fi);
			try {
				operationUIPushImp.pullOperationEvent2GIS(fi.getMntMentID(), "add", "fence", jsonStr);
			} catch (Exception e) {
				log.info("pullOperationEvent2GIS failed when addFence:"+e.getMessage());
			}
			return 0;
		} else {
			log.info("remote invoke addFence failed,fence hostID is:"+fi.getHostID());
			throw new Exception();
		}
	}
	@Override
	public int delFence(String hostID) throws Exception{
		
		//根据主机ID查询围栏信息
		FenceBean fi = fenceDao.getFenceByHostID(hostID);
		List<Integer> zoneIDs=null;
		//先删除关联关系,同时也要删除与刚围栏绑定的防区
		// 根据设备类型进行不同处理
		switch (fi.getFenceType()) {

		case 1:// 高压脉冲
			//通过围栏主机id查询防区id
			zoneIDs=pulseZoneMapDao.getZoneIDsByHostID(hostID);
			linkDelZone(zoneIDs);
			pulseZoneMapDao.delPulseZoneMapByHostID(hostID);
			break;
		case 2:// 一体化
			//通过围栏主机id查询防区id
			zoneIDs=integrationZoneMapDao.getZoneIDsByHostID(hostID);
			linkDelZone(zoneIDs);
			integrationZoneMapDao.delIntegrationZoneMapByHostID(hostID);
			break;
		case 3:// 防区型光纤
			//通过围栏主机id查询防区id
			zoneIDs=defenceZoneMapDao.getZoneIDsByHostID(hostID);
			linkDelZone(zoneIDs);
			defenceZoneMapDao.delDefenceZoneMapByHostID(hostID);
			break;
		case 4:// 定位型光纤
			//通过围栏主机id查询防区id
			zoneIDs=positionZoneMapDao.getZoneIDsByHostID(hostID);
			linkDelZone(zoneIDs);
			positionZoneMapDao.delPositionZoneMapByHostID(hostID);
			break;
		}
		
		fenceDao.delFence(hostID);
		
		log.info("remote invoke delFence,fence hostID is:"+fi.getHostID());
		int flag = 0;
		//如果状态为4则不需要进行远程删除
		if(!(fi.getFenceStatus().equals(4))){
			
			flag=remoteDelFence(fi);
		}
		if (flag == 0||flag == -2) {
			//向gis界面推送消息
			Map map = new HashMap();
			map.put("fenceID", fi.getHostID());
			String jsonStr = JSON.toJSONString(map);
			try {
				operationUIPushImp.pullOperationEvent2GIS(fi.getMntMentID(), "del", "fence", jsonStr);
			} catch (Exception e) {
				log.info("pullOperationEvent2GIS failed when delFence:"+e.getMessage());
			}
			return 0;
		} else {
			log.info("remote invoke delFence failed,fence hostID is:"+fi.getHostID());
			throw new Exception();
		}
	}
	/**
	 * @param zoneIDs
	 * 联动删除防区信息
	 */
	private void linkDelZone(List<Integer> zoneIDs) throws Exception{
		//根据ID查询防区信息
		if(zoneIDs!=null&&zoneIDs.size()>0){
			for(Integer zoneID:zoneIDs){
				ZoneBean zb = zoneManagerService.getZoneByID(zoneID);
				zoneManagerService.delZone(zb);
			}
		}
	}

	@Override
	public FenceBean getFenceByID(FenceBean fenceInfo) throws Exception {
		
		return fenceDao.getFenceByHostID(fenceInfo.getHostID());
	}

	public int modFence(FenceBean fi) throws Exception{
		
		fenceDao.modFence(fi);
		
		if (remoteModFence(fi) == 0) {
			return 0;
		} else {
			log.info("remote invoke modFence failed,fence hostID is:"+fi.getHostID());
			throw new Exception();
		}
	}
	
	public List<Integer> getAllFenceHostID() throws Exception {
		
		return fenceDao.getAllFenceHostID();
		
	}
	
	/**
	 * 通过设备ID查询告警信息
	 */
	public List<AlarmDTO> findAlarmsByDeviceID(String devID) throws Exception {
		return fenceDao.findAlarmsByDeviceID(devID);
	}
	
	/**
	 * 根据用户机构等级过滤围栏信息（根据分公司或管理处ID）
	 */
	@Override
	public List<FenceBean> getFencesBySubComIdOrMngId(FenceBean fenceBean)
			throws Exception {
		return fenceDao.getFencesBySubComIdOrMngId(fenceBean);
	}
	/**
	 * 管理处重启，处理方法。
	 * 将该管理处的围栏重新添加一次
	 */
	@Override
	public void handleRemoteJmsMsg(String mngIP) throws Exception {
		//通过管理处ip获取管理处id
		String mgtID = serverDao.getMgtIdByMgtIP(mngIP);
		if(mgtID==null){
			log.info("handleRemoteJmsMsg:find not mngid by mngip.....");
			return;
		}
		//通过管理处id查询围栏信息
		FenceBean fb = new FenceBean();
		fb.setMntMentID(mgtID);
		List<FenceBean> fbs = fenceDao.getFencesBySubComIdOrMngId(fb);
		for(FenceBean f:fbs){
		 if(f.getFenceStatus().equals(4)){
				f.setFenceStatus(3);
				FenceInfo fenceInfo = new FenceInfo();
				fenceInfo.setBaseInfo(f.getHostID(), f.getFenceType(), f.getIp(), f.getPort());
				fenceInfo.status=3;
				fenceDao.updateFenceStatus(fenceInfo);
			}
			int result=-1;
			for(int i=0;i<3;i++){
			    result =remoteAddFence(f,mngIP);
				if(result==0){
					break;
				}
			}
			if(result!=0){
				log.info("manager restart syn add efence failed,fence hostid:"+f.getHostID());
				/*int flag = delFence(f.getHostID());
				if(flag!=0){
					log.info("delete fence failed when manager restart syn add efence,fence hostid:"+f.getHostID());
				}else{
					log.info("delete fence sucess when manager restart syn add efence,fence hostid:"+f.getHostID());
				}*/
				//设置为管理处失联状态
				FenceInfo fenceInfo = new FenceInfo();
				fenceInfo.setBaseInfo(f.getHostID(), f.getFenceType(), f.getIp(), f.getPort());
				fenceInfo.status=4;
				fenceDao.updateFenceStatus(fenceInfo);
			}
			
		}
	}
	/**
	 * 通过管理处ID查询分公司ID
	 */
	@Override
	public String findSubComByMngID(FenceBean fi) throws Exception {
		
		return fenceDao.findSubComByMngID(fi);
		
	}
	private int remoteModFence(FenceBean fi) {
		//根据管理处ID获取主机IP
		String hostIP="";
		int result = 0;
		try {
			hostIP = serverDao.getMgtIPByMgtID(fi.getMntMentID());
			IFenceManager fenceManager = getRemoteEJB(hostIP);
			
			if(fenceManager==null)
				return -1;
			FenceInfo fence = new FenceInfo();
			fence = FenceConvertUtil.localToRemoteFence(fi, fence);
			
			log.info("remote invoke modFence,fence hostID is:"+fi.getHostID());
			result = fenceManager.modFence(fence);
			log.info("remote invoke modFence RESULT:"+result);
			return result;
		} catch (Exception e) {
			log.info("remote invoke modFence failed:"+e.getMessage());
			return -1;
		}
	}
	private int remoteAddFence(FenceBean fi,String mntIP) {
		//根据管理处ID获取主机IP
		
		int result = 0;
		try {
			if(mntIP==null){
				
				mntIP = serverDao.getMgtIPByMgtID(fi.getMntMentID());
				
			}
			IFenceManager fenceManager = getRemoteEJB(mntIP);
			
			if(fenceManager==null)
				return -1;
			FenceInfo fence = new FenceInfo();
			fence = FenceConvertUtil.localToRemoteFence(fi, fence);
			
			log.info("remote invoke addFence,fence hostID is:"+fi.getHostID());
			result = fenceManager.addFence(fence);
			log.info("remote invoke addFence result:"+result);
			return result;
		} catch (Exception e) {
			
			log.info("remote invoke addFence failed:"+e.getMessage());
			return -2;
			
		}
	}
	
	private int remoteDelFence(FenceBean fi) {
		
		int result = 0;
		try {
			
			//根据管理处ID获取主机IP
			String hostIP="";
			hostIP = serverDao.getMgtIPByMgtID(fi.getMntMentID());
			
			IFenceManager fenceManager = getRemoteEJB(hostIP);
			
			if(fenceManager==null)
				return -1;
			
			log.info("remote invoke delFence,fence hostID is:"+fi.getHostID());
			result = fenceManager.delFence(fi.getHostID());
			log.info("remote invoke delFence result:"+result);
			return result;
		} catch (Exception e) {
			
			log.info("remote invoke delFence failed"+e.getMessage());
			return -1;
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
			log.error("the manager connect failed,fail message:"+e.getMessage());
			return null;
		}

	}
	
	private IFenceManager getRemoteEJBObj(String hostIP){
		
		
		//动态连接jboss的ejb
		ConfigUtil.write("remote.connection.default.host", hostIP,"jboss-ejb-client.properties");
		try {
			Properties properties = new Properties();
			properties.put(Context.URL_PKG_PREFIXES,
					"org.jboss.ejb.client.naming");
			Context ctx = new InitialContext(properties);
			
			final String appName = ConfigUtil.getPropertyValue("appName","EJBConfig.properties");
			final String moduleName = ConfigUtil.getPropertyValue("moduleName","EJBConfig.properties");
			final String distinctName = ConfigUtil.getPropertyValue("distinctName","EJBConfig.properties");
			
			String addr = "ejb:"+ appName + "/" + moduleName + "/" + distinctName
					+ "/FenceManager!com.nsbd.fence.IFenceManager";
			
			log.info("lookup FenceManager:addr==>"+addr);
			IFenceManager fenceManager = (IFenceManager) ctx.lookup(addr);
			return fenceManager;
		} catch (Exception e) {
			log.info("lookup FenceManager failed:"+e);
			e.printStackTrace();
			return null;
		}
	}

	public IFenceDao getFenceDao() {
		return fenceDao;
	}

	public void setFenceDao(IFenceDao fenceDao) {
		this.fenceDao = fenceDao;
	}

	public IServerDao getServerDao() {
		return serverDao;
	}

	public void setServerDao(IServerDao serverDao) {
		this.serverDao = serverDao;
	}

	public IDefenceZoneMapDao getDefenceZoneMapDao() {
		return defenceZoneMapDao;
	}

	public void setDefenceZoneMapDao(IDefenceZoneMapDao defenceZoneMapDao) {
		this.defenceZoneMapDao = defenceZoneMapDao;
	}

	public IIntegrationZoneMapDao getIntegrationZoneMapDao() {
		return integrationZoneMapDao;
	}

	public void setIntegrationZoneMapDao(
			IIntegrationZoneMapDao integrationZoneMapDao) {
		this.integrationZoneMapDao = integrationZoneMapDao;
	}

	public IPositionZoneMapDao getPositionZoneMapDao() {
		return positionZoneMapDao;
	}

	public void setPositionZoneMapDao(IPositionZoneMapDao positionZoneMapDao) {
		this.positionZoneMapDao = positionZoneMapDao;
	}

	public IPulseZoneMapDao getPulseZoneMapDao() {
		return pulseZoneMapDao;
	}

	public void setPulseZoneMapDao(IPulseZoneMapDao pulseZoneMapDao) {
		this.pulseZoneMapDao = pulseZoneMapDao;
	}

	public Page<FenceBean> queryFenceByPage(Page<FenceBean> page) throws Exception {
		return fenceDao.queryFenceByPage(page);
	}

	public OperationUIPushImp getOperationUIPushImp() {
		return operationUIPushImp;
	}

	public void setOperationUIPushImp(OperationUIPushImp operationUIPushImp) {
		this.operationUIPushImp = operationUIPushImp;
	}

	public IZoneService getZoneManagerService() {
		return zoneManagerService;
	}

	public void setZoneManagerService(IZoneService zoneManagerService) {
		this.zoneManagerService = zoneManagerService;
	}

	public IOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(IOrganization organization) {
		this.organization = organization;
	}

}
