package com.actions.videomonitor;

import java.sql.SQLException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.service.videomonitor.AlarmVideoService;

public class AlarmVideoAction extends ActionSupport {
	
	private String defectId;
	private String ipcsjson;
	private Date beginDate;
	private AlarmVideoService alarmVideoService;

	public AlarmVideoService getAlarmVideoService() {
		return alarmVideoService;
	}

	public void setAlarmVideoService(AlarmVideoService alarmVideoService) {
		this.alarmVideoService = alarmVideoService;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}
	
	public String getIpcsjson() {
		return ipcsjson;
	}

	public void setIpcsjson(String ipcsjson) {
		this.ipcsjson = ipcsjson;
	}

	public String getDefectId() {
		return defectId;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public String searchAlarmVideo(){
		String res = SUCCESS;
		try {
			String ipcs = alarmVideoService.searchAlarmVideo(defectId,beginDate);
			setIpcsjson(JSON.toJSONString(ipcs));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = ERROR;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = ERROR;
		}
		return res;
	}

	private String configJson;
	
	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

	public String testConfigIpcToNvr(){
		int res = alarmVideoService.configIpcToNvr(configJson);
		return SUCCESS;
	}
	
	public String testConfigIpcToNvrEx(){
		JSONObject jo = JSONObject.parseObject(configJson);
		String nvrip = jo.getString("nvrip");
		List<String> ipcs = new ArrayList<String>();
		for(Object oipc : jo.getJSONArray("ipcs")){
			ipcs.add(((JSONObject)oipc).toJSONString());
		}
		int res = alarmVideoService.configIpcToNvr(nvrip,ipcs);
		return SUCCESS;
	}
	
	public String testConfigNTP(){
		JSONObject jo = JSONObject.parseObject(configJson);
		String devip = jo.getString("devip");
		int devport = jo.getInteger("devport");
		String devuser = jo.getString("devuser");
		String devpassword = jo.getString("devpassword");
		String ntpip = jo.getString("ntpip");
		int ntpport = jo.getIntValue("ntpport");
		int interval = jo.getIntValue("interval");
		int enable = jo.getIntValue("enable");
		int res = alarmVideoService.configNTP(devip, devport, devuser, devpassword, ntpip, ntpport, interval, enable);
		return SUCCESS;
	}
	
	public String testConfigTimezone(){
		JSONObject jo = JSONObject.parseObject(configJson);
		String devip = jo.getString("devip");
		int port = jo.getInteger("port");
		String user = jo.getString("user");
		String password = jo.getString("password");
		int timedifh = jo.getIntValue("timedifh");
		int timedifm = jo.getIntValue("timedifm");
		int res = alarmVideoService.configTimezone(devip, port, user, password, timedifh, timedifm);
		return SUCCESS;
	}
	
	public String testPtzControl(){
		JSONObject jo = JSONObject.parseObject(configJson);
		String nvrid = jo.getString("nvrid");
		String ipcid = jo.getString("ipcid");
		int cmdval = jo.getIntValue("cmdval");
		int stop = jo.getIntValue("stop");
		int speed = jo.getIntValue("speed");
		int res = alarmVideoService.ptzControl(nvrid, ipcid, cmdval, stop, speed);
		return SUCCESS;
	}
}
