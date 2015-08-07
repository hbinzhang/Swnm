package com.entity.zone;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class PulseZoneMapBean implements Serializable,DeviceMapInterface {
	
	private static final long serialVersionUID = 4283297910614638254L;
	
	private Integer pulseZoneID;
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@Length(max=9,message="防区ID长度限制为9位")
	private Integer zoneID;
	@NotNull(message = "围栏主机ID不能为空")
	@NotEmpty(message = "围栏主机ID不能为空")
	private String hostID;
	private List<String> subZones;
	private String info;
	@NotNull(message = "子防区号不能为空")
	@NotEmpty(message = "子防区号不能为空")
	@MemberOf(value = {"1,2","1","2"},message ="子防区号只有  1,2 或 1 或 2 三种内容 ")
	private String subZone;//子防区号
	
	public String getSubZone() {
		
		StringBuffer sb= null;
		if(subZones!=null&&subZone==null){
			sb= new StringBuffer();
			for(String s : subZones){
				sb.append(s);
				sb.append(",");
			}
		}
		if(sb!=null){
			return sb.toString().substring(0, sb.toString().length()-1);
		}else{
			
			return subZone;
		}
		
	}
	public void setSubZone(String subZone) {
		this.subZone = subZone;
	}
	public Integer getPulseZoneID() {
		return pulseZoneID;
	}
	public void setPulseZoneID(Integer pulseZoneID) {
		this.pulseZoneID = pulseZoneID;
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
	public List<String> getSubZones() {
		return subZones;
	}
	public void setSubZones(List<String> subZones) {
		this.subZones = subZones;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
