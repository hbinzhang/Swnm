package com.util.fence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.common.ExcelConfigInfo;
import com.entity.efence.FenceBean;
import com.entity.zone.DefenceZoneMapBean;
import com.util.zone.ZoneConvertUtil;
import com.util.zone.handler.SoundZoneMapHandler;

public class ExcelUtil {
	
	private final static Log log = LogFactory.getLog(ExcelUtil.class);
	/**
	 * 对外提供读取excel 的方法 map参数：存放的是excel表的所有数据 key：存放的是hostID的值
	 * value:存放的是excel表的行数据，使用LinkedList依次存放表字段的值 顺序录入，顺序解析
	 * */
	public static Map<Integer, List<Object>> readExcel(File file,
			String fileName, Map<String, Object> errorMessage)
			throws IOException {
		// String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		System.out.println(fileName);
		if ("xls".equals(extension)) {
			return read2003Excel(file, errorMessage);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(file, errorMessage);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static Map<Integer, List<Object>> read2003Excel(File file,
			Map<String, Object> errorMessage) throws IOException {
		
		//存放内容错误的行号
		List<String> errorRowMeg = new ArrayList<String>();

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串

		// 检查文件格式是否正确
		checkFile2003(sheet, errorMessage);
		if(errorMessage.size()>0)
			return null;

		// 迭代行数据
		for (int i =0; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);

			// 如果行数据为空或者是第一行数据（标题行）则continue
			if (row == null || i == 0) {
				continue;
			} else {
				counter++;
			}

			List<Object> linked = new LinkedList<Object>();

			// 迭代列数据
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
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
			/**
			 * 此处抛出异常，可以检查整行数据是否被赋值字符串类型
			 */
			FenceBean fBean=null;
			try {
				fBean = FenceConvertUtil.getFence(linked);
			} catch (Exception e) {
				errorRowMeg.add("第"+(i+1)+"行:数据类型错误\r\n");
				e.printStackTrace();
				continue;
			}
			//内容校验
			checkFenceBean(fBean,errorRowMeg,i);
			//如果文件检查到有错误行，则不在往map中存数据
			if(errorRowMeg.size()<=0)
			map.put(Integer.valueOf((String) linked.get(0)), linked);
		}
		if(errorRowMeg.size()>0){
			errorMessage.put(ExcelConfigInfo.FENCE_DATA_ERROR, errorRowMeg);
			return null;
		}else{
			
			return map;
		}
	}

	/**
	 * 读取Office 2007 excel
	 * */
	private static Map<Integer, List<Object>> read2007Excel(File file,
			Map<String, Object> errorMessage) throws IOException {
		
		//存放内容错误的行号
		List<String> errorRowMeg = new ArrayList<String>();

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		
		// 检查文件格式是否正确
		checkFile2007(sheet, errorMessage);
		if(errorMessage.size()>0)
			return null;

		// 迭代行数据
		for (int i = 0; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);

			// 如果行数据为空或者是第一行数据（标题行）则continue
			if (row == null || i == 0) {
				continue;
			} else {
				counter++;
			}

			// FenceBean fenceBean = new FenceBean();
			List<Object> linked = new LinkedList<Object>();

			// 迭代列数据
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
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
			/**
			 * 此处抛出异常，可以检查整型数据是否被赋值字符串类型
			 */
			FenceBean fBean=null;
			try {
				fBean = FenceConvertUtil.getFence(linked);
			} catch (Exception e) {
				errorRowMeg.add("第"+(i+1)+"行:数据类型错误\r\n");
				e.printStackTrace();
				continue;
			}
			//内容校验
			checkFenceBean(fBean,errorRowMeg,i);
			//如果文件检查到有错误行，则不在往map中存数据
			if(errorRowMeg.size()<=0)
			map.put(Integer.valueOf((String) linked.get(0)), linked);
		}
		if(errorRowMeg.size()>0){
			errorMessage.put(ExcelConfigInfo.FENCE_DATA_ERROR, errorRowMeg);
			return null;
		}else{
			
			return map;
		}
	}
	
	/**
	 * 检查文件内容是否正确
	 * @param fBean
	 * @param errorMessage
	 */
	private static void checkFenceBean(FenceBean fBean,List<String> errorRowMeg,int rowNum) {
		Validator validator = new Validator();
		List<ConstraintViolation> l =validator.validate(fBean);
		
		if(l.size()>0){
			//日志打印
			StringBuffer sb = new StringBuffer();
			for(ConstraintViolation constraintViolation:l){
				sb.append("第"+(rowNum+1)+"行:");
				sb.append(constraintViolation.getMessage()+"\r\n");
			}
			errorRowMeg.add(sb.toString());
			log.error(sb.toString());
		}
	}

	/**
	 * 检查文件格式是否正确，根据文件的列数跟第一列的名称
	 * 
	 * @param sheet
	 * @param errorMessage
	 */
	private static void checkFile2003(HSSFSheet sheet,
			Map<String, Object> errorMessage) {
		HSSFRow row = sheet.getRow(0);
		String name = row.getCell(0).getStringCellValue();
		if (!(row.getLastCellNum() == ExcelConfigInfo.FENCE_CELL_NUM && ExcelConfigInfo.FENCE_FIRST_CELL_NAME
				.equals(name)))
			errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());

	}

	/**
	 * 检查文件格式是否正确，根据文件的列数跟第一列的名称
	 * 
	 * @param sheet
	 * @param errorMessage
	 */
	private static void checkFile2007(XSSFSheet sheet,
			Map<String, Object> errorMessage) {
		XSSFRow row = sheet.getRow(0);
		String name = row.getCell(0).getStringCellValue();
		if (!(row.getLastCellNum() == ExcelConfigInfo.FENCE_CELL_NUM && ExcelConfigInfo.FENCE_FIRST_CELL_NAME
				.equals(name)))
			errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());

	}

	public static void main(String[] args) {
		/*
		 * try {
		 * 
		 * Map<Integer,List<Object>> map =readExcel(new
		 * File("D:\\fence.xlsx"),"fence.xlsx");
		 * 
		 * System.out.println("===================================================="
		 * ); System.out.println("总共导入数据条数：   "+map.size());
		 * 
		 * for(Integer key:map.keySet()){ for(Object obj:map.get(key)){
		 * System.out.print(obj+","); } System.out.println(""); }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
	}
}