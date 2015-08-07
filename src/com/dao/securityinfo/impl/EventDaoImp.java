package com.dao.securityinfo.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.securityinfo.IEventDao;
import com.entity.securityinfo.Event;
import com.entity.securityinfo.EventCondition;

public class EventDaoImp implements IEventDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public void deleteEvent(Event event) {
		sqlmapclienttemplate.delete("deleteEvent",event);
		
	}

	@Override
	public void updateEvent(Event event) {
		sqlmapclienttemplate.update("updateEvent",event);
		
	}

	@Override
	public Object queryEventByName(Event event) {
		return sqlmapclienttemplate.queryForObject("queryEventByName",event);
	}

	@Override
	public void createEvent(Event event) {
		sqlmapclienttemplate.insert("createEvent",event);
		
	}

	@Override
	public List queryEvent(EventCondition eventCondition) {
		return sqlmapclienttemplate.queryForList("queryEvent",eventCondition);
	}
}
