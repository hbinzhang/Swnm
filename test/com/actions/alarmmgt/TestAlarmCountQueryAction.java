package com.actions.alarmmgt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.actions.alarmmgt.AlarmCountQueryAction;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-action.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmCountQueryAction {
		
	@Autowired
	private AlarmCountQueryAction alarmCountQueryAction;
	
	@Test
	public void testQueryAlarmCount1() {	
		alarmCountQueryAction.queryAlarmCount();
		assertNotNull(alarmCountQueryAction.getMonitorAlarmCount());
		assertNotNull(alarmCountQueryAction.getAlarmQueryService());
		assertNotNull(alarmCountQueryAction.getAjaxObject());
	}
	
	@Test
	public void testQueryAlarmCount2() {	
		alarmCountQueryAction.setAlarmQueryService(null);
		assertEquals(alarmCountQueryAction.queryAlarmCount(),"error");
		alarmCountQueryAction.setMonitorAlarmCount(null);
		assertNull(alarmCountQueryAction.getMonitorAlarmCount());
		alarmCountQueryAction.setAjaxObject(null);
		assertNull(alarmCountQueryAction.getAjaxObject());
	}

}
