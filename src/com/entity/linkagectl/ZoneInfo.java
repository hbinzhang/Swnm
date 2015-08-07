package com.entity.linkagectl;

public class ZoneInfo {
	private Integer zoneID; 		//防区编号
	private String zoneName; 		//防区名称
	private Integer mgtID;			//管理处ID
	private Integer status; 		//处理状态
	private String orientation ;	//朝向
	private String startLon;		//防区起始点经度
	private String startLat;		//防区起始点纬度
	private String endLon;			//防区终止点经度
	private String endLat;			//防区终止点纬度
	private String info;			//附加信息
	public Integer getZoneID() {
		return zoneID;
	}
	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}
	public String getZoneName() {
		if (zoneName == null)
		{
			return "";
		}
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public Integer getMgtID() {
		return mgtID;
	}
	public void setMgtID(Integer mgtID) {
		this.mgtID = mgtID;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getStartLon() {
		if(null == startLon)
		{
			return "";
		}
		return startLon;
	}
	public void setStartLon(String startLon) {
		this.startLon = startLon;
	}
	public String getStartLat() {
		if(null == startLat)
		{
			return "";
		}
		return startLat;
	}
	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}
	public String getEndLon() {
		if(null == endLon)
		{
			return "";
		}
		return endLon;
	}
	public void setEndLon(String endLon) {
		this.endLon = endLon;
	}
	public String getEndLat() {
		if(null == endLat)
		{
			return "";
		}
		return endLat;
	}
	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
