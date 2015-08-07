package com.util.zone.handler;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.entity.zone.DeviceMapInterface;


public interface ZoneCommonHandler {
	
	void parse2003Excel(HSSFSheet sheet,Map<String,List<List<Object>>> map,Map<String, Object> errorMessage);
	void parse2007Excel(XSSFSheet sheet,Map<String,List<List<Object>>> map,Map<String, Object> errorMessage);
	
}
