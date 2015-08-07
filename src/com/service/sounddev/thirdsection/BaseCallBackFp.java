package com.service.sounddev.thirdsection;

import java.util.HashMap;
import java.util.Map;

/**
 * 回调函数基类
 * @author maming
 * 2015-3-21下午1:53:06
 *
 */
abstract class BaseCallBackFp implements CallBackFp{
	/**
	 * 终端ID映射到会话id，抢占式
	 */
	Map<String,Integer> thirdTermidToSid = new HashMap<String,Integer>();
	/**
	 * 会话id映射到终端id
	 * 回调时，先从secondsidToTermid查终端id，
	 * 根据终端id从secondTermidTosid查sid
	 * sid不相等，则被抢占，不必控制IO控制器
	 */
	Map<Integer,String> thirdSidToTermid = new HashMap<Integer,String>();
	Map<Integer, String> thirdSidToFileName = new HashMap<Integer, String>();
	SoundDevInnerService service = null;
	
	public void setsidToFileName(int sid, String fileName) {
		this.thirdSidToFileName.put(sid, fileName);
	}

	public String getFileName(int sid) {
		return this.thirdSidToFileName.get(sid);
	}
	public BaseCallBackFp() {
	}
	public BaseCallBackFp(SoundDevInnerService supply){
		this.service = supply;
	}
	public SoundDevInnerService getService() {
		return service;
	}
	public void setService(SoundDevInnerService service) {
		this.service = service;
	}
	
	public String getThirdTermId(int sid) {
		return thirdSidToTermid.get(sid);
	}

	public void setThirdSidToTermId(int sid, String termId) {
		thirdSidToTermid.put(sid,termId);
	}
	
	public int getThirdSid(String termId) {
		return thirdTermidToSid.get(termId);
	}

	public void setThirdTermidTosid(String termId,int sid) {
		thirdTermidToSid.put(termId,sid);
	}
	
}
