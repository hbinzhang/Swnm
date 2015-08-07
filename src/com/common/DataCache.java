package com.common;

import java.util.HashMap;
import java.util.Map;

public class DataCache {
	
	public  Map<Integer,Object> dataMap;
	
	private static DataCache dateCache=new DataCache();
	
	private DataCache(){
		dataMap=new HashMap<Integer,Object>();
	}
	public static DataCache getInstance(){
		
		return dateCache;
				
	}

}
