package com.entity.authmgt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3918186638158728629L;
	
//	 工号。不可以为空
	private String id;
//	组织机构代码。不可以为空
	private String orgId;
//	机构名称用于界面显示
	private String orgNmForUI;
//	 用户名。不可以为空
	private String userId;
//	密码。不可以为空
	private String password;
//	 姓名。不可以为空
	private String userName;
//	职务。可以为空
	private String position;
//	职能描述。可以为空
	private String positionDesc;
//	性别。可以为空
	private String sex;
//	生日。可以为空
	private Date birthday=new Date();
//	办公电话 。可以为空
	private String offTel;
//	手机 。可以为空
	private String phone;
//	家庭电话 。可以为空
	private String homeTel;
//	家庭地址 。可以为空
	private String homeAddress;
//	邮箱 。可以为空
	private String mail;
//	资料更新日期。可以为空
	private Date updateDate=new Date();
//	资料更新责任人。可以为空
	private String updatePer;
//	备注。可以为空
	private String remark;
//	授予的角色。可以为空
	private String roles;
//	帐号类型。不可以为空。0:管理员账号:普通账号
	private int type;
//	是否是内置帐号
//	private boolean innerAccount;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositionDesc() {
		return positionDesc;
	}
	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getOffTel() {
		return offTel;
	}
	public void setOffTel(String offTel) {
		this.offTel = offTel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHomeTel() {
		return homeTel;
	}
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatePer() {
		return updatePer;
	}
	public void setUpdatePer(String updatePer) {
		this.updatePer = updatePer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}


	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getOrgNmForUI() {
		return orgNmForUI;
	}
	public void setOrgNmForUI(String orgNmForUI) {
		this.orgNmForUI = orgNmForUI;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", orgId=" + orgId + ", orgNmForUI="
				+ orgNmForUI + ", userId=" + userId + ", password=" + password
				+ ", userName=" + userName + ", position=" + position
				+ ", positionDesc=" + positionDesc + ", sex=" + sex
				+ ", birthday=" + birthday + ", offTel=" + offTel + ", phone="
				+ phone + ", homeTel=" + homeTel + ", homeAddress="
				+ homeAddress + ", mail=" + mail + ", updateDate=" + updateDate
				+ ", updatePer=" + updatePer + ", remark=" + remark
				+ ", roles=" + roles + ", type=" + type + "]";
	}
	


}
