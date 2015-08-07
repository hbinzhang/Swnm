package com.entity.linkagectl;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class IpcZoneMap {
	Integer iD; //摄像头防区映射编号
	@NotNull(message = "摄像头ID不能为空")
	@NotEmpty(message = "摄像头ID不能为空")
	String ipcId; //摄像头ID
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@Length(max=9,message="防区ID长度限制为9位")
	Integer zoneID;//防区ID
	@NotNull(message = "预置位不能为空")
	@NotEmpty(message = "预置位不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="预置位只能为整数")
	@Length(max=3,message="预置位长度限制为5位")
	Integer point; //预置位
	@NotNull(message = "主辅摄像头不能为空")
	@NotEmpty(message = "主辅摄像头不能为空")
	@MemberOf(value = {"0","1"},message ="主辅摄像头只有0或1")
	Integer mainIpc ;//主辅摄像头
	String info;//附加信息
	public Integer getID() {
		return iD;
	}
	public void setID(Integer iD) {
		this.iD = iD;
	}
	
	public String getIpcId() {
		return ipcId;
	}
	public void setIpcId(String ipcId) {
		this.ipcId = ipcId;
	}
	public Integer getZoneID() {
		return zoneID;
	}
	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getMainIpc() {
		return mainIpc;
	}
	public void setMainIpc(Integer mainIpc) {
		this.mainIpc = mainIpc;
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
