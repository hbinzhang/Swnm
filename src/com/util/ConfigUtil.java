package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigUtil {
	
	public static void write(String key,String value,String filename){
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(new File(path,filename));
			prop.load(fis);
			fis.close();
			prop.setProperty(key, value);
			FileOutputStream fos = new FileOutputStream(new File(path,filename));
			prop.store(fos, "update '"+key+"' "+value);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	public static String getPropertyValue(String key,String fileName){
		
		String value="";
		try {
			Properties envs = new Properties();
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			envs.load(new FileInputStream(new File(path, fileName)));
			value=envs.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static void main(String[] args) {
		/*System.out.println(getPropertyValue("appName").equals(""));
		System.out.println(getPropertyValue("moduleName"));*/
	}

}
