//package com.actions.devmgt.sound;
//
//import java.util.Enumeration;
//import java.util.List;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.struts2.ServletActionContext;
//
//import com.common.page.Page;
//import com.entity.devmgt.sound.SoundDev;
//import com.entity.devmgt.sound.SoundDevQueryCondition;
//import com.opensymphony.xwork2.ActionSupport;
//import com.service.sounddev.database.ISoundDevservice;
//import common.page.Pager;
//
///**
// * 音频设备管理前台入口
// * 
// * @author maming 2015-3-21下午6:17:17
// * 
// */
//public class SoundDevAction extends ActionSupport {
//
//	public static final String SOUNDDEVQUERYCONDITION = "sounddevquerycondition";
//	public static final String SOUNDDEVQUERYRESULT = "sounddevqueryresult";
//	public static final String TOADDSOUNDDEV = "toAddSoundDev";
//	public static final String TOUPDATESOUNDDEV = "toUpdateSoundDev";
//	public static final String TODELETESOUNDDEV = "toDeleteSoundDev";
//
//	private ISoundDevservice soundDevService = null;
//
//	public ISoundDevservice getSoundDevService() {
//		return soundDevService;
//	}
//
//	public void setSoundDevService(ISoundDevservice soundDevService) {
//		this.soundDevService = soundDevService;
//	}
//
//	@SuppressWarnings("unchecked")
//	public String query() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		SoundDevQueryCondition queryCondition = (SoundDevQueryCondition) request
//				.getSession().getAttribute(SOUNDDEVQUERYCONDITION);
//		// 音频服务器不属于任何管理处，管理处条件去掉
//		if (queryCondition.devType.equals("音频服务器")) {
//			queryCondition.mgt = null;
//		}
//		request.getSession().removeAttribute(SOUNDDEVQUERYCONDITION);
//		Pager<SoundDev> soundDevResult = (Pager<SoundDev>) soundDevService
//				.query(queryCondition);
//		request.getSession().setAttribute(SOUNDDEVQUERYRESULT, soundDevResult);
//		test(soundDevResult.getDatas());
//		return "success";
//	}
//
//	private void test(List<SoundDev> soundDevList2) {
//		System.out.println("音频设备查询");
//		System.out
//				.println("devId------devName------factoryName------ipAddress------devType------ownerName------ownerIp------mgtCode");
//		for (SoundDev dev : soundDevList2) {
//			System.out.print(dev.devId + "------");
//			System.out.print(dev.devName + "------");
//			System.out.print(dev.factoryName + "------");
//			System.out.print(dev.ipAddress + "------");
//			System.out.print(dev.devType + "------");
//			System.out.print(dev.ownerName + "------");
//			System.out.print(dev.ownerIp + "------");
//			System.out.println(dev.mgtCode + "------");
//		}
//		System.out.println("-----------------query()-----------------");
//	}
//
//	public String add() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		SoundDev toAddSoundDev = (SoundDev) request.getSession().getAttribute(
//				TOADDSOUNDDEV);
//		request.getSession().removeAttribute(TOADDSOUNDDEV);
//		soundDevService.addDev(toAddSoundDev);
//		return "success";
//	}
//
//	public String update() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		SoundDev toUpdateSoundDev = (SoundDev) request.getSession()
//				.getAttribute(TOUPDATESOUNDDEV);
//		request.getSession().removeAttribute(TOUPDATESOUNDDEV);
//		soundDevService.modify(toUpdateSoundDev);
//		return "success";
//	}
//
//	public String delete() {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		List<String> toDeleteSoundDevId = (List<String>) request.getSession()
//				.getAttribute(TODELETESOUNDDEV);
//		request.getSession().removeAttribute(TODELETESOUNDDEV);
//		soundDevService.batchDelete(toDeleteSoundDevId);
//		return "success";
//	}
//
//	public String execute() {
//		return "success";
//	}
//}
