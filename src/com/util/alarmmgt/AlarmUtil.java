package com.util.alarmmgt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.entity.CommonBean;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.authmgt.Session;

public class AlarmUtil {

	// 存放数据库中的字段名称与UI呈现名称的对应关系
	public static Map<Integer, String> affectMap = new HashMap<Integer, String>();
	public static Map<Integer, String> levelMap = new HashMap<Integer, String>();
	public static Map<Integer, String> typeMap = new HashMap<Integer, String>();
	public static Map<Integer, String> checkLevelMap = new HashMap<Integer, String>();
	public static Map<Integer, String> statusMap = new HashMap<Integer, String>();
	public static Map<Integer, String> checkMethodMap = new HashMap<Integer, String>();
	public static Map<Integer, String> reportMap = new HashMap<Integer, String>();
	public static Map<Integer, String> isRealMap = new HashMap<Integer, String>();
	// 安防告警设备类型名称
//	public static Map<Integer, String> secAlarmDeviceTypeMap = new HashMap<Integer, String>();
	// 设备告警设备类型名称
	public static Map<Integer, String> devAlarmDeviceTypeMap = new HashMap<Integer, String>();
	
	public static Log log = LogFactory.getLog(AlarmUtil.class);

	static {
		initNameMap();
	}
	
	public static void initNameMap() {
		/*secAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_HIGH_VOLTAGE_PULSE, "高压脉冲主机");
		secAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_INTEGRATION, "一体化探测主机");
		secAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_DEFENSE_VIBRATING_FIBER, "防区型振动光纤");
		secAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_POSITION_VIBRATING_FIBER, "定位型振动光纤");
		secAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_INTELLIGENT_VIDEO_SERVER, "智能视频服务器");*/

		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_HIGH_VOLTAGE_PULSE, "高压脉冲主机");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_INTEGRATION, "一体化探测主机");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_DEFENSE_VIBRATING_FIBER, "防区型振动光纤");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_POSITION_VIBRATING_FIBER, "定位型振动光纤");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_NVR, "NVR");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_IPC, "IPC");
		devAlarmDeviceTypeMap.put(AlarmConstants.DEVICE_INTELLIGENT_VIDEO_SERVER, "智能视频服务器");
		
		affectMap.put(AlarmConstants.NOT_AFFECT, "不影响");
		affectMap.put(AlarmConstants.AFFECT, "影响");
		
		levelMap.put(AlarmConstants.LEVEL_WARNING, "警告");
		levelMap.put(AlarmConstants.LEVEL_MINOR, "次要");
		levelMap.put(AlarmConstants.LEVEL_MAJOR, "主要");
		levelMap.put(AlarmConstants.LEVEL_SERIOUS, "严重");
		
		typeMap.put(AlarmConstants.TYPE_SECURITY_ALARM, "安防告警");
		typeMap.put(AlarmConstants.TYPE_DEVICE_ALARM, "设备告警");
		
		checkLevelMap.put(AlarmConstants.CHECK_LEVEL_SLIGHT, "警告");
		checkLevelMap.put(AlarmConstants.CHECK_LEVEL_GENERAL, "次要");
		checkLevelMap.put(AlarmConstants.CHECK_LEVEL_SERIOUS, "主要");
		checkLevelMap.put(AlarmConstants.CHECK_LEVEL_VERY_SERIOUS, "严重");
		
		statusMap.put(AlarmConstants.STATUS_NOT_HANDLED, "未处理");
		statusMap.put(AlarmConstants.STATUS_HANDLED, "已处理");
		
		checkMethodMap.put(AlarmConstants.CHECK_METHOD_VIDEO, "视频复核");
		checkMethodMap.put(AlarmConstants.CHECK_METHOD_SCENE, "现场复核");
		
		isRealMap.put(AlarmConstants.IS_NOT_REAL, "虚警");
		isRealMap.put(AlarmConstants.IS_REAL, "实警");
	}
	
	
	public static List getListByMap(Map map){
		List list = new ArrayList();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()){
			String id = (String)iter.next();
			list.add(new CommonBean(id, (String)map.get(id)));
		}
		return list;
	}
	
	public static String getNameByKey(Map map, int key){
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()){
			int id = (Integer)iter.next();
			if(id == key){
				return (String)map.get(key);
			}
		}
		return "";
	}

	public static String getSqlByList(List<String> list){
		StringBuffer s = new StringBuffer();
		if(null != list && list.size() > 0){
			s.append("(");
			for(String str : list){
				s.append("'");
				s.append(str);
				s.append("'");
				s.append(",");
			}
			if(s != null && s.length() > 0){
				s.deleteCharAt(s.length()-1);
				s.append(")");
			}
		}	
		return s.toString();
	}
	
	public static String formatString(String s) {
		if (s == null || s.equals("null")) {
			return "";
		} else {
			return s;
		}
	}
	
	public static String date2String(Date date, SimpleDateFormat df) {
		if (date == null) {
			return "";
		} else {
			return df.format(date);
		}
	}	

	public static Date string2Date(String s, SimpleDateFormat df) {
		try{
			return df.parse(s);
		}catch(Exception e){
			log.error("string2Date error!",e);
		}
		return null;
	}	
	
	// 设置excel的title样式
	public HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
		HSSFFont boldFont = wb.createFont();	
		boldFont.setFontHeight((short) 200);	
		HSSFCellStyle style = wb.createCellStyle();	
		style.setFont(boldFont);	
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));	
		return style;
	}
		
	public XSSFCellStyle createTitleStyle(XSSFWorkbook wb) {
		XSSFFont boldFont = wb.createFont();	
		boldFont.setFontHeight((short) 200);	
		XSSFCellStyle style = wb.createCellStyle();	
		style.setFont(boldFont);	
		XSSFDataFormat fmt = wb.createDataFormat();   
		style.setDataFormat(fmt.getFormat("###,##0"));	
		return style;
	}		
		
	// 创建Excel单元格
	public void createCell(HSSFRow row, int column, HSSFCellStyle style,
			int cellType, Object value) {	
		HSSFCell cell = row.createCell(column);	
		if (style != null) {	
			cell.setCellStyle(style);	
		}	
		switch (cellType) {	
			case HSSFCell.CELL_TYPE_BLANK: {	
			}					
			break;
			case HSSFCell.CELL_TYPE_STRING: {	
				cell.setCellValue(String.valueOf(value));
			}					
			break;
			case HSSFCell.CELL_TYPE_NUMERIC: {	
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);	
				cell.setCellValue(Double.parseDouble(String.valueOf(value)));	
			}
			break;	
		default:
		break;	
		}
	}
	
	// 创建Excel单元格
	public void createCell(XSSFRow row, int column, XSSFCellStyle style,
			int cellType, Object value) {	
		XSSFCell cell = row.createCell(column);	
		if (style != null) {	
			cell.setCellStyle(style);	
		}	
		switch (cellType) {	
			case XSSFCell.CELL_TYPE_BLANK: {	
			}					
			break;
			case XSSFCell.CELL_TYPE_STRING: {	
				cell.setCellValue(String.valueOf(value));
			}					
			break;
			case XSSFCell.CELL_TYPE_NUMERIC: {	
				cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);	
				cell.setCellValue(Double.parseDouble(String.valueOf(value)));	
			}
			break;	
		default:
		break;	
		}
	}
	
	public static List getSubList(List list, int start, int end){
		List l = new ArrayList();		
		for(int i=start; i<= end; i++){
			l.add(list.get(i));
		}
		return l;
	}
	
	public static Session getLoginSession(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = null;
		if(null != httpSession){
			session = (Session) httpSession.getAttribute("session");			
		}
		return session;
	}

	public static void main(String args[]){
		for(int i=0; i<10;i++){
			try{
				System.out.println("i="+i);
				if(i==3){
					throw new Exception();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("end");
	}

}
