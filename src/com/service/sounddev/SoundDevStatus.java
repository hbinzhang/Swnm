package com.service.sounddev;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 音频设备状态
 * 
 * @author maming 2015-5-29下午5:16:12
 * 
 */
public class SoundDevStatus {
	
	public static final int SPEAKSTATUSLEVEL = 100;
	/**
	 * null/0空闲 >0工作状态，1-99优先级，100喊话状态
	 */
	private static ConcurrentHashMap<String, Integer> devStatusMap = new ConcurrentHashMap<String, Integer>();

	private Log log = LogFactory.getLog(this.getClass());
	
	private static SoundDevStatus instance = null;
	
	public static SoundDevStatus getInstance(){
		if(null == instance)
			instance = new SoundDevStatus();
		return instance;
	}

	public ConcurrentHashMap getDevStatusMap() {
		return devStatusMap;
	}

	public void setDevStatusMap(ConcurrentHashMap devStatusMap) {
		this.devStatusMap = devStatusMap;
	}

	public synchronized void setDevStatus(String termId, int status) {
		log.debug("成套终端：" + termId + "工作状态由:" + devStatusMap.get(termId) + "变为"
				+ status);
		devStatusMap.put(termId, status);
	}

	public synchronized int getDevStatus(String termId) {
		if (null == devStatusMap.get(termId))
			return 0;
		return devStatusMap.get(termId);

	}
	
	public synchronized void reSetDev(String termId) {
		devStatusMap.remove(termId);
		System.out.println(devStatusMap);
	}
}
