package com.service.linkagectl.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSON;
import com.dao.linkagectl.IAlarmDao;
import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.Organization;
import com.entity.linkagectl.AlarmResult;
import com.entity.linkagectl.UIMCSecurityAlarm;
import com.service.authmgt.IAccountManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.linkagectl.IGetSeAlarm;

public class GetSeAlarmImp implements IGetSeAlarm {
	
	private Map<String,String> warningLevelMap; 
	private Map<String,String> deviceTypeMap;
	private IOrganizationManagerService orgMngService;
	private IAlarmDao seAlarmDao;
	private IAccountManagerService accMgtService;
	private List<CommonBean> peopleList;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public Map<String,String> getWarningLevelMap() {
		return warningLevelMap;
	}
	public void setWarningLevelMap(Map<String,String> warningLevelMap) {
		this.warningLevelMap = warningLevelMap;
	}
	
	public Map<String,String> getDeviceTypeMap() {
		return deviceTypeMap;
	}
	public void setDeviceTypeMap(Map<String,String> deviceTypeMap) {
		this.deviceTypeMap = deviceTypeMap;
	}
	
	public IOrganizationManagerService getOrgMngService() {
		return orgMngService;
	}
	public void setOrgMngService(IOrganizationManagerService orgMngService) {
		this.orgMngService = orgMngService;
	}
	
	public IAlarmDao getSeAlarmDao() {
		return seAlarmDao;
	}

	public void setSeAlarmDao(IAlarmDao seAlarmDao) {
		this.seAlarmDao = seAlarmDao;
	}
	
	public IAccountManagerService getAccMgtService() {
		return accMgtService;
	}
	public void setAccMgtService(IAccountManagerService accMgtService) {
		this.accMgtService = accMgtService;
	}
	
	//orgLvl 0--总公司 1---分公司 2---管理处
	public List<UIMCSecurityAlarm>  findAllUIMcAlarmInfo(String orgID, String userID){
	
		List <UIMCSecurityAlarm> uiAlarmList;
		String orgResult;
		String devType;
		Integer severityLvl;
		
		try
		{
			if ((orgID == null) ||(userID == null) )
			{
				return null;
			}
			uiAlarmList = seAlarmDao.findallUIAlarm(orgID,userID);
		
			List<Organization> orgList = orgMngService.queryOrganizationsByAccountId(userID);
			Map<String, String> orgMap = new HashMap<String, String>();
			
			for(int j=0; j<orgList.size(); j++)
			{
				orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
			}
			
			for (int i = 0; i < uiAlarmList.size();i++)
			{
				UIMCSecurityAlarm _tempUIAlarm = uiAlarmList.get(i);
			
				//add warning level info 
				if(_tempUIAlarm.getIdevType() != null)
				{
					devType = deviceTypeMap.get(_tempUIAlarm.getIdevType().toString());
					_tempUIAlarm.setDevType(devType);
				}
			
				severityLvl = _tempUIAlarm.getAlarmLvl();
				if((null == severityLvl)||(severityLvl>4)||(severityLvl<1))
				{
					log.warn("GetSeAlarmImp get an warning,it's alarm level is outof range or it is not set!");
				}
				else
				{
					_tempUIAlarm.setSeverityLvl(warningLevelMap.get(severityLvl.toString()));
				}
			
								
				//添加 管理处的名称
				orgResult = orgMap.get(_tempUIAlarm.getMgtName());
				if (orgResult==null)
				{
					log.error("Department "+_tempUIAlarm.getMgtName() +" can't be found\n");
					_tempUIAlarm.setMgtName("管理处名称未知");
				}
				else 
				{
					_tempUIAlarm.setMgtName(orgResult);
				}
				//添加分工司的名称
				orgResult =  orgMap.get (_tempUIAlarm.getBranchName());
				if (orgResult==null)
				{
					log.error("Branch "+_tempUIAlarm.getBranchName() +" can't be found\n");
					_tempUIAlarm.setBranchName("分公司名称未知");
				}
				else 
				{
					_tempUIAlarm.setBranchName(orgResult);
				}
				
				/*//添加 管理处的名称
				orgResult = (String) orgMngService.getOrgNmByOrgId(_tempUIAlarm.getMgtName());
				if (orgResult==null)
				{
					log.error("Department "+_tempUIAlarm.getMgtName() +" can't be found\n");
					_tempUIAlarm.setMgtName("管理处名称未知");
				}
				else 
				{
					_tempUIAlarm.setMgtName(orgResult);
				}
				//添加分工司的名称
				orgResult =  (String) orgMngService.getOrgNmByOrgId(_tempUIAlarm.getBranchName());
				if (orgResult==null)
				{
					log.error("Department "+_tempUIAlarm.getBranchName() +" can't be found\n");
					_tempUIAlarm.setBranchName("分公司名称未知");
				}
				else 
				{
					_tempUIAlarm.setBranchName(orgResult);
				}*/
				
				Integer checkMethod = _tempUIAlarm.getCheckMethod();
				if(checkMethod == null)
				{
					_tempUIAlarm.setCheckMethodStr("");
				}
				else if (checkMethod.equals(1))
				{
					_tempUIAlarm.setCheckMethodStr("视频复核");
				}
				else if (checkMethod.equals(2))
				{
					_tempUIAlarm.setCheckMethodStr("现场复核");
				}
				
				Integer isReal = _tempUIAlarm.getIsReal();
				if(isReal == null)
				{
					_tempUIAlarm.setIsRealStr("");
				}
				else if (isReal.equals(1))
				{
					_tempUIAlarm.setIsRealStr("实警");
				}
				else if (isReal.equals(0))
				{
					_tempUIAlarm.setIsRealStr("虚警");
				}
				
				if( _tempUIAlarm.getPeopleID() != null && !_tempUIAlarm.getPeopleID().equals(" ") )
				{
					Account account = (Account)accMgtService.queryAccountByAccountId(_tempUIAlarm.getPeopleID());
					if(account != null)
					{
						_tempUIAlarm.setPeopleName(account.getUserName());
				
					}
				}
				
				if( _tempUIAlarm.getUserID() != null && !_tempUIAlarm.getUserID().equals(" "))
				{
					Account account = (Account)accMgtService.queryAccountByAccountId(_tempUIAlarm.getUserID());
					if(account != null)
					{
						_tempUIAlarm.setUserName(account.getUserName());
				
					}
				}
			}
			return uiAlarmList;
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
	public List<CommonBean> findAllOrgPeople(String orgID) throws Exception
    {
		try
		{
			if (orgID != null )
			{
				peopleList = accMgtService.getAccountIdAndUserNamesByOrgId(orgID);
			}
			else
			{
				return null;
			}
			return peopleList;
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
		catch(Exception e)
        {
            log.error(e.getMessage(),e);
            throw e;
        }
    }
	
	public int  getActiveAlarmCountByDevId(String devID)
	{
		try {
			return seAlarmDao.getActiveAlarmCountByDevId(devID);
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
	@Override
	public List<Map> getActiveAlarmCountByOrgid(String userID,String orgID) {
		// TODO Auto-generated method stub
		List <AlarmResult> alarmList = new ArrayList();
		List <AlarmResult> tmpList ;
		List <Map> resultList = new ArrayList();;
		try {
			List<Organization> orgList = orgMngService.queryOrganizationsByAccountId(userID);
			Map<String, String> orgMap = new HashMap<String, String>();
			//Map<String, String> resultMap = new HashMap<String, String>();
			
			for(int i=0; i<orgList.size(); i++)
			{
				orgMap.put(orgList.get(i).getOrgId(), orgList.get(i).getOrgNm());
			}
			
			if(orgMngService.isParentCompanyByAccountId(userID))//总公司
			{
				for(int i=0; i<orgList.size(); i++)
				{
					String orglev = orgList.get(i).getLev();
					String orgid = orgList.get(i).getOrgId();
					
					if(orglev.compareToIgnoreCase("0") == 0)//总公司添加总公司结果
					{
						Map<String, String> resultMap = new HashMap<String, String>();
						tmpList = seAlarmDao.getActiveAlarmCountByOrgid(orglev ,orgid);
						resultMap.clear();
						resultMap.put("orgid", orgid);
						resultMap.put("orgname", orgMap.get(orgid));
						if(tmpList != null && tmpList.size()>=1)
						{
							resultMap.put("seLevel1", tmpList.get(0).getLevel1Count().toString());//xiugai
							resultMap.put("seLevel2", tmpList.get(0).getLevel2Count().toString());
							resultMap.put("seLevel3", tmpList.get(0).getLevel3Count().toString());
							resultMap.put("seLevel4", tmpList.get(0).getLevel4Count().toString());
						}
						else
						{
							resultMap.put("seLevel1", "0");
							resultMap.put("seLevel2", "0");
							resultMap.put("seLevel3", "0");
							resultMap.put("seLevel4", "0");
						}
						resultList.add(resultMap);
						//return (List)sqlmapclienttemplate.queryForList("getActiveAlarmCount_zong");
					}
					else if (orglev.compareToIgnoreCase("1") == 0)//总公司添加分公司结果
					{
						tmpList = seAlarmDao.getActiveAlarmCountByOrgid(orglev ,orgid);
						
						if(tmpList != null && tmpList.size()>=1)
						{
							for(int j=0; j<tmpList.size(); j++)
							{
								Map<String, String> resultMap = new HashMap<String, String>();
								resultMap.clear();
								if(j == 0 && !tmpList.get(j).getOrgID().equalsIgnoreCase(orgid) )
								{
									Map<String, String> resultMap2 = new HashMap<String, String>();
									resultMap2.put("orgid", orgid);
									resultMap2.put("orgname", orgMap.get(orgid));
								
									resultMap2.put("seLevel1", "0");
									resultMap2.put("seLevel2", "0");
									resultMap2.put("seLevel3", "0");
									resultMap2.put("seLevel4", "0");
									
									resultList.add(resultMap2);
								}
								
								resultMap.put("orgid", tmpList.get(j).getOrgID());
								resultMap.put("orgname", orgMap.get(tmpList.get(j).getOrgID()));
								
								resultMap.put("seLevel1", tmpList.get(j).getLevel1Count().toString());
								resultMap.put("seLevel2", tmpList.get(j).getLevel2Count().toString());
								resultMap.put("seLevel3", tmpList.get(j).getLevel3Count().toString());
								resultMap.put("seLevel4", tmpList.get(j).getLevel4Count().toString());
													
								
								resultList.add(resultMap);
							}
						}
						else
						{
							Map<String, String> resultMap = new HashMap<String, String>();
							resultMap.clear();
							
							resultMap.put("orgid", orgid);
							resultMap.put("orgname", orgMap.get(orgid));
						
							resultMap.put("seLevel1", "0");
							resultMap.put("seLevel2", "0");
							resultMap.put("seLevel3", "0");
							resultMap.put("seLevel4", "0");
							
							resultList.add(resultMap);
						}
					}
					else
					{
						continue;
					}
				}
			}
			else if (orgMngService.isCompanyByAccountId(userID))//分公司
			{
				
				tmpList = seAlarmDao.getActiveAlarmCountByOrgid("1" ,orgID);
				
				if(tmpList != null && tmpList.size()>=1)
				{
					for(int j=0; j<tmpList.size(); j++)
					{
						Map<String, String> resultMap = new HashMap<String, String>();
						resultMap.clear();
						
						resultMap.put("orgid", tmpList.get(j).getOrgID());
						resultMap.put("orgname", orgMap.get(tmpList.get(j).getOrgID()));
					
						resultMap.put("seLevel1", tmpList.get(j).getLevel1Count().toString());
						resultMap.put("seLevel2", tmpList.get(j).getLevel2Count().toString());
						resultMap.put("seLevel3", tmpList.get(j).getLevel3Count().toString());
						resultMap.put("seLevel4", tmpList.get(j).getLevel4Count().toString());
						
						resultList.add(resultMap);
						//log.warn("resultMap-j-"+j+"-"+JSON.toJSONString(resultMap));
					}
				}
				else
				{
					Map<String, String> resultMap = new HashMap<String, String>();
					resultMap.clear();
					
					resultMap.put("orgid", orgID);
					resultMap.put("orgname", orgMap.get(orgID));
				
					resultMap.put("seLevel1", "0");
					resultMap.put("seLevel2", "0");
					resultMap.put("seLevel3", "0");
					resultMap.put("seLevel4", "0");
					
					resultList.add(resultMap);
				}
			}
			else if (orgMngService.isManagementByAccountId(userID))//管理处
			{
				Map<String, String> resultMap = new HashMap<String, String>();
				tmpList = seAlarmDao.getActiveAlarmCountByOrgid("2" ,orgID);
				resultMap.clear();
				
				resultMap.put("orgid", orgID);
				resultMap.put("orgname", orgMap.get(orgID));
			
				if(tmpList != null && tmpList.size()>=1)
				{
					resultMap.put("seLevel1", tmpList.get(0).getLevel1Count()==null? "0":tmpList.get(0).getLevel1Count().toString());
					resultMap.put("seLevel2", tmpList.get(0).getLevel2Count()==null? "0":tmpList.get(0).getLevel2Count().toString());
					resultMap.put("seLevel3", tmpList.get(0).getLevel3Count()==null? "0":tmpList.get(0).getLevel3Count().toString());
					resultMap.put("seLevel4", tmpList.get(0).getLevel4Count()==null? "0":tmpList.get(0).getLevel4Count().toString());
				}
				else
				{
					resultMap.put("seLevel1", "0");
					resultMap.put("seLevel2", "0");
					resultMap.put("seLevel3", "0");
					resultMap.put("seLevel4", "0");
				}
		
				resultList.add(resultMap);
				
				
			}
			else
			{
				return null;
			}
			
			/*if(resultList != null && resultList.size()>=1)
			{
				for(int j=0; j<resultList.size(); j++)
				{
					log.warn("resultList-j-"+j+"-"+JSON.toJSONString(resultList.get(j)));
				}
			}*/
			return resultList;
			
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

}

