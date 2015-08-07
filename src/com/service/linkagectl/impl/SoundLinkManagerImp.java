package com.service.linkagectl.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;


import com.dao.linkagectl.ISoundSetDao;
import com.dao.linkagectl.ISoundZoneMapDao;
import com.entity.linkagectl.Alarm;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundSet;
import com.entity.linkagectl.SoundZoneMap;

import com.service.linkagectl.IAlarmManager;
import com.service.sounddev.thirdsection.SoundDevServProvider;

public class SoundLinkManagerImp implements IAlarmManager {

	private ISoundZoneMapDao soundZoneMapDao=null;
	private ISoundSetDao soundSetDao=null;
	public ISoundSetDao getSoundSetDao() {
		return soundSetDao;
	}
	public void setSoundSetDao(ISoundSetDao soundSetDao) {
		this.soundSetDao = soundSetDao;
	}
	private List<String> listSoundID=null;
	
	
	public ISoundZoneMapDao getSoundZoneMapDao() {
		return soundZoneMapDao;
	}
	public void setSoundZoneMapDao(ISoundZoneMapDao soundZoneMapDao) {
		this.soundZoneMapDao = soundZoneMapDao;
	}
	
	public List<String> getListSoundID() {
		return listSoundID;
	}
	public void setListSoundID(List<String> listSoundID) {
		this.listSoundID = listSoundID;
	}
	private Log log = LogFactory.getLog(this.getClass());
	public int Execute(Alarm alarm) {
		// TODO Auto-generated method stub
		try{
			log.info("SoundLinkManagerImp");
			int re=ControlSound(alarm);
			if(re==0){
				log.error("ControlSound error");
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
	public int ControlSound(Alarm alarm){ // //根据防区查找喇叭列表，播放声音
		try{		
			List<String> list =soundZoneMapDao.findallSoundID(alarm.getZoneID());
			setListSoundID(list);
			if(listSoundID.size()>0){    	  
					
				SoundSet soundSet=(SoundSet)soundSetDao.findSoundSetFromAlarmCode(alarm.getAlarmCode());
				if(soundSet==null){
					log.error("get soundSet error");
					return 0;
				}
				log.info(soundSet.getPath());
				SoundDevServProvider.getService().devPlay(listSoundID,soundSet.getPath(),1);//调用视频接口
				
				Iterator<String> iter = listSoundID.iterator();
      			while(iter.hasNext())
					{
      					String soundID=iter.next();
						log.info(soundID);//	
						
					}
			}
			listSoundID=null;		
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
			  e.printStackTrace();
			  return 0;
		  }
		 catch(Exception e){
			  log.error(e.getMessage(),e);
			  return 0;
		 }		
		
	}
	
                           
}
