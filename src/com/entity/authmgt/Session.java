package com.entity.authmgt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Session implements Serializable {
	
//	 工号。不可以为空
	private String id;
//	private String accountName;	
//	 会话标识。不可以为空	
	private long contextId;
//	 帐号类型。不可以为空	
//	private int accountType;
//	登录时间。不可以为空	
	private Date loginTime;
//	主机名称。不可以为空	
	private String loginHostName;
//	 主机地址。不可以为空	
	private String loginHostIp;
//	机构标识。不可以为空	
	private String organizationId;
//	机构名称用于界面显示
	private String orgNmForUI;
//	用户名用于界面显示
	private String userIdForUI;
//	 姓名用于界面显示
	private String userNameForUI;
//	机构级别。不可以为空	
	private String lev;
//	机构级别用于界面显示。不可以为空	
	private String levForUI;
//操作显示名称列表。可以为空
	private List<String> authorizatedOps;
	//分工司和管理处id和name。
	//若用户属总公司，orgIdAndNames的subCompanys中放所有分公司，managements中放所有管理处；
	//若用户属分公司，orgIdAndNames的subCompanys中放这个分公司，managements中放其下所有管理处；
	//若用户属管理处，orgIdAndNames的subCompanys中存该管理处所属的分公司，managements中放这个管理处
	private OrgIdAndNames orgIdAndNames;
//	帐号类型。不可以为空。0:管理员账号:普通账号
	private int type;
//	服务器IP。不可以为空	
	private String serverHostIp;
//	 内置帐号。
//	private boolean innerAccount;
	
	public String getId() {
		return id;
	}
	
	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getContextId() {
		return contextId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginHostName() {
		return loginHostName;
	}

	public void setLoginHostName(String loginHostName) {
		this.loginHostName = loginHostName;
	}

	public String getLoginHostIp() {
		return loginHostIp;
	}

	public void setLoginHostIp(String loginHostIp) {
		this.loginHostIp = loginHostIp;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public List<String> getAuthorizatedOps() {
		return authorizatedOps;
	}

	public void setAuthorizatedOps(List<String> authorizatedOps) {
		this.authorizatedOps = authorizatedOps;
	}

	public OrgIdAndNames getOrgIdAndNames() {
		return orgIdAndNames;
	}

	public void setOrgIdAndNames(OrgIdAndNames orgIdAndNames) {
		this.orgIdAndNames = orgIdAndNames;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgNmForUI() {
		return orgNmForUI;
	}

	public void setOrgNmForUI(String orgNmForUI) {
		this.orgNmForUI = orgNmForUI;
	}

	public String getUserIdForUI() {
		return userIdForUI;
	}

	public void setUserIdForUI(String userIdForUI) {
		this.userIdForUI = userIdForUI;
	}

	public String getServerHostIp() {
		return serverHostIp;
	}

	public void setServerHostIp(String serverHostIp) {
		this.serverHostIp = serverHostIp;
	}

	public String getLevForUI() {
		return levForUI;
	}

	public void setLevForUI(String levForUI) {
		this.levForUI = levForUI;
	}

	public String getUserNameForUI() {
		return userNameForUI;
	}

	public void setUserNameForUI(String userNameForUI) {
		this.userNameForUI = userNameForUI;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", contextId=" + contextId
				+ ", loginTime=" + loginTime + ", loginHostName="
				+ loginHostName + ", loginHostIp=" + loginHostIp
				+ ", organizationId=" + organizationId + ", orgNmForUI="
				+ orgNmForUI + ", userIdForUI=" + userIdForUI
				+ ", userNameForUI=" + userNameForUI + ", lev=" + lev
				+ ", levForUI=" + levForUI + ", authorizatedOps="
				+ authorizatedOps + ", orgIdAndNames=" + orgIdAndNames
				+ ", type=" + type + ", serverHostIp=" + serverHostIp + "]";
	}


}
