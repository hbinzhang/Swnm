package com.actions.alarmmgt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.util.alarmmgt.AlarmConstants;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-action.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmQueryAction {
	@Autowired
	private AlarmQueryAction alarmQueryAction;
	private String jsonStr1 = "{'type':'1'}";
	
	private String jsonStr6 = "{'type':'1','offset':'1000000'}";

	private String jsonStr2 = "{'type':'2'}";
	
	private String jsonStr3 = "{'type':'-1'}";

	private String jsonStr4 = "{'type':'1','deviceId':'9999999','alarmName':'aaa','departmentId':'38922','branchId':'4342'}";

	private String jsonStr5 = "{'type':'2','deviceId':'9999999','alarmName':'aaa','departmentId':'38922','branchId':'4342'}";

	@Test
	public void testInitAlarm() {
		alarmQueryAction.initAlarm();	
		assertEquals(alarmQueryAction.getHasEventVideo(),0);	
		assertNull(alarmQueryAction.getUserLevel());	
		assertNull(alarmQueryAction.getBranchList());	
		assertNull(alarmQueryAction.getDepartmentList());	
		assertNull(alarmQueryAction.getZoneList());	
		assertNotNull(alarmQueryAction.getCommonBean());
	}
	
	@Test
	public void testQueryDepartByBranch() {
		alarmQueryAction.setBranchId("01");
		assertEquals(alarmQueryAction.getBranchId(),"01");	
		assertEquals(alarmQueryAction.queryDepartByBranch(),"error");	
	}

	@Test
	public void testQueryZoneByDepart1() {
		alarmQueryAction.setDepartmentId("1");
		assertEquals(alarmQueryAction.getDepartmentId(),"1");	
		assertEquals(alarmQueryAction.queryZoneByDepart(),"success");	
	}
	
	@Test
	public void testQueryAlarmNameByDevType1() {
		alarmQueryAction.setDeviceTypeId(1);
		assertEquals(alarmQueryAction.getDeviceTypeId(),1);	
		assertEquals(alarmQueryAction.queryAlarmNameByDevType(),"success");	
		assertNull(alarmQueryAction.getAlarmNameList());
	}

	@Test
	public void testQueryAlarm1() {
		alarmQueryAction.setJsonStr(jsonStr1);
		alarmQueryAction.queryAlarm();
		assertEquals(alarmQueryAction.getJsonStr(),"{'type':'1'}");	
		assertEquals(alarmQueryAction.getAjaxObject().getResult(),AlarmConstants.RESULT_SUC);
	}
	
	@Test
	public void testQueryAlarm6() {
		alarmQueryAction.setJsonStr(jsonStr6);
		alarmQueryAction.queryAlarm();
		assertEquals(alarmQueryAction.getAjaxObject().getResult(),AlarmConstants.RESULT_SUC);
	}

	@Test
	public void testQueryAlarm2() {
		alarmQueryAction.setJsonStr(jsonStr2);
		assertEquals(alarmQueryAction.queryAlarm(),"success");	
	}
	
	@Test
	public void testQueryAlarm3() {
		alarmQueryAction.setJsonStr(jsonStr3);
		alarmQueryAction.queryAlarm();
		assertEquals(alarmQueryAction.getAjaxObject().getResult(),AlarmConstants.RESULT_FAIL);
	}
	
	@Test
	public void testQueryAlarm4() {
		alarmQueryAction.setJsonStr(jsonStr4);
		alarmQueryAction.queryAlarm();
		assertEquals(alarmQueryAction.queryAlarm(),"success");	
	}
	
	@Test
	public void testQueryAlarm5() {
		alarmQueryAction.setJsonStr(jsonStr5);
		alarmQueryAction.queryAlarm();
		assertEquals(alarmQueryAction.queryAlarm(),"success");	
	}
	
	@Test
	public void testExportAlarm1() {
		alarmQueryAction.setJsonStr(jsonStr1);
		alarmQueryAction.setType(1);
		alarmQueryAction.exportAlarm();
		assertNotNull(alarmQueryAction.getFileName());
		assertNotNull(alarmQueryAction.getInputStream());	
	}

	@Test
	public void testExportAlarm2() {
		alarmQueryAction.setJsonStr(jsonStr2);
		alarmQueryAction.setType(2);
		assertEquals(alarmQueryAction.getType(),2);	
		assertEquals(alarmQueryAction.exportAlarm(),"success");	
	}
	
	@Test
	public void testExportAlarm4() {
		alarmQueryAction.setJsonStr(jsonStr4);
		alarmQueryAction.setType(1);
		assertEquals(alarmQueryAction.exportAlarm(),"success");	
	}
	
	@Test
	public void testExportAlarm5() {
		alarmQueryAction.setJsonStr(jsonStr5);
		alarmQueryAction.setType(2);
		assertEquals(alarmQueryAction.exportAlarm(),"success");	
	}
	
	@Test
	public void testQueryAlarmPictureUrl() {
		alarmQueryAction.setAlarmId(19467);
		alarmQueryAction.queryAlarmPictureUrl();	
		assertEquals(alarmQueryAction.getAlarmId(),19467);	
		assertEquals(alarmQueryAction.getPictureUrl().size(),0);
	}

	@Test
	public void testQueryAlarmVideoUrl() {
		alarmQueryAction.setAlarmId(200);
		alarmQueryAction.queryAlarmVideoUrl();
		assertNull(alarmQueryAction.getVideoAlarm());	
	}

	@Test
	public void testGetAlarmKnowledgeService() {
		assertNotNull(alarmQueryAction.getAlarmKnowledgeService());	
	}
	
	@Test
	public void testGetAlarmQueryService() {
		assertNotNull(alarmQueryAction.getAlarmQueryService());	
	}
	
	@Test
	public void testGetOperationLogService() {
		assertNotNull(alarmQueryAction.getOperationLogService());	
	}
	
	@Test
	public void testGetOrganManagerService() {
		assertNotNull(alarmQueryAction.getOrganManagerService());	
	}
	
	@Test
	public void testQueryZoneByDepart2() {
		alarmQueryAction.setDepartmentId("1");
		alarmQueryAction.setAlarmQueryService(null);
		alarmQueryAction.setAlarmKnowledgeService(null);
		assertEquals(alarmQueryAction.queryZoneByDepart(),"error");		
		assertEquals(alarmQueryAction.queryAlarmNameByDevType(),"error");	
		assertNull(alarmQueryAction.getAlarmQueryService());
		assertNull(alarmQueryAction.getAlarmKnowledgeService());
		assertEquals(alarmQueryAction.queryAlarmPictureUrl(),"error");
		assertEquals(alarmQueryAction.queryAlarmVideoUrl(),"error");
		
		alarmQueryAction.setAjaxObject(null);
		assertNull(alarmQueryAction.getAjaxObject());
		alarmQueryAction.setBranchList(null);
		alarmQueryAction.setDepartmentList(null);
		alarmQueryAction.setZoneList(null);
		alarmQueryAction.setAlarmNameList(null);
		alarmQueryAction.setCommonBean(null);
		alarmQueryAction.setVideoAlarm(null);
		alarmQueryAction.setUserLevel(null);
		alarmQueryAction.setPictureUrl(null);
		alarmQueryAction.setInputStream(null);
		alarmQueryAction.setHasEventVideo(1);
		alarmQueryAction.setPictureAlarm(null);
		alarmQueryAction.setFileName("ss.xls");
		assertNull(alarmQueryAction.getBranchList());
		assertNull(alarmQueryAction.getDepartmentList());
		assertNull(alarmQueryAction.getZoneList());
		assertNull(alarmQueryAction.getAlarmNameList());
		assertNull(alarmQueryAction.getCommonBean());
		assertNull(alarmQueryAction.getVideoAlarm());
		assertNull(alarmQueryAction.getUserLevel());
		assertNull(alarmQueryAction.getPictureUrl());
		assertNull(alarmQueryAction.getInputStream());
		assertEquals(alarmQueryAction.getHasEventVideo(), 1);
		assertNull(alarmQueryAction.getPictureAlarm());
		assertEquals(alarmQueryAction.getFileName(), "ss.xls");
	}
	
	
	@Test
	public void testQueryAlarm7() {
		alarmQueryAction.setJsonStr(jsonStr1);
		alarmQueryAction.setAlarmQueryService(null);
		assertEquals(alarmQueryAction.queryAlarm(),"error");
	}
	
	@Test
	public void testQueryAlarm8() {
		alarmQueryAction.setJsonStr(jsonStr2);
		alarmQueryAction.setAlarmQueryService(null);
		assertEquals(alarmQueryAction.queryAlarm(),"error");	
	}
	
}
