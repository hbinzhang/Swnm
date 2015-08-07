package com.service.logmgt;

import java.util.List;
import java.util.Map;

import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;

public interface IOperationLogService {
	
	/**
	 * 创建操作日志
	 * @param name 下发的操作命令，即 command-info.xml的name属性，要求全局唯一
	 * @param operationObjects 操作对象
	 * @param opResult 操作结果 ，0：失败 ; 1 ：成功 ;  2：部分失败，针对批量操作; 3：正在执行，针对异步操作 
	 * @param opDetail 详细信息
	 */
	public void createOperationLog(String name, String operationObjects,
			int opResult, String opDetail);
	
	/**
	 * 根据操作日志主键(id)更新操作结果，结束时间，详细信息
	 */
	public void updateOperationLog(OperationLog operationLog);
				
/*	public Map<Integer, String> getModules();
	
	public List<CommandInfo> getCommandInfos();*/

	public int getOperationLogId();

	public int getOperLogCountByOrgId(OperationLogCondition operationLogCondition);

	public List queryOperLogByOrgId(OperationLogCondition operationLogCondition);

	public int getOperLogCountByAccId(OperationLogCondition operationLogCondition);

	public List queryOperLogByAccId(OperationLogCondition operationLogCondition);

	public int getOperLogCountByOrgsAndAccId(OperationLogCondition operationLogCondition);

	public List queryOperLogByOrgsAndAccId(OperationLogCondition operationLogCondition);

	public void deleteOperLogById(long olsId);
}
