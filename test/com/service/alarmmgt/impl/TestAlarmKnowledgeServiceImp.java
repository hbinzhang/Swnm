package com.service.alarmmgt.impl;

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
		   "file:test/alarmmgt-config/Context-service.xml",
		   "file:test/alarmmgt-config/Context-dao.xml"})
public class TestAlarmKnowledgeServiceImp {

	@Autowired
	private AlarmKnowledgeServiceImp alarmKnowledgeServiceImp;
	
	private AlarmKnowledge alarmKnowledge = new AlarmKnowledge();

	@Test
	public void testQueryAllAlarmKnowledge() {
		assertNotNull(alarmKnowledgeServiceImp.queryAllAlarmKnowledge());	
	}

	@Test
	public void testUpdateAlarmKnowledge() {
		try{
			alarmKnowledge.setAlarmCode(101);
			alarmKnowledge.setCause("");
			alarmKnowledge.setResult("");
			alarmKnowledge.setInfo("");
			alarmKnowledge.setOperation("");
			alarmKnowledgeServiceImp.updateAlarmKnowledge(alarmKnowledge);	
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testQueryAlaKnowByKnowledge() {
		alarmKnowledge.setAlarmCode(101);
		alarmKnowledge.setCause("");
		alarmKnowledge.setResult("");
		alarmKnowledge.setInfo("");
		alarmKnowledge.setOperation("");
		assertNotNull(alarmKnowledgeServiceImp.queryAlaKnowByKnowledge(alarmKnowledge));
		alarmKnowledge.setAlarmName(null);
		alarmKnowledge.setAlarmLevel(1);
		alarmKnowledge.setCause(null);
		alarmKnowledge.setOperation(null);
		alarmKnowledge.setResult(null);
		alarmKnowledge.setIsAffect(0);
		assertEquals(alarmKnowledge.getAlarmName(),"");
		assertEquals(alarmKnowledge.getAlarmLevel(),new Integer(1));
		assertEquals(alarmKnowledge.getCause(),"");
		assertEquals(alarmKnowledge.getOperation(),"");
		assertEquals(alarmKnowledge.getResult(),"");
		assertEquals(alarmKnowledge.getIsAffect(),new Integer(0));
	}

	@Test
	public void testQueryAlarmKnowledgeByCode() {
		assertNotNull(alarmKnowledgeServiceImp.queryAlarmKnowledgeByCode(101));	
	}

}
