package com.actions.devmgt.sound;

import java.util.HashMap;
import java.util.List;

import com.entity.devmgt.sound.AudioAdapter;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.sounddev.database.ISoundDevservice;

import common.page.AjaxObject;

/**
 * 添加界面设备类型是控制器时联动查询
 * @author maming
 *
 */
public class SounddevAddIOControllerAttachedQueryAction   extends ActionSupport {
	private static final long serialVersionUID = -7066044436847339739L;

	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;
	
	private AjaxObject ajaxObject = null;

	private List<AudioAdapter> audioAdapter = null;
	
	private ISoundDevservice soundDevService = null;
	
	private HashMap<String, Object> attachedDataHashMap = new HashMap<String, Object>();
	
	private IOrganizationManagerService organManagerService = null;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		int result = 1;
		try {
			audioAdapter = (List<AudioAdapter>) soundDevService
				.queryAllAdapterNotAttachedController();
			attachedDataHashMap.put("audioAdapter", audioAdapter);
			ajaxObject = new AjaxObject(result, attachedDataHashMap);
			
		}
		catch (Exception e) {
			result = RESULT_FAIL;
		}
		return "success";
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public List<AudioAdapter> getAudioAdapter() {
		return audioAdapter;
	}

	public void setAudioAdapter(List<AudioAdapter> audioAdapter) {
		this.audioAdapter = audioAdapter;
	}
	
	public void setSoundDevService(ISoundDevservice soundDevService) {
		this.soundDevService = soundDevService;
	}
	
	public ISoundDevservice getSoundDevService() {
		return soundDevService;
	}

	public HashMap<String, Object> getAttachedDataHashMap() {
		return attachedDataHashMap;
	}

	public void setAttachedDataHashMap(HashMap<String, Object> attachedDataHashMap) {
		this.attachedDataHashMap = attachedDataHashMap;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

}
