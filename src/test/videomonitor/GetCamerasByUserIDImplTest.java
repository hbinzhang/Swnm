package test.videomonitor;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.entity.videomonitor.TVmIpc;
import com.service.videomonitor.impl.GetCamerasByUserIDImpl;

public class GetCamerasByUserIDImplTest {

	private static ApplicationContext ctx;
	private GetCamerasByUserIDImpl handle;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ctx = new FileSystemXmlApplicationContext("WebRoot//WEB-INF//Context-*.xml");
		handle = (GetCamerasByUserIDImpl) ctx.getBean("getCamerasByUserId");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCamerasByUserID() {
		//fail("Not yet implemented");
		System.out.println("###test1:guanlichu###");
		List<TVmIpc> ipcs1 = handle.getCamerasByUserID("5"); //guanlichu
		System.out.println("ipc1ID=" + ipcs1.get(0).getIpcid());
		System.out.println("ipc1Status=" + ipcs1.get(0).getIpcFault());
		System.out.println("###test2:fengongsi###");
		List<TVmIpc> ipcs2 = handle.getCamerasByUserID("6");//fengongsi
		System.out.println("ipc2ID=" + ipcs2.get(1).getIpcid());
		System.out.println("ipc2Status=" + ipcs2.get(1).getIpcFault());
		System.out.println("###test3:zonggongsi###");
		List<TVmIpc> ipcs3 = handle.getCamerasByUserID("1");//zonggongsi
		System.out.println("ipc3ID=" + ipcs3.get(2).getIpcid());
		System.out.println("ipc3Status=" + ipcs3.get(1).getIpcFault());
		System.out.println("###test4:kong###");
		List<TVmIpc> ipcs4 = handle.getCamerasByUserID("test");//kong
		if (ipcs4.isEmpty()) {
			System.out.println("result14= ipcs4 is empty");	
		} else {
			System.out.println("never reach");
		}
		
	}

}
