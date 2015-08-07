package com.entity.zone;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class IntegrationZoneMapBean implements Serializable,DeviceMapInterface {

	private static final long serialVersionUID = -2666385532768107291L;

	private Integer integrationZoneID;
	@NotNull(message = "围栏主机ID不能为空")
	@NotEmpty(message = "围栏主机ID不能为空")
	private String hostID;
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@Length(max=9,message="防区ID长度限制为9位")
	private Integer zoneID;
	@NotNull(message = "节点号不能为空")
	@NotEmpty(message = "节点号不能为空")
	@MatchPattern(pattern = "(\\d+)(,\\d+)*",message ="节点格式不正确(如:1,2,3)")
	private String point;//节点号
	private String info;

	public Integer getIntegrationZoneID() {
		return integrationZoneID;
	}

	public void setIntegrationZoneID(Integer integrationZoneID) {
		this.integrationZoneID = integrationZoneID;
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

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
