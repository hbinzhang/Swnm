package com.service.linkagectl.impl;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;


import com.dao.linkagectl.IAlarmDao;
import com.dao.linkagectl.impl.SeAlarmDaoOracleImp;
import com.entity.linkagectl.Alarm;
import com.entity.linkagectl.DeviceAlarm;
import com.entity.linkagectl.SecurityAlarm;
import com.service.linkagectl.IAlarmManager;

public class SaveDeAlarmImp implements IAlarmManager {
	
	private IAlarmDao deAlarmDao=null;
	private Log log = LogFactory.getLog(this.getClass());
	
	public IAlarmDao getDeAlarmDao() {
		return deAlarmDao;
	}
	public void setDeAlarmDao(IAlarmDao deAlarmDao) {
		this.deAlarmDao = deAlarmDao;
	}
	public int Execute(Alarm alarm) {
		// TODO Auto-generated method stub
		try{
			log.info("SaveDeAlarmImp");
			if(alarm instanceof DeviceAlarm){
				int alarmid= save((DeviceAlarm)alarm);
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
public int save(DeviceAlarm deAlarm){
	
	try{ 
		int id=deAlarmDao.save(deAlarm);
		return id;//保存设备告警
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
