package com.actions.videomonitor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.BaseAction;
import com.service.zone.IIpcZoneMapService;
import common.page.AjaxObject;

public class IpcQueryPointAction extends BaseAction {

	private final static Log log = LogFactory.getLog(IpcQueryPointAction.class);
	private String ipcID;
	private AjaxObject ajaxObject;
	private IIpcZoneMapService ipcZoneMapService;

	public String execute() {
		
		try {
			List<Integer> points = ipcZoneMapService.getPointByIpcId(ipcID);
			if(points.size()>0){
				ajaxObject=new AjaxObject(1,points);
				return SUCCESS;
			}else{
				ajaxObject=new AjaxObject(0,"查询有误");
				return ERROR;
			}
		} catch (Exception e) {
			
			log.fatal("IpcQueryPointAction execute failed:"+e.getMessage());
			ajaxObject=new AjaxObject(0,"查询有误");
			return ERROR;
		}
		
		
	}

	/******************************* setter/getter *********************************/

	public String getIpcID() {
		return ipcID;
	}

	public void setIpcID(String ipcID) {
		this.ipcID = ipcID;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public IIpcZoneMapService getIpcZoneMapService() {
		return ipcZoneMapService;
	}

	public void setIpcZoneMapService(IIpcZoneMapService ipcZoneMapService) {
		this.ipcZoneMapService = ipcZoneMapService;
	}

}
