package com.service.logmgt.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/logmgt-config/Context-db.xml",
		   "file:test/logmgt-config/Context-service.xml",
		   "file:test/logmgt-config/Context-dao.xml"})
public class TestOperationLogServiceImp {

	@Autowired
	private OperationLogServiceImp operationLogServiceImp ;
	
	private OperationLog operationLog = new OperationLog();;
	
	private OperationLogCondition operationLogCondition = new OperationLogCondition();
	
	@Test
	public void testCreateOperationLog() {
		try{
			operationLogServiceImp.createOperationLog("test", "",
					1, "");
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
		
	}

	@Test
	public void testUpdateOperationLog() {
		operationLog.setOlsId(0);
		operationLog.setAccountId("1");
		operationLog.setCommandId(102010002);
		operationLog.setModuleId(2);
		operationLog.setOpResult(1);
		operationLog.setOrganizationId("010000");
		operationLog.setCommandName("修改告警基本信息");
		Date beginTime = new Date(115,5,10);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		Date endTime = new Date(115,6,1);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		operationLog.setEndTime(endTime);
		operationLog.setHostIp("10.3.17.88");
		operationLog.setHostName("www");
		operationLog.setModuleName("告警管理");
		operationLog.setOpDetail("ewewe");
		operationLog.setOperationObjects("wewe");
		operationLog.setOpTime(beginTime);
		operationLog.setOrgName("总公司");
		operationLogServiceImp.updateOperationLog(operationLog);
		assertEquals(0,operationLog.getOlsId());
		assertEquals("1",operationLog.getAccountId());
		assertEquals(new Integer(102010002),operationLog.getCommandId());
		assertEquals(new Integer(2),operationLog.getModuleId());
		assertEquals(new Integer(1),operationLog.getOpResult());
		assertEquals("010000",operationLog.getOrganizationId());
		assertEquals("修改告警基本信息",operationLog.getCommandName());
		assertNotNull(operationLog.getEndTime());
		assertEquals("10.3.17.88",operationLog.getHostIp());
		assertEquals("www",operationLog.getHostName());
		assertEquals("告警管理",operationLog.getModuleName());
		assertEquals("ewewe",operationLog.getOpDetail());
		assertEquals("wewe",operationLog.getOperationObjects());
		assertNotNull(operationLog.getOpTime());
		assertEquals("总公司",operationLog.getOrgName());
		assertNotNull(operationLog.toString());
		
		operationLog.setAccountId(null);
		operationLog.setModuleName(null);
		operationLog.setCommandName(null);
		operationLog.setOperationObjects(null);
		operationLog.setOrganizationId(null);
		operationLog.setOpDetail(null);
		operationLog.setHostIp(null);
		operationLog.setHostName(null);
		operationLog.setOrgName(null);
		assertEquals(operationLog.getAccountId(),"");
		assertEquals(operationLog.getModuleName(),"");
		assertEquals(operationLog.getCommandName(),"");
		assertEquals(operationLog.getOperationObjects(),"");
		assertEquals(operationLog.getOrganizationId(),"");
		assertEquals(operationLog.getOpDetail(),"");
		assertEquals(operationLog.getHostIp(),"");
		assertEquals(operationLog.getHostName(),"");
		assertEquals(operationLog.getOrgName(),"");
	}

	@Test
	public void testGetOperationLogId() {
		try{
			operationLogServiceImp.getOperationLogId();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetOperLogCountByOrgId() {
		try{
			operationLogCondition.setOrganizationId("010300");
			operationLogServiceImp.getOperLogCountByOrgId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}	
	}

	@Test
	public void testQueryOperLogByOrgId() {
		try{
			operationLogCondition.setOrganizationId("010300");
			operationLogServiceImp.queryOperLogByOrgId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetOperLogCountByAccId() {
		try{
			operationLogCondition.setAccountId("1");
			operationLogServiceImp.getOperLogCountByAccId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryOperLogByAccId() {
		try{
			operationLogCondition.setAccountId("1");
			operationLogServiceImp.queryOperLogByAccId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetOperLogCountByOrgsAndAccId() {
		try{
			operationLogCondition.setOrgans("('1','2','3')");
			operationLogServiceImp.getOperLogCountByOrgsAndAccId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryOperLogByOrgsAndAccId() {
		try{
			operationLogCondition.setOrgans("('1','2','3')");
			operationLogServiceImp.queryOperLogByOrgsAndAccId(operationLogCondition);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testDeleteOperLogById() {
		try{
			operationLogServiceImp.deleteOperLogById(1);
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

}
