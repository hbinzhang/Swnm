package com.actions.alarmmgt;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-action.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestDeviceAlarmMonitorAction {

	@Autowired
	private DeviceAlarmMonitorAction deviceAlarmMonitorAction;
	
	@Test
	public void testQueryDeviceAlarm1() {
		deviceAlarmMonitorAction.queryDeviceAlarm();
		assertNull(deviceAlarmMonitorAction.getDeviceAlarmList());
	}

	@Test
	public void testHandleDeviceAlarm1() {
		deviceAlarmMonitorAction.setAlarmId(1);
		deviceAlarmMonitorAction.handleDeviceAlarm();
		assertEquals(deviceAlarmMonitorAction.getAlarmId(),1);
		assertNotNull(deviceAlarmMonitorAction.getCommonBean());
		assertNotNull(deviceAlarmMonitorAction.getAlarmQueryService());
		assertNotNull(deviceAlarmMonitorAction.getOrganManagerService());
		assertNotNull(deviceAlarmMonitorAction.getOperationLogService());		
		assertNotNull(deviceAlarmMonitorAction.getAlarmUiPushImp());
	}

	@Test
	public void testQueryDeviceAlarm2() {
		deviceAlarmMonitorAction.setAlarmQueryService(null);
		deviceAlarmMonitorAction.setAlarmUiPushImp(null);
		deviceAlarmMonitorAction.setCommonBean(null);
		deviceAlarmMonitorAction.setDeviceAlarmList(null);
		deviceAlarmMonitorAction.setOperationLogService(null);
		deviceAlarmMonitorAction.setOrganManagerService(null);
		assertNull(deviceAlarmMonitorAction.getAlarmQueryService());
		assertNull(deviceAlarmMonitorAction.getAlarmUiPushImp());
		assertNull(deviceAlarmMonitorAction.getCommonBean());
		assertNull(deviceAlarmMonitorAction.getDeviceAlarmList());
		assertNull(deviceAlarmMonitorAction.getOperationLogService());
		assertNull(deviceAlarmMonitorAction.getOrganManagerService());
	}

	
}
