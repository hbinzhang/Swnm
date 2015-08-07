package com.service.linkagectl.impl;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.dao.linkagectl.IAlarmDao;
import com.dao.linkagectl.impl.SeAlarmDaoOracleImp;
import com.entity.linkagectl.Alarm;
import com.entity.linkagectl.SecurityAlarm;
import com.service.linkagectl.IAlarmManager;

public class SaveSeAlarmImp implements IAlarmManager {
	
	private IAlarmDao seAlarmDao=null;
	private Log log = LogFactory.getLog(this.getClass());
	
	public IAlarmDao getSeAlarmDao() {
		return seAlarmDao;
	}
	public void setSeAlarmDao(IAlarmDao seAlarmDao) {
		this.seAlarmDao = seAlarmDao;
	}
	public int Execute(Alarm alarm) {
		// TODO Auto-generated method stub
		try{
			log.info("SaveSeAlarmImp");
			if(alarm instanceof SecurityAlarm){
				int alarmid=save((SecurityAlarm)alarm);
				if(alarmid<0){
					log.error("alarmid <0 error");
					return 0;
				}
				alarm.setAlarmID(alarmid);
			}
			return 1;
		}
		
		catch(RuntimeException e)
        {
        	log.error(e.getMessage(),e);
        	throw e;
        }
	}
public int save(SecurityAlarm seAlarm){
	
	try{
		int id=seAlarmDao.save(seAlarm);
		return id;//保存安防告警
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
		  return -1;
	  }
	 catch(Exception e){
		  log.error(e.getMessage(),e);
		  return -1;
	 }
}

}
