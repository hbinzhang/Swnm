package com.service.sounddev.thirdsection;

import java.util.HashMap;
import java.util.Map;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

/**
 * 音频服务器回调函数接口
 * 
 * @author maming 2015-3-19下午4:03:00
 * 
 */
public interface CallBackFp extends StdCallCallback {

	public int invoke(int EventNo, String ParamStr);
}
