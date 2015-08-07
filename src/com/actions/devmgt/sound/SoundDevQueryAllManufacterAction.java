package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.opensymphony.xwork2.ActionSupport;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 界面下发查询所有的音频设备厂商
 * 
 * @author maming 2015-4-10下午2:47:55
 * 
 */
public class SoundDevQueryAllManufacterAction extends ActionSupport {
	public static final String TOADDSOUNDDEV = "queryAllManufacturer";

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
		List<SoundDevManufacturer> soundDevResult = null;
		try {
			soundDevResult = (List<SoundDevManufacturer>) soundDevService
					.queryAllManufacturer();
		} catch (Exception e) {
			log.error("SoundDevQueryAllManufacterAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		test(soundDevResult);
		ajaxObject = new AjaxObject(result, message.toString(),soundDevResult);
		if (result == RESULT_SUC)
			return "success";
		return "success";
	}

	private void test(List<SoundDevManufacturer> soundDevResult) {
		System.out.println("音频设备厂商查询");
		System.out.println("vendorId------vendorName------");
		for (SoundDevManufacturer dev : soundDevResult) {
			System.out.print(dev.vendorId + "------");
			System.out.println(dev.vendorName + "------");
		}
		System.out.println("-----------------音频设备厂商查询end-----------------");

	}
}
