package com.util.zone.handler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.entity.common.ExcelConfigInfo;
import com.entity.linkagectl.SoundZoneMap;
import com.util.zone.ZoneConvertUtil;

public class SoundZoneMapHandler implements ZoneCommonHandler {
 
	private final static Log log = LogFactory.getLog(SoundZoneMapHandler.class);
	
	@Override
	public void parse2003Excel(HSSFSheet sheet,
			Map<String, List<List<Object>>> map, Map<String, Object> errorMessage) {
		//存放内容错误的行号
		List<String> errorRowMeg = new ArrayList<String>();
		
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		//存放所有行数据
		List<List<Object>> linkeds = new LinkedList<List<Object>>();
		// 迭代行数据
		for (int i = sheet.getFirstRowNum(); i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);

			// 如果行数据为空或者是第一行数据（标题行）则continue
			if (row == null || i == 0) {
				continue;
			} else {
				counter++;
			}
			//存放一行数据
			List<Object> linked = new LinkedList<Object>();

			// 迭代列数据
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add(null);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					System.out.println(i + "行" + j + " 列 is String type");
					value = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					System.out.println(i + "行" + j
							+ " 列 is Number type ; DateFormt:"
							+ cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println(i + "行" + j + " 列 is Boolean type");
					value = cell.getBooleanCellValue();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					System.out.println(i + "行" + j + " 列 is Blank type");
					value = "";
					break;
				default:
					System.out.println(i + "行" + j + " 列 is default type");
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					linked.add(null);
					continue;
				}
				linked.add(value);
			}
			System.out.println(linked);
			if(linked.get(0)==null){
				continue;
			}
			/**
			 * 此处抛出异常，可以检查整型数据是否被赋值字符串类型
			 */
			SoundZoneMap soundZoneMap=null;
			try {
				soundZoneMap = ZoneConvertUtil.getSoundZoneMap(linked);
			} catch (Exception e) {
				errorRowMeg.add("音频映射信息:第"+(i+1)+"行:数据类型错误\r\n");
				log.error(sheet.getSheetName()+":"+(i+1)+ "行" +"数据转换错误："+e);
				continue;
			}
			//内容校验
			checkSoundZoneMap(soundZoneMap,errorRowMeg,i);
			//如果文件检查到有错误行，则不再往大List中存数据
			if(errorRowMeg.size()<=0)
			linkeds.add(linked);
		}
		if(errorRowMeg.size()>0){
			//内容校验有错
			errorMessage.put(ExcelConfigInfo.ZONE_SOUND_DATA_ERROR, errorRowMeg);
		}else{
			map.put(ExcelConfigInfo.ZONE_SOUND_DATA_NAME, linkeds);
		}
	}

	@Override
	public void parse2007Excel(XSSFSheet sheet,
			Map<String, List<List<Object>>> map, Map<String, Object> errorMessage) {
		//存放内容错误的行号
		List<String> errorRowMeg = new ArrayList<String>();
		
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		//存放所有行数据
		List<List<Object>> linkeds = new LinkedList<List<Object>>();
		// 迭代行数据
		for (int i = sheet.getFirstRowNum(); i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);

			// 如果行数据为空或者是第一行数据（标题行）则continue
			if (row == null || i == 0) {
				continue;
			} else {
				counter++;
			}
			//存放一行数据
			List<Object> linked = new LinkedList<Object>();

			// 迭代列数据
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add(null);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					System.out.println(i + "行" + j + " 列 is String type");
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					System.out.println(i + "行" + j
							+ " 列 is Number type ; DateFormt:"
							+ cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println(i + "行" + j + " 列 is Boolean type");
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					System.out.println(i + "行" + j + " 列 is Blank type");
					value = "";
					break;
				default:
					System.out.println(i + "行" + j + " 列 is default type");
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					linked.add(null);
					continue;
				}
				linked.add(value);
			}
			System.out.println(linked);
			if(linked.get(0)==null){
				continue;
			}
			/**
			 * 此处抛出异常，可以检查整型数据是否被赋值字符串类型
			 */
			SoundZoneMap soundZoneMap=null;
			try {
				soundZoneMap = ZoneConvertUtil.getSoundZoneMap(linked);
			} catch (Exception e) {
				errorRowMeg.add("音频映射信息:第"+(i+1)+"行:数据类型错误\r\n");
				log.error(sheet.getSheetName()+":"+(i+1)+ "行" +"数据转换错误："+e);
				continue;
			}
			//内容校验
			checkSoundZoneMap(soundZoneMap,errorRowMeg,i);
			//如果文件检查到有错误行，则不再往大List中存数据
			if(errorRowMeg.size()<=0)
			linkeds.add(linked);
		}
		if(errorRowMeg.size()>0){
			//内容校验有错
			errorMessage.put(ExcelConfigInfo.ZONE_SOUND_DATA_ERROR, errorRowMeg);
		}else{
			map.put(ExcelConfigInfo.ZONE_SOUND_DATA_NAME, linkeds);
		}
	}
	
	private void checkSoundZoneMap(SoundZoneMap soundZoneMap, List<String> errorRowMeg,
			int rowNum) {
		Validator validator = new Validator();
		List<ConstraintViolation> l =validator.validate(soundZoneMap);
		if(l.size()>0){
			//日志打印
			StringBuffer sb = new StringBuffer();
			for(ConstraintViolation constraintViolation:l){
				sb.append("音频映射信息:第"+(rowNum+1)+"行:");
				sb.append(constraintViolation.getMessage()+"\r\n");
			}
			errorRowMeg.add(sb.toString());
			log.error(sb.toString());
		}
		
	}

}
