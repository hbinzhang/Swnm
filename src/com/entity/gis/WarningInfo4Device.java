/**
 * 
 */
package com.entity.gis;

import com.entity.alarmmgt.DeviceAlarm;

/**
 * @author wyj
 * 
 *         GIS �澯��Ϣ
 * 
 */
public class WarningInfo4Device {

	private String alarmID;
	private String cause;// 告警原因
	private String result;// 后果
	private String operation;// 建议操作
	private String isAffect;// 是否影响业务,0 不影响,1 影响
	private Integer alarmCode;// 告警码
	private String alarmName;// 告警名称
	private String alarmLevel;// 告警级别
	private String deviceType;// 设备类型
	private String deviceId;// 设备Id
	private Integer zoneId;// 防区Id
	private String zoneName;// 防区
	private String departmentName;
	private String departmentId;
	private String branchName;
	private String type = "设备告警";// 告警类型
	private String branchId;
	private double lat = 0;
	private double lon = 0;

	public WarningInfo4Device(DeviceAlarm da) {
		this.alarmID = da.getAlarmId().toString();
		this.cause = da.getCause();
		this.result = da.getResult();
		this.operation = da.getOperation();
		if (da.getIsAffect() != null) {
			if (da.getIsAffect() != 0) {
				this.isAffect = "对业务没有影响";
			} else {
				this.isAffect = "对业务有影响";
			}
		}else{
			this.isAffect = "对业务没有影响";
		}
		this.alarmCode = da.getAlarmCode();
		this.alarmName = da.getAlarmName();
		switch(da.getAlarmLevel()){
		case 1:
			this.alarmLevel = "轻微";
			break;
		case 2:
			this.alarmLevel = "一般";
			break;
		case 3:
			this.alarmLevel = "严重";
			break;
		case 4:
			this.alarmLevel = "非常严重";
			break;
		}
		switch (da.getDeviceType()) {
		case 1:
			this.deviceType = "高压脉冲主机";
			break;
		case 2:
			this.deviceType = "一体化探测主机";
			break;
		case 3:
			this.deviceType = "防区型振动光纤";
			break;
		case 4:
			this.deviceType = "定位型振动光纤";
			break;
		case 5:
			this.deviceType = "NVR";
			break;
		case 6:
			this.deviceType = "网络摄像头(IPC)";
			break;
		case 7:
			this.deviceType = "智能视频服务器";
			break;
		case 8:
			this.deviceType = "音频服务器";
			break;
		case 9:
			this.deviceType = "音频终端";
			break;
		case 10:
			this.deviceType = "IO控制器";
			break;
		}
		this.deviceId = da.getDeviceId();
		this.zoneId = da.getZoneId();
		this.zoneName = da.getZoneName();
		this.departmentId = da.getDepartmentId();
		this.departmentName = da.getDepartmentName();
		this.branchId = da.getBranchId();
		this.branchName = da.getBranchName();
	}

	public String getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(String alarmID) {
		this.alarmID = alarmID;
	}

	public WarningInfo4Device() {

	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIsAffect() {
		return isAffect;
	}

	public void setIsAffect(String isAffect) {
		this.isAffect = isAffect;
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
		this.alarmName = alarmName;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

}
