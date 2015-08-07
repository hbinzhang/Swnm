package com.service.alarmmgt.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.alarmmgt.IAlarmQueryDao;
import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;
import com.entity.authmgt.Session;
import com.oracle.vmm.client.provider.ovm22.ws.sps.HashMap;
import com.service.alarmmgt.IAlarmQueryService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class AlarmQueryServiceImp implements IAlarmQueryService{

	private IAlarmQueryDao alarmQueryDao = null;
	private Log log = LogFactory.getLog(this.getClass());


	public void setAlarmQueryDao(IAlarmQueryDao alarmQueryDao) {
		this.alarmQueryDao = alarmQueryDao;
	}
	
	public List querySecurityAlarm(AlarmQueryCondition alarmQueryCondition) {
		return alarmQueryDao.querySecurityAlarm(alarmQueryCondition);
	}

	public List queryDeviceAlarm(AlarmQueryCondition alarmQueryCondition) {
		return alarmQueryDao.queryDeviceAlarm(alarmQueryCondition);
	}

	public int getSecurityAlarmCount(AlarmQueryCondition alarmQueryCondition) {
		return alarmQueryDao.getSecurityAlarmCount(alarmQueryCondition);
	}

	public int getDeviceAlarmCount(AlarmQueryCondition alarmQueryCondition) {
		return alarmQueryDao.getDeviceAlarmCount(alarmQueryCondition);
	}

	public List queryDeviceAlarmByStatus(DeviceAlarm deviceAlarm) {
		return alarmQueryDao.queryDeviceAlarmByStatus(deviceAlarm);
	}

	@Override
	public void updateDeviceAlarm(DeviceAlarm deviceAlarm) {
		alarmQueryDao.updateDeviceAlarm(deviceAlarm);	
	}

	@Override
	public List queryZoneByDepartId(String departmentId) {
		return alarmQueryDao.queryZoneByDepartId(departmentId);
	}

	@Override
	public Object getZoneNameById(int zoneId) {
		return alarmQueryDao.getZoneNameById(zoneId);
	}

	/**
	 * 查询未处理的安防告警，设备告警数量
	 * 如果用户是总公司用户，查询所有安防告警，设备告警数量
	 * 如果用户是分公司用户，查询分公司下的安防告警，设备告警数量
	 * 如果用户是管理处用户，查询管理处下的安防告警，设备告警数量
	 * list[0] 安防告警数量
	 * list[1] 设备告警数量
	 */
	public List monitorAlarmCount() {
		List<Integer> monitorAlarmCountList = new ArrayList<Integer>();
		try{
			Session session = AlarmUtil.getLoginSession();
			log.info("monitorAlarmCount, session = " + session);
			if(null != session){		
				String organizationId = session.getOrganizationId();
				String userLevel = session.getLev();
				if(null != userLevel){
					/*String organizationId = "1";
					String userLevel = AlarmConstants.USER_BRANCH;*/
					DeviceAlarm dAlarm = new DeviceAlarm();
					SecurityAlarm sAlarm = new SecurityAlarm();
					sAlarm.setAlarmStatus(AlarmConstants.STATUS_NOT_HANDLED);
					dAlarm.setAlarmStatus(AlarmConstants.STATUS_NOT_HANDLED);
					if(!userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						if(userLevel.equals(AlarmConstants.USER_BRANCH)){
							sAlarm.setBranchId(organizationId);				
							sAlarm.setReport(Integer.parseInt(AlarmConstants.USER_BRANCH));
							dAlarm.setBranchId(organizationId);	
						}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
							sAlarm.setReport(Integer.parseInt(AlarmConstants.USER_DEPARTMENT));
							sAlarm.setDepartmentId(organizationId);				
							dAlarm.setDepartmentId(organizationId);			
						}else{
							return monitorAlarmCountList;
						}	
					}else {
						sAlarm.setReport(Integer.parseInt(AlarmConstants.USER_HEADQUARTERS));
					}
					log.info("monitorAlarmCount, userLevel = " + userLevel + ", dAlarm = "
							+ dAlarm + ", sAlarm = " + sAlarm);
					int securityAlarmCount = alarmQueryDao.monitorSecurityAlarmCount(
							sAlarm);
					int deviceAlarmCount = alarmQueryDao.monitorDeviceAlarmCount(
							dAlarm);
					log.info("monitorAlarmCount, securityAlarmCount = " + securityAlarmCount + 
							", deviceAlarmCount = " + deviceAlarmCount );
					monitorAlarmCountList.add(securityAlarmCount);
					monitorAlarmCountList.add(deviceAlarmCount);
				}				
			}
		}catch(Exception e){
			log.error("monitorAlarmCount error!", e);
		}	
		return monitorAlarmCountList;
	}

	@Override
	public Object querySecAlarmById(int alarmId) {
		return alarmQueryDao.querySecAlarmById(alarmId);
	}

	@Override
	public List queryZoneInfos() {
		return alarmQueryDao.queryZoneInfos();
	}

	@Override
	public Object queryDeviceAlarmById(int alarmId) {
		return alarmQueryDao.queryDeviceAlarmById(alarmId);
	}

	@Override
	public List queryBranchDevAlarmByStatus(DeviceAlarm dAlarm) {
		return alarmQueryDao.queryBranchDevAlarmByStatus(dAlarm);
	}

	@Override
	public List queryDepDevAlarmByStatus(DeviceAlarm dAlarm) {
		return alarmQueryDao.queryDepDevAlarmByStatus(dAlarm);
	}

	@Override
	public List queryCompDevAlarmByStatus(DeviceAlarm dAlarm) {
		return alarmQueryDao.queryCompDevAlarmByStatus(dAlarm);
	}
			
	public List statDevAlarmCountUnHandled(String level, String orgId){
		log.info("statDevAlarmCountUnHandled, level = " + level + 
				", orgId = " + orgId );
		List countList = null;
	    if(level.equals(AlarmConstants.USER_BRANCH)){
	    	countList =  alarmQueryDao.statDAlarmCountUnHandled4Branch(orgId);
		}else if(level.equals(AlarmConstants.USER_DEPARTMENT)){
			countList =  alarmQueryDao.statDAlarmCountUnHandled4Dep(orgId);
		}else{
			log.info("statDevAlarmCountUnHandled, level is wrong!");
		}
		log.info("end statDevAlarmCountUnHandled! countList = " + countList);
		return countList;
	}

}
