package com.entity.zone;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;

public class DefenceZoneMapBean implements Serializable,DeviceMapInterface{

	private static final long serialVersionUID = 1101086656971332145L;

	private Integer defenceZoneID;
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@Length(max=9,message="防区ID长度限制为9位")
	private Integer zoneID;
	@NotNull(message = "围栏主机ID不能为空")
	@NotEmpty(message = "围栏主机ID不能为空")
	private String hostID;
	@NotNull(message = "内部防区号不能为空")
	@NotEmpty(message = "内部防区号不能为空")
	@MatchPattern(pattern = "(\\d+)(,\\d+)*",message ="内部防区格式不正确(如:1,2,3)")
	private String innerZoneID;//內部防区序列号
	private String info;

	public Integer getDefenceZoneID() {
		return defenceZoneID;
	}

	public void setDefenceZoneID(Integer defenceZoneID) {
		this.defenceZoneID = defenceZoneID;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public String getHostID() {
		return hostID;
	}

	public void setHostID(String hostID) {
		this.hostID = hostID;
	}

	public String getInnerZoneID() {
		return innerZoneID;
	}

	public void setInnerZoneID(String innerZoneID) {
		this.innerZoneID = innerZoneID;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
