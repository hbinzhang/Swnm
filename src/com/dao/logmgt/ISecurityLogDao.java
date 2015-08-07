package com.dao.logmgt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.entity.logmgt.SecurityLog;
import com.entity.logmgt.SecurityLogCondition;

public interface ISecurityLogDao {
	
	public void createSecurityLog(SecurityLog securityLog);

	public void deleteSecLogById(long slsId);

	public int getSecurityLogId();
	
	public int getSecLogCountByOrgsAndAccId(SecurityLogCondition securityLogCondition);

	public List querySecLogByOrgsAndAccId(SecurityLogCondition securityLogCondition);

	public int getSecLogCountByOrgId(SecurityLogCondition securityLogCondition);

	public int getSecLogCountByAccId(SecurityLogCondition securityLogCondition);

	public List querySecLogByOrgId(SecurityLogCondition securityLogCondition);

	public List querySecLogByAccId(SecurityLogCondition securityLogCondition);

	public void deleteSecLogByTime(SecurityLogCondition securityLogCondition);
}
