package com.entity.logmgt;

import java.util.Date;

public class SecurityLog{

	/**
	 * 流水号 
	 */
	protected long slsId;
	
	/**
	 * 日志产生时间
	 */
	protected Date opTime;
	
	/**
	 * 工号
	 */
	private String accountId;

	/**
	 * 结果，成功
	 */
	private Integer opResult = -1;

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 失败
	 */
	public static final int FAILED = 0;


	/**
	 * 操作标识
	 */
	private Integer commandId;
	
	private String commandName;

	/**
	 * 详细信息
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
	 *  机构标识
	 */
	private String organizationId;
	
	private String orgName;


	public String getCommandName() {
		if (commandName == null ){
			return "";
		}
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public Integer getCommandId() {
		return commandId;
	}

	public long getSlsId() {
		return slsId;
	}

	public void setSlsId(long slsId) {
		this.slsId = slsId;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
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

	public String getOrganizationId() {
		if (organizationId == null ){
			return "";
		}
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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
		return "SecurityLog [slsId=" + slsId + ", opTime=" + opTime
				+ ", accountId=" + accountId + ", opResult=" + opResult
				+ ", commandId=" + commandId + ", commandName=" + commandName
				+ ", opDetail=" + opDetail + ", hostName=" + hostName
				+ ", hostIp=" + hostIp + ", organizationId=" + organizationId
				+ ", orgName=" + orgName + "]";
	}
	
}
