package com.dao.logmgt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;

public interface IOperationLogDao {

	public void createOperationLog(OperationLog operationLog); 

	public void updateOperationLog(OperationLog operationLog);

	public int getOperationLogId();

	public int getOperLogCountByOrgId(OperationLogCondition operationLogCondition);

	public List queryOperLogByOrgId(OperationLogCondition operationLogCondition);

	public int getOperLogCountByAccId(OperationLogCondition operationLogCondition);

	public List queryOperLogByAccId(OperationLogCondition operationLogCondition);

	public int getOperLogCountByOrgsAndAccId(OperationLogCondition operationLogCondition);

	public List queryOperLogByOrgsAndAccId(OperationLogCondition operationLogCondition);

	public void deleteOperLogById(long olsId);
	
	public void deleteOperLogByTime(OperationLogCondition operationLogCondition);

}
