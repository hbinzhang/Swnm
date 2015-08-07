package com.actions.devmgt.sound;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.devmgt.sound.util.ContextUtil;
import com.entity.CommonBean;
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.SoundDev;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.entity.devmgt.sound.SoundDevQueryCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;

import common.page.AjaxObject;

/**
 * 音频设备修改action
 * 
 * @author maming 2015-4-10下午1:10:32
 * 
 */
public class SoundDevUpdateAction extends ActionSupport {

	public static final String TOUPDATESOUNDDEV = "toUpdateSoundDev";

	private SoundDev sounddev = null;

	private String conditionString = null;

	private SoundDevQueryCondition queryCondition = null;

	public void setQueryCondition(SoundDevQueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public SoundDevQueryCondition getQueryCondition() {
		return queryCondition;
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	private String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SoundDev getSounddev() {
		return sounddev;
	}

	public void setSounddev(SoundDev sounddev) {
		this.sounddev = sounddev;
	}

	private IOperationLogService operationLogService = null;

	private ISoundDevservice soundDevService = null;

	private Log log = LogFactory.getLog(this.getClass());

	private SoundDev toUpdateSoundDev = null;

	private HashMap<String, Object> attachedDataHashMap = new HashMap<String, Object>();

	private List<String> ipcids = null;

	private List<AudioServer> soundServer = null;

	private List<CommonBean> mgts = null;
	private List<SoundDevManufacturer> manufacturer = null;

	private List<AudioAdapter> audioAdapter = null;

	public HashMap<String, Object> getAttachedDataHashMap() {
		return attachedDataHashMap;
	}

	public void setAttachedDataHashMap(
			HashMap<String, Object> attachedDataHashMap) {
		this.attachedDataHashMap = attachedDataHashMap;
	}

	public List<String> getIpcids() {
		return ipcids;
	}

	public void setIpcids(List<String> ipcids) {
		this.ipcids = ipcids;
	}

	public List<AudioServer> getSoundServer() {
		return soundServer;
	}

	public void setSoundServer(List<AudioServer> soundServer) {
		this.soundServer = soundServer;
	}

	public List<CommonBean> getMgts() {
		return mgts;
	}

	public void setMgts(List<CommonBean> mgts) {
		this.mgts = mgts;
	}

	public List<SoundDevManufacturer> getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(List<SoundDevManufacturer> manufacturer) {
		this.manufacturer = manufacturer;
	}

	public List<AudioAdapter> getAudioAdapter() {
		return audioAdapter;
	}

	public void setAudioAdapter(List<AudioAdapter> audioAdapter) {
		this.audioAdapter = audioAdapter;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	private IOrganizationManagerService organManagerService = null;

	private AjaxObject ajaxObject = null;

	public SoundDev getToUpdateSoundDev() {
		return toUpdateSoundDev;
	}

	public void setToUpdateSoundDev(SoundDev toUpdateSoundDev) {
		this.toUpdateSoundDev = toUpdateSoundDev;
	}

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

	@SuppressWarnings("unchecked")
	public String execute() {
		String message = "";
		int result = RESULT_SUC;
		try {
			soundDevService.modify(toUpdateSoundDev);
		} catch (Exception e) {
			log.error("SoundDevUpdateAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		String operator = toUpdateSoundDev.getId();
		operationLogService.createOperationLog("updateSoundDev", operator,
				result, message);
		if (result == RESULT_SUC) {
			ajaxObject = new AjaxObject(1);
			return SUCCESS;
		} else {
			ajaxObject = new AjaxObject(0, message);
			return ERROR;
		}
	}

	// 进入编辑界面要查询相应的关联
	@SuppressWarnings("unchecked")
	public String enter() {
		// AssembleCondition();
		int result = 1;
		Object object = soundDevService.queryById(id);
		if (null == object)
			result = RESULT_FAIL;
		else {
			sounddev = (SoundDev) object;
		}
		soundServer = (List<AudioServer>) soundDevService.queryAllAudioServer();
		manufacturer = (List<SoundDevManufacturer>) soundDevService
				.queryAllManufacturer();
		mgts = ContextUtil.getDepartmentsCanBeSeen(organManagerService);
		/**
		 * 2015年5月15日 16:20:47 
		 * 修改bug 修改时候不光要加上查出来的未关联终端
		 * 本身占用的终端也要算作未关联终端
		 */
		audioAdapter = soundDevService.queryAllAdapterNotAttachedController();
		if(sounddev.devType.equals("IO控制器")){
			audioAdapter.add(((List<AudioAdapter>)soundDevService.queryAdapterByIp(sounddev.ownerIp)).get(0));
		}
		
//		ipcids = (List<String>) soundDevService.queryAllIPCIDNotAttached();
		if(sounddev.devType.equals("音频终端")){
			ipcids = (List<String>) soundDevService.queryAllIPCIDByMgtId(sounddev.mgtCode);
		/**
		 * 2015年5月15日 16:20:47 
		 * 修改bug 修改时候不光要加上查出来的未关联终端
		 * 本身占用的终端也要算作未关联终端
		 */
			ipcids.add(sounddev.ipcCode);
		}
		attachedDataHashMap.put("soundServer", soundServer);
		attachedDataHashMap.put("mgts", mgts);
		attachedDataHashMap.put("vendorname", manufacturer);
		attachedDataHashMap.put("ipcids", ipcids);
		attachedDataHashMap.put("audioAdapter", audioAdapter);
		ajaxObject = new AjaxObject(result, attachedDataHashMap);
		return "success";
	}
}
