package com.service.alarmmgt.impl;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dao.linkagectl.IAlarmDao;
import com.service.linkagectl.impl.SaveDeAlarmImp;
import com.entity.linkagectl.DeviceAlarm;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestSaveDeAlarmImp {

	@Autowired
	private SaveDeAlarmImp saveDeAlarmImp;
	
	
	@Test
	public void testSave() {
		try{
			DeviceAlarm deviceAlarm = new DeviceAlarm();	
			deviceAlarm.setAlarmCode(303);
			deviceAlarm.setAlarmTime(new Date());
			deviceAlarm.setBranchID("010300");
			deviceAlarm.setDepartmentID("010309");
			deviceAlarm.setDeviceID("XLE01GX");
			deviceAlarm.setAlarmStatus(0);
			int a = saveDeAlarmImp.save(deviceAlarm);
			System.out.println("a="+a);
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}
}
