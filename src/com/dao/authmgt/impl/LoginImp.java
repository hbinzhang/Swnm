package com.dao.authmgt.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.authmgt.ILogin;
import com.entity.authmgt.Account;
import com.entity.authmgt.Session;

public class LoginImp implements ILogin {

	private SqlMapClientTemplate sqlmapclienttemplate = null;

	private Log log = LogFactory.getLog(this.getClass());


	public void setSqlmapclienttemplate(
			SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void create(Object object) {
		Session session = (Session) object;
		sqlmapclienttemplate.insert("createSession", session);
		return;
	}

	 public void deleteSessionByAccountId(String accountId) {
		 sqlmapclienttemplate.delete("deleteSessionByAccountId", accountId);
		 return;
	 }

	public void delete(long contextId) {
		sqlmapclienttemplate.delete("deleteSession", contextId);
		return;

	}

	public Object getCountByContextId(long contextId) {
		return sqlmapclienttemplate.queryForObject("getCountByContextId",
				contextId);
	}

}
