package com.entity.authmgt;

import java.io.Serializable;
import java.util.List;

import com.entity.CommonBean;

public class OrgLevAndIdNames implements Serializable {
	//若用户属总公司，company中放所在总公司,subCompanys中放所有分公司、managements中放所有管理处；
	//若用户属分公司，subCompanys中放所在分公司；managements中放所在分公司下的管理处
	//若用户属管理处，subCompanys为空;managements中放所在管理处
	List<CommonBean> levs;
	CommonBean company;
	List<CommonBean> subCompanys;
	List<CommonBean> managements;
	
	public List<CommonBean> getLevs() {
		return levs;
	}
	public void setLevs(List<CommonBean> levs) {
		this.levs = levs;
	}
	public List<CommonBean> getSubCompanys() {
		return subCompanys;
	}
	public void setSubCompanys(List<CommonBean> subCompanys) {
		this.subCompanys = subCompanys;
	}
	public List<CommonBean> getManagements() {
		return managements;
	}
	public void setManagements(List<CommonBean> managements) {
		this.managements = managements;
	}
	
	public OrgLevAndIdNames() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommonBean getCompany() {
		return company;
	}
	public void setCompany(CommonBean company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "OrgLevAndIdNames [levs=" + levs + ", company=" + company
				+ ", subCompanys=" + subCompanys + ", managements="
				+ managements + "]";
	}
	

}
