package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

public class SoundDevQQueryAdapterByIPCIdAction {

	private ISoundDevservice soundDevService = null;

	private IOperationLogService operationLogService = null;
	
	private AjaxObject ajaxObject = null;

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

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
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			@SuppressWarnings("unchecked")
			String id = (String) request.getSession()
					.getAttribute("SoundDevQQueryAdapterByIPCId");
			request.getSession().removeAttribute("SoundDevQQueryAdapterByIPCId");
			soundDevService.queryAdapterByIPCId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
