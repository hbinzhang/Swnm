package com.actions.alarmmgt;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.entity.alarmmgt.AlarmKnowledge;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/alarmmgt-config/Context-db.xml",
		   "file:test/alarmmgt-config/Context-action.xml",
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmKnowledgeAction {

	@Autowired
	private AlarmKnowledgeAction alarmKnowledgeAction;
	
	@Test
	public void testQueryAllAlarmKnowledge1() {
		assertEquals(alarmKnowledgeAction.queryAllAlarmKnowledge(),"success");	
	}

	@Test
	public void testGoupdateAlarmKnowledge() {
		alarmKnowledgeAction.setAlarmCode(1);
		assertEquals(alarmKnowledgeAction.goupdateAlarmKnowledge(),"success");	
		assertEquals(alarmKnowledgeAction.getAlarmCode(),1);	
	}

	@Test
	public void testUpdateAlarmKnowledge1() {
		AlarmKnowledge alarmKnowledge = new AlarmKnowledge();
		alarmKnowledge.setAlarmCode(1);
		alarmKnowledge.setCause("");
		alarmKnowledge.setDeviceType(1);
		alarmKnowledge.setAlarmLevel(1);
		alarmKnowledge.setAlarmName("wew");
		alarmKnowledge.setAlarmType(1);
		alarmKnowledge.setInfo("");
		alarmKnowledge.setIsAffect(1);
		alarmKnowledge.setOperation("");
		alarmKnowledge.setResult("");
		alarmKnowledgeAction.setAlarmKnowledge(alarmKnowledge);
		assertEquals(alarmKnowledgeAction.updateAlarmKnowledge(),"success");	
		assertNotNull(alarmKnowledgeAction.getAlarmKnowledge());	
		assertNotNull(alarmKnowledgeAction.getCommonBean());	
	}

	@Test
	public void testQueryAllAlarmKnowledge2() {
		alarmKnowledgeAction.setAlarmKnowledgeService(null);
		assertEquals(alarmKnowledgeAction.queryAllAlarmKnowledge(),"error");		
	}
	
	@Test
	public void testUpdateAlarmKnowledge2() {
		AlarmKnowledge alarmKnowledge = new AlarmKnowledge();
		alarmKnowledge.setAlarmCode(1);
		alarmKnowledge.setCause("");
		alarmKnowledge.setDeviceType(1);
		alarmKnowledge.setAlarmLevel(1);
		alarmKnowledge.setAlarmName("wew");
		alarmKnowledge.setAlarmType(1);
		alarmKnowledge.setInfo("");
		alarmKnowledge.setIsAffect(1);
		alarmKnowledge.setOperation("");
		alarmKnowledge.setResult("");
		alarmKnowledgeAction.setAlarmKnowledge(alarmKnowledge);
		assertEquals(alarmKnowledgeAction.updateAlarmKnowledge(),"success");	
		alarmKnowledgeAction.setAlarmKnowledgeService(null);
		assertEquals(alarmKnowledgeAction.updateAlarmKnowledge(),"error");
		assertEquals(alarmKnowledgeAction.goupdateAlarmKnowledge(),"error");
		
		assertNull(alarmKnowledgeAction.getAlarmKnowledgeService());	
		alarmKnowledgeAction.setAlarmKnowledgeList(null);
		alarmKnowledgeAction.setAlarmKnowledge(null);
		alarmKnowledgeAction.setCommonBean(null);
		alarmKnowledgeAction.setOperationLogService(null);
		assertNull(alarmKnowledgeAction.getAlarmKnowledgeList());	
		assertNull(alarmKnowledgeAction.getAlarmKnowledge());	
		assertNull(alarmKnowledgeAction.getCommonBean());	
		assertNull(alarmKnowledgeAction.getOperationLogService());	
	}
	
}
