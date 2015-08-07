package com.entity.linkagectl;

import java.util.Date;


public class SecurityAlarm extends Alarm {

	private Integer lastTime=0;//持续时间
	private Integer alarmCount=0; //次数
	private String userID; //处理人ID	
	private Integer checkMothod=1; //复核方式 1 视频复核 2 现场复核
	private Integer checkLevel=1; //复核严重程度
	private Integer isReal=0;     //虚实警
	private String reason;    //事件原因
	private String description; //事件过程
	private String peopleID;       //责任人ID
	private Integer report=2;      //上报情况
	private String vidioURL;    //防区摄像头录像URL
	private String leftVidioURL; //左防区摄像头录像URL
	private String rightVidioURL; //右防区摄像头录像URL
	private String pictureURL;   //图片地址
	private String info;         //附加信息
	public SecurityAlarm() {
		super();
		// TODO Auto-generated constructor stub
		      
	}
	
	public SecurityAlarm(Integer alarmID, Integer alarmCode, Date alarmTime,
			String deviceID, Integer zoneID, String departmentID,
			String branchID, Integer alarmStatus, Date managerTime,
			Integer lastTime, Integer alarmCount, String userID,
			Integer checkMothod, Integer checkLevel, Integer isReal,
			String reason, String description, String peopleID, Integer report,
			String vidioURL, String leftVidioURL, String rightVidioURL,
			String pictureURL, String info) {
		super(alarmID, alarmCode, alarmTime, deviceID, zoneID, departmentID,
				branchID, alarmStatus, managerTime);
		this.lastTime = lastTime;
		this.alarmCount = alarmCount;
		this.userID = userID;
		this.checkMothod = checkMothod;
		this.checkLevel = checkLevel;
		this.isReal = isReal;
		this.reason = reason;
		this.description = description;
		this.peopleID = peopleID;
		this.report = report;
		this.vidioURL = vidioURL;
		this.leftVidioURL = leftVidioURL;
		this.rightVidioURL = rightVidioURL;
		this.pictureURL = pictureURL;
		this.info = info;
	}

	public String getPeopleID() {
		return peopleID;
	}

	public void setPeopleID(String peopleID) {
		this.peopleID = peopleID;
	}

	public Integer getLastTime() {
		return lastTime;
	}
	public void setLastTime(Integer lastTime) {
		this.lastTime = lastTime;
	}
	public Integer getAlarmCount() {
		return alarmCount;
	}
	public void setAlarmCount(Integer alarmCount) {
		this.alarmCount = alarmCount;
	}
	public String getUserID() {
		if(null==userID)
		{
			return "";
		}
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public Integer getCheckMothod() {
		return checkMothod;
	}
	public void setCheckMothod(Integer checkMothod) {
		this.checkMothod = checkMothod;
	}
	public Integer getCheckLevel() {
		return checkLevel;
	}
	public void setCheckLevel(Integer checkLevel) {
		this.checkLevel = checkLevel;
	}
	public Integer getIsReal() {
		return isReal;
	}
	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}
	public String getReason() {
		if(null==reason)
		{
			return "";
		}
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	public Integer getReport() {
		return report;
	}
	public void setReport(Integer report) {
		this.report = report;
	}
	public String getVidioURL() {
		if(null==vidioURL)
		{
			return "";
		}
		return vidioURL;
	}
	public void setVidioURL(String vidioURL) {
		this.vidioURL = vidioURL;
	}
	public String getLeftVidioURL() {
		if(null==leftVidioURL)
		{
			return "";
		}
		return leftVidioURL;
	}
	public void setLeftVidioURL(String leftVidioURL) {
		this.leftVidioURL = leftVidioURL;
	}
	public String getRightVidioURL() {
		if(null==rightVidioURL)
		{
			return "";
		}
		return rightVidioURL;
	}
	public void setRightVidioURL(String rightVidioURL) {
		this.rightVidioURL = rightVidioURL;
	}
	public String getPictureURL() {
		if(null==pictureURL)
		{
			return "";
		}
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		
		this.pictureURL = pictureURL;
	}
	public String getInfo() {
		if(null==info)
		{
			return "";
		}
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	}
