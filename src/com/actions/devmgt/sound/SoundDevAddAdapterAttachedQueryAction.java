package com.actions.devmgt.sound;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.devmgt.sound.util.ContextUtil;
import com.entity.CommonBean;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.sounddev.database.ISoundDevservice;

import common.page.AjaxObject;

/**
 * 添加界面设备类型为音频终端时联动查询
 * 
 * @author maming
 * 
 */
public class SoundDevAddAdapterAttachedQueryAction extends ActionSupport {

	private static final long serialVersionUID = 7735735312485090298L;
	/**
	 * 返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

	private HashMap<String, Object> attachedDataHashMap = new HashMap<String, Object>();

	private ISoundDevservice soundDevService = null;

	private List<String> ipcids = null;

	private List<AudioServer> soundServer = null;

	private List<CommonBean> mgts = null;
	private List<SoundDevManufacturer> manufacturer = null;

	private IOrganizationManagerService organManagerService = null;

	private AjaxObject ajaxObject = null;
	
	private Log log = LogFactory.getLog(this.getClass());

	public List<AudioServer> getSoundServer() {
		return soundServer;
	}

	public void setSoundServer(List<AudioServer> soundServer) {
		this.soundServer = soundServer;
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

	@SuppressWarnings("unchecked")
	public String execute() {
		int result = 1;
		try {
			soundServer = (List<AudioServer>) soundDevService
				.queryAllAudioServer();
			manufacturer = (List<SoundDevManufacturer>) soundDevService
					.queryAllManufacturer();
			mgts = ContextUtil.getDepartmentsCanBeSeen(organManagerService);
//			ipcids = (List<String>) soundDevService
//					.queryAllIPCIDNotAttached();
			ipcids = (List<String>) soundDevService.queryAllIPCIDByMgtId(mgts.get(0).getId());
			attachedDataHashMap.put("soundServer", soundServer);
			attachedDataHashMap.put("mgts", mgts);
			attachedDataHashMap.put("vendorname", manufacturer);
			attachedDataHashMap.put("ipcids", ipcids);
			ajaxObject = new AjaxObject(result, attachedDataHashMap);
			
		}
		catch (Exception e) {
			log.error("SoundDevAddAdapterAttachedQueryAction error!", e);
		}
		return "success";
	}

	public List<String> getIpcids() {
		return ipcids;
	}

	public void setIpcids(List<String> ipcids) {
		this.ipcids = ipcids;
	}

	public List<CommonBean> getMgts() {
		return mgts;
	}

	public void setMgts(List<CommonBean> mgts) {
		this.mgts = mgts;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
