package com.entity.alarmmgt;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

public class Alarm {

	/**
	 * 告警编号
	 */
	private Integer alarmId;
	
	/**
	 * 告警时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date alarmTime;
	

	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	@Override
	public String toString() {
		return "Alarm [alarmId=" + alarmId + ", alarmTime=" + alarmTime + "]";
	}
	
}
