package com.actions.devmgt.sound;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;
import java.util.List;
import java.util.ArrayList;

/**
 * 音频按界面下发删除
 * 
 * @author maming 2015-4-10上午8:59:16
 * 
 */
public class SoundDevDeleteAction extends ActionSupport {

	private static final long serialVersionUID = -5290715505895274423L;

	private ISoundDevservice soundDevService = null;

	private IOperationLogService operationLogService = null;
	
	private List<String> toDeleteSoundDevIdUseByDatabase = null;
	
	private String toDeleteSoundDevId = null;
	public String getToDeleteSoundDevId() {
		return toDeleteSoundDevId;
	}

	public void setToDeleteSoundDevId(String toDeleteSoundDevId) {
		this.toDeleteSoundDevId = toDeleteSoundDevId;
	}

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
	public static final String TODELETESOUNDDEV = "toDeleteSoundDev";

	public String execute() {
		String [] tempIdString = toDeleteSoundDevId.split(",");
		toDeleteSoundDevIdUseByDatabase = new ArrayList<String>();
		for (String string : tempIdString) {
			toDeleteSoundDevIdUseByDatabase.add(string);
		}
		String message = "";
		int result = RESULT_SUC;
		try {
			int devsums = (Integer)soundDevService.batchDeleteCheck(toDeleteSoundDevIdUseByDatabase);
			if(devsums > 0)
			{
				message = "待删除设备存在从属设备，必须完整删除";
				result = RESULT_FAIL;
				ajaxObject = new AjaxObject(result, message.toString());
				return "error";
			}
			soundDevService.batchDelete(toDeleteSoundDevIdUseByDatabase);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SoundDevDeleteAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		String operator = toDeleteSoundDevId.toString();
		operationLogService.createOperationLog("deleteSoundDev", operator,
				result, message);
		ajaxObject = new AjaxObject(result, message.toString());
		if (result == RESULT_SUC)
			return "success";
		return "error";
	}
}
