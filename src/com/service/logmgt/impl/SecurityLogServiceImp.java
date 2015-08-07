package com.service.logmgt.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.logmgt.ISecurityLogDao;
import com.entity.authmgt.Session;
import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.SecurityLog;
import com.entity.logmgt.SecurityLogCondition;
import com.service.logmgt.ISecurityLogService;
import com.util.alarmmgt.AlarmUtil;
import com.util.logmgt.CommandParser;

public class SecurityLogServiceImp implements ISecurityLogService{

	private ISecurityLogDao securityLogDao = null;

	private Log log = LogFactory.getLog(this.getClass());

	public void setSecurityLogDao(ISecurityLogDao securityLogDao){
		this.securityLogDao = securityLogDao;
	}
	
	/**
	 * 创建安全日志，即登录，注销日志
	 * @param name 下发的操作命令，即 command-info.xml的name属性，要求全局唯一
	 * @param operationObjects 操作对象
	 * @param opResult 操作结果 ，0：失败 ; 1 ：成功 ;
	 * @param opDetail 详细信息
	 */
	public void createSecurityLog(String name, String operationObjects,
			int opResult, String opDetail) {
		log.info("start createSecurityLog, name = " + name 
				+", operationObjects = " + operationObjects
				+", opResult = " + opResult + ", opDetail = "
				+ opDetail);		
		try{
			Session session = AlarmUtil.getLoginSession();
			log.info("createSecurityLog, session = " + session);
			if(null != session){
				SecurityLog securityLog = new SecurityLog();
				securityLog.setAccountId(session.getId());			
				securityLog.setHostIp(session.getLoginHostIp());
				securityLog.setHostName(session.getLoginHostName());			
				securityLog.setOrganizationId(session.getOrganizationId());
				securityLog.setOpTime(new Date());
				securityLog.setOpResult(opResult);
				securityLog.setSlsId(securityLogDao.getSecurityLogId());
				securityLog.setOpDetail(opDetail);			
				if(null != name){
					CommandInfo commandInfo = CommandParser.getInstance().getDetailByName(
							name);
					if(null != commandInfo){
						securityLog.setCommandId(commandInfo.getCommandId());
					}
				}
				log.info("createSecurityLog, securityLog = " + securityLog);
				securityLogDao.createSecurityLog(securityLog);	
				log.info("end createSecurityLog!");
			}	
		}catch(Exception e){
			log.error("createSecurityLog error!", e);
		}
	}

	public void deleteSecLogById(long slsId) {
		securityLogDao.deleteSecLogById(slsId);
	}

	@Override
	public int getSecurityLogId() {
		return securityLogDao.getSecurityLogId();
	}

/*	@Override
	public List<CommandInfo> getCommandInfos() {
		List<CommandInfo> cList = CommandParser.getInstance().getAllCommand();
		List<CommandInfo> securityCommandList = new ArrayList<CommandInfo>();
		for(CommandInfo c : cList){
			if(c.getCommandId() == 103050001 || c.getCommandId() == 103050002){
				securityCommandList.add(c);
			}
		}
		return securityCommandList;
	}*/

	@Override
	public int getSecLogCountByOrgsAndAccId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.getSecLogCountByOrgsAndAccId(securityLogCondition);
	}

	@Override
	public List querySecLogByOrgsAndAccId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.querySecLogByOrgsAndAccId(securityLogCondition);
	}

	@Override
	public int getSecLogCountByOrgId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.getSecLogCountByOrgId(securityLogCondition);
	}

	@Override
	public int getSecLogCountByAccId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.getSecLogCountByAccId(securityLogCondition);
	}

	@Override
	public List querySecLogByOrgId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.querySecLogByOrgId(securityLogCondition);
	}

	@Override
	public List querySecLogByAccId(SecurityLogCondition securityLogCondition) {
		return securityLogDao.querySecLogByAccId(securityLogCondition);
	}
	
}
