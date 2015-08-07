package test.linkagectl;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import sun.misc.BASE64Encoder;



import com.service.linkagectl.impl.Base64ToJPG;

public class Base64ToJPGTest {


	private Base64ToJPG base64ToJPG;
	private static ApplicationContext ctx;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ctx = new FileSystemXmlApplicationContext("WebRoot//WEB-INF//Context-*.xml");
		base64ToJPG = (Base64ToJPG) ctx.getBean("base64ToJpg");
	}

	@After
	public void tearDown() throws Exception {
	}

	//jpg to base64
	private String getImageStr(String imgFile) {
			
			InputStream in = null;
			byte[] data = null;
			try {
				in = new FileInputStream(imgFile);
				data = new byte[in.available()];
				in.read(data);
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return "-1";
			}
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		}
		
	@Test
	public void testBase64ToJPG() {
		//fail("Not yet implemented");
		int alarmID = 1;
		String path = System.getProperty("user.dir") + "/WebRoot";
		ArrayList<String> imgList = new ArrayList();
		
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test1.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test2.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test3.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test4.bmp"));
		
		StringBuffer result = base64ToJPG.base64ToJPG(alarmID, path, imgList);
		System.out.println("result= " + result);
		
	    imgList.clear();
	    System.out.println("empty imgList result2=" + base64ToJPG.base64ToJPG(alarmID, path, imgList));
	  
	    imgList.clear();
	    System.out.println("test3");
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test1.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test2.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test3.bmp"));
		imgList.add("");
	    System.out.println("empty path result3=" + base64ToJPG.base64ToJPG(alarmID, "", imgList));	 
	    System.out.println("empty element result4=" + base64ToJPG.base64ToJPG(alarmID, path, imgList));
	   
	    imgList.clear();
		imgList.add("");
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test2.bmp"));
		imgList.add(getImageStr(path + "/resources/alarmimage/orgPic/test3.bmp"));
		System.out.println("empty element result5=" + base64ToJPG.base64ToJPG(alarmID, path, imgList));
	}

}
