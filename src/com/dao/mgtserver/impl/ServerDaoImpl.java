package com.dao.mgtserver.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.mgtserver.IServerDao;
import com.entity.mgtserver.ServerBean;

public class ServerDaoImpl implements IServerDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	
	public String getMgtIPByMgtID(String mgtID) {
		mgtID=mgtID.trim();
		return (String)sqlmapclienttemplate.queryForObject("getMgtIPByMgtID", mgtID);
	}

	public List<ServerBean> getAllServer() throws Exception {
		
		return (List<ServerBean>)sqlmapclienttemplate.queryForList("getAllServer");
		
	}
	/**
	 * 通过管理处IP获取管理处id
	 */
	@Override
	public String getMgtIdByMgtIP(String mngIP) throws Exception {
		
		return (String)sqlmapclienttemplate.queryForObject("getMgtIdByMgtIP",mngIP);
		
	}
	@Override
	public String hasServerByMgtID(String mngId) throws Exception {
		return (String)sqlmapclienttemplate.queryForObject("hasServerByMgtID",mngId);
	}
	

}
