package com.service.linkagectl.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.service.linkagectl.IGetDevAlarm;
import com.service.linkagectl.IGetSeAlarm;

import common.justobjects.pushlet.core.Dispatcher;
import common.justobjects.pushlet.core.Event;

public class OperationUIPushImp {

	//private String orgResult;
	private Log log = LogFactory.getLog(this.getClass());
	private IGetDevAlarm devAlarmService;
	private IGetSeAlarm secAlarmService;
	
	
	
	public IGetDevAlarm getDevAlarmService() {
		return devAlarmService;
	}
	public void setDevAlarmService(IGetDevAlarm devAlarmService) {
		this.devAlarmService = devAlarmService;
	}
	public IGetSeAlarm getSecAlarmService() {
		return secAlarmService;
	}
	public void setSecAlarmService(IGetSeAlarm secAlarmService) {
		this.secAlarmService = secAlarmService;
	}
	// 将操作放入推送框架的推送队列
	// 推送到主控界面
	public int pullOperationEvent2MC(String orgID,String devOperation, String devType, String messageBody) {
		/*
		 * eventName maybe
		 * /warning/mc/orgID,/warning/gis/orgID,
		 * /operation/mc/orgID,/operation/gis/orgID
		 * 在数据库中定义，可以是管理处、分公司或者管理处的 orgID;
		 */
		log.info("operation pull an event to MC!\n");
		String eventName = "/operation/mc/"+orgID;
		
		
		try {
			// 创建事件，并制定事件的主题
			Event warningEvent = Event.createDataEvent(eventName);
			
			warningEvent.setField("devType", devType);
			warningEvent.setField("devOperation", devOperation);
			warningEvent.setField("message", messageBody);
			log.info("operation pull an event to MC! eventName is " +eventName+" devType is "+devType
					+" devOperation is "+devOperation+ " messageBody is "+messageBody);
			if(Dispatcher.getInstance() != null)
			{
				Dispatcher.getInstance().multicast(warningEvent);
			}
			log.info("operation pull an event to MC! return 0\n");
			return 0;
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}

	}
	// 将操作放入推送框架的推送队列
		// 推送到GIS界面
		public int pullOperationEvent2GIS(String orgID, String devOperation, String devType, String messageBody) {
			/*
			 * eventName maybe
			 * /warning/mc/orgID,/warning/gis/orgID,
			 * /operation/mc/orgID,/operation/gis/orgID
			 * 在数据库中定义，可以是管理处、分公司或者管理处的 orgID;
			 */
			String eventNameGIS = "/operation/gis/"+orgID;// +alarmOrgID;

			
			log.info("opratiom pull an event to GIS!\n");
			log.info("operation pull an event to GIS! eventNameGIS is " +eventNameGIS +" devType is "+devType
					+" devOperation is "+devOperation+ " messageBody is "+messageBody);
			
			try {
				// 创建事件，并制定事件的主题
				Event warningEvent = Event.createDataEvent(eventNameGIS);

				/*
				 * try { data=new String(data.getBytes("UTF-8"),"ISO-8859-1"); }
				 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
				 */
				warningEvent.setField("devType", devType);
				warningEvent.setField("devOperation", devOperation);
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

		public boolean devHasActiveAlarms(String devID)
		{
			try {
				int Alarmcount;
				Alarmcount = devAlarmService.getActiveAlarmCountByDevId(devID);
				if (Alarmcount > 0)
				{
					return true;
				}
				Alarmcount = secAlarmService.getActiveAlarmCountByDevId(devID);
				if (Alarmcount > 0)
				{
					return true;
				}
				return false;
			} catch (RuntimeException e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
				throw e;
			}
		}
}
