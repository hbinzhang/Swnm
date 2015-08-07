/**
 * 
 */
package com.entity.gis;

import com.entity.zone.ZoneBean;

/**
 * @author wyj
 * 
 *         GIS
 */
public class SecurityZoneInfo {

	private static final String ZONE_STAT_0 = "撤防";
	private static final String ZONE_STAT_1 = "布防";
	// private static final String ZONE_STAT_2 = "撤防";

	private String zoneID;// 防区ID
	private String zoneName;// 防区名称
	private String orientation;// 防区朝向
	private String mgtID;// 管理处ID
	private String mgtName;// 管理处名称
	private String branchID;// 分公司id
	private String branchName;// 分公司名称
	private String stat;// 防区状态
	private double startLon = 0;// 起始经度
	private double startLat = 0;// 起始纬度
	private double endLon = 0;// 终止经度
	private double endLat = 0;// 终止纬度

	public SecurityZoneInfo(ZoneBean zone) {
		this.zoneID = String.valueOf(zone.getZoneID().intValue());
		this.zoneName = zone.getZoneName();
		this.mgtID = zone.getMgtID();
		this.mgtName = zone.getMgtName();
		this.branchID = zone.getBranchID();
		this.branchName = zone.getBranchName();
		this.orientation = zone.getOrientation();
		switch (zone.getStatus()) {
		case 0:
			this.stat = ZONE_STAT_0;
			break;
		case 1:
			this.stat = ZONE_STAT_1;
			break;
		// case 2:
		// this.stat = ZONE_STAT_2;
		// break;
		}
		try{
			if (zone.getStartLon() != null) {
				this.startLon = Double.parseDouble(zone.getStartLon());
			}
			if (zone.getStartLat() != null) {
				this.startLat = Double.parseDouble(zone.getStartLat());
			}
			if (zone.getEndLon() != null) {
				this.endLon = Double.parseDouble(zone.getEndLon());
			}
			if (zone.getEndLat() != null) {
				this.endLat = Double.parseDouble(zone.getEndLat());
			}
		}catch (NumberFormatException nfe) {
			System.out.println("防区,编号：" + zoneID + "的地理坐标数据不是数字！");
			// TODO: handle exception
		}
	}

	public SecurityZoneInfo() {

	}

	public String getZoneID() {
		return zoneID;
	}

	public void setZoneID(String zoneID) {
		this.zoneID = zoneID;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getMgtID() {
		return mgtID;
	}

	public void setMgtID(String mgtID) {
		this.mgtID = mgtID;
	}

	public String getMgtName() {
		return mgtName;
	}

	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public double getStartLon() {
		return startLon;
	}

	public void setStartLon(double startLon) {
		this.startLon = startLon;
	}

	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getEndLon() {
		return endLon;
	}

	public void setEndLon(double endLon) {
		this.endLon = endLon;
	}

	public double getEndLat() {
		return endLat;
	}

	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

}
