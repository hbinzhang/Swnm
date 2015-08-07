package com.service.logmgt;

import java.util.List;

import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.SecurityLogCondition;

public interface ISecurityLogService {

	/**
	 * 创建安全日志，即登录，注销日志
	 * @param name 下发的操作命令，即 command-info.xml的name属性，要求全局唯一
	 * @param operationObjects 操作对象
	 * @param opResult 操作结果 ，0：失败 ; 1 ：成功 ;
	 * @param opDetail 详细信息
	 */
	public void createSecurityLog(String name, String operationObjects,
			int opResult, String opDetail);
		
	public void deleteSecLogById(long slsId);

	public int getSecurityLogId();

/*	public List<CommandInfo> getCommandInfos();
*/
	public int getSecLogCountByOrgsAndAccId(SecurityLogCondition securityLogCondition);

	public List querySecLogByOrgsAndAccId(SecurityLogCondition securityLogCondition);

	public int getSecLogCountByOrgId(SecurityLogCondition securityLogCondition);

	public int getSecLogCountByAccId(SecurityLogCondition securityLogCondition);

	public List querySecLogByOrgId(SecurityLogCondition securityLogCondition);

	public List querySecLogByAccId(SecurityLogCondition securityLogCondition);

}
