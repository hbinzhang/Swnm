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
import com.entity.logmgt.SecurityLogCondition;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		 	"file:test/logmgt-config/Context-action.xml",
		   "file:test/logmgt-config/Context-db.xml",
		   "file:test/logmgt-config/Context-service.xml",
		   "file:test/logmgt-config/Context-dao.xml"})
public class TestSecurityLogAction {

	@Autowired
	private SecurityLogAction securityLogAction;
	
	@Test
	public void testInitSecurityLog() {
		assertEquals("error",securityLogAction.initSecurityLog());
	}

	@Test
	public void testQuerySecurityLog() {
		Date beginTime = new Date(115,5,10);
		beginTime.setHours(11);
		beginTime.setMinutes(11);
		beginTime.setSeconds(11);
		Date endTime = new Date(115,6,1);
		endTime.setHours(11);
		endTime.setMinutes(11);
		endTime.setSeconds(11);
		SecurityLogCondition securityLogCondition = new SecurityLogCondition();
		securityLogCondition.setAccountId("1");
		securityLogCondition.setBeginTime(beginTime);
		securityLogCondition.setCommandId(102010002);
		securityLogCondition.setEnd(10000);
		securityLogCondition.setEndTime(endTime);
		securityLogCondition.setFlag(1);
		securityLogCondition.setOpResult(1);
		securityLogCondition.setOrganizationId("010000");
		securityLogCondition.setOrgans("");
		securityLogCondition.setOrgLev("0");
		securityLogCondition.setSearchUri("");
		securityLogCondition.setStart(1);
		assertEquals("error",securityLogAction.querySecurityLog());
		assertEquals("1",securityLogCondition.getAccountId());
		assertNotNull(securityLogCondition.getBeginTime());
		assertEquals(102010002,securityLogCondition.getCommandId());
		assertEquals(10000,securityLogCondition.getEnd());
		assertEquals(1,securityLogCondition.getFlag());
		assertEquals(1,securityLogCondition.getOpResult());
		assertEquals("010000",securityLogCondition.getOrganizationId());
		assertEquals(0,securityLogCondition.getOrgans().length());
		assertEquals("0",securityLogCondition.getOrgLev());
		assertNotNull(securityLogCondition.getSearchUri());
		assertEquals(1,securityLogCondition.getStart());
		assertTrue(securityLogCondition.accountIdIsValid());
		assertTrue(securityLogCondition.beginTimeIsValid());
		assertTrue(securityLogCondition.endTimeIsValid());
		assertTrue(securityLogCondition.opResultIsValid());
		assertNotNull(securityLogCondition.toString());
	}

	@Test
	public void testDeleteSecurityLog1() {
		assertEquals("error",securityLogAction.deleteSecurityLog());
	}

	@Test
	public void testDeleteSecurityLog2() {
		securityLogAction.setAccountId("1");
		securityLogAction.setAjaxObject(null);
		securityLogAction.setBranchList(null);
		securityLogAction.setCommonBean(null);
		securityLogAction.setCompanyList(null);
		securityLogAction.setDepartmentList(null);
		securityLogAction.setOffset(10);
		securityLogAction.setSecurityLogCondition(null);
		securityLogAction.setSecurityLogList(null);
		securityLogAction.setOperationLogService(null);
		securityLogAction.setOrganizationList(null);
		securityLogAction.setOrganManagerService(null);
		securityLogAction.setPager(null);
		securityLogAction.setUserLevel(null);
		securityLogAction.setSecurityLogService(null);
		assertEquals(securityLogAction.getAccountId(),"1");
		assertNull(securityLogAction.getAjaxObject());
		assertNull(securityLogAction.getBranchList());
		assertNull(securityLogAction.getCommonBean());
		assertNull(securityLogAction.getCompanyList());
		assertNull(securityLogAction.getDepartmentList());
		assertEquals(securityLogAction.getOffset(),10);
		assertNull(securityLogAction.getSecurityLogCondition());
		assertNull(securityLogAction.getSecurityLogList());
		assertNull(securityLogAction.getOperationLogService());
		assertNull(securityLogAction.getOrganizationList());
		assertNull(securityLogAction.getOrganManagerService());
		assertNull(securityLogAction.getPager());
		assertNull(securityLogAction.getUserLevel());
		assertNull(securityLogAction.getSecurityLogService());
	}
}
