package com.service.videomonitor;

public interface IVAConfigSocketService {
	/**
	 * @param ip
	 * @param port
	 * @param msg 消息内容
	 * @return 是否通知成功
	 */
	public boolean notifyIVAServer(String ip,int port,String msg);
}
