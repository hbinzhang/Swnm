package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.AudioServer;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;

/**
 * 查询所有的音频服务器
 * @author maming
 * 2015-4-12下午1:09:18
 *
 */
public class SoundDevQueryAllAudioServerAction  extends ActionSupport {
	
	private static final long serialVersionUID = 4281463622465690892L;
	
	public static final String TOADDSOUNDDEV = "queryAllAudioServer";
	
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
		List<AudioServer> soundDevResult = null;
		try {
			soundDevResult = (List<AudioServer>) soundDevService
				.queryAllAudioServer();
		}
		catch (Exception e) {
			log.error("SoundDevQueryAllAudioServerAction error!", e);
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

	private void test(List<AudioServer> soundDevResult) {
		System.out.println("音频所有服务器查询");
		System.out.println("SERVERID------SERVERNAME------IPADDRESS------");
		for (AudioServer dev : soundDevResult) {
			System.out.print(dev.SERVERID + "------");
			System.out.print(dev.SERVERNAME + "------");
			System.out.println(dev.IPADDRESS + "------");
		}
		System.out.println("-----------------音频所有服务器查询end-----------------");

	}
}
