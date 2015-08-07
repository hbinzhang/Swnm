package com.service.videomonitor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.videomonitor.IIpcConfigMapper;
import com.entity.alarmmgt.WarnInfo;
import com.service.videomonitor.AlarmIvaService;
import com.service.videomonitor.jms.JMSDevWarnSender;
import com.service.videomonitor.jms.JMSSecWarnSender;

public class AlarmIvaServiceImpl implements AlarmIvaService {
	
	private final static Log log = LogFactory
			.getLog(AlarmIvaServiceImpl.class);
	private JMSDevWarnSender jmsDevWarnSender;
	private JMSSecWarnSender jmsSecWarnSender;
	private IIpcConfigMapper ipcConfigMapper;

	@Override
	public void report(String deviceIp, Integer alarmCode, Date alarmTime,
			Integer point,String pictureURL) {
 
		log.info("告警信息,deviceIp:" + deviceIp + ",alarmCode:"
				+ alarmCode + ",alarmTime:" + alarmTime + ",point:" + point);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ipcIp", deviceIp);
		paramMap.put("point", point);
		try {
			WarnInfo warnInfo=ipcConfigMapper.getAlarmByIpAndPreset(paramMap);
			warnInfo.setBegintime(alarmTime);
			
			WarnInfo wi = ipcConfigMapper.getAlarmByAlarmCode(alarmCode);
			
			if(wi!=null){
				warnInfo.setAlarmCode(alarmCode);
				warnInfo.setAlarmType(wi.getAlarmType());
				warnInfo.setRemarks(wi.getRemarks());
			}
			if(warnInfo.getAlarmType()==1){
				warnInfo.setPictureURL(pictureURL);
				jmsSecWarnSender.warnSend(warnInfo);
			}else if(warnInfo.getAlarmType()==2){
				jmsDevWarnSender.warnSend(warnInfo);
			}
			
		} catch (Exception e) {
			log.fatal("get ipc alarminfo error:"+e.getMessage());
		}
		
	}

	/************************* getter/setter *******************************/
	public JMSDevWarnSender getJmsDevWarnSender() {
		return jmsDevWarnSender;
	}

	public void setJmsDevWarnSender(JMSDevWarnSender jmsDevWarnSender) {
		this.jmsDevWarnSender = jmsDevWarnSender;
	}

	public JMSSecWarnSender getJmsSecWarnSender() {
		return jmsSecWarnSender;
	}

	public void setJmsSecWarnSender(JMSSecWarnSender jmsSecWarnSender) {
		this.jmsSecWarnSender = jmsSecWarnSender;
	}

	public IIpcConfigMapper getIpcConfigMapper() {
		return ipcConfigMapper;
	}

	public void setIpcConfigMapper(IIpcConfigMapper ipcConfigMapper) {
		this.ipcConfigMapper = ipcConfigMapper;
	}

}
