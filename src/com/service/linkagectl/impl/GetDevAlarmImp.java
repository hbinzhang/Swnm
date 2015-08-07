package com.service.linkagectl.impl;

import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSON;
import com.dao.linkagectl.IAlarmDao;
import com.entity.alarmmgt.DeviceAlarm;
import com.service.authmgt.IOrganizationManagerService;
import com.service.linkagectl.IGetDevAlarm;


public class GetDevAlarmImp implements IGetDevAlarm {
	private IOrganizationManagerService orgMngService;
	private IAlarmDao devAlarmDao;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public IOrganizationManagerService getOrgMngService() {
		return orgMngService;
	}
	public void setOrgMngService(IOrganizationManagerService orgMngService) {
		this.orgMngService = orgMngService;
	}
	
	public IAlarmDao getDevAlarmDao() {
		return devAlarmDao;
	}

	public void setDevAlarmDao(IAlarmDao devAlarmDao) {
		this.devAlarmDao = devAlarmDao;
	}
	
	
	//orgLvl 0--总公司 1---分公司 2---管理处
	public String findUIMcDevAlarmInfo(Integer alarmID){
	
		String orgResult;
		
		try
		{
			if (alarmID == null)
			{
				return null;
			}
			
			DeviceAlarm deviceAlarm = 
					devAlarmDao.findUIDevAlarmByID(alarmID);
			if (deviceAlarm == null)
			{
				return null;
			}
			
			// 添加 管理处的名称
			orgResult = (String) orgMngService.getOrgNmByOrgId(
							deviceAlarm.getDepartmentId());
			if (orgResult == null) {
				log.error("Department " + deviceAlarm.getDepartmentId()
						+ " can't be found\n");
				deviceAlarm.setDepartmentName("管理处名称未知");
			} else {
				deviceAlarm.setDepartmentName(orgResult);
			}
			// 添加分工司的名称
			orgResult = (String) orgMngService.getOrgNmByOrgId(
					deviceAlarm.getBranchId());
			if (orgResult == null) {
				log.error("Branch " + deviceAlarm.getBranchId()
						+ " can't be found\n");
				deviceAlarm.setBranchName("分公司名称未知");
			} else {
				deviceAlarm.setBranchName(orgResult);
			}

			return  JSON.toJSONString(deviceAlarm);
		
		}
		catch(DataAccessException de)
        {
           log.error(de.getMessage(),de);
           throw de;
        }
        catch(RuntimeException re)
        {
            log.error(re.getMessage(),re);
            throw re;
        }
		catch(SQLException e)
		{
			 log.error(e.getMessage(),e);
			 e.printStackTrace();
		 }
        catch(Exception e)
        {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
		return null;
		
	}
	
	public int  getActiveAlarmCountByDevId(String devID)
	{
		try {
			return devAlarmDao.getActiveAlarmCountByDevId(devID);
		} 
		catch(DataAccessException de)
        {
           log.error(de.getMessage(),de);
           throw de;
        }
        catch(RuntimeException re)
        {
            log.error(re.getMessage(),re);
            throw re;
        }
		catch(SQLException e)
		{
			 log.error(e.getMessage(),e);
			 e.printStackTrace();
		 }
        catch(Exception e)
        {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
		return -1;
	}
}

