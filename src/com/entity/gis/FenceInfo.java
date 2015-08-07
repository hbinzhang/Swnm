/**
 * 
 */
package com.entity.gis;

import com.entity.efence.FenceBean;

/**
 * @author wyj
 * 
 *         GIS
 * 
 */
public class FenceInfo {

	public FenceInfo(FenceBean fb) {
		this.hostID = fb.getHostID();
		this.fenceType = String.valueOf(fb.getFenceType());
		this.ip = fb.getIp();
		this.port = String.valueOf(fb.getPort());
		this.fenceStatus = String.valueOf(fb.getFenceStatus());
		this.mntMentID = fb.getMntMentID();
		this.mntMentName = fb.getMntMentName();
		this.branchID = fb.getSubComID();
		this.branchName = fb.getSubComName();
		this.fenceName = fb.getFenceName();
		try{
			if (fb.getEfLongitude() != null) {
				this.hostLon = Double.parseDouble(fb.getEfLongitude());
			}
			if (fb.getEfLatitude() != null) {
				this.hostLat = Double.parseDouble(fb.getEfLatitude());
			}
		}catch (NumberFormatException nfe) {
			System.out.println("电子围栏,编号：" + hostID + "的地理坐标数据不是数字！");
			// TODO: handle exception
		}
	}

	public FenceInfo() {

	}

	private String hostID;
	private String fenceType;
	private String fenceName;
	private String ip;
	private String port;
	private String fenceStatus;
	private String mntMentID;
	private String branchID;
	private String mntMentName;
	private String branchName;
	private double hostLon;
	private double hostLat;

	public String getHostID() {
		return hostID;
	}

	public void setHostID(String hostID) {
		this.hostID = hostID;
	}

	public String getFenceType() {
		return fenceType;
	}

	public void setFenceType(String fenceType) {
		this.fenceType = fenceType;
	}

	public String getFenceName() {
		return fenceName;
	}

	public void setFenceName(String fenceName) {
		this.fenceName = fenceName;
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

	public String getFenceStatus() {
		return fenceStatus;
	}

	public void setFenceStatus(String fenceStatus) {
		this.fenceStatus = fenceStatus;
	}

	public String getMntMentID() {
		return mntMentID;
	}

	public void setMntMentID(String mntMentID) {
		this.mntMentID = mntMentID;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getMntMentName() {
		return mntMentName;
	}

	public void setMntMentName(String mntMentName) {
		this.mntMentName = mntMentName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public double getHostLon() {
		return hostLon;
	}

	public void setHostLon(double hostLon) {
		this.hostLon = hostLon;
	}

	public double getHostLat() {
		return hostLat;
	}

	public void setHostLat(double hostLat) {
		this.hostLat = hostLat;
	}

}
