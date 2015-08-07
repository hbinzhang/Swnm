package com.service.alarmmgt.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entity.alarmmgt.AlarmStatisticCondition;
import com.entity.alarmmgt.AlarmStatisticInfo;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmStatisticServiceImp {

	@Autowired
	private AlarmStatisticServiceImp alarmStatisticServiceImp;
	
	private AlarmStatisticCondition alarmStatisticCondition = new AlarmStatisticCondition();;
	
	
	@Test
	public void testStatisticSecurityAlarm() {
		try{
			alarmStatisticCondition.setType(1);
			Date startdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startdate);
			cal.add(Calendar.DATE, 1);		
			alarmStatisticCondition.setEndTime(cal.getTime());
			alarmStatisticCondition.setTimeGranularity("DD");
			alarmStatisticCondition.setColumnStr("b.BEGINTIME, b.ENDTIME");
			alarmStatisticCondition.setGroupByStr("BEGINTIME, ENDTIME");
			alarmStatisticCondition.setStart(1);
			alarmStatisticCondition.setEnd(10);
			alarmStatisticServiceImp.statisticSecurityAlarm(alarmStatisticCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testStatisticDeviceAlarm() {
		try{
			alarmStatisticCondition.setType(2);
			Date startdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startdate);
			cal.add(Calendar.DATE, 1);	
			alarmStatisticCondition.setBeginTime(startdate);
			alarmStatisticCondition.setEndTime(cal.getTime());
			alarmStatisticCondition.setTimeGranularity("DD");
			alarmStatisticCondition.setStart(1);
			alarmStatisticCondition.setEnd(10);
			alarmStatisticCondition.setColumnStr("b.BEGINTIME, b.ENDTIME");
			alarmStatisticCondition.setGroupByStr("BEGINTIME, ENDTIME");
			alarmStatisticServiceImp.statisticDeviceAlarm(alarmStatisticCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetStatisSecAlarmCount() {
		try{
			alarmStatisticCondition.setType(1);
			Date startdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startdate);
			cal.add(Calendar.DATE, 1);	
			alarmStatisticCondition.setEndTime(cal.getTime());
			alarmStatisticCondition.setTimeGranularity("DD");
			alarmStatisticCondition.setColumnStr("b.BEGINTIME, b.ENDTIME");
			alarmStatisticCondition.setGroupByStr("BEGINTIME, ENDTIME");
			alarmStatisticServiceImp.getStatisSecAlarmCount(alarmStatisticCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetStatisDevAlarmCount() {
		try{
			alarmStatisticCondition.setType(2);
			Date startdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startdate);
			cal.add(Calendar.DATE, 1);	
			alarmStatisticCondition.setBeginTime(startdate);
			alarmStatisticCondition.setEndTime(cal.getTime());
			alarmStatisticCondition.setTimeGranularity("DD");
			alarmStatisticCondition.setColumnStr("b.BEGINTIME, b.ENDTIME");
			alarmStatisticCondition.setGroupByStr("BEGINTIME, ENDTIME");
			alarmStatisticServiceImp.getStatisDevAlarmCount(alarmStatisticCondition);	
			assertTrue(true);
			AlarmStatisticInfo info = new AlarmStatisticInfo();
			info.setDepartmentId(null);
			info.setBranchId(null);
			info.setBranchName(null);
			info.setZoneName(null);
			info.setDepartmentName(null);
			info.setType(1);
			info.setCheckLevel(1);
			assertNotNull(info.toString());
			assertEquals(info.getBranchId(),"");
			assertEquals(info.getDepartmentId(),"");
			assertEquals(info.getBranchName(),"");
			assertEquals(info.getZoneName(),"");
			assertEquals(info.getDepartmentName(),"");
			assertEquals(info.getType(),new Integer(1));
			assertEquals(info.getCheckLevel(),new Integer(1));
			info.setBranchName("aaaa");
			info.setDepartmentName("bbb");
			info.setZoneName("ccc");
			assertEquals(info.getBranchName(),"aaaa");
			assertEquals(info.getDepartmentName(),"bbb");
			assertEquals(info.getZoneName(),"ccc");
		}catch (Exception e){
			assertTrue(false);
		}
	}

}
