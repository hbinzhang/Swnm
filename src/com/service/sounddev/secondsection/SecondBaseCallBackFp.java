package com.service.sounddev.secondsection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.service.sounddev.thirdsection.CallBackFp;
import com.service.sounddev.thirdsection.SoundDevInnerService;

public abstract class SecondBaseCallBackFp implements CallBackFp {
	// 记录会话Id对应的终端Id
	/**
	 * 终端ID映射到会话id，抢占式
	 */
	
	/**
	 * 会话id映射到终端id
	 * 回调时，先从secondsidToTermid查终端id，
	 * 根据终端id从secondTermidTosid查sid
	 * sid不相等，则被抢占，不必控制IO控制器
	 */
	ConcurrentHashMap<String,Integer> secondTermIdToSid = new ConcurrentHashMap<String,Integer>();

	ConcurrentHashMap<Integer,String> secondSidToTermId = new ConcurrentHashMap<Integer,String>();
	
	ConcurrentHashMap<Integer, String> secondSidToFileName = new ConcurrentHashMap<Integer, String>();
	SoundDevInnerService secondservice = null;

	public Map<Integer, String> getSecondsidToFileName() {
		return secondSidToFileName;
	}

	public void setSecondsidToFileName(int sid, String fileName) {
		this.secondSidToFileName.put(sid, fileName);
	}

	public String getFileName(int sid) {
		return this.secondSidToFileName.get(sid);
	}

	public SecondBaseCallBackFp() {
	}

	public SecondBaseCallBackFp(SoundDevInnerService supply) {
		this.secondservice = supply;
	}

	public SoundDevInnerService getService() {
		return secondservice;
	}

	public void setService(SoundDevInnerService service) {
		this.secondservice = service;
	}

	public String getSecondTermId(int sid) {
		return secondSidToTermId.get(sid);
	}

	public void setSecondSidToTermId(int sid, String termId) {
		secondSidToTermId.put(sid,termId);
	}
	
	public int getSecondSid(String termId) {
		return secondTermIdToSid.get(termId);
	}

	public void setSecondTermidTosid(String termId,int sid) {
		secondTermIdToSid.put(termId,sid);
	}
	
	public static void main(String[] args) {
		SecondBaseCallBackFp fffBackFp = new SecondCallBackFpImpl();
		int ss = -99;
		String ssString = "";
		try {
			ssString = fffBackFp.secondSidToTermId.get(12);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ss);		
		return;
	}

}
