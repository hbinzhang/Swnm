package com.actions.alarmmgt;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmQueryService;
import com.util.alarmmgt.AlarmConstants;
import common.page.AjaxObject;

public class AlarmCountQueryAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5179121914664030665L;

	private List<Integer> monitorAlarmCount;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private IAlarmQueryService alarmQueryService;
	
	private AjaxObject ajaxObject;
	
	public String queryAlarmCount()
	{
		try{
			monitorAlarmCount = (List<Integer>)alarmQueryService.monitorAlarmCount();
			ajaxObject = new AjaxObject(AlarmConstants.RESULT_SUC,monitorAlarmCount);
			return "success";
		}catch(Exception e){
			log.error("queryAlarmCount error!",e);
			ajaxObject = new AjaxObject(AlarmConstants.RESULT_FAIL);
			return "error";
		}
	}
	
	public void setMonitorAlarmCount(List<Integer> monitorAlarmCount) {
		this.monitorAlarmCount = monitorAlarmCount;
	}
	
	public List<Integer> getMonitorAlarmCount() {
		return monitorAlarmCount;
	}
	
	public void setAlarmQueryService(IAlarmQueryService alarmQueryService) {
		this.alarmQueryService = alarmQueryService;
	}
	
	public IAlarmQueryService getAlarmQueryService() {
		return alarmQueryService;
	}
	
	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}
}
