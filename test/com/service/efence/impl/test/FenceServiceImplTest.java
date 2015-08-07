package com.service.efence.impl.test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.entity.efence.FenceBean;
import com.service.efence.impl.FenceServiceImpl;

public class FenceServiceImplTest {
	
	private static  FenceServiceImpl fenceService;
	
	private	static  ApplicationContext ctx;

	@BeforeClass
	public static void setUp() throws Exception {
		
		ctx = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/Context-*.xml");
		fenceService = (FenceServiceImpl) ctx.getBean("fenceManagerServiceImpl");
	}
	@Ignore
	@Test
	public void testUpdateFenceStatus() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetFenceByID() throws Exception {
		FenceBean fence = new FenceBean();
		fence.setHostID("10009");
		assertNotNull(fenceService.getFenceByID(fence));
		
	}
	
	@Test
	public void testAddFence() throws Exception{
		FenceBean fence = new FenceBean();
		fence.setHostID("10008");
		fence.setFenceName("weilan");
		fence.setFenceType(3);
		fence.setIp("10.3.10.109");
		fence.setPort(6009);
		int result = fenceService.addFence(fence);
		//assertEquals(0, result);
		assertEquals("10008",fenceService.getFenceByID(fence).getHostID());
	}
	@Ignore
	@Test
	public void testDelFence() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testModFence() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testGetAllFenceHostID() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testFindAlarmsByDeviceID() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testGetFencesBySubComIdOrMngId() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testHandleRemoteJmsMsg() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testFindSubComByMngID() {
		fail("Not yet implemented");
	}

}
