package com.entity.alarmmgt;


public class AlarmDTO {
	
	private Integer alarmID;
	private Integer alarmCode;
	private String alarmName;
	private String alarmTime;
	private String alarmLevel;
	private String alarmType;
	private String deviceType;
	private String alarmStatus;
	private String ip;
	private String branchName;
	private String mgtName;
	private String zoneName;
	
	public Integer getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}
	public Integer getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		if(alarmName==null){
			this.alarmName="";
		}else{
			
			this.alarmName = alarmName;
		}
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		if(alarmTime==null){
			this.alarmTime="";
		}else{
			this.alarmTime = alarmTime;
		}
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		if(alarmLevel==null){
			
			this.alarmLevel = "";

		}else{
			int lev  = Integer.parseInt(alarmLevel);
			switch(lev){
			case 1:
				this.alarmLevel = "警告";
				break;
			case 2:
				this.alarmLevel = "次要";
				break;
			case 3:
				this.alarmLevel = "主要";
				break;
			case 4:
				this.alarmLevel = "严重";
				break;
			default:
				this.alarmLevel = "不识别级别";
			}
		}
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		if(alarmType==null){
			
			this.alarmType = "";

		}else{
			int type = Integer.parseInt(alarmType);
			switch(type){
			case 1:
				this.alarmType = "安防告警";
				break;
			case 2:
				this.alarmType = "设备告警";
				break;
			default:
				this.alarmType = "不识别类型";
			}
		}
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		if(deviceType==null){
			this.deviceType="";
		}else{
			int devType = Integer.parseInt(deviceType);
			switch(devType){
			case 1:
				this.deviceType="高压脉冲主机";
				break;
			case 2:
				this.deviceType="一体化探测主机";
				break;
			case 3:
				this.deviceType="防区型振动光纤";
				break;
			case 4:
				this.deviceType="定位型振动光纤";
				break;
			case 5:
				this.deviceType="NVR";
				break;
			case 6:
				this.deviceType="IPC";
				break;
			case 7:
				this.deviceType="智能视频服务器";
				break;
			default:
				this.deviceType = "不识别类型";
			}
		}
	}
	public String getAlarmStatus() {
		return alarmStatus;
	}
	public void setAlarmStatus(String alarmStatus) {
		if(alarmStatus==null){
			this.alarmStatus="";
		}else{
			int status = Integer.parseInt(alarmStatus);
			switch(status){
			case 0:
				this.alarmStatus="未处理";
				break;
			case 1:
				this.alarmStatus="已处理";
				break;
			default:
				this.alarmStatus = "不识别状态";
			}
		}
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		if(ip==null){
			this.ip="";
		}else{
			this.ip = ip;
		}
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		if(branchName==null){
			this.branchName="";
		}else{
			
			this.branchName = branchName;
		}
	}
	public String getMgtName() {
		return mgtName;
	}
	public void setMgtName(String mgtName) {
		if(mgtName==null){
			this.mgtName="";
		}else{
			
			this.mgtName = mgtName;
		}
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		if(zoneName==null){
			this.zoneName="";
		}else{
			
			this.zoneName = zoneName;
		}
	}

}
