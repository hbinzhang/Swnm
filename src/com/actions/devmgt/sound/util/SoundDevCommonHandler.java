package com.actions.devmgt.sound.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface SoundDevCommonHandler {

	void parse2003Excel(HSSFSheet sheet,Map<String,List<List<Object>>> map,Map<String, Object> errorMessage);

	void parse2007Excel(XSSFSheet sheet,Map<String,List<List<Object>>> map,Map<String, Object> errorMessage);

	
}
