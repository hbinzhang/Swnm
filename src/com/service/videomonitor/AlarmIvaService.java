package com.service.videomonitor;

import java.util.Date;
public interface AlarmIvaService {
	void report(String deviceIp, Integer alarmCode, Date alarmTime,
			Integer point,String pictureURL);
}