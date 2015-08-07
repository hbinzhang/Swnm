package com.actions.devmgt.sound;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.entity.devmgt.sound.SoundDev;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.sounddev.database.ISoundDevservice;
import common.page.AjaxObject;
/**
 * 音频按界面下发id查询
 * 
 * @author maming 2015-4-10上午8:59:16
 * 
 */
public class SoundDevQueryById extends ActionSupport {

	private static final long serialVersionUID = 637055769622948044L;
	

	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

	private IOrganizationManagerService organManagerService;

	private ISoundDevservice soundDevService = null;

	private SoundDev sounddev = null;

	private String id = null;

	public SoundDev getSounddev() {
		return sounddev;
	}

	public void setSounddev(SoundDev sounddev) {
		this.sounddev = sounddev;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	private AjaxObject ajaxObject = null;

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}


	private Log log = LogFactory.getLog(this.getClass());

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
			Object object = soundDevService.queryById(id);
			if(null == object)
				result = RESULT_FAIL;
			else {
				sounddev = (SoundDev)object;	
			}
		} catch (Exception e) {
			log.error("SoundDevQueryById error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}
		ajaxObject = new AjaxObject(result, message.toString());
		test(sounddev);
		if (result == RESULT_SUC)
			return "success";
		return "error";
	}

	private void test(SoundDev dev) {
		if(null!= dev){
			System.out.println("音频设备根据id查询");
			System.out
					.println("devId------devName------factoryName------ipAddress------devType------ownerName------ownerIp------mgtCode");
			System.out.print(dev.id + "------");
			System.out.print(dev.name + "------");
			System.out.print(dev.vendorName + "------");
			System.out.print(dev.ipAddress + "------");
			System.out.print(dev.devType + "------");
			System.out.print(dev.ownerdev + "------");
			System.out.print(dev.ownerIp + "------");
			System.out.println(dev.mgtCode + "------");
			System.out.println("-----------------query()-----------------");
		}
	}

	public String enter() {
		return "success";
	}
}
