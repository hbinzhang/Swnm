package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.actions.devmgt.sound.util.ContextUtil;
import com.entity.devmgt.sound.SoundDev;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.entity.devmgt.sound.SoundDevQueryCondition;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 音频设备按界面下发增加
 * 
 * @author maming 2015-4-10上午8:59:16
 * 
 */
public class SoundDevAddAction {

	public static final String TOADDSOUNDDEV = "toAddSoundDev";

	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;
	public static final int RESULT_IDREPEAT = 2;

	private ISoundDevservice soundDevService = null;
	
	private SoundDev toAddSoundDev = null;

	private IOperationLogService operationLogService = null;
	
	private Log log = LogFactory.getLog(this.getClass());
	
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

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	private AjaxObject ajaxObject = null;

	private List<SoundDevManufacturer> manufacturer = null;

	public List<SoundDevManufacturer> getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(List<SoundDevManufacturer> manufacturer) {
		this.manufacturer = manufacturer;
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
//		HttpServletRequest request = ServletActionContext.getRequest();
//		 toAddSoundDev = (SoundDev) request.getSession().getAttribute(
//				TOADDSOUNDDEV);
//		request.getSession().removeAttribute(TOADDSOUNDDEV);
		String message = "";
		int result = RESULT_SUC;
		try {
			if(null != soundDevService.queryById(toAddSoundDev.id)){
				result = RESULT_IDREPEAT;
			}
			//当前用户是非总公司用户当前添加的是音频服务器
			else if(!ContextUtil.isHeadquarters() && toAddSoundDev.devType.equals("音频服务器")){
				message = "只有总公司用户可以添加音频服务器";
				result = RESULT_FAIL;
			}
			else {
				soundDevService.addDev(toAddSoundDev);
				operationLogService.createOperationLog("addSoundDev", toAddSoundDev.id,
						result, message);
			}
		}
		catch (Exception e) {
			log.error("SoundDevDeleteAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		ajaxObject = new AjaxObject(result, message.toString());
		if (result == RESULT_SUC || result == RESULT_IDREPEAT)
			return "success";
		return "error";
	}
	
	public SoundDev getToAddSoundDev() {
		return toAddSoundDev;
	}

	public void setToAddSoundDev(SoundDev toAddSoundDev) {
		this.toAddSoundDev = toAddSoundDev;
	}

	@SuppressWarnings("unchecked")
	public String enter(){
		manufacturer = (List<SoundDevManufacturer>) soundDevService
				.queryAllManufacturer();
		return "success";
	}
}
