/**
 * 
 */
package com.entity.gis;

import com.entity.videomonitor.TVmIpc;

/**
 * @author wyj
 * 
 *         GIS
 */
public class CameraInfo {
	private String deviceID;// 设备编号
	private String typeName;// 设备类型名称
	private String deviceName;// 设备名称

	private String mntID;// 管理处id
	private String mntName;// 管理处名称
	private String orgID;// 所属机构ID
	private String orgName;// 所属机构名称
	private String ip;// ip地址
	private String port;// ip端口
	private int stat = 0;// 状态 --对应ipcfault，暂缺，先定义成正常--0，
	private double lon = 0;// 经度
	private double lat = 0;// 纬度

	public CameraInfo(){
		
	}
	
	public CameraInfo(TVmIpc obj) {
		this.deviceID = obj.getIpcid();
		this.ip = obj.getIp();
		this.port = obj.getPort().toPlainString();
		try{
			if (obj.getIpclongitude() != null) {
				this.lon = Double.parseDouble(obj.getIpclongitude());
			}
			if (obj.getIpclatitude() != null) {
				this.lat = Double.parseDouble(obj.getIpclatitude());
			}
		}catch (NumberFormatException nfe) {
			System.out.println("摄像头,编号：" + deviceID + "的地理坐标数据不是数字！");
			// TODO: handle exception
		}
		this.deviceName = obj.getDevname();
		this.mntID = obj.getManagementagency();
		this.orgID = obj.getBranch();
		this.typeName = obj.getDevtype();// 是否需要转成名称
		this.stat = obj.getIpcFault();
	}
	
	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMntID() {
		return mntID;
	}

	public void setMntID(String mntID) {
		this.mntID = mntID;
	}

	public String getMntName() {
		return mntName;
	}

	public void setMntName(String mntName) {
		this.mntName = mntName;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
