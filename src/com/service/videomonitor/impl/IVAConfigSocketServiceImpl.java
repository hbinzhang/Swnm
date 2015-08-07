package com.service.videomonitor.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

import com.service.videomonitor.IVAConfigSocketService;

public class IVAConfigSocketServiceImpl implements IVAConfigSocketService {

	@Override
	public boolean notifyIVAServer(String ip, int port, String msg) {
		boolean res = false;
		try {
			Socket socket = new Socket(InetAddress.getByName(ip),port);
			OutputStream os = socket.getOutputStream();
			os.write(msg.getBytes());
			os.close();
			socket.close();
			res = true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
