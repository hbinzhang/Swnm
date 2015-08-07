package com.service.linkagectl.impl;

import com.dao.linkagectl.IIpcZoneMapDao;
import java.sql.SQLException;

import com.entity.linkagectl.Alarm;
import com.service.linkagectl.IAlarmManager;
import com.service.videomonitor.PresetService;
import com.entity.linkagectl.IpcZoneMap;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class VidioLinkManagerImp implements IAlarmManager {
	
	private IIpcZoneMapDao ipcZoneMapDao=null;
	private List<IpcZoneMap> listIpcZone=null;
	private Log log = LogFactory.getLog(this.getClass());
	private PresetService presetService;
	
	public PresetService getPresetService() {
		return presetService;
	}
	public void setPresetService(PresetService presetService) {
		this.presetService = presetService;
	}
	public int Execute(Alarm alarm)  {
		// TODO Auto-generated method stub
		try{
			log.info("VidioLinkManagerImp");
			int re=ControlIPC(alarm.getZoneID());
			if(re==0){
				log.error("ControlIPC error");
			}			
				
			return re;
		}
		catch(RuntimeException e)
        {
        	log.error(e.getMessage(),e);
        	throw e;
        }
		
	}
	@SuppressWarnings("unchecked")
	public int ControlIPC(int ZoneID)  { //根据防区查找摄像头,并控制摄像头转到预置位
		try{		
			setListIpcZone(ipcZoneMapDao.findall(ZoneID));
			if(listIpcZone.size()>0){    	  
				Iterator<IpcZoneMap> iter = listIpcZone.iterator();
		      			
				while(iter.hasNext())
					{
						IpcZoneMap ipcZoneMap=iter.next();
						log.info(ipcZoneMap.getIpcId());//	
						presetService.gotoPtzPreset(ipcZoneMap.getIpcId(),ipcZoneMap.getPoint());//调用视频接口
					}
			}
			listIpcZone=null;		
			return 1;  
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
		  catch(SQLException e){
			  log.error(e.getMessage(),e);
			  return 0;
		  }
		  catch(Exception e){
			  log.error(e.getMessage(),e);
			  return 0;
		 }		 
	}
                 
	public IIpcZoneMapDao getIpcZoneMapDao() {
		return ipcZoneMapDao;
	}
	public void setIpcZoneMapDao(IIpcZoneMapDao ipcZoneMapDao) {
		this.ipcZoneMapDao = ipcZoneMapDao;
	}
	
	public List<IpcZoneMap> getListIpcZone() {
		return listIpcZone;
	}
	public void setListIpcZone(List<IpcZoneMap> listIpcZone) {
		this.listIpcZone = listIpcZone;
	}
	
}
