package com.entity.authmgt;

import java.io.Serializable;

public class Login implements Serializable{
	
//	 工号。不可以为空
	private String id;
	//账号名称
//	private String accountName;
	//账号口令。不可以为空
	private String password;
	//客户端主机名。不可以为空
	private String loginHostName;
	//客户端主机IP。不可以为空
	private String loginHostIp;
	
	
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Login(String id, String password, String loginHostName,
			String loginHostIp) {
		super();
		this.id = id;
		this.password = password;
		this.loginHostName = loginHostName;
		this.loginHostIp = loginHostIp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	@Override
	public String toString() {
		return "Login [id=" + id + ", password=" + password
				+ ", loginHostName=" + loginHostName + ", loginHostIp="
				+ loginHostIp + "]";
	}	
	
	
}
