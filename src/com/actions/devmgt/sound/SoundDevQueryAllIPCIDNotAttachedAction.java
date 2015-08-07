package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.devmgt.sound.AudioServer;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 查询所有未关联的IPC的ID
 * 
 * @author maming 2015-4-3下午2:00:56
 * 
 */
public class SoundDevQueryAllIPCIDNotAttachedAction {
	public static final String TOADDSOUNDDEV = "queryAllIPCIDNotAttached";
	private ISoundDevservice soundDevService = null;

	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

	private AjaxObject ajaxObject = null;

	private Log log = LogFactory.getLog(this.getClass());

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public ISoundDevservice getSoundDevService() {
		return soundDevService;
	}

	public void setSoundDevService(ISoundDevservice soundDevService) {
		this.soundDevService = soundDevService;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		int result = RESULT_SUC;
		String message = "";
		List<String>  soundDevResult = null;
		try {
		soundDevResult = (List<String>) soundDevService
				.queryAllIPCIDNotAttached();
		}
		catch (Exception e) {
			log.error("SoundDevQueryAllIPCIDNotAttachedAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute(TOADDSOUNDDEV, soundDevResult);
		test(soundDevResult);
		ajaxObject = new AjaxObject(result, message.toString());
		if (result == RESULT_SUC)
			return "success";
		return "success";
	}

	private void test(List<String> soundDevResult) {
		System.out.println("音频所有摄像头ID查询");
		System.out.println("IPC的ID------");
		for (String dev : soundDevResult) {
			System.out.println(dev + "------");
		}
		System.out.println("-----------------音频所有摄像头ID查询end-----------------");

	}
}
