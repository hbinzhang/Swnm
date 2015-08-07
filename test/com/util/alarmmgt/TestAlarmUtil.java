package com.util.alarmmgt;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.entity.CommonBean;
import com.util.logmgt.LogUtil;

public class TestAlarmUtil {
	
	private AlarmUtil alarmUtil = new AlarmUtil();
	
	@Test
	public void testInitNameMap() {
		try{
			AlarmUtil.initNameMap();
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testGetListByMap() {
		assertEquals(AlarmUtil.getListByMap(LogUtil.organizationMap).size(),3);
	}

	@Test
	public void testGetNameByKey() {
		assertEquals(AlarmUtil.getNameByKey(AlarmUtil.affectMap, 1),"影响");
	}

	@Test
	public void testGetSqlByList() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		assertEquals(AlarmUtil.getSqlByList(list),"('1','2')");
	}

	@Test
	public void testFormatString1() {
		assertEquals(AlarmUtil.formatString("ssss"),"ssss");
	}

	@Test
	public void testFormatString2() {
		assertEquals(AlarmUtil.formatString(null),"");
	}
	
	@Test
	public void testDate2String1() {		
		Date date = new Date(114,0,1);
		date.setHours(11);
		date.setMinutes(11);
		date.setSeconds(11);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		assertEquals(AlarmUtil.date2String(date, df),"2014-01-01 11:11:11");
	}

	@Test
	public void testDate2String2() {
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		assertEquals(AlarmUtil.date2String(date, df),"");
	}

	@Test
	public void testCreateCell1() {
		try{
			HSSFWorkbook wb = new HSSFWorkbook();		
			HSSFSheet sheet = wb.createSheet("Sheet1");     
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = alarmUtil.createTitleStyle(wb);   
			alarmUtil.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
			        "告警编号");   
			alarmUtil.createCell(row, 0, style, HSSFCell.CELL_TYPE_BLANK,
			        "");  
			alarmUtil.createCell(row, 0, style, HSSFCell.CELL_TYPE_NUMERIC,
			        333.22);  
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}

	@Test
	public void testCreateCell2() {
		try{
			XSSFWorkbook wb = new XSSFWorkbook();		
			XSSFSheet sheet = wb.createSheet("Sheet1");     
			XSSFRow row = sheet.createRow(0);
			XSSFCellStyle style = alarmUtil.createTitleStyle(wb);   
			alarmUtil.createCell(row, 0, style, XSSFCell.CELL_TYPE_STRING,
			        "告警编号");   
			alarmUtil.createCell(row, 0, style, XSSFCell.CELL_TYPE_BLANK,
			        "");  
			alarmUtil.createCell(row, 0, style, XSSFCell.CELL_TYPE_NUMERIC,
			        333.22);  
			assertTrue(true);
		}catch(Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetSubList() {
		List<CommonBean> list = AlarmUtil.getListByMap(LogUtil.organizationMap);
		List l = AlarmUtil.getSubList(list, 1, 2);
		assertEquals(l.size(),2);
	}

	@Test
	public void testGetLoginSession() {
		try{
			AlarmUtil.getLoginSession();
			assertTrue(false);
		}catch(Exception e){
			assertTrue(true);
		}
	}

}
