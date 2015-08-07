package com.actions.devmgt.sound;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 根据管理处ID查摄像头
 * @author maming
 * 2015-5-19上午11:14:23
 *
 */
public class SoundDevQueryIPCByMgtIdAction  extends ActionSupport {
	private static final long serialVersionUID = 7735735312485090298L;
	/**
	 * 返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;
	
	private ISoundDevservice soundDevService = null;
	
	private AjaxObject ajaxObject = null;
	
	private List<String> ipcids = null;
	
	private String mgtId = null;

	public String getMgtId() {
		return mgtId;
	}

	public void setMgtId(String mgtId) {
		this.mgtId = mgtId;
	}

	public ISoundDevservice getSoundDevService() {
		return soundDevService;
	}

	public void setSoundDevService(ISoundDevservice soundDevService) {
		this.soundDevService = soundDevService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public List<String> getIpcids() {
		return ipcids;
	}

	public void setIpcids(List<String> ipcids) {
		this.ipcids = ipcids;
	}
	
	
	@SuppressWarnings("unchecked")
	public String execute() {
		int result = 1;
		ipcids = (List<String>) soundDevService
				.queryAllIPCIDByMgtId(mgtId);
		ajaxObject = new AjaxObject(result, ipcids);
		return "success";
	}
}
