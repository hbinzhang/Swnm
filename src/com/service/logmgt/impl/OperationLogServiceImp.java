package com.service.logmgt.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.logmgt.IOperationLogDao;
import com.entity.authmgt.Session;
import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmUtil;
import com.util.logmgt.CommandParser;

public class OperationLogServiceImp implements IOperationLogService{
	
	private IOperationLogDao operationLogDao = null;
	
	private Log log = LogFactory.getLog(this.getClass());


	public void setOperationLogDao(IOperationLogDao operationLogDao){
		this.operationLogDao = operationLogDao;
	}
	
	/**
	 * 创建操作日志
	 * @param name 下发的操作命令，即 command-info.xml的name属性，要求全局唯一
	 * @param operationObjects 操作对象
	 * @param opResult 操作结果 ，0：失败 ; 1 ：成功 ;  2：部分失败，针对批量操作; 3：正在执行，针对异步操作 
	 * @param opDetail 详细信息
	 */
	public void createOperationLog(String name, String operationObjects,
			int opResult, String opDetail) {
		log.info("start createOperationLog, methodname = " + name 
				+", operationObjects = " + operationObjects
				+", opResult = " + opResult + ", opDetail = "
				+ opDetail);
		try{
			Session session = AlarmUtil.getLoginSession();
			/*Session session = new Session();
			session.setId("aaa");
			session.setLoginHostIp("10.3.17.88");
			session.setLoginHostName("test");
			session.setOrganizationId("1");*/
			log.info("createOperationLog, session = " + session);
			if(null != session){
				OperationLog operationLog = new OperationLog();
				operationLog.setAccountId(session.getId());			
				operationLog.setHostIp(session.getLoginHostIp());
				operationLog.setHostName(session.getLoginHostName());			
				operationLog.setOrganizationId(session.getOrganizationId());
				operationLog.setOpTime(new Date());
				operationLog.setOpResult(opResult);
				operationLog.setOperationObjects(operationObjects);
				operationLog.setOlsId(operationLogDao.getOperationLogId());
				operationLog.setOpDetail(opDetail);			
				if(null != name){
					CommandInfo commandInfo = CommandParser.getInstance().getDetailByName(
							name);
					if(null != commandInfo){
						operationLog.setModuleId(commandInfo.getModuleId());
						operationLog.setCommandId(commandInfo.getCommandId());
					}
				}
				log.info("createOperationLog, operationLog = " + operationLog);
				operationLogDao.createOperationLog(operationLog);	
				log.info("end createOperationLog!");
			}
		}catch(Exception e){
			log.error("createOperationLog error!", e);
		}	
	}

	/**
	 * 根据操作日志主键(id)更新操作结果，结束时间，详细信息
	 */
	public void updateOperationLog(OperationLog operationLog) {
		operationLogDao.updateOperationLog(operationLog);		
	}
	
/*	*//**
	 * 获取所有模块id，模块名称的map
	 * @return map
	 *//*
	public Map<Integer, String> getModules(){
		return ModuleAndObjectTypeConf.getInstance().getModuleMap();
	}
	
	*//**
	 * 获取所有操作项信息
	 * @return List<CommandInfo>
	 *//*
	public List<CommandInfo> getCommandInfos(){
		List<CommandInfo> cList = CommandParser.getInstance().getAllCommand();
		List<CommandInfo> rList =  new ArrayList<CommandInfo>();
		for(CommandInfo c : cList){
			if(c.getCommandId() == 103050001 || c.getCommandId() == 103050002){
				rList.add(c);
			}
		}
		cList.removeAll(rList);
		return cList;	
	}*/

	@Override
	public int getOperationLogId() {
		return operationLogDao.getOperationLogId();
	}

	@Override
	public int getOperLogCountByOrgId(OperationLogCondition operationLogCondition) {
		return operationLogDao.getOperLogCountByOrgId(operationLogCondition);
	}

	@Override
	public List queryOperLogByOrgId(OperationLogCondition operationLogCondition) {
		return operationLogDao.queryOperLogByOrgId(operationLogCondition);
	}

	@Override
	public int getOperLogCountByAccId(OperationLogCondition operationLogCondition) {
		return operationLogDao.getOperLogCountByAccId(operationLogCondition);
	}

	@Override
	public List queryOperLogByAccId(OperationLogCondition operationLogCondition) {
		return operationLogDao.queryOperLogByAccId(operationLogCondition);
	}

	@Override
	public int getOperLogCountByOrgsAndAccId(OperationLogCondition operationLogCondition) {
		return operationLogDao.getOperLogCountByOrgsAndAccId(operationLogCondition);
	}

	@Override
	public List queryOperLogByOrgsAndAccId(
			OperationLogCondition operationLogCondition) {
		return operationLogDao.queryOperLogByOrgsAndAccId(operationLogCondition);
	}

	@Override
	public void deleteOperLogById(long olsId) {
		operationLogDao.deleteOperLogById(olsId);	
	}
	
}
