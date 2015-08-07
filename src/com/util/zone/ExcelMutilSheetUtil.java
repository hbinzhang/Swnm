package com.util.zone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.common.ExcelConfigInfo;
import com.util.zone.handler.DefenceZoneMapHandler;
import com.util.zone.handler.IPCZoneMapHandler;
import com.util.zone.handler.IntegrationZoneMapHandler;
import com.util.zone.handler.PositionZoneMapHandler;
import com.util.zone.handler.PulseZoneMapHandler;
import com.util.zone.handler.SoundZoneMapHandler;
import com.util.zone.handler.ZoneCommonHandler;
import com.util.zone.handler.ZoneHandler;

public class ExcelMutilSheetUtil {
	/**
	 * 对外提供读取多sheet页 excel 的方法 map参数：存放的是excel表的所有数据
	 *  key：存放的是sheet页标识
	 *  value:存放的是每个sheet页 excel表的行数据
	 * */
	public static Map<String,List<List<Object>>> readExcel(File file,
			String fileName, Map<String, Object> errorMessage)
			throws IOException {
		
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
	private static Map<String,List<List<Object>>> read2003Excel(File file,
			Map<String, Object> errorMessage) throws IOException {
		

		//存放多表数据集合
		Map<String,List<List<Object>>> map = new HashMap<String,List<List<Object>>>();
		
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		
		ZoneCommonHandler zoneCommonHandler = null;
		//迭代sheet数据
		for(int i=0;i<hwb.getNumberOfSheets();i++){
			//表对象
			HSSFSheet sheet = hwb.getSheetAt(i);
			//获取标题行
			HSSFRow row = sheet.getRow(0);
			//获取标题行首列名称
			String name = row.getCell(0).getStringCellValue();
			
			//主防区信息表
			if (row.getLastCellNum() == ExcelConfigInfo.ZONE_CELL_NUM && ExcelConfigInfo.ZONE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new ZoneHandler();
			}
			//防区型映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_DEFENCE_CELL_NUM && ExcelConfigInfo.ZONE_DEFENCE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new DefenceZoneMapHandler();
			}
			//定位型映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_POSITION_CELL_NUM&& ExcelConfigInfo.ZONE_POSITION_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new PositionZoneMapHandler();
			}
			//一体化映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_INTE_CELL_NUM && ExcelConfigInfo.ZONE_INTE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new IntegrationZoneMapHandler();
			}
			//高压脉冲映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_PULSE_CELL_NUM && ExcelConfigInfo.ZONE_PULSE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new PulseZoneMapHandler();
			}
			//摄像头映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_IPC_CELL_NUM && ExcelConfigInfo.ZONE_IPC_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new IPCZoneMapHandler();
			}
			//音频映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_SOUND_CELL_NUM && ExcelConfigInfo.ZONE_SOUND_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new SoundZoneMapHandler();
			}
			else{
				errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());
				return null;
			}
			zoneCommonHandler.parse2003Excel(sheet, map, errorMessage);
		}
		/*if(errorRowMeg.size()>0){
			errorMessage.put("FILE_ERR_ROW", errorRowMeg);
			return null;
		}else{
			
			return map;
		}*/
		return map;
	}

	/**
	 * 读取Office 2007 excel
	 * */
	private static Map<String,List<List<Object>>> read2007Excel(File file,
			Map<String, Object> errorMessage) throws IOException {
 
		//存放多表数据集合
		Map<String,List<List<Object>>> map = new HashMap<String,List<List<Object>>>();
		
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		
		ZoneCommonHandler zoneCommonHandler = null;
		//迭代sheet数据
		for(int i=0;i<xwb.getNumberOfSheets();i++){
			//表对象
			XSSFSheet sheet = xwb.getSheetAt(i);
			//获取标题行
			XSSFRow row = sheet.getRow(0);
			//获取标题行的首列名称
			
			if(row==null){
				continue;
			}
			
			String name = row.getCell(0).getStringCellValue();

			//主防区信息表
			if (row.getLastCellNum() == ExcelConfigInfo.ZONE_CELL_NUM && ExcelConfigInfo.ZONE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new ZoneHandler();
			}
			//防区型映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_DEFENCE_CELL_NUM && ExcelConfigInfo.ZONE_DEFENCE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new DefenceZoneMapHandler();
			}
			//定位型映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_POSITION_CELL_NUM&& ExcelConfigInfo.ZONE_POSITION_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new PositionZoneMapHandler();
			}
			//一体化映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_INTE_CELL_NUM && ExcelConfigInfo.ZONE_INTE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new IntegrationZoneMapHandler();
			}
			//高压脉冲映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_PULSE_CELL_NUM && ExcelConfigInfo.ZONE_PULSE_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new PulseZoneMapHandler();
			}
			//摄像头映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_IPC_CELL_NUM && ExcelConfigInfo.ZONE_IPC_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new IPCZoneMapHandler();
			}
			//音频映射表
			else if(row.getLastCellNum() == ExcelConfigInfo.ZONE_SOUND_CELL_NUM && ExcelConfigInfo.ZONE_SOUND_FIRST_CELL_NAME
					.equals(name)){
				zoneCommonHandler=new SoundZoneMapHandler();
			}
			else{
				errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());
				return null;
			}
			zoneCommonHandler.parse2007Excel(sheet, map, errorMessage);
		}
		
		return map;
 
	}
	
}