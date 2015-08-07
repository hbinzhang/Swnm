package com.dao.authmgt.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.dao.authmgt.ISession;

public class SessionImp implements ISession {

	private SqlMapClientTemplate sqlmapclienttemplate = null;

	public void setSqlmapclienttemplate(
			SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	@Override
	public List querySessionsByAccountId(String id) {
		List sessions = (List) sqlmapclienttemplate.queryForList(
						"querySessionsByAccountId", id);
		return sessions;
	}

	@Override
	public List querySessionsByOrganizationId(String orgId) {
		List sessions = (List) sqlmapclienttemplate.queryForList(
				"querySessionsByOrganizationId", orgId);
		return sessions;
	}

	public Object getCountByContextId(long contextId) {
		return sqlmapclienttemplate.queryForObject("getSessionCountByContextId",contextId);
	}

	public void delete(long contextId) {
		sqlmapclienttemplate.delete("deleteSession", contextId);
		
	}

}
