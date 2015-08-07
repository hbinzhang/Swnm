package com.service.securityinfo;

import java.util.List;

import com.entity.securityinfo.Event;
import com.entity.securityinfo.EventCondition;

public interface IEventService {

	List queryEvent(EventCondition eventCondition);

	void createEvent(Event event);

	Object queryEventByName(Event event);

	void updateEvent(Event event);

	void deleteEvent(Event event);

}
