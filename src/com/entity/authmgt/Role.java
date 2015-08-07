package com.entity.authmgt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Role implements Serializable {
	/** 角色名称 */
	private String name;
	/** 角色描述 */
	private String desc;
	/** 授权的操作，用“,”分隔 */
	private String authorizedOpIds;
	// 用于界面构造
	private List<String> authorizedOpsForUI;

	// private int type;
	// /**机构名称。当type为“管理角色”时，默认为总公司*/
	// private String organizationId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuthorizedOpIds() {
		return authorizedOpIds;
	}

	public void setAuthorizedOpIds(String authorizedOpIds) {
		this.authorizedOpIds = authorizedOpIds;
	}
	

	public List<String> getAuthorizedOpsForUI() {
		return authorizedOpsForUI;
	}

	public void setAuthorizedOpsForUI(List<String> authorizedOpsForUI) {
		this.authorizedOpsForUI = authorizedOpsForUI;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", desc=" + desc + ", authorizedOpIds="
				+ authorizedOpIds + "]";
	}


}
