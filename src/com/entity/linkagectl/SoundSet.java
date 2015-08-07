package com.entity.linkagectl;

public class SoundSet {
	private Integer alarmLevel; 		//告警级别
	private String path; 		   //音频路径
	private String info; 		   //附加信息
	public Integer getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getPath() {
		if(null==path)
		{
			return "";
		}
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	
	public SoundSet() {
		super();
		alarmLevel=2;	
		// TODO Auto-generated constructor stub
	}
	
	
}
