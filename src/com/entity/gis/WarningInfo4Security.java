/**
 * 
 */
package com.entity.gis;

import com.entity.linkagectl.UIMCSecurityAlarm;

/**
 * @author wyj
 * 
 *         GIS �澯��Ϣ
 * 
 */
public class WarningInfo4Security {

	private Integer alarmID; // 告警编号
	private String alarmTime; // 告警时间
	private Integer alarmZoneID; // 告警防区编号
	private String alarmZoneName; // 告警防区名称
	private String alarmName; // 告警名称
	private String devType; // 设备类型
	private String devID; // 设备ID
	private String severityLvl; // 严重程度
	private String mgtName; // 管理处名称
	private String branchName; // 分公司名称
	private String type = "安防告警";// 告警类型
	private double zoneStartLon = 0;// 告警防区经度
	private double zoneStartLat = 0;// 告警防区纬度
	private double zoneEndLon = 0;// 告警防区经度
	private double zoneEndLat = 0;// 告警防区纬度


	public WarningInfo4Security(UIMCSecurityAlarm sa) {
		this.alarmID = sa.getAlarmID();
		this.alarmTime = sa.getAlarmTime().toString();
		this.alarmZoneID = sa.getAlarmZoneID();
		alarmZoneName = sa.getAlarmZoneName();
		alarmName = sa.getAlarmName();
		this.devType = sa.getDevType();
		this.devID = sa.getDevID();
		this.severityLvl = sa.getSeverityLvl();
		this.mgtName = sa.getMgtName();
		this.branchName = sa.getBranchName();
	}

	public WarningInfo4Security() {

	}

	public Integer getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
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

	public double getZoneStartLon() {
		return zoneStartLon;
	}

	public void setZoneStartLon(double zoneStartLon) {
		this.zoneStartLon = zoneStartLon;
	}

	public double getZoneStartLat() {
		return zoneStartLat;
	}

	public void setZoneStartLat(double zoneStartLat) {
		this.zoneStartLat = zoneStartLat;
	}

	public double getZoneEndLon() {
		return zoneEndLon;
	}

	public void setZoneEndLon(double zoneEndLon) {
		this.zoneEndLon = zoneEndLon;
	}

	public double getZoneEndLat() {
		return zoneEndLat;
	}

	public void setZoneEndLat(double zoneEndLat) {
		this.zoneEndLat = zoneEndLat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
