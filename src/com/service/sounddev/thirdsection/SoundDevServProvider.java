package com.service.sounddev.thirdsection;

import com.service.sounddev.secondsection.SecondCallBackFpImpl;

/**
 * 音频模块对外功能提供
 * @author maming
 * 2015-3-19下午1:16:53
 *
 */
public class SoundDevServProvider {
	private static SoundDevService service = null;

	public static SoundDevService getService() {
		if (null == service){
			service = new SoundDevServiceImpl(new CallBackFpImpl(),new SecondCallBackFpImpl());
		}
			
		return service;
	}
}
