package com.service.videomonitor;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface AlarmVideoService {
	public String searchAlarmVideo(String defectId,Date beginDate) throws NumberFormatException, SQLException;
	
	/** 配置ipc到NVR上
	 * @param configJson ：json串格式 "{"nvr":{"address":"192.168.88.114", "port":8000, "user":"admin", "password":"12345"}, "ipc":[{"address":"192.168.88.201", "port":8000, "user":"admin", "password":"12345", "protocol":"HIKVISION"},{"address":"192.168.88.206", "port":80, "user":"admin", "password":"12345", "protocol":"HIKVISION"}]}"; 
	 * @return
	 */
	public int configIpcToNvr(String configJson);
	
	public int configIpcToNvr(String nvrid,List<String> ipcids);
	
	/**
	 * @param "{\"dev\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"cmd\":\"setntp\", \"ntp\":{\"server\":\"192.168.50.2\", \"port\":300, \"interval\":10,\"enable\":0}}";
	 * @return
	 */
	public int configNTP(String devip,int devport,String devuser,String devpassword,String ntpip,int ntpport,int interval,int enable);
	
	/**
	 * @param "{\"dev\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"cmd\":\"settimezone\", \"timezone\":{\"timedifh\":1, \"timedifm\":30}}";
	 * @return
	 */
	public int configTimezone(String devip,int port,String user,String password,int timedifh,int timedifm);
	
	public int configRestore();
	
	/**
	 * @param "{\"nvr\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"ipc\":{\"address\":\"192.168.50.85\"}, \"ptz\":{\"cmd\":\"control\", \"val\":23, \"stop\":0, \"speed\":1}}";
	 * @return
	 */
	public int ptzControl(String nvrid,String ipcid,int cmdval,int stop,int speed);
}
