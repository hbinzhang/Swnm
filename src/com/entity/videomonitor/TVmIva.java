package com.entity.videomonitor;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class TVmIva {
	@NotNull(message = "IVA ID不能为空")
	@NotEmpty(message = "IVA ID不能为空")
	@Length(max = 7,message="IVA ID最长为7位")
	private String ivaID;
	@NotNull(message = "IP不能为空")
	@NotEmpty(message = "IP不能为空")
	@MatchPattern(pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$", message = "IP格式错误")
	private String ip;
	@NotNull(message = "Port不能为空")
	@NotEmpty(message = "Port不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="Port只能为整数")
	private Integer port;
	private String managementagency;
	private String mgtName;
	private String name;
	private String branch;
	private String branchName;
	
	public String getIvaID() {
		return ivaID;
	}
	public void setIvaID(String ivaID) {
		this.ivaID = ivaID;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getManagementagency() {
		return managementagency;
	}
	public void setManagementagency(String managementagency) {
		this.managementagency = managementagency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getMgtName() {
		return mgtName;
	}
	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
 
}