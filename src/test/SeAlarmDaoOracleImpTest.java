package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.impl.SeAlarmDaoOracleImp;
import com.entity.linkagectl.Alarm;
import com.entity.linkagectl.SecurityAlarm;

import com.dao.linkagectl.impl.SeAlarmDaoOracleImp;
import com.entity.linkagectl.Alarm;
import com.entity.linkagectl.SecurityAlarm;

public class SeAlarmDaoOracleImpTest {

	private SqlMapClientTemplate sqlmapclienttemplate=null;
	private SeAlarmDaoOracleImp seAlarmDaoOracleImp;
	private static ApplicationContext ctx;
	@Before 
	public void setUp() throws Exception {

		//ctx = new ClassPathXmlApplicationContext("Context-dao.xml");
		ctx = new FileSystemXmlApplicationContext("WebRoot//WEB-INF//Context-*.xml");
		seAlarmDaoOracleImp = (SeAlarmDaoOracleImp) ctx.getBean("seAlarmDaoOracleImp");
	}
	
	@Test
	public void testsave(){
		 Alarm alarm = new SecurityAlarm();		 
		 alarm.setZoneID(1);
		 alarm.setBranchID("1");
		 alarm.setDepartmentID("1");
		 alarm.setDeviceID("1");
		 alarm.setAlarmCode(1);
		 alarm.setAlarmTime(new java.util.Date());
		 alarm.setManagerTime(new java.util.Date());
		 try{
			 seAlarmDaoOracleImp.save(alarm);
		 }
		
		 catch(SQLException e){
			 System.out.println("catch database exception success!");
			 return ; 
		  }
		 catch(Exception e){			  
			 e.printStackTrace(); 
			 fail();
		  }
	}
	@Test
	public void testfindAlarm(){
		
		 try{
			 SecurityAlarm alarm = (SecurityAlarm)seAlarmDaoOracleImp.findAlarm(42);
			 assertNull(alarm);
		 }
		
		 catch(SQLException e){
			 System.out.println("catch database exception success!");
			 return ; 
		  }
		 catch(Exception e){			  
			 e.printStackTrace(); 
			 fail();
		  }
	}
	
	@Test
	public void testfindall(){
		
		 try{
			 List<SecurityAlarm> list= seAlarmDaoOracleImp.findall();
			 assertEquals(list.size(),0);
		 }
		
		 catch(SQLException e){
			 System.out.println("catch database exception success!");
			 return ; 
		  }
		 catch(Exception e){			  
			 e.printStackTrace(); 
			 fail();
		  }
	}
	

}
