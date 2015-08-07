package com.service.securityinfo.impl;

import java.util.List;

import com.dao.securityinfo.IEventDao;
import com.entity.securityinfo.Event;
import com.entity.securityinfo.EventCondition;
import com.service.securityinfo.IEventService;

public class EventServiceImp implements IEventService{

	private IEventDao eventDao;

	public IEventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	public List queryEvent(EventCondition eventCondition) {
		return eventDao.queryEvent(eventCondition);
	}

	@Override
	public void createEvent(Event event) {
		eventDao.createEvent(event);
		
	}

	@Override
	public Object queryEventByName(Event event) {
		return eventDao.queryEventByName(event);
	}

	@Override
	public void updateEvent(Event event) {
		eventDao.updateEvent(event);
		
	}

	@Override
	public void deleteEvent(Event event) {
		eventDao.deleteEvent(event);
		
	}
}
