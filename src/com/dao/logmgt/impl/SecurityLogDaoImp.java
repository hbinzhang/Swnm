package com.dao.logmgt.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.logmgt.ISecurityLogDao;
import com.entity.logmgt.SecurityLog;
import com.entity.logmgt.SecurityLogCondition;

public class SecurityLogDaoImp implements ISecurityLogDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	
	public void createSecurityLog(SecurityLog securityLog) {
		sqlmapclienttemplate.insert("createSecurityLog", securityLog);		
	}

	public void deleteSecLogById(long slsId) {
		sqlmapclienttemplate.delete("deleteSecLogById", slsId);
	}

	public int getSecurityLogId() {
		return (Integer)sqlmapclienttemplate.queryForObject("getSecurityLogId");
	}

	@Override
	public int getSecLogCountByOrgsAndAccId(SecurityLogCondition securityLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getSecLogCountByOrgsAndAccId", 
				securityLogCondition);
	}

	@Override
	public List querySecLogByOrgsAndAccId(SecurityLogCondition securityLogCondition) {
		return sqlmapclienttemplate.queryForList("querySecLogByOrgsAndAccId", securityLogCondition);
	}

	@Override
	public int getSecLogCountByOrgId(SecurityLogCondition securityLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getSecLogCountByOrgId", 
				securityLogCondition);
	}

	@Override
	public int getSecLogCountByAccId(SecurityLogCondition securityLogCondition) {
		return (Integer)sqlmapclienttemplate.queryForObject("getSecLogCountByAccId", 
				securityLogCondition);
	}

	@Override
	public List querySecLogByOrgId(SecurityLogCondition securityLogCondition) {
		return sqlmapclienttemplate.queryForList("querySecLogByOrgId", securityLogCondition);
	}

	@Override
	public List querySecLogByAccId(SecurityLogCondition securityLogCondition) {
		return sqlmapclienttemplate.queryForList("querySecLogByAccId", securityLogCondition);
	}

	@Override
	public void deleteSecLogByTime(SecurityLogCondition securityLogCondition) {
		sqlmapclienttemplate.delete("deleteSecLogByTime",securityLogCondition);				
	}

}
