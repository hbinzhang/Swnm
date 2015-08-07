package com.actions.devmgt.sound.util;

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
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.PulseZoneMapBean;
import com.util.zone.ZoneConvertUtil;

public class SoundDevHandler implements SoundDevCommonHandler {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void parse2003Excel(HSSFSheet sheet,
			Map<String, List<List<Object>>> map,
			Map<String, Object> errorMessage) {
		// 存放内容错误的行号
		List<Integer> errorRowMeg = new ArrayList<Integer>();
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		// 存放所有行数据
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
			// 存放一行数据
			List<Object> linked = new LinkedList<Object>();
			// 迭代列数据
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add(null);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String字符
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
			/**
			 * 
			 * 此处抛出异常，可以检查整型数据是否被赋值字符串类型
			 */
			Object bean = null;
			try {
				if (sheet.getSheetName().equals(ExcelUtil.SOUNDTERMINALTABLE)) {
					bean = ExcelObjectConvertUtil.getAudioAdapter(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.IOCONTROLLERTABLE)) {
					bean = ExcelObjectConvertUtil.getIOCtroller(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.SOUNDSERVERTABLE)) {
					bean = ExcelObjectConvertUtil.getAudioServer(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.MANUFACTURERTABLE)) {
					bean = ExcelObjectConvertUtil.getManufacturer(linked);
				}
				// 内容校验
				checkOneBean(bean, errorRowMeg, i);
				// 如果文件检查到有错误行，则不再往大List中存数据
				if (errorRowMeg.size() <= 0)
					linkeds.add(linked);
			} catch (Exception e) {
				errorRowMeg.add(i + 1);
				log.error(sheet.getSheetName() + ":" + (i + 1) + "行"
						+ "数据转换错误：" + e);
				continue;
			}
		}
		if (errorRowMeg.size() > 0) {
			if (sheet.getSheetName().equals(ExcelUtil.SOUNDTERMINALTABLE)) {
				// 内容校验有错
				errorMessage.put(ExcelUtil.ADAPTER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.IOCONTROLLERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.IOCONTROLLER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.SOUNDSERVERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.AUDIOSERVER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.MANUFACTURERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.AUDIOMANUFACTURER_DATA_ERROR, errorRowMeg);	
			}
		} else {
			map.put(sheet.getSheetName(), linkeds);
		}
	}

	@Override
	public void parse2007Excel(XSSFSheet sheet,
			Map<String, List<List<Object>>> map,
			Map<String, Object> errorMessage) {
		// 存放内容错误的行号
		List<Integer> errorRowMeg = new ArrayList<Integer>();
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		// 存放所有行数据
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
			// 存放一行数据
			List<Object> linked = new LinkedList<Object>();
			// 迭代列数据
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					linked.add(null);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String字符
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
			/**
			 * 
			 * 此处抛出异常，可以检查整型数据是否被赋值字符串类型
			 */
			Object bean = null;
			try {
				if (sheet.getSheetName().equals(ExcelUtil.SOUNDTERMINALTABLE)) {
					bean = ExcelObjectConvertUtil.getAudioAdapter(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.IOCONTROLLERTABLE)) {
					bean = ExcelObjectConvertUtil.getIOCtroller(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.SOUNDSERVERTABLE)) {
					bean = ExcelObjectConvertUtil.getAudioServer(linked);
				}
				if (sheet.getSheetName().equals(ExcelUtil.MANUFACTURERTABLE)) {
					bean = ExcelObjectConvertUtil.getManufacturer(linked);
				}
				// 内容校验
				checkOneBean(bean, errorRowMeg, i);
				// 如果文件检查到有错误行，则不再往大List中存数据
				if (errorRowMeg.size() <= 0)
					linkeds.add(linked);
			} catch (Exception e) {
				errorRowMeg.add(i + 1);
				log.error(sheet.getSheetName() + ":" + (i + 1) + "行"
						+ "数据转换错误：" + e);
				continue;
			}
		}
		if (errorRowMeg.size() > 0) {
			if (sheet.getSheetName().equals(ExcelUtil.SOUNDTERMINALTABLE)) {
				// 内容校验有错
				errorMessage.put(ExcelUtil.ADAPTER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.IOCONTROLLERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.IOCONTROLLER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.SOUNDSERVERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.AUDIOSERVER_DATA_ERROR, errorRowMeg);	
			}else if(sheet.getSheetName().equals(ExcelUtil.MANUFACTURERTABLE)){
				// 内容校验有错
				errorMessage.put(ExcelUtil.AUDIOMANUFACTURER_DATA_ERROR, errorRowMeg);	
			}
		} else {
			map.put(sheet.getSheetName(), linkeds);
		}
	}

	private void checkOneBean(Object bean, List<Integer> errorRowMeg,int rowNum) {
		Validator validator = new Validator();
		List<ConstraintViolation> l = validator.validate(bean);
		for (ConstraintViolation constraintViolation : l) {
			log.error(constraintViolation.getMessage());
		}
		if (l.size() > 0) {
			errorRowMeg.add(rowNum + 1);
		}
	}

}
