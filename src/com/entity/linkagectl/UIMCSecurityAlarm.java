package com.entity.linkagectl;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class UIMCSecurityAlarm implements java.io.Serializable{
	private Integer  alarmID; 			//告警编号
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date  alarmTime; 			//告警时间
	private Integer	alarmDuringTime; 	//告警持续时间
	private Integer  alarmZoneID ; 		//告警防区编号
	private String  alarmZoneName ; 		//告警防区编号
	private String 	alarmName; 			//告警名称
	private String 	devType; 			//设备类型
	private String devID; 				//设备ID
	private String 	severityLvl; 		//严重程度
	private Integer 	alarmCnt; 			//告警次数
	private String 	mgtName; 			//管理处名称
	private String 	branchName; 		//分公司名称
	private String 	info; 			//附加信息
	private Integer 	idevType;
	private Integer 	alarmLvl;
	
	private Integer 	checkMethod;  //
	private String 		checkMethodStr;//1:视频复核 2现场符合
	private Integer 	isReal;//0 虚警 1实警
	private String 		isRealStr;//
	private String 		reason;
	private String 		peopleID;//名字
	private String 		peopleName;//名字
	private String 		userID;//名字
	private String 		userName;//名字
	private Integer 	report;
	private String 		vidioURL;
	private String 		leftVidioURL;
	private String 		rightVidioURL;
	private String 		pictureURL;
	private String 		description;
	private Integer 	checkLevel;
	private String 		checkLevelStr;
	private Integer 	alarmCode;
	
	private String cxsjUI;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMgtName() {
		return mgtName;
	}
	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getAlarmID() {
		return alarmID;
	}
	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public Integer getAlarmDuringTime() {
		return alarmDuringTime;
	}
	public void setAlarmDuringTime(Integer alarmDuringTime) {
		this.alarmDuringTime = alarmDuringTime;
	}
	public Integer getAlarmZoneID() {
		return alarmZoneID;
	}
	public void setAlarmZoneID(Integer alarmZoneID) {
		this.alarmZoneID = alarmZoneID;
	}
	
	public String getAlarmZoneName() {
		return alarmZoneName;
	}
	public void setAlarmZoneName(String alarmZoneName) {
		this.alarmZoneName = alarmZoneName;
	}
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getDevID() {
		return devID;
	}
	public void setDevID(String devID) {
		this.devID = devID;
	}
	public String getSeverityLvl() {
		return severityLvl;
	}
	public void setSeverityLvl(String severityLvl) {
		this.severityLvl = severityLvl;
	}
	public Integer getAlarmCnt() {
		return alarmCnt;
	}
	public void setAlarmCnt(Integer alarmCnt) {
		this.alarmCnt = alarmCnt;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public Integer getIdevType() {
		return idevType;
	}
	public void setIdevType(Integer idevType) {
		this.idevType = idevType;
	}
	public Integer getAlarmLvl() {
		return alarmLvl;
	}
	public void setAlarmLvl(Integer alarmLvl) {
		this.alarmLvl = alarmLvl;
	}
	
	
	
	public Integer getCheckMethod() {
		return checkMethod;
	}
	public void setCheckMethod(Integer checkMethod) {
		this.checkMethod = checkMethod;
	}
	public String getCheckMethodStr() {
		return checkMethodStr;
	}
	public void setCheckMethodStr(String checkMethodStr) {
		this.checkMethodStr = checkMethodStr;
	}
	public Integer getIsReal() {
		return isReal;
	}
	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}
	public String getIsRealStr() {
		return isRealStr;
	}
	public void setIsRealStr(String isRealStr) {
		this.isRealStr = isRealStr;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPeopleID() {
		return peopleID;
	}
	public void setPeopleID(String peopleID) {
		this.peopleID = peopleID;
	}
	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}
	public Integer getReport() {
		return report;
	}
	public void setReport(Integer report) {
		this.report = report;
	}
	public String getVidioURL() {
		return vidioURL;
	}
	public void setVidioURL(String vidioURL) {
		this.vidioURL = vidioURL;
	}
	public String getLeftVidioURL() {
		return leftVidioURL;
	}
	public void setLeftVidioURL(String leftVidioURL) {
		this.leftVidioURL = leftVidioURL;
	}
	public String getRightVidioURL() {
		return rightVidioURL;
	}
	public void setRightVidioURL(String rightVidioURL) {
		this.rightVidioURL = rightVidioURL;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCxsjUI(String cxsjUI) {
		this.cxsjUI = cxsjUI;
	}
	
	public String getCxsjUI() {
		if(this.alarmDuringTime==null){
			this.cxsjUI = "0分 0秒";
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append((Integer)this.alarmDuringTime/60+"分 ");
			sb.append(this.alarmDuringTime%60+"秒");
			this.cxsjUI = sb.toString();
		}
		return cxsjUI;
	}
	
	public Integer getCheckLevel() {
		return checkLevel;
	}
	public void setCheckLevel(Integer checkLevel) {
		this.checkLevel = checkLevel;
	}
	
	public String getCheckLevelStr() {
		return checkLevelStr;
	}
	public void setCheckLevelStr(String checkLevelStr) {
		this.checkLevelStr = checkLevelStr;
	}
	
	public Integer getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}
	public UIMCSecurityAlarm (Integer alarmID, Date alarmTime,Integer alarmDuringTime,
			Integer  alarmZoneID, String  alarmZoneName, String alarmName,String devType,
			String devID,String severityLvl,Integer alarmCnt,String mgtName,
			String branchName,String info)
	{
		super();
		
		this.alarmID = alarmID;
		this.alarmTime = alarmTime;
		this.alarmDuringTime = alarmDuringTime;
		this.alarmZoneID = alarmZoneID;
		this.alarmZoneName = alarmZoneName;
		this.alarmName = alarmName;
		this.devType = devType;
		this.devID = devID;
		this.severityLvl =	severityLvl;
		this.alarmCnt = alarmCnt;
		this.mgtName =	mgtName;
		this.branchName = branchName;
		this.info = info;
	}
	
	public UIMCSecurityAlarm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	}