package com.dao.authmgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.authmgt.IOrganization;
import com.entity.authmgt.Organization;

public class OrganizationImp implements IOrganization {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public List getAll() {
		List organizations=(List)sqlmapclienttemplate.queryForList("getAllOrganizations",null);		
		return organizations;
	}
	
	public void create(Object object) {
		Organization organization=(Organization)object;
		sqlmapclienttemplate.insert("createOrganization",organization);
		return;
	}

	public void update(Object object) {
		Organization organization=(Organization)object;	
		sqlmapclienttemplate.update("updateOrganization",organization);
		return;
	}
	public void delete(String name) {
		sqlmapclienttemplate.delete("deleteOrganization",name);
		return;
	}

	public Object getCountByOrgId(String orgId) {
		return sqlmapclienttemplate.queryForObject("getOrganizationCountByOrgId",orgId);
	}

	public List getOrgIdAndOrgNmsByLev(String lev) {
		return (List)sqlmapclienttemplate.queryForList("getOrgIdAndOrgNmsByLev",lev);
	}

	public Object getOrgNmByOrgId(String orgId) {
		return sqlmapclienttemplate.queryForObject("getOrgNmByOrgId",orgId);
	}

	public List getOrgIdAndOrgNmsFor1ByPOrgId(String porgId) {
		return (List)sqlmapclienttemplate.queryForList("getOrgIdAndOrgNmsFor1ByPOrgId",porgId);
	}

	public List getOrgIdAndOrgNmsFor2ByPOrgId(String porgId) {
		return (List)sqlmapclienttemplate.queryForList("getOrgIdAndOrgNmsFor2ByPOrgId",porgId);
	}

	public List getOrgIdsByPOrgId(String porgId) {
		return (List)sqlmapclienttemplate.queryForList("getOrgIdsByPOrgId",porgId);
	}

	public Object getLevByOrgId(String orgId) {
		return sqlmapclienttemplate.queryForObject("getLevByOrgId",orgId);
	}

	public Object getPOrgIdByOrgId(String orgId) {
		return sqlmapclienttemplate.queryForObject("getPOrgIdByOrgId",orgId);
	}

	public List getOrganizationsFor1ByPOrgId(String porgId) {
		return (List)sqlmapclienttemplate.queryForList("getOrganizationsFor1ByPOrgId",porgId);
	}

	public List getOrganizationsFor2ByPOrgId(String porgId) {
		return (List)sqlmapclienttemplate.queryForList("getOrganizationsFor2ByPOrgId",porgId);
	}

	public List getOrganizationsByLev(String lev) {
		return (List)sqlmapclienttemplate.queryForList("getOrganizationsByLev",lev);
	}

	public Object getOrgIdByOrgNm(Map map) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getOrgIdByOrgNm",map);
	}

	public Object getOrganizationByOrgID(String orgId) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getOrganizationByOrgID",orgId);
	}

	public List getOrgIdAndOrgNmsForSubCompany() {
		// TODO Auto-generated method stub
		return  (List)sqlmapclienttemplate.queryForList("getOrgIdAndOrgNmsForSubCompany",null);
	}

	@Override
	public List getOrgIdAndOrgNmsForManagement() {
		// TODO Auto-generated method stub
		return (List)sqlmapclienttemplate.queryForList("getOrgIdAndOrgNmsForManagement",null);
	}

	@Override
	public Object getCountByOrgIdAndOrgNm(Map map) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("getCountByOrgIdAndOrgNm",map);
	}

}
