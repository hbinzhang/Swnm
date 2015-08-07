package com.dao.logmgt.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.logmgt.IOperationLogDao;
import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;

public class OperationLogDaoImp implements IOperationLogDao{
	
	private SqlMapClientTemplate sqlmapclienttemplate = null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public void createOperationLog(OperationLog operationLog) {
		sqlmapclienttemplate.insert("createOperationLog", operationLog);
	}

	public void updateOperationLog(OperationLog operationLog) {
		sqlmapclienttemplate.update("updateOperationLog", operationLog);		
	}

	public int getOperationLogId() {
		return (Integer)sqlmapclienttemplate.queryForObject("getOperationLogId");
	}

	@Override
	public int getOperLogCountByOrgId(
			OperationLogCondition operationLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getOperLogCountByOrgId",
				operationLogCondition);
	}

	@Override
	public List queryOperLogByOrgId(OperationLogCondition operationLogCondition) {
		return sqlmapclienttemplate.queryForList("queryOperLogByOrgId",
				operationLogCondition);
	}

	@Override
	public int getOperLogCountByAccId(
			OperationLogCondition operationLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getOperLogCountByAccId",
				operationLogCondition);
	}

	@Override
	public List queryOperLogByAccId(OperationLogCondition operationLogCondition) {
		return sqlmapclienttemplate.queryForList("queryOperLogByAccId",
				operationLogCondition);
	}

	@Override
	public int getOperLogCountByOrgsAndAccId(
			OperationLogCondition operationLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getOperLogCountByOrgsAndAccId",
				operationLogCondition);
	}

	@Override
	public List queryOperLogByOrgsAndAccId(
			OperationLogCondition operationLogCondition) {
		return sqlmapclienttemplate.queryForList("queryOperLogByOrgsAndAccId",
				operationLogCondition);
	}

	@Override
	public void deleteOperLogById(long olsId) {
		sqlmapclienttemplate.delete("deleteOperLogById",olsId);		
	}

	@Override
	public void deleteOperLogByTime(OperationLogCondition operationLogCondition) {
		sqlmapclienttemplate.delete("deleteOperLogByTime",operationLogCondition);			
	}
}
