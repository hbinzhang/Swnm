package com.actions.alarmmgt;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.entity.alarmmgt.AlarmStatisticCondition;
import com.util.alarmmgt.AlarmConstants;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-action.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmStatisticAction {

	@Autowired
	private AlarmStatisticAction alarmStatisticAction;
	
	private AlarmStatisticCondition alarmStatisticCondition = new AlarmStatisticCondition();

	private List<Integer> statisticGranuList = new ArrayList<Integer> ();
	
	private String jsonStr1 = "{'type':'1','staticGranularity':['1'],'timeGranularity':'DD'}";
		
	private String jsonStr5 = "{'type':'2','staticGranularity':['1','2','3','4','5','6'],,'timeGranularity':'DD','departmentId':'38922','branchId':'4342'}";
	
	@Test
	public void testInitStatisticAlarm() {
		alarmStatisticAction.initStatisticAlarm();
		assertEquals(alarmStatisticAction.getType(),1);
		assertEquals(alarmStatisticAction.getTimeGranularity(),"DD");
		assertEquals(alarmStatisticAction.getCommonBean().getId(),"0");
		assertNull(alarmStatisticAction.getBranchList());
		assertNull(alarmStatisticAction.getDepartmentList());
		assertNull(alarmStatisticAction.getZoneList());
		assertNull(alarmStatisticAction.getUserLevel());

	}

	@Test
	public void testStatisticAlarmNormal() {
		statisticGranuList.add(1);	
		statisticGranuList.add(2);
		statisticGranuList.add(3);
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,4,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
	}

	@Test
	public void testStatisticAlarmForOffset() {
		statisticGranuList.add(1);		
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
	}
	
	@Test
	public void testStatisticAlarmForGran11() {
		statisticGranuList.add(1);		
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
		
	}
	
	@Test
	public void testStatisticAlarmForGran112() {
		statisticGranuList.add(1);	
		statisticGranuList.add(2);		
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
		
	}

	@Test
	public void testStatisticAlarmForGran1123() {
		statisticGranuList.add(1);	
		statisticGranuList.add(2);	
		statisticGranuList.add(3);		
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
		
	}
	
	@Test
	public void testStatisticAlarmForGran12() {
		statisticGranuList.add(2);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setLevelId(4);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
	}
	
	@Test
	public void testStatisticAlarmForGran13() {
		statisticGranuList.add(3);	
		statisticGranuList.add(4);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		

		alarmStatisticCondition.setDeviceTypeId(333);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
	}
	
	@Test
	public void testStatisticAlarmForGran123() {
		statisticGranuList.add(2);	
		statisticGranuList.add(3);
		statisticGranuList.add(4);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
		
		alarmStatisticCondition.setDeviceTypeId(333);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setLevelId(4);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setDeviceTypeId(-1);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
	}
	
	@Test
	public void testStatisticAlarmForGran113() {
		statisticGranuList.add(1);	
		statisticGranuList.add(3);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());				
	}
	
	@Test
	public void testStatisticAlarmForType() {
		statisticGranuList.add(3);
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(-1);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		assertEquals("error",alarmStatisticAction.statisticAlarm());
	}
	
	@Test
	public void testStatisticAlarmForGran21() {
		statisticGranuList.add(1);
		Date beginTime = new Date(115,4,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticCondition.setTimeGranularity("DD");
		assertEquals("success",alarmStatisticAction.statisticAlarm());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
	}
	
	@Test
	public void testStatisticAlarmForGran212() {
		statisticGranuList.add(1);	
		statisticGranuList.add(2);		
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());		
		
	}
	
	@Test
	public void testStatisticAlarmForGran2123() {
		statisticGranuList.add(1);	
		statisticGranuList.add(2);	
		statisticGranuList.add(3);		
		statisticGranuList.add(4);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setDeviceTypeId(333);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setLevelId(4);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setDeviceTypeId(-1);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
	}
	
	@Test
	public void testStatisticAlarmForGran22() {
		statisticGranuList.add(2);	
		statisticGranuList.add(4);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setDeviceTypeId(333);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
	}
	
	@Test
	public void testStatisticAlarmForGran23() {
		statisticGranuList.add(3);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
		
		alarmStatisticCondition.setLevelId(4);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());	
	}
	
	@Test
	public void testStatisticAlarmForGran223() {
		statisticGranuList.add(2);	
		statisticGranuList.add(3);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		assertNotNull(alarmStatisticAction.getAlarmStatisticCondition());		
	}
	
	@Test
	public void testStatisticAlarmForGran213() {
		statisticGranuList.add(1);	
		statisticGranuList.add(3);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(2);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setOffset(1000000);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setBranchId("1");		
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setDepartmentId("2");
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticCondition.setZoneId(3);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_SUC,alarmStatisticAction.getAjaxObject().getResult());				
	}
	
	@Test
	public void testExportStatisticAlarm1() {
		Date beginTime = new Date(115,5,10);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,6,1);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setTimeGranularity("DY");
		statisticGranuList.add(1);	
		statisticGranuList.add(4);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setType(1);
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		String json = JSON.toJSONString(alarmStatisticCondition);
		alarmStatisticAction.setJsonStr(json);
		assertEquals(alarmStatisticAction.exportStatisticAlarm(),"success");
	}
	
	@Test
	public void testExportStatisticAlarm2() {
		Date beginTime = new Date(115,5,10);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,6,1);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setTimeGranularity("DY");
		statisticGranuList.add(2);		
		statisticGranuList.add(3);		
		statisticGranuList.add(4);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setType(2);
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		String json = JSON.toJSONString(alarmStatisticCondition);
		alarmStatisticAction.setJsonStr(json);
		alarmStatisticAction.exportStatisticAlarm();
		assertNotNull(alarmStatisticAction.getFileName());
		assertNotNull(alarmStatisticAction.getInputStream());
	}
	
	@Test
	public void testExportStatisticAlarm5() {
		alarmStatisticAction.setJsonStr(jsonStr5);
		alarmStatisticAction.exportStatisticAlarm();
		assertEquals("success", alarmStatisticAction.exportStatisticAlarm());
	}
	
	@Test
	public void testStatisticAlarmForError() {
		statisticGranuList.add(2);	
		statisticGranuList.add(3);
		statisticGranuList.add(4);	
		statisticGranuList.add(5);	
		alarmStatisticCondition.setStaticGranularity(statisticGranuList);
		alarmStatisticCondition.setType(1);
		Date beginTime = new Date(115,5,1);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		alarmStatisticCondition.setBeginTime(beginTime);
		Date endTime = new Date(115,12,12);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		alarmStatisticCondition.setEndTime(endTime);
		alarmStatisticCondition.setTimeGranularity("DD");
		alarmStatisticAction.setAlarmStatisticCondition(alarmStatisticCondition);
		alarmStatisticAction.setAlarmStatisticService(null);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_FAIL,alarmStatisticAction.getAjaxObject().getResult());
		assertNull(alarmStatisticAction.getAlarmStatisticService());
		
		alarmStatisticCondition.setType(2);
		alarmStatisticAction.statisticAlarm();
		assertEquals(AlarmConstants.RESULT_FAIL,alarmStatisticAction.getAjaxObject().getResult());
		
		alarmStatisticAction.setBranchList(null);
		alarmStatisticAction.setAjaxObject(null);
		alarmStatisticAction.setAlarmStatisticCondition(null);
		alarmStatisticAction.setAlarmQueryService(null);
		alarmStatisticAction.setCommonBean(null);
		alarmStatisticAction.setDepartmentList(null);
		alarmStatisticAction.setFileName(null);
		alarmStatisticAction.setInputStream(null);
		alarmStatisticAction.setJsonStr(null);
		alarmStatisticAction.setOperationLogService(null);
		alarmStatisticAction.setOrganManagerService(null);
		alarmStatisticAction.setTimeGranularity(null);
		alarmStatisticAction.setType(0);
		alarmStatisticAction.setUserLevel(null);
		alarmStatisticAction.setZoneList(null);
		assertNull(alarmStatisticAction.getBranchList());
		assertNull(alarmStatisticAction.getAjaxObject());
		assertNull(alarmStatisticAction.getAlarmStatisticCondition());
		assertNull(alarmStatisticAction.getAlarmQueryService());
		assertNull(alarmStatisticAction.getCommonBean());
		assertNull(alarmStatisticAction.getDepartmentList());
		assertNull(alarmStatisticAction.getFileName());
		assertNull(alarmStatisticAction.getInputStream());
		assertNull(alarmStatisticAction.getJsonStr());
		assertNull(alarmStatisticAction.getOperationLogService());
		assertNull(alarmStatisticAction.getOrganManagerService());
		assertNull(alarmStatisticAction.getTimeGranularity());
		assertEquals(alarmStatisticAction.getType(),0);
		assertNull(alarmStatisticAction.getUserLevel());
		assertNull(alarmStatisticAction.getZoneList());

	}
}
