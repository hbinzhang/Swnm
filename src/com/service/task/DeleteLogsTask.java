package com.service.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dao.logmgt.IOperationLogDao;
import com.dao.logmgt.ISecurityLogDao;
import com.entity.logmgt.OperationLogCondition;
import com.entity.logmgt.SecurityLogCondition;

public class DeleteLogsTask extends QuartzJobBean{
	
	private final static Log log = LogFactory.getLog(DeleteLogsTask.class);
	
	private IOperationLogDao operationLogDao;
	private ISecurityLogDao securityLogDao;
	

	@Override
	protected void executeInternal(JobExecutionContext arg)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 在这里定义任务
	 */
	public void start() {
		log.info("start deleteLogsTask!");		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		// 结束时间=当前时间
		Date endTime = cal.getTime();
		cal.add(Calendar.YEAR, -1);
		// 开始时间=前一年
		Date beginTime = cal.getTime();
		log.info("deleteLogsTask, beginTime = " + beginTime + ", endTime = "
				+ endTime);
		OperationLogCondition operLogCon = new OperationLogCondition();
		operLogCon.setBeginTime(beginTime);
		operLogCon.setEndTime(endTime);		
		SecurityLogCondition secLogCon = new SecurityLogCondition();
		secLogCon.setBeginTime(beginTime);
		secLogCon.setEndTime(endTime);		
		try{
			// 删除前一年的日志
			securityLogDao.deleteSecLogByTime(secLogCon);
			operationLogDao.deleteOperLogByTime(operLogCon);
		}catch(Exception e){
			log.error("deleteLogsTask error!",e);	
		}
		log.info("end deleteLogsTask!");		
	}


	public IOperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationDao(IOperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public ISecurityLogDao getSecurityLogDao() {
		return securityLogDao;
	}

	public void setSecurityLogDao(ISecurityLogDao securityLogDao) {
		this.securityLogDao = securityLogDao;
	}
	
}
