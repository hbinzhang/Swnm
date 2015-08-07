package com.dao.linkagectl.impl;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.IAlarmDao;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.linkagectl.AlarmResult;

import com.service.authmgt.IOrganizationManagerService;

public class SeAlarmDaoOracleImp implements IAlarmDao {
	
	private IOrganizationManagerService orgMngService;
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	
	

	public IOrganizationManagerService getOrgMngService() {
		return orgMngService;
	}

	public void setOrgMngService(IOrganizationManagerService orgMngService) {
		this.orgMngService = orgMngService;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteSeAlarm",o.toString());
		return true;
	}

	public List findall() throws SQLException {
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllSeAlarm",null);
	}

	public Object findAlarm(Object o) throws SQLException {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getSeAlarm",o.toString());
	}

	public int save(Object o) throws SQLException {
		// TODO Auto-generated method stub
		
		int id =(Integer )sqlmapclienttemplate.insert("insertSeAlarm",o);
		return id;
	}

	public boolean update(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateSeAlarm",o);
		return true;
	}
	
	public boolean updateSimple(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateSeAlarmSimple",o);
		return true;
	}
	
	public List findallUIAlarm(String orgID,String userID) throws SQLException
	{
		if(orgMngService.isParentCompanyByAccountId(userID))//总公司
		{
			return (List)sqlmapclienttemplate.queryForList("getAllUISeAlarm_zong");
		}
		else if (orgMngService.isCompanyByAccountId(userID))//分公司
		{
			return (List)sqlmapclienttemplate.queryForList("getAllUISeAlarm_com",orgID);
		}
		else if (orgMngService.isManagementByAccountId(userID))//管理处
		{
			return (List)sqlmapclienttemplate.queryForList("getAllUISeAlarm_mgt",orgID);
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public DeviceAlarm findUIDevAlarmByID(Integer alarmID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getActiveAlarmCountByDevId(String devID) throws SQLException
	{
		return (Integer) sqlmapclienttemplate.queryForObject(

				"getActiveSecurityAlarmCount", devID);
	}
	
	public List<AlarmResult> getActiveAlarmCountByOrgid(String orgLev,String orgID) throws SQLException
	{
		if(orgLev.compareToIgnoreCase("0") == 0)//总公司
		{
			//List <Map> zongList
			return (List)sqlmapclienttemplate.queryForList("getActiveAlarmCount_zong");
		}
		else if (orgLev.compareToIgnoreCase("1") == 0)//分公司
		{
			return (List)sqlmapclienttemplate.queryForList("getActiveAlarmCount_com",orgID);
		}
		else if (orgLev.compareToIgnoreCase("2") == 0)//管理处
		{
			return (List)sqlmapclienttemplate.queryForList("getActiveAlarmCount_mgt",orgID);
		}
		else
		{
			return null;
		}
	}
}
