package com.dao.securityinfo;

import java.util.List;

import com.entity.securityinfo.Event;
import com.entity.securityinfo.EventCondition;

public interface IEventDao {

	void deleteEvent(Event event);

	void updateEvent(Event event);

	Object queryEventByName(Event event);

	void createEvent(Event event);

	List queryEvent(EventCondition eventCondition);

}
