package com.entity.zone;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class PositionZoneMapBean implements Serializable,DeviceMapInterface {

	private static final long serialVersionUID = 8298943266283917921L;
	
	private Integer positionZoneID;
	@NotNull(message = "围栏主机ID不能为空")
	@NotEmpty(message = "围栏主机ID不能为空")
	private String hostID;
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@Length(max=9,message="防区ID长度限制为9位")
	private Integer zoneID;
	@NotNull(message = "起始点不能为空")
	@NotEmpty(message = "起始点不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="起始点只能为整数")
	private Integer startPoint;//起始点
	@NotNull(message = "终止点不能为空")
	@NotEmpty(message = "终止点不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="终止点只能为整数")
	private Integer endPoint;//终止点
	@NotNull(message = "内部防区号不能为空")
	@NotEmpty(message = "内部防区号不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="内部防区号只能为整数")
	private Integer innerZoneID;//内部防区号
	@NotNull(message = "通道号不能为空")
	@NotEmpty(message = "通道号不能为空")
	@MemberOf(value = {"1","2"},message ="通道号只有1或2")
	private Integer chanID;//通道号
	private String info;
	
	public Integer getPositionZoneID() {
		return positionZoneID;
	}
	public void setPositionZoneID(Integer positionZoneID) {
		this.positionZoneID = positionZoneID;
	}
	public String getHostID() {
		return hostID;
	}
	public void setHostID(String hostID) {
		this.hostID = hostID;
	}
	public Integer getZoneID() {
		return zoneID;
	}
	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}
	public Integer getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}
	public Integer getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Integer endPoint) {
		this.endPoint = endPoint;
	}
 
	public Integer getInnerZoneID() {
		return innerZoneID;
	}
	public void setInnerZoneID(Integer innerZoneID) {
		this.innerZoneID = innerZoneID;
	}
	public Integer getChanID() {
		return chanID;
	}
	public void setChanID(Integer chanID) {
		this.chanID = chanID;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
