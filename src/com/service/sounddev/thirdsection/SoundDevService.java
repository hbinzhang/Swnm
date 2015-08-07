package com.service.sounddev.thirdsection;

import java.util.List;

import javax.servlet.ServletException;

/**
 * 音频模块服务接口
 * 
 * @author maming 2015-3-19上午10:40:27
 * 
 */
public interface SoundDevService {

	/**
	 * 音频终端播放声音文件
	 * 
	 * @param ipList
	 * @param fileNameList
	 */
	@Deprecated
	//建议调用public synchronized void devPlay(List<String> list, String fileName,int level)
	void devPlay(List<String> list, String fileName) throws ServletException;

	void devPlay(List<String> list, String fileName, int level)
			throws ServletException;
}
