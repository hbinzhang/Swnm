package com.service.alarmmgt.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dao.alarmmgt.impl.AlarmQueryDaoImp;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmQueryServiceImp {

	@Autowired
	private AlarmQueryServiceImp alarmQueryServiceImp;
	@Autowired
	private AlarmQueryDaoImp alarmQueryDaoImp;
	
	private AlarmQueryCondition alarmQueryCondition = new AlarmQueryCondition();
	
	private DeviceAlarm deviceAlarm = new DeviceAlarm();

	
	@Test
	public void testQuerySecurityAlarm() {
		try{
			alarmQueryCondition.setType(1);
			alarmQueryServiceImp.querySecurityAlarm(alarmQueryCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryDeviceAlarm() {
		try{
			alarmQueryCondition.setType(2);
			alarmQueryServiceImp.queryDeviceAlarm(alarmQueryCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);

		}
	}

	@Test
	public void testGetSecurityAlarmCount() {
		try{
			alarmQueryCondition.setType(1);
			alarmQueryServiceImp.getSecurityAlarmCount(alarmQueryCondition);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetDeviceAlarmCount() {
		try{
			alarmQueryCondition.setType(2);
			alarmQueryServiceImp.getDeviceAlarmCount(alarmQueryCondition);	
			assertTrue(true);
			alarmQueryCondition.setBeginTime(new Date());
			alarmQueryCondition.setEndTime(new Date());
			alarmQueryCondition.setZoneId(1);
			alarmQueryCondition.setDeviceTypeId(1);
			alarmQueryCondition.setLevelId(1);
			alarmQueryCondition.setHasEventVideo(0);
			alarmQueryCondition.setAlarmStatus(0);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryDeviceAlarmByStatus() {
		try{
			deviceAlarm.setAlarmStatus(0);
			deviceAlarm.setAlarmId(0);
			alarmQueryServiceImp.queryDeviceAlarmByStatus(deviceAlarm);
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testUpdateDeviceAlarm() {
		try{
			deviceAlarm.setAlarmStatus(0);
			deviceAlarm.setAlarmId(0);
			alarmQueryServiceImp.updateDeviceAlarm(deviceAlarm);	
			assertTrue(true);
			deviceAlarm.setAlarmCode(1);
			deviceAlarm.setAlarmLevel(1);
			deviceAlarm.setAlarmName("2323");
			deviceAlarm.setAlarmTime(new Date());
			deviceAlarm.setBranchId("1");
			deviceAlarm.setBranchName("2");
			deviceAlarm.setCause("ewe");
			deviceAlarm.setDepartmentId("4");
			deviceAlarm.setDepartmentName("d");
			deviceAlarm.setDeviceId("11");
			deviceAlarm.setDeviceType(11);
			deviceAlarm.setInfo("24242");
			deviceAlarm.setIsAffect(1);
			deviceAlarm.setManagerTime(new Date());
			deviceAlarm.setOperation("ooo");
			deviceAlarm.setPeopleId("222");
			deviceAlarm.setResult("ewewe");
			deviceAlarm.setType(2);
			deviceAlarm.setZoneId(11);
			deviceAlarm.setZoneName("ewe");
			alarmQueryDaoImp.monitorDeviceAlarmCount(deviceAlarm);
			assertTrue(true);
			assertEquals(deviceAlarm.getAlarmCode(),new Integer(1));
			assertEquals(deviceAlarm.getAlarmLevel(),new Integer(1));
			assertEquals(deviceAlarm.getAlarmName(),"2323");
			assertNotNull(deviceAlarm.getAlarmTime());
			assertEquals(deviceAlarm.getBranchId(),"1");
			assertEquals(deviceAlarm.getBranchName(),"2");
			assertEquals(deviceAlarm.getCause(),"ewe");
			assertEquals(deviceAlarm.getDepartmentId(),"4");
			assertEquals(deviceAlarm.getDepartmentName(),"d");
			assertEquals(deviceAlarm.getDeviceId(),"11");
			assertEquals(deviceAlarm.getDeviceType(),new Integer(11));
			assertEquals(deviceAlarm.getInfo(),"24242");
			assertEquals(deviceAlarm.getIsAffect(),new Integer(1));
			assertNotNull(deviceAlarm.getManagerTime());
			assertEquals(deviceAlarm.getOperation(),"ooo");
			assertEquals(deviceAlarm.getPeopleId(),"222");
			assertEquals(deviceAlarm.getResult(),"ewewe");
			assertEquals(deviceAlarm.getType(),new Integer(2));
			assertEquals(deviceAlarm.getZoneId(),new Integer(11));
			assertEquals(deviceAlarm.getZoneName(),"ewe");
			assertNotNull(deviceAlarm.toString());
			deviceAlarm.setCause(null);
			deviceAlarm.setResult(null);
			deviceAlarm.setOperation(null);
			deviceAlarm.setBranchName(null);
			deviceAlarm.setAlarmName(null);
			deviceAlarm.setZoneName(null);
			deviceAlarm.setDepartmentName(null);
			deviceAlarm.setDeviceId(null);
			assertEquals(deviceAlarm.getCause(),"");
			assertEquals(deviceAlarm.getResult(),"");
			assertEquals(deviceAlarm.getOperation(),"");
			assertEquals(deviceAlarm.getBranchName(),"");
			assertEquals(deviceAlarm.getAlarmName(),"");
			assertEquals(deviceAlarm.getZoneName(),"");
			assertEquals(deviceAlarm.getDepartmentName(),"");
			assertEquals(deviceAlarm.getDeviceId(),"");
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryZoneByDepartId() {
		try{
			alarmQueryServiceImp.queryZoneByDepartId("1");	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetZoneNameById() {
		try{
			alarmQueryServiceImp.getZoneNameById(2);	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testMonitorAlarmCount() {
		try{
			alarmQueryServiceImp.monitorAlarmCount();	
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQuerySecAlarmById() {
		try{
			alarmQueryServiceImp.querySecAlarmById(22);	
			assertTrue(true);
			SecurityAlarm sAlarm = new SecurityAlarm();
			sAlarm.setBranchId(null);
			sAlarm.setCheckMothod(1);
			sAlarm.setPictureUrl("sss");
			sAlarm.setAlarmName(null);
			sAlarm.setDeviceId(null);
			sAlarm.setZoneName(null);
			sAlarm.setDepartmentName(null);
			sAlarm.setBranchName(null);
			sAlarm.setType(1);
			sAlarm.setDepartmentId(null);
			sAlarm.setAlarmLevel(1);
			assertEquals(sAlarm.getBranchId(),"");
			assertEquals(sAlarm.getCheckMothod(),new Integer(1));
			assertEquals(sAlarm.getPictureUrl(),"sss");
			assertEquals(sAlarm.getAlarmName(),"");
			assertEquals(sAlarm.getDeviceId(),"");
			assertEquals(sAlarm.getZoneName(),"");
			assertEquals(sAlarm.getDepartmentName(),"");
			assertEquals(sAlarm.getBranchName(),"");
			assertEquals(sAlarm.getType(),new Integer(1));
			assertEquals(sAlarm.getDepartmentId(),"");
			assertEquals(sAlarm.getAlarmLevel(),new Integer(1));	
			sAlarm.setZoneName("a");
			sAlarm.setDepartmentName("b");
			sAlarm.setBranchName("c");
			assertEquals(sAlarm.getZoneName(),"a");
			assertEquals(sAlarm.getDepartmentName(),"b");
			assertEquals(sAlarm.getBranchName(),"c");
			assertNotNull(sAlarm.toString());
			
			AlarmDTO dto = new AlarmDTO();
			dto.setAlarmCode( 1);
			dto.setAlarmID(1);
			dto.setAlarmLevel("1");
			dto.setAlarmName("daf");
			dto.setAlarmStatus("1");
			dto.setAlarmTime(null);
			dto.setAlarmType("1");
			dto.setBranchName("a");
			dto.setDeviceType("1");
			dto.setIp("10.3.17.1");
			dto.setMgtName("d");
			dto.setZoneName("b");
			assertEquals(dto.getAlarmCode(),new Integer(1));
			assertEquals(dto.getAlarmID(),new Integer(1));
			assertEquals(dto.getAlarmLevel(),"警告");
			assertEquals(dto.getAlarmName(),"daf");
			assertEquals(dto.getAlarmStatus(),"已处理");
			assertEquals(dto.getAlarmType(),"安防告警");
			assertEquals(dto.getBranchName(),"a");
			assertEquals(dto.getDeviceType(),"高压脉冲主机");
			assertEquals(dto.getIp(),"10.3.17.1");
			assertEquals(dto.getMgtName(),"d");
			assertEquals(dto.getZoneName(),"b");
			assertEquals(dto.getAlarmTime(),"");		
			alarmQueryDaoImp.monitorSecurityAlarmCount(sAlarm);
			assertTrue(true);
		}catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void teststatDevAlarmCountUnHandled() {
		try{
			List a = alarmQueryServiceImp.statDevAlarmCountUnHandled("2","010311");	
			System.out.println(a);
			assertTrue(true);
		}catch (Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
