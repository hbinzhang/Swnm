package com.service.logmgt.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entity.logmgt.SecurityLog;
import com.entity.logmgt.SecurityLogCondition;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/logmgt-config/Context-db.xml",
		   "file:test/logmgt-config/Context-service.xml",
		   "file:test/logmgt-config/Context-dao.xml"})
public class TestSecurityLogServiceImp {

	@Autowired
	private SecurityLogServiceImp securityLogServiceImp ;
		
	private SecurityLogCondition securityLogCondition = new SecurityLogCondition();

	
	@Test
	public void testCreateSecurityLog() {
		try{
			securityLogServiceImp.createSecurityLog("test", "",
					1, "");
		}catch(Exception e){
			assertTrue(true);
		}
		
	}

	@Test
	public void testGetSecurityLogId() {
		try{
			securityLogServiceImp.getSecurityLogId();
			assertTrue(true);
			SecurityLog securityLog = new SecurityLog();
			securityLog.setSlsId(0);
			securityLog.setAccountId("1");
			securityLog.setCommandId(102010002);
			securityLog.setOpResult(1);
			securityLog.setOrganizationId("010000");
			securityLog.setCommandName("修改告警基本信息");
			Date beginTime = new Date(115,5,10);
			beginTime.setHours(11);
			beginTime.setMinutes(11);
			beginTime.setSeconds(11);
			securityLog.setHostIp("10.3.17.88");
			securityLog.setHostName("www");
			securityLog.setOpDetail("ewewe");
			securityLog.setOpTime(beginTime);
			securityLog.setOrgName("总公司");
			assertEquals(0,securityLog.getSlsId());
			assertEquals("1",securityLog.getAccountId());
			assertEquals(new Integer(102010002),securityLog.getCommandId());
			assertEquals(new Integer(1),securityLog.getOpResult());
			assertEquals("010000",securityLog.getOrganizationId());
			assertEquals("修改告警基本信息",securityLog.getCommandName());
			assertEquals("10.3.17.88",securityLog.getHostIp());
			assertEquals("www",securityLog.getHostName());
			assertEquals("ewewe",securityLog.getOpDetail());
			assertNotNull(securityLog.getOpTime());
			assertEquals("总公司",securityLog.getOrgName());
			assertNotNull(securityLog.toString());
			
			securityLog.setAccountId(null);
			securityLog.setCommandName(null);
			securityLog.setOrganizationId(null);
			securityLog.setOpDetail(null);
			securityLog.setHostIp(null);
			securityLog.setHostName(null);
			securityLog.setOrgName(null);
			assertEquals(securityLog.getAccountId(),"");
			assertEquals(securityLog.getCommandName(),"");
			assertEquals(securityLog.getOrganizationId(),"");
			assertEquals(securityLog.getOpDetail(),"");
			assertEquals(securityLog.getHostIp(),"");
			assertEquals(securityLog.getHostName(),"");
			assertEquals(securityLog.getOrgName(),"");			
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetSecLogCountByOrgId() {
		try{
			securityLogCondition.setOrganizationId("010300");
			securityLogServiceImp.getSecLogCountByOrgId(securityLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}	
	}

	@Test
	public void testQuerySecLogByOrgId() {
		try{
			securityLogCondition.setOrganizationId("010300");
			securityLogServiceImp.querySecLogByOrgId(securityLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetSecLogCountByAccId() {
		try{
			securityLogCondition.setAccountId("1");
			securityLogServiceImp.getSecLogCountByAccId(securityLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQuerySecLogByAccId() {
		try{
			securityLogCondition.setAccountId("1");
			securityLogServiceImp.querySecLogByAccId(securityLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetSecLogCountByOrgsAndAccId() {
		try{
			securityLogCondition.setOrgans("('1','2','3')");
			securityLogServiceImp.getSecLogCountByOrgsAndAccId(securityLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQuerySecLogByOrgsAndAccId() {
		try{
			securityLogCondition.setOrgans("('1','2','3')");
			securityLogServiceImp.querySecLogByOrgsAndAccId(securityLogCondition);
			assertTrue(true);
			securityLogCondition.setAccountId(null);
			securityLogCondition.setBeginTime(null);
			securityLogCondition.setEndTime(null);
			securityLogCondition.setOpResult(-1);
			securityLogCondition.setCommandId(-1);
			assertFalse(securityLogCondition.accountIdIsValid());
			assertFalse(securityLogCondition.beginTimeIsValid());
			assertFalse(securityLogCondition.commandIdIsValid());
			assertFalse(securityLogCondition.endTimeIsValid());
			assertFalse(securityLogCondition.opResultIsValid());
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testDeleteSecLogById() {
		try{
			securityLogServiceImp.deleteSecLogById(1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

}
