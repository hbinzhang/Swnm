package com.actions.logmgt;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entity.logmgt.OperationLogCondition;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		 	"file:test/logmgt-config/Context-action.xml",
		   "file:test/logmgt-config/Context-db.xml",
		   "file:test/logmgt-config/Context-service.xml",
		   "file:test/logmgt-config/Context-dao.xml"})
public class TestOperationLogAction {

	@Autowired
	private OperationLogAction operationLogAction;

	
	@Test
	public void testInitOperationLog() {
		assertEquals("error",operationLogAction.initOperationLog());
	}

	@Test
	public void testQueryOperationLog() {
		Date beginTime = new Date(115,5,10);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		Date endTime = new Date(115,6,1);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		OperationLogCondition operationLogCondition = new OperationLogCondition();	
		operationLogCondition.setAccountId("1");
		operationLogCondition.setBeginTime(beginTime);
		operationLogCondition.setCommandId(102010002);
		operationLogCondition.setEnd(10000);
		operationLogCondition.setEndTime(endTime);
		operationLogCondition.setFlag(1);
		operationLogCondition.setModuleId(2);
		operationLogCondition.setOpResult(1);
		operationLogCondition.setOrganizationId("010000");
		operationLogCondition.setOrgans("");
		operationLogCondition.setOrgLev("0");
		operationLogCondition.setSearchUri("");
		operationLogCondition.setStart(1);
		operationLogAction.setOperationLogCondition(operationLogCondition);
		assertEquals("error",operationLogAction.queryOperationLog());
		assertEquals("1",operationLogCondition.getAccountId());
		assertNotNull(operationLogCondition.getBeginTime());
		assertEquals(102010002,operationLogCondition.getCommandId());
		assertEquals(10000,operationLogCondition.getEnd());
		assertEquals(1,operationLogCondition.getFlag());
		assertEquals(2,operationLogCondition.getModuleId());
		assertEquals(1,operationLogCondition.getOpResult());
		assertEquals("010000",operationLogCondition.getOrganizationId());
		assertEquals(0,operationLogCondition.getOrgans().length());
		assertEquals("0",operationLogCondition.getOrgLev());
		assertNotNull(operationLogCondition.getSearchUri());
		assertEquals(1,operationLogCondition.getStart());
		assertTrue(operationLogCondition.accountIdIsValid());
		assertTrue(operationLogCondition.beginTimeIsValid());
		assertTrue(operationLogCondition.endTimeIsValid());
		assertTrue(operationLogCondition.opResultIsValid());
		assertNotNull(operationLogCondition.toString());
	}

	@Test
	public void testDeleteOperationLog1() {
		assertEquals("error",operationLogAction.deleteOperationLog());
	}
	
	@Test
	public void testDeleteOperationLog2() {
		operationLogAction.setAccountId("1");
		operationLogAction.setAjaxObject(null);
		operationLogAction.setBranchList(null);
		operationLogAction.setCommonBean(null);
		operationLogAction.setCompanyList(null);
		operationLogAction.setDepartmentList(null);
		operationLogAction.setOffset(10);
		operationLogAction.setOperationLogCondition(null);
		operationLogAction.setOperationLogList(null);
		operationLogAction.setOperationLogService(null);
		operationLogAction.setOrganizationList(null);
		operationLogAction.setOrganManagerService(null);
		operationLogAction.setPager(null);
		operationLogAction.setUserLevel(null);
		assertEquals(operationLogAction.getAccountId(),"1");
		assertNull(operationLogAction.getAjaxObject());
		assertNull(operationLogAction.getBranchList());
		assertNull(operationLogAction.getCommonBean());
		assertNull(operationLogAction.getCompanyList());
		assertNull(operationLogAction.getDepartmentList());
		assertEquals(operationLogAction.getOffset(),10);
		assertNull(operationLogAction.getOperationLogCondition());
		assertNull(operationLogAction.getOperationLogList());
		assertNull(operationLogAction.getOperationLogService());
		assertNull(operationLogAction.getOrganizationList());
		assertNull(operationLogAction.getOrganManagerService());
		assertNull(operationLogAction.getPager());
		assertNull(operationLogAction.getUserLevel());
	}

}
