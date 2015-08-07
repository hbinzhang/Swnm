package com.entity.logmgt;

import java.util.Date;

public class OperationLog {

	/**
	 * 流水号 
	 */
	protected long olsId;
	
	/**
	 * 日志产生时间
	 */
	protected Date opTime;
	
	/**
	 * 工号
	 */
	private String accountId;

	/**
	 * 软件模块标识
	 */
	private Integer moduleId;
	
	private String moduleName;

	/**
	 * 操作标识
	 */
	private Integer commandId;
	
	private String commandName;

	/**
	 * 操作对象类型，由二次开发指定
	 */
//	private Integer objectType = -1;

	/**
	 * 操作对象列表，由二次开发指定
	 */
	private String operationObjects;

	/**
	 * 结果，成功、失败、部分失败，正在执行
	 */
	private Integer opResult = -1;

	/**
	 * 成功
	 */
	public static final Integer SUCCESS = 1;

	/**
	 * 失败
	 */
	public static final Integer FAILED = 0;

	/**
	 * 部分失败，针对批量操作
	 */
	public static final Integer PART_FAILED = 2;

	/**
	 * 正在执行，针对异步操作
	 */
	public static final Integer EXECUTING = 3;

	/**
	 * 详细信息，开发人员赋值
	 */
	private String opDetail;

	/**
	 * 主机名称
	 */
	private String hostName;

	/**
	 * 主机IP
	 */
	private String hostIp;

	/**
	 * 异步操作结束时间，开发人员赋值
	 */
	private Date endTime;
	
	/**
	 *  机构标识
	 */
	private String organizationId;

	
	private String orgName;


	public String getModuleName() {
		if (moduleName == null ){
			return "";
		}
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCommandName() {
		if (commandName == null ){
			return "";
		}
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getCommandId() {
		return commandId;
	}

	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}

	public long getOlsId() {
		return olsId;
	}

	public void setOlsId(long olsId) {
		this.olsId = olsId;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	/*	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
*/
	public String getOperationObjects() {
		if (operationObjects == null ){
			return "";
		}
		return operationObjects;
	}

	public void setOperationObjects(String operationObjects) {
		this.operationObjects = operationObjects;
	}

	public String getOrganizationId() {
		if (organizationId == null ){
			return "";
		}
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getOpResult() {
		return opResult;
	}

	public void setOpResult(Integer opResult) {
		this.opResult = opResult;
	}

	public String getOpDetail() {
		if (opDetail == null ){
			return "";
		}
		return opDetail;
	}

	public void setOpDetail(String opDetail) {
		this.opDetail = opDetail;
	}

	public String getHostName() {
		if (hostName == null ){
			return "";
		}
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostIp() {
		if (hostIp == null ){
			return "";
		}
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public OperationLog() {
		super();
	}

	public String getAccountId() {
		if (accountId == null ){
			return "";
		}
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOrgName() {
		if (orgName == null ){
			return "";
		}
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Override
	public String toString() {
		return "OperationLog [olsId=" + olsId + ", opTime=" + opTime
				+ ", accountId=" + accountId + ", moduleId=" + moduleId
				+ ", moduleName=" + moduleName + ", commandId=" + commandId
				+ ", commandName=" + commandName + ", operationObjects="
				+ operationObjects + ", opResult=" + opResult + ", opDetail="
				+ opDetail + ", hostName=" + hostName + ", hostIp=" + hostIp
				+ ", endTime=" + endTime + ", organizationId=" + organizationId
				+ ", orgName=" + orgName + "]";
	}

}
