package com.util.logmgt;

import static org.junit.Assert.*;

import org.junit.Test;

import com.entity.logmgt.CommandInfo;

public class TestCommandParser {

	private CommandParser cp = CommandParser.getInstance();

	@Test
	public void testGetDetail() {
		CommandInfo c = cp.getDetail(102010002);
		assertEquals(c.getName(),"updateAlarmKnowledge");
		assertEquals(c.getDisplayName(),"修改告警基本信息");
		c.setIsAsynch("false");
		assertEquals(c.getIsAsynch(),"false");
		c.setLogType(0);
		assertEquals(c.getLogType(),0);
		assertNotNull(c.toString());
	}

	@Test
	public void testGetDetailByName() {
		CommandInfo c = cp.getDetailByName("updateAlarmKnowledge");
		assertEquals(c.getCommandId(),102010002);
	}

	@Test
	public void testGetAllCommand() {
		assertNotNull(cp.getAllCommand());
	}

	@Test
	public void testGetCommandsByModule() {
		assertEquals(cp.getCommandsByModule(2).size(), 4);
	}

	@Test
	public void testGetModuleWithCommandMap() {
		assertNotNull(cp.getModuleWithCommandMap());
	}

}
