package com.entity.linkagectl;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class SoundZoneMap {
	Integer iD; //音频防区映射编号
	@NotNull(message = "音频ID不能为空")
	@NotEmpty(message = "音频ID不能为空")
	String audioID; //音频ID
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	Integer zoneID;//防区ID
	@NotNull(message = "主辅音频不能为空")
	@NotEmpty(message = "主辅音频不能为空")
	@MemberOf(value = {"0","1"},message ="主辅音频只有0或1")
	Integer main ;//主辅音频
	String info;//附加信息
	public Integer getiD() {
		return iD;
	}
	public void setiD(Integer iD) {
		this.iD = iD;
	}
	public String getAudioID() {
		return audioID;
	}
	public void setAudioID(String audioID) {
		this.audioID = audioID;
	}
	public Integer getZoneID() {
		return zoneID;
	}
	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}
	public Integer getMain() {
		return main;
	}
	public void setMain(Integer main) {
		this.main = main;
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
