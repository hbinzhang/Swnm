package com.entity.authmgt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Organization implements Serializable {
	// 组织机构代码。不可以为空
	private String orgId;
	// 用于界面构造
	private int orgIdForUI;
	// 组织机构名称。不可以为空
	private String orgNm;
	// 级别。不可以为空
	private String lev;
	// 资料更新日期。可以为空
	private Date updateDate;
	// 资料更新责任人。可以为空
	private String updatePer;
	// 上级组织。可以为空
	private String porgId;
	// 用于界面构造
	private int porgIdForUI;
	// 备注。可以为空
	private String remark;

	// 授予的角色。不可以为空
	// private String role;

	public String getOrgId() {
		return orgId;
	}

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
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

	public int getOrgIdForUI() {
		return orgIdForUI;
	}

	public void setOrgIdForUI(int orgIdForUI) {
		this.orgIdForUI = orgIdForUI;
	}

	public String getPorgId() {
		return porgId;
	}

	public void setPorgId(String porgId) {
		this.porgId = porgId;
	}

	public int getPorgIdForUI() {
		return porgIdForUI;
	}

	public void setPorgIdForUI(int porgIdForUI) {
		this.porgIdForUI = porgIdForUI;
	}

	@Override
	public String toString() {
		return "Organization [orgId=" + orgId + ", orgIdForUI=" + orgIdForUI
				+ ", orgNm=" + orgNm + ", lev=" + lev + ", updateDate="
				+ updateDate + ", updatePer=" + updatePer + ", porgId="
				+ porgId + ", porgIdForUI=" + porgIdForUI + ", remark="
				+ remark + "]";
	}
	

}
