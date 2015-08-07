package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.devmgt.sound.AudioAdapter;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 所有未关联IO控制器的音频终端查询
 * @author maming
 * 2015-4-3下午1:44:03
 *
 */
public class SoundDevQueryAllAdapterNotAttachedControllerAction {
	
	public static final String TOADDSOUNDDEV = "queryAllAdapterNotAttachedController";
	
	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;
	
	private ISoundDevservice soundDevService = null;
	
	private IOperationLogService operationLogService = null;
	
	private AjaxObject ajaxObject = null;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	
	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public ISoundDevservice getSoundDevService() {
		return soundDevService;
	}

	public void setSoundDevService(ISoundDevservice soundDevService) {
		this.soundDevService = soundDevService;
	}

	public String execute() {
		@SuppressWarnings("unchecked")
		int result = RESULT_SUC;
		String message = "";
		List<AudioAdapter> soundDevResult = null;
		try {
				soundDevResult = (List<AudioAdapter>) soundDevService
				.queryAllAdapterNotAttachedController();
		}
		catch (Exception e) {
			log.error("SoundDevDeleteAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute(TOADDSOUNDDEV, soundDevResult);
		test(soundDevResult);
		ajaxObject = new AjaxObject(result, message.toString());
		if (result == RESULT_SUC)
			return "success";
		return "error";
	}

	private void test(List<AudioAdapter> soundDevResult) {
		System.out.println("所有未关联IO控制器的音频终端查询");
		System.out.println("getAudioId------getAudioName------getAudioIp------");
		for (AudioAdapter dev : soundDevResult) {
			System.out.print(dev.getAudioId() + "------");
			System.out.print(dev.getAudioName() + "------");
			System.out.println(dev.getAudioIp() + "------");
		}
		System.out.println("-----------------所有未关联IO控制器的音频终端查询end-----------------");

	}
}
