package com.service.sounddev;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.swing.JOptionPane;

import com.service.sounddev.thirdsection.SoundDevServProvider;

/**
 * 终端播放音频文件前台调用入口
 * @author maming
 * 2015-3-20下午1:53:00
 *
 */
public class SoundDevPlayAction {
	private List<String> ipAddress = new ArrayList<String>();
	private String fileName;

	public List<String> getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(List<String> ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String execute() {
		try {
			SoundDevServProvider.getService().devPlay(ipAddress, fileName);
			System.out.println("成功执行了");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
