package com.dao.securityinfo.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.securityinfo.IRectificationDao;
import com.entity.securityinfo.Rectification;
import com.entity.securityinfo.RectificationCondition;

public class RectificationDaoImp implements IRectificationDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public List queryRectification(RectificationCondition rectificationCondition) {
		return sqlmapclienttemplate.queryForList("queryRectification",rectificationCondition);
	}

	@Override
	public void createRectification(Rectification rectification) {
		sqlmapclienttemplate.insert("createRectification",rectification);
		
	}

	@Override
	public Object queryRectificationByName(Rectification rectification) {
		return sqlmapclienttemplate.queryForObject("queryRectificationByName",rectification);
	}

	@Override
	public void updateRectification(Rectification rectification) {
		sqlmapclienttemplate.update("updateRectification",rectification);
		
	}

	@Override
	public void deleteRectification(Rectification rectification) {
		sqlmapclienttemplate.delete("deleteRectification",rectification);
		
	}
}
