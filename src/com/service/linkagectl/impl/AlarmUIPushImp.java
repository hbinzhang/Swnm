package com.service.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.linkagectl.*;
import com.dao.linkagectl.IAlarmDao;
import com.dao.linkagectl.IZoneInfoDao;
import com.dao.linkagectl.impl.AlarmKnlgDaoOracleImp;
import com.service.linkagectl.IAlarmManager;
import com.service.linkagectl.IGetDevAlarm;
import com.service.linkagectl.IGetSeAlarm;

import common.justobjects.pushlet.core.Dispatcher;
import common.justobjects.pushlet.core.Event;
import com.alibaba.fastjson.JSON;

import com.service.authmgt.IAccountManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.impl.*;
import com.util.alarmmgt.AlarmUtil;

public class AlarmUIPushImp implements IAlarmManager {

	private AlarmKnowledge alarmKnowledge;
	private AlarmKnlgDaoOracleImp alarmKnowledgeDao;
	private UIMCSecurityAlarm uiMcSecAlarm;
	private UIGisSecurityAlarm uiGisSecAlarm;
	private ZoneInfo alarmZoneInfo;
	private IZoneInfoDao alarmZoneInfoDao;
	private Map<String, String> warningLevelMap;
	private Map<String, String> deviceTypeMap;
	private IOrganizationManagerService orgMngService;
	private List<CommonBean> orgResultList;
	private String orgResult;
	private IGetDevAlarm devAlarmService;
	private IAccountManagerService accMgtService;
	
	public static String centerID = null;
	public static String centerName = null;

	public String getCenterID() {
		try{
			if (centerID == null) {
				// lev=”0”为总公司；lev=”1”为分公司；lev=”2”为管理处
				orgResultList = orgMngService.getOrgIdAndOrgNmsByLev("0");
				if (orgResultList.size() == 0) {
					log.error("headoffice can't be found\n");
					return null;
				} else if (orgResultList.size() == 1) {
					centerName = orgResultList.get(0).getName();
					centerID = orgResultList.get(0).getId();
				} else if (orgResultList.size() > 1) {
					log.warn("service found more than one headoffices\n");
					centerName = orgResultList.get(0).getName();
					centerID = orgResultList.get(0).getId();
				}
			}
		}
		catch(DataAccessException e)
	    {
			log.error(e.getMessage(),e);
	        throw e;
	    }

	    catch(RuntimeException e)
	    {
	    	log.error(e.getMessage(),e);
	        throw e;
	    }
		catch(Exception e){
			  log.error(e.getMessage(),e);
			  e.printStackTrace();
			  return null;
		 }
		return centerID;
	}

	public String getCenterName() {
		try{
			if (centerName == null) {
				// lev=”0”为总公司；lev=”1”为分公司；lev=”2”为管理处
				orgResultList = orgMngService.getOrgIdAndOrgNmsByLev("0");
				if (orgResultList.size() == 0) {
					log.error("headoffice can't be found\n");
					return null;
				} else if (orgResultList.size() == 1) {
					centerName = orgResultList.get(0).getName();
					centerID = orgResultList.get(0).getId();
				} else if (orgResultList.size() > 1) {
					log.warn("service found more than one headoffices\n");
					centerName = orgResultList.get(0).getName();
					centerID = orgResultList.get(0).getId();
				}
			}
		}
		catch(DataAccessException e)
	    {
			log.error(e.getMessage(),e);
	        throw e;
	    }

	    catch(RuntimeException e)
	    {
	    	log.error(e.getMessage(),e);
	        throw e;
	    }
		catch(Exception e){
			  log.error(e.getMessage(),e);
			  e.printStackTrace();
			  return null;
		 }
		return centerName;
	}

	public Map<String, String> getWarningLevelMap() {
		return warningLevelMap;
	}

	public void setWarningLevelMap(Map<String, String> warningLevelMap) {
		this.warningLevelMap = warningLevelMap;
	}

	public Map<String, String> getDeviceTypeMap() {
		return deviceTypeMap;
	}

	public void setDeviceTypeMap(Map<String, String> deviceTypeMap) {
		this.deviceTypeMap = deviceTypeMap;
	}

	/*
	 * public AlarmKnowledge getAlarmKnowledge() { return alarmKnowledge; }
	 * public void setAlarmKnowledge(AlarmKnowledge alarmKnowledge) {
	 * this.alarmKnowledge = alarmKnowledge; }
	 */
	public AlarmKnlgDaoOracleImp getAlarmKnowledgeDao() {
		return alarmKnowledgeDao;
	}

	public void setAlarmKnowledgeDao(AlarmKnlgDaoOracleImp alarmKnowledgeDao) {
		this.alarmKnowledgeDao = alarmKnowledgeDao;
	}

	public UIMCSecurityAlarm getUiMcSecAlarm() {
		return uiMcSecAlarm;
	}

	public void setUiMcSecAlarm(UIMCSecurityAlarm uiMcSecAlarm) {
		this.uiMcSecAlarm = uiMcSecAlarm;
	}

	public IZoneInfoDao getAlarmZoneInfoDao() {
		return alarmZoneInfoDao;
	}

	public void setAlarmZoneInfoDao(IZoneInfoDao alarmZoneInfoDao) {
		this.alarmZoneInfoDao = alarmZoneInfoDao;
	}

	public IOrganizationManagerService getOrgMngService() {
		return orgMngService;
	}

	public void setOrgMngService(IOrganizationManagerService orgMngService) {
		this.orgMngService = orgMngService;
	}

	
	public IGetDevAlarm getDevAlarmService() {
		return devAlarmService;
	}

	public void setDevAlarmService(IGetDevAlarm devAlarmService) {
		this.devAlarmService = devAlarmService;
	}
	

	public IAccountManagerService getAccMgtService() {
		return accMgtService;
	}

	public void setAccMgtService(IAccountManagerService accMgtService) {
		this.accMgtService = accMgtService;
	}


	private Log log = LogFactory.getLog(this.getClass());

	public int Execute(Alarm alarm) {
		// TODO Auto-generated method stub

		System.out.println("AlarmUIPushImp");
		log.info("AlarmUIPushImp receive an alarm!\n");

		try{
			String result;
			String alarmType;
			String alarmOrgID;
			String alarmOperation = "add";
			
						
			if (alarm instanceof SecurityAlarm) {
				/*
				 * 推送到主控页面的安防告警,告警名"/warning/security/mc/"+alarmOrgID
				 * 所有属于这个orgID所有用户都会收到该告警
				 */
				/* SecurityAlarm if report equals 2 push to management Agence */
				if(alarm.getZoneID() != null )
				{
					alarmZoneInfo = (ZoneInfo) alarmZoneInfoDao.findZoneInfo(alarm.getZoneID());
					if ( alarmZoneInfo == null )
					{
						log.error("AlarmUIPushImp drop an SecurityAlarm that zoneInfo is not found.\n");
						return -1;
					}
				}
				else
				{
					log.error("AlarmUIPushImp drop an SecurityAlarm that zoneID is null.\n");
					return -1;
				}
					
				
				alarmType = "securityAlarm";
				alarmOrgID = getReportOrg((SecurityAlarm)alarm);
				if (alarmOrgID == "Error") {
					log.error("receive an undefied destination warning return error\n");
					return -1;
				}
				
				result = formUIMcSecAlarm((SecurityAlarm) alarm, uiMcSecAlarm);
				if (result != "OK") {
					log.error("generate an UIMcSecAlarm return error\n");
					return -1;
				}

				String alarmJson = JSON.toJSONString(uiMcSecAlarm);
				
				pullWarningEvent2MC(alarmOrgID,alarmOperation, alarmType, alarmJson);

				/* 推送到GIS页面的安防告警，在之前的信息上加上gis信息 */
				uiGisSecAlarm = new UIGisSecurityAlarm(uiMcSecAlarm);
				result = formUIGISSecAlarm((SecurityAlarm) alarm, uiGisSecAlarm);
				if (result != "OK") {
					log.error("generate an UIGISSecAlarm return error\n");
					return -1;
				}

				String alarmJsonGIS = JSON.toJSONString(uiGisSecAlarm);
				pullWarningEvent2GIS(alarmOrgID, alarmOperation, alarmType, alarmJsonGIS);
				
			}
			if (alarm instanceof DeviceAlarm) {
				/*
				 * 推送到主控页面的设备告警,告警名"/warning/dev/mc/"+alarmOrgID
				 * 所有属于这个orgID所有用户都会收到该告警
				 */
				alarmType = "deviceAlarm";
				if (alarm.getZoneID() != null)
				{
					alarmZoneInfo = (ZoneInfo) alarmZoneInfoDao.findZoneInfo(alarm.getZoneID());
					if ( alarmZoneInfo == null )
					{
						log.error("AlarmUIPushImp receive an dev alarm that zoneInfo is not found.\n");
					}
				}
				
				alarmOrgID = alarm.getDepartmentID();
				if (alarmOrgID == "Error") {
					log.error("receive an undefied destination warning return error\n");
					return -1;
				}
				
				result = formUIMcDevAlarm((DeviceAlarm) alarm, uiMcSecAlarm);
				if (result != "OK") {
					log.error("generate an dev UIMcSecAlarm return error\n");
					return -1;
				}

				//String devAlarmString = JSON.toJSONString(uiMcSecAlarm);
				String devAlarmString = devAlarmService.findUIMcDevAlarmInfo(alarm.getAlarmID());
				
				pullWarningEvent2MC(alarmOrgID, alarmOperation, alarmType, devAlarmString);

				/* 推送到GIS页面的安防告警，在之前的信息上加上gis信息 */
				uiGisSecAlarm = new UIGisSecurityAlarm(uiMcSecAlarm);
				result = formUIGISDevAlarm((DeviceAlarm) alarm, uiGisSecAlarm);
				if (result != "OK") {
					log.error("generate an UIGISSecAlarm return error\n");
					return -1;
				}

				String alarmJsonGIS = JSON.toJSONString(uiGisSecAlarm);//
				pullWarningEvent2GIS(alarmOrgID,alarmOperation, alarmType, alarmJsonGIS);
				
			}
			return 0;
		}
		catch(DataAccessException e)
		{
			log.error(e.getMessage(),e);
		    throw e;
		}
	    catch(RuntimeException e)
	    {
	    	log.error(e.getMessage(),e);
	        throw e;
	    }
		catch(Exception e){
			log.error(e.getMessage(),e);
			e.printStackTrace();
			return -1;
		}
		
	}
	
	// 将告警放入推送框架的推送队列
	// 推送到主控界面
	public int pullWarningEvent2MC(String orgID,String alarmOperation, String alarmType, String messageBody) {
		/*
		 * eventName maybe
		 * /warning/mc/orgID,/warning/gis/orgID,
		 * ---------------------------------------------------------/warning/dev/mc/orgID,/warning/dev/gis/orgID;orgID
		 * /operation/mc/orgID,/operation/gis/orgID
		 * 在数据库中定义，可以是管理处、分公司或者管理处的 orgID;
		 */
		
		String eventName = "/warning/mc/"+orgID;// +alarmOrgID;
		log.info("AlarmUIPushImp pull an alarm warning to MC! eventName is "+eventName+" alarmOperation is "+alarmOperation+" alarmType is "+alarmType);
		log.info("AlarmUIPushImp pull an alarm warning to MC! messageBody is "+messageBody);
		
		try {
			// 创建事件，并制定事件的主题
			Event warningEvent = Event.createDataEvent(eventName);
			// Date nowTime = new Date(System.currentTimeMillis());
			// SimpleDateFormat sdFormatter = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String curtime = sdFormatter.format(nowTime);
			// String data ="myevent" + buffer.toString() + curtime;
			//String data = messageBody;
			/*
			 * try { data=new String(data.getBytes("UTF-8"),"ISO-8859-1"); }
			 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
			 */
			warningEvent.setField("alarmType", alarmType);
			warningEvent.setField("alarmOperation", alarmOperation);
			warningEvent.setField("message", messageBody);
			
			if(Dispatcher.getInstance() != null)
			{
				Dispatcher.getInstance().multicast(warningEvent);
			}
			return 0;
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}

	}
	
	
	
	// 将告警放入推送框架的推送队列
		// 推送到界面
		public int pullWarningEvent2GIS(String orgID, String alarmOperation, String alarmType, String messageBody) {
			/*
			 * eventName maybe
			 * /warning/mc/orgID,/warning/gis/orgID,
			 * ---------------------------------------------------------/warning/dev/mc/orgID,/warning/dev/gis/orgID;orgID
			 * /operation/mc/orgID,/operation/gis/orgID
			 * 在数据库中定义，可以是管理处、分公司或者管理处的 orgID;
			 */
			//String eventNameGIS = "/warning/gis/"+orgID;// +alarmOrgID;
			String eventNameGIS = "/warning/gis/"+orgID;// +alarmOrgID;
			/*if(alarmType.equals("devAlarm"))
			{
				eventNameGIS = "/warning/dev/gis/"+orgID;
			}
			else if (alarmType.equals("securityAlarm"))
			{
				eventNameGIS = "/warning/security/gis/"+orgID;
			}*/
			if (alarmOperation.equals("del"))
			{
				//eventNameGIS = "/opration/warning/"+orgID;
				messageBody ="{alarmID:"+messageBody+"}";
			}
			log.info("AlarmUIPushImp pull an alarm to GIS!eventNameGIS is "+eventNameGIS);
			log.info("AlarmUIPushImp pull an alarm to GIS!messageBody is "+messageBody);
			try {
				// 创建事件，并制定事件的主题
				Event warningEvent = Event.createDataEvent(eventNameGIS);

				/*
				 * try { data=new String(data.getBytes("UTF-8"),"ISO-8859-1"); }
				 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
				 */
				warningEvent.setField("alarmType", alarmType);
				warningEvent.setField("alarmOperation", alarmOperation);
				warningEvent.setField("message", messageBody);
				if(Dispatcher.getInstance() != null)
				{
					Dispatcher.getInstance().multicast(warningEvent);
				}
				
				return 0;
			} catch (RuntimeException e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				throw e;
			}

		}

	// 获取告警上报组织编号
	private String getReportOrg(SecurityAlarm alarm) {
		/*
		 * alarm.getReport:1-告警推往管理处 2-告警推往分公司 3-告警推往总公司
		 */
		try{
			if (alarm.getReport() == 2) // warning will be pushed to management
										// Agence
			{
				return alarm.getDepartmentID().toString();
			} else if (alarm.getReport() == 1) // warning will be pushed to branch
			{
				return alarm.getBranchID().toString();
			} else if (alarm.getReport() == 0) // warning will be pushed to
												// zonggongsi
			{
				return this.getCenterID();
			} else {
				log.warn("AlarmUIPushImp receive an undefined warning!");
				return "Error";
			}
		}
		catch(RuntimeException e)
        {
        	log.error(e.getMessage(),e);
        	e.printStackTrace();
        	throw e;
        }
	}

	// 生成推往主控界面的告警
	private String formUIMcSecAlarm(SecurityAlarm alarm,
			UIMCSecurityAlarm uiMcSecAlarm) {

		String devType;
		Integer severityLvl;

		try {
			uiMcSecAlarm.setAlarmID(alarm.getAlarmID());
			uiMcSecAlarm.setAlarmTime(alarm.getAlarmTime());
			uiMcSecAlarm.setAlarmDuringTime(((SecurityAlarm) alarm)
					.getLastTime());
			uiMcSecAlarm.setCheckLevel(((SecurityAlarm) alarm).getCheckLevel());
			severityLvl =((SecurityAlarm) alarm).getCheckLevel();
			if ((null == severityLvl) || (severityLvl > 4)
					|| (severityLvl < 1)) {
				log.warn("AlarmUIPushImp receive an warning,it's alarm level is outof range.set to 1 forcely!");
				severityLvl = 1;
			}
			uiMcSecAlarm.setCheckLevelStr(warningLevelMap.get(severityLvl.toString()).toString());
			uiMcSecAlarm.setAlarmZoneID(alarm.getZoneID());
			if (alarmZoneInfo != null) {
				uiMcSecAlarm.setAlarmZoneName(alarmZoneInfo.getZoneName());
			}
			alarmKnowledge = (AlarmKnowledge) alarmKnowledgeDao
					.findAlarmKnowledge(alarm.getAlarmCode().toString());
			// alarmName = alarmKnowledge.getAlarmName();
			if (alarmKnowledge != null) {
				uiMcSecAlarm.setAlarmName(alarmKnowledge.getAlarmName());

				devType = deviceTypeMap.get(
						alarmKnowledge.getDeviceType().toString()).toString();
				uiMcSecAlarm.setDevType(devType);
				uiMcSecAlarm.setDevID(alarm.getDeviceID());
				// severityLvl = warningLevelMap.get(((SecurityAlarm)
				// alarm).getCheckLevel().toString()).toString();
				// use this ((SecurityAlarm) alarm).getCheckLevel() or
				// alarmKnowledge.getAlarmLevel().
				severityLvl = alarmKnowledge.getAlarmLevel();
				if ((null == severityLvl) || (severityLvl > 4) || (severityLvl < 1)) {
					log.warn("AlarmUIPushImp receive an warning,it's alarm level is outof range or it is not set!");
					
				}
				else
				{
					uiMcSecAlarm.setSeverityLvl(warningLevelMap.get(
						severityLvl.toString()).toString());
				}
				uiMcSecAlarm.setAlarmCode(alarmKnowledge.getAlarmCode());
				uiMcSecAlarm.setAlarmLvl(severityLvl);
				
			} else {
				log.warn("AlarmUIPushImp no alarmKnowledge has been found!");

				uiMcSecAlarm.setAlarmName("未知");

				uiMcSecAlarm.setDevType("未知");
				uiMcSecAlarm.setDevID("未知");
				uiMcSecAlarm.setSeverityLvl("未知");
				uiMcSecAlarm.setAlarmLvl(1);//默认值
			}
			uiMcSecAlarm.setAlarmCnt(((SecurityAlarm) alarm).getAlarmCount());

			// 添加 管理处的名称
			orgResult = (String) orgMngService.getOrgNmByOrgId(alarm
					.getDepartmentID());
			if (orgResult == null) {
				log.error("Department " + alarm.getDepartmentID()
						+ " can't be found\n");
				uiMcSecAlarm.setMgtName("管理处名称未知");
			} else {
				uiMcSecAlarm.setMgtName(orgResult);
			}
			// 添加分工司的名称
			orgResult = (String) orgMngService.getOrgNmByOrgId(alarm
					.getBranchID());
			if (orgResult == null) {
				log.error("Department " + alarm.getBranchID()
						+ " can't be found\n");
				uiMcSecAlarm.setBranchName("分公司名称未知");
			} else {
				uiMcSecAlarm.setBranchName(orgResult);
			}
			
			/* info 字段有可能携带更详细的信息，也可能是gis信息 */
			uiMcSecAlarm.setInfo(((SecurityAlarm) alarm).getInfo());
			
			Integer checkMothod = ((SecurityAlarm) alarm).getCheckMothod();
			uiMcSecAlarm.setCheckMethod(checkMothod);
			if(checkMothod == null)
			{
				uiMcSecAlarm.setCheckMethodStr("");
			}
			else if (checkMothod.equals(1))
			{
				uiMcSecAlarm.setCheckMethodStr("视频复核");
			}
			else if (checkMothod.equals(2))
			{
				uiMcSecAlarm.setCheckMethodStr("现场复核");
			}
			
			Integer isReal = ((SecurityAlarm) alarm).getIsReal();
			uiMcSecAlarm.setIsReal(isReal);
			if(isReal == null)
			{
				uiMcSecAlarm.setIsRealStr("");
			}
			else if (isReal.equals(1))
			{
				uiMcSecAlarm.setIsRealStr("实警");
			}
			else if (isReal.equals(0))
			{
				uiMcSecAlarm.setIsRealStr("虚警");
			}
			
			uiMcSecAlarm.setReason(((SecurityAlarm) alarm).getReason());
			uiMcSecAlarm.setPeopleID(((SecurityAlarm) alarm).getPeopleID());
			if( (((SecurityAlarm) alarm).getPeopleID() != null) && (!((SecurityAlarm) alarm).getPeopleID().equals(" "))  )
			{
				Account account = (Account)accMgtService.queryAccountByAccountId(((SecurityAlarm) alarm).getPeopleID());
				if(account != null)
				{
					uiMcSecAlarm.setPeopleName(account.getUserName());
			
				}
			}
			
			uiMcSecAlarm.setUserID(((SecurityAlarm) alarm).getUserID());
			if( (((SecurityAlarm) alarm).getUserID() != null ) && (!((SecurityAlarm) alarm).getUserID().equals(" ")) )
			{
				Account account = (Account)accMgtService.queryAccountByAccountId(((SecurityAlarm) alarm).getUserID());
				if(account != null)
				{
					uiMcSecAlarm.setUserName(account.getUserName());
			
				}
			}
			uiMcSecAlarm.setReport(((SecurityAlarm) alarm).getReport());
			uiMcSecAlarm.setVidioURL(((SecurityAlarm) alarm).getVidioURL());
			uiMcSecAlarm.setLeftVidioURL(((SecurityAlarm) alarm).getLeftVidioURL());
			uiMcSecAlarm.setRightVidioURL(((SecurityAlarm) alarm).getRightVidioURL());
			uiMcSecAlarm.setPictureURL(((SecurityAlarm) alarm).getPictureURL());
			uiMcSecAlarm.setDescription(((SecurityAlarm) alarm).getDescription());
			
		} catch (DataAccessException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return "ERROR";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return "ERROR";
		}
		return "OK";
	}

	// 生成推往GIS界面的告警
	private String formUIGISSecAlarm(SecurityAlarm alarm,
			UIGisSecurityAlarm uiGisSecAlarm) {

		try{
			// Tobe complish
			if (alarmZoneInfo != null) {
				uiGisSecAlarm.setAlarmLongitude(alarmZoneInfo.getStartLon());
				uiGisSecAlarm.setAlarmLatitude(alarmZoneInfo.getStartLat());
			} 
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		} 
		return "OK";
	}
	
	// 生成推往主控界面的告警
		private String formUIMcDevAlarm(DeviceAlarm alarm,
				UIMCSecurityAlarm uiMcSecAlarm) {

			String devType;
			Integer severityLvl;

			try {
				uiMcSecAlarm.setAlarmID(alarm.getAlarmID());
				uiMcSecAlarm.setAlarmTime(alarm.getAlarmTime());
				/*uiMcSecAlarm.setAlarmDuringTime(((SecurityAlarm) alarm)
						.getLastTime());*/
				uiMcSecAlarm.setAlarmZoneID(alarm.getZoneID());
				if (alarmZoneInfo != null) {
					uiMcSecAlarm.setAlarmZoneName(alarmZoneInfo.getZoneName());
				}
				alarmKnowledge = (AlarmKnowledge) alarmKnowledgeDao
						.findAlarmKnowledge(alarm.getAlarmCode().toString());
				// alarmName = alarmKnowledge.getAlarmName();
				if (alarmKnowledge != null) {
					uiMcSecAlarm.setAlarmName(alarmKnowledge.getAlarmName());

					devType = deviceTypeMap.get(
							alarmKnowledge.getDeviceType().toString()).toString();
					uiMcSecAlarm.setDevType(devType);
					uiMcSecAlarm.setDevID(alarm.getDeviceID());
					// severityLvl = warningLevelMap.get(((SecurityAlarm)
					// alarm).getCheckLevel().toString()).toString();
					// use this ((SecurityAlarm) alarm).getCheckLevel() or
					// alarmKnowledge.getAlarmLevel().
					severityLvl = alarmKnowledge.getAlarmLevel();
					if ((null == severityLvl) || (severityLvl > 4)|| (severityLvl < 1)) 
					{
						log.warn("AlarmUIPushImp receive an warning,it's alarm level is outof range or it is not set!");
					}
					else
					{
					uiMcSecAlarm.setSeverityLvl(warningLevelMap.get(
							severityLvl.toString()).toString());
					}
				} else {
					log.warn("AlarmUIPushImp no alarmKnowledge has been found!");

					uiMcSecAlarm.setAlarmName("未知");

					uiMcSecAlarm.setDevType("未知");
					uiMcSecAlarm.setDevID("未知");
					uiMcSecAlarm.setSeverityLvl("未知");
				}
				//uiMcSecAlarm.setAlarmCnt(((SecurityAlarm) alarm).getAlarmCount());

				// 添加 管理处的名称
				orgResult = (String) orgMngService.getOrgNmByOrgId(alarm
						.getDepartmentID());
				if (orgResult == null) {
					log.error("Department " + alarm.getDepartmentID()
							+ " can't be found\n");
					uiMcSecAlarm.setMgtName("管理处名称未知");
				} else {
					uiMcSecAlarm.setMgtName(orgResult);
				}
				// 添加分工司的名称
				orgResult = (String) orgMngService.getOrgNmByOrgId(alarm
						.getBranchID());
				if (orgResult == null) {
					log.error("Department " + alarm.getBranchID()
							+ " can't be found\n");
					uiMcSecAlarm.setBranchName("分公司名称未知");
				} else {
					uiMcSecAlarm.setBranchName(orgResult);
				}
				
				/* info 字段有可能携带更详细的信息，也可能是gis信息 */
				uiMcSecAlarm.setInfo( alarm.getInfo());
			} catch (DataAccessException e)
			{
				log.error(e.getMessage(), e);
				throw e;
			}
			catch (RuntimeException e)
			{
				log.error(e.getMessage(), e);
				throw e;
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				return "ERROR";
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				return "ERROR";
			}
			return "OK";
		}

		// 生成推往GIS界面的告警
		private String formUIGISDevAlarm(DeviceAlarm alarm,
				UIGisSecurityAlarm uiGisSecAlarm) {

			try{
				// Tobe complish
				if (alarmZoneInfo != null) {
					uiGisSecAlarm.setAlarmLongitude(alarmZoneInfo.getStartLon());
					uiGisSecAlarm.setAlarmLatitude(alarmZoneInfo.getStartLat());
				} 
			}
			catch (RuntimeException e)
			{
				log.error(e.getMessage(), e);
				throw e;
			} 
			return "OK";
		}
}
