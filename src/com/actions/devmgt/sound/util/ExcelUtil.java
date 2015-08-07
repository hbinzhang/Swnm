package com.actions.devmgt.sound.util;
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
public class ExcelUtil {
	public static final String SOUNDTERMINALTABLE = "音频终端表";
	public static final String IOCONTROLLERTABLE = "IO控制器表";
	public static final String SOUNDSERVERTABLE = "音频服务器表";
	public static final String MANUFACTURERTABLE = "厂商信息表";
	// 自用以及前台交互用
	public static String ADAPTER_DATA_ERROR = "ADAPTERDATAERROR";//
	public static String IOCONTROLLER_DATA_ERROR = "IOCONTROLLERDATAERROR";//
	public static String AUDIOSERVER_DATA_ERROR = "AUDIOSERVERDATAERROR";//
	public static String AUDIOMANUFACTURER_DATA_ERROR = "AUDIOMANUFACTURERDATAERROR";// 
	/**
	 * 
	 * 对外提供读取多sheet页 excel 的方法 map参数：存放的是excel表的所有数据
	 * 
	 * key：存放的是sheet页标识
	 * 
	 * value:存放的是每个sheet页 excel表的行数据
	 * 
	 * */
	public static Map<String, List<List<Object>>> readExcel(File file,
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
	 * 
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * 
	 * @throws FileNotFoundException
	 */

	private static Map<String, List<List<Object>>> read2003Excel(File file,

	Map<String, Object> errorMessage) throws IOException {

		// 存放多表数据集合
		Map<String, List<List<Object>>> map = new HashMap<String, List<List<Object>>>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		SoundDevCommonHandler sounddevCommonHandler = null;
		// 迭代sheet数据
		for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
			// 表对象
			HSSFSheet sheet = hwb.getSheetAt(i);
			// 获取标题行
			HSSFRow row = sheet.getRow(0);
			// 获取标题行首列名称
			String name = sheet.getSheetName();
			// 音频终端表
			if (ExcelUtil.SOUNDTERMINALTABLE.equals(name)
					|| ExcelUtil.IOCONTROLLERTABLE.equals(name)
					|| ExcelUtil.SOUNDSERVERTABLE.equals(name)
					|| ExcelUtil.MANUFACTURERTABLE.equals(name)) {
				sounddevCommonHandler = new SoundDevHandler();
			}
			else {
				errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());
				return null;
			}
			sounddevCommonHandler.parse2003Excel(sheet, map, errorMessage);
		}
		return map;
	}

	/**
	 * 
	 * 读取Office 2007 excel
	 * 
	 * */

	private static Map<String, List<List<Object>>> read2007Excel(File file,
	Map<String, Object> errorMessage) throws IOException {
		// 存放多表数据集合
		Map<String, List<List<Object>>> map = new HashMap<String, List<List<Object>>>();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		SoundDevCommonHandler sounddevCommonHandler = null;
		// 迭代sheet数据
		for (int i = 0; i < xwb.getNumberOfSheets(); i++) {
			// 表对象
			XSSFSheet sheet = xwb.getSheetAt(i);
			// 获取标题行
			XSSFRow row = sheet.getRow(0);
			// 获取标题行首列名称
			String name = sheet.getSheetName();
			// 音频终端表
			if (ExcelUtil.SOUNDTERMINALTABLE.equals(name)
					|| ExcelUtil.IOCONTROLLERTABLE.equals(name)
					|| ExcelUtil.SOUNDSERVERTABLE.equals(name)
					|| ExcelUtil.MANUFACTURERTABLE.equals(name)) {
				sounddevCommonHandler = new SoundDevHandler();
			}
			else {
				errorMessage.put("FILE_FORMAT_ERROR", sheet.getSheetName());
				return null;
			}
			sounddevCommonHandler.parse2007Excel(sheet, map, errorMessage);
		}
		return map;
	}
}