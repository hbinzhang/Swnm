package com.entity.alarmmgt;

import java.util.Date;

public class WarnInfo {
	public int alarmID;
	public int alarmCode;
	public int zoneID;
	public Date begintime;
	public int alarmType;
	public int status;
	public String ipcID;
	private String pictureURL;
	public String mngMentID;
	public String subComID;
	public String remarks;

	public int getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(int alarmID) {
		this.alarmID = alarmID;
	}

	public int getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(int alarmCode) {
		this.alarmCode = alarmCode;
	}

	public int getZoneID() {
		return zoneID;
	}

	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIpcID() {
		return ipcID;
	}

	public void setIpcID(String ipcID) {
		this.ipcID = ipcID;
	}

	public String getMngMentID() {
		return mngMentID;
	}

	public void setMngMentID(String mngMentID) {
		this.mngMentID = mngMentID;
	}

	public String getSubComID() {
		return subComID;
	}

	public void setSubComID(String subComID) {
		this.subComID = subComID;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
}