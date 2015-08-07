package com.util.logmgt;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestModuleAndObjectTypeConf {

	private ModuleAndObjectTypeConf mc = ModuleAndObjectTypeConf.getInstance();

	@Test
	public void testGetModuleMap() {
		assertNotNull(mc.getModuleMap());
	}

}
