package com.service.sounddev.thirdsection;

import javax.servlet.ServletException;

/**
 * 音频功能接口类，模块内部使用
 * @author maming
 * 2015-3-21下午12:10:47
 *
 */
public abstract class SoundDevInnerService implements SoundDevService{
	public abstract  String getIOControllerAddress(String termIp);
	public abstract  boolean firstcontrollPower(String ipAddress,byte openOrClose) throws ServletException ;
	public abstract  boolean secondcontrollPower(String ipAddress,byte openOrClose) throws ServletException ;
	public abstract  boolean thirdcontrollPower(String ipAddress,byte openOrClose) throws ServletException ;
	
}
