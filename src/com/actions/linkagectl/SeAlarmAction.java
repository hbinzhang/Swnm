package com.actions.linkagectl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.dao.linkagectl.IAlarmDao;
import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.linkagectl.SecurityAlarm;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.linkagectl.IAlarmManager;
import com.service.linkagectl.IBase64ToJPG;
import com.service.logmgt.IOperationLogService;
import common.page.AjaxObject;
import com.service.linkagectl.impl.AlarmUIPushImp;
public class SeAlarmAction extends ActionSupport {
	private IAlarmDao seAlarmDao=null;
	private Log log = LogFactory.getLog(this.getClass());	
	private AlarmUIPushImp alarmUIPush;
	private AjaxObject ajaxObject;
	private SecurityAlarm alarm;
	private IOperationLogService operationLogService;
	private ArrayList<String> pictures = new ArrayList<String>();
	private String autopicture;
	public String getAutopicture() {
		return autopicture;
	}

	public void setAutopicture(String autopicture) {
		this.autopicture = autopicture;
	}

	// 执行结果。用于页面显示
	private CommonBean commonBean;
	private Integer alarmID;
	private IBase64ToJPG base64ToJPG;
	
	
	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public Integer getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(Integer alarmID) {
		this.alarmID = alarmID;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	
	public SecurityAlarm getAlarm() {
		return alarm;
	}

	public void setAlarm(SecurityAlarm alarm) {
		this.alarm = alarm;
	}

	
	 public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}
	
	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public SeAlarmAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public IAlarmDao getSeAlarmDao() {
			return seAlarmDao;
		}

		public void setSeAlarmDao(IAlarmDao seAlarmDao) {
			this.seAlarmDao = seAlarmDao;
		}
		
	public IBase64ToJPG getBase64ToJPG() {
			return base64ToJPG;
		}

		public void setBase64ToJPG(IBase64ToJPG base64ToJPG) {
			this.base64ToJPG = base64ToJPG;
		}

	public AlarmUIPushImp getAlarmUIPush() {
			return alarmUIPush;
		}

		public void setAlarmUIPush(AlarmUIPushImp alarmUIPush) {
			this.alarmUIPush = alarmUIPush;
		}

	@SuppressWarnings("finally")
	public String confirmSecurityAlarm() throws IOException{
		//更新告警
		/* 提供给界面  需要传递SecurityAlarm对象，选择某条活动告警表。
		    alarmID      //选择的告警ID
		    alarmStatus  //处理状态，赋值1 已处理
      	    userID      //登陆的用户ID
      	    managerTime //点确认时的时间 java.util.Date;
      	    checkMothod  //1 视频复核  2 现场复核
      		checkLevel   //1 轻微 2 一般 3 主要 4严重
      		isReal      // 0 虚警 1 实警
      		reason      //触发告警原因
      		description  //事件过程
      		peopleID      //负责人  
      		report        //管理处为2 ，分公司为1 ，总公司为0    		   
      		vidioURL     //防区主摄像头录像URL 视频外包提供
      		leftVidioURL  //防区左相邻摄像头录像URL 视频外包提供
      		rightVidioURL //防区右相邻摄像头录像URL 视频外包提供
      		pictureURL    //图片 视频外包提供
      		info          // 记录备注
		 * */	
		if(alarm==null){
			log.error("alarm 告警赋值错误");
			return null;
		}
		int result = 0;
		StringBuffer msg = new StringBuffer("确认告警");	
		StringBuffer logmsg = new StringBuffer("");
		SecurityAlarm alarmTmp;
		
		try{		
			ActionContext ctx = ActionContext.getContext();
			Session session = (Session)ctx.getSession().get("session");
			alarm.setAlarmStatus(1);
			alarm.setUserID(session.getId());
			alarm.setManagerTime(new Date());
			alarm.setReport(Integer.parseInt(session.getLev()));
			
			//String webRoot = this.getClass().getClassLoader().getResource("/").getPath();        
		    //webRoot = webRoot.substring(0,webRoot.indexOf("WEB-INF")); 
			String resourceRoot=systemConfig("resourcePath");
			if(resourceRoot!=""){
				StringBuffer pictureURL= base64ToJPG.base64ToJPG(alarm.getAlarmID(), resourceRoot, pictures);				
				if(autopicture==null ){					
					alarm.setPictureURL(pictureURL.toString());
				}else{
					alarm.setPictureURL(autopicture+pictureURL.toString());
					autopicture=null;
				}				
			}
		    logmsg.append("告警ID: "+String.valueOf(alarm.getAlarmID()));	
		    alarmTmp = (SecurityAlarm) seAlarmDao.findAlarm(alarm.getAlarmID());
		    
		    if(alarmTmp.getReport()!= Integer.parseInt(session.getLev()) || alarmTmp.getAlarmStatus()!= 0)
		    {
		    	msg.append("告警ID: "+String.valueOf(alarm.getAlarmID()));	
		    	msg.append("该告警已经被处理，请刷新界面获取最新未处理告警列表！");		
				//msg.append("确认的时间: "+String.valueOf(alarmTmp.getManagerTime().toString())+",");
				
				log.info("not update the Security Alarm for it has been already processed!");		
				result = 2;
				return ERROR;
		    }
		    else
		    {
		    	msg.append("确认人ID: "+String.valueOf(alarm.getUserID())+",");		
				msg.append("确认的时间: "+String.valueOf(alarm.getManagerTime().toString())+",");
				if(alarm.getIsReal()==1){
					msg.append("实警");
				}else{
					msg.append("虚警");
				}
				log.info("update Security Alarm!");		
				seAlarmDao.update(alarm);			
				msg.append("--成功。"); 			
				alarmUIPush.pullWarningEvent2GIS(session.getOrganizationId(), "del", "securityAlarm", alarm.getAlarmID().toString()); //增加GIS推送消除告警
				result = 1;
				return SUCCESS;
		    }
			
		}
		catch(DataAccessException e)

	    {
			log.error(e.getMessage(),e);
			result = 0;
			msg.append("--失败，数据访问异常！");
	        throw e;

	    }

	    catch(RuntimeException e)

	    {
	    	log.error(e.getMessage(),e);
	    	result = 0;
	    	msg.append("--失败，运行期异常！");
	        throw e;

	    }		
		catch(SQLException e){
			  log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("--失败，数据访问异常！");
			  return ERROR;
		  }
		catch(Exception e){
			 log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("--失败，其他异常！");
			  return ERROR;
		 }
		finally{
			
			ajaxObject = new AjaxObject(result, msg.toString());
			operationLogService.createOperationLog("confirmSecurityAlarm", logmsg.toString(), 
					result, msg.toString());
			
		}
	}
	
	@SuppressWarnings("finally")
	public String reportSecurityAlarm() throws IOException{
		//更新告警
		//重新推送
		/* 界面  需要传递SecurityAlarm对象，选择某条活动告警表。
	    alarmID      //选择的告警ID
	    alarmStatus  //处理状态，赋值0 未处理完毕
  	    userID      //登陆的用户ID
  	    managerTime //点确认时的时间 java.util.Date;
  	    checkMothod  //1 视频复核  2 现场复核
  		checkLevel   //1 轻微 2 一般 3 主要 4严重
  		isReal      // 0 虚警 1 实警
  		reason      //触发告警原因
  		description  //事件过程
  		peopleID      //负责人  
  		report        //管理处上报为1 ，分公司上报为0     		   
  		vidioURL     //防区主摄像头录像URL 视频外包提供
  		leftVidioURL  //防区左相邻摄像头录像URL 视频外包提供
  		rightVidioURL //防区右相邻摄像头录像URL 视频外包提供
  		pictureURL    //图片 视频外包提供
  		info          // 记录备注
	 * */
	if(alarm==null){
		log.error("alarm 告警赋值错误");
		return null;
	}
	int result = 0;
	StringBuffer msg = new StringBuffer("上报告警");
	StringBuffer logmsg = new StringBuffer("");
	SecurityAlarm alarmTmp;
		
	try{
		ActionContext ctx = ActionContext.getContext();
		Session session = (Session)ctx.getSession().get("session");
		alarm.setAlarmStatus(0);
		alarm.setUserID(session.getId());
		alarm.setManagerTime(new Date());
		alarm.setReport(Integer.parseInt(session.getLev())-1);
		
		//String webRoot = this.getClass().getClassLoader().getResource("/").getPath();        
	    //webRoot = webRoot.substring(0,webRoot.indexOf("WEB-INF"));    
		String resourceRoot=systemConfig("resourcePath");
		if(resourceRoot!=""){
			StringBuffer pictureURL= base64ToJPG.base64ToJPG(alarm.getAlarmID(), resourceRoot, pictures);
			if(autopicture==null){					
				alarm.setPictureURL(pictureURL.toString());
			}else{
				alarm.setPictureURL(autopicture+pictureURL.toString());
				autopicture=null;
			}				
		}
	    
		logmsg.append("告警ID: "+String.valueOf(alarm.getAlarmID()));	
		alarmTmp = (SecurityAlarm) seAlarmDao.findAlarm(alarm.getAlarmID());
	    
		if(alarmTmp.getReport()!=Integer.parseInt(session.getLev()) || alarmTmp.getAlarmStatus()!=0)
	    {
			msg.append("告警ID: "+String.valueOf(alarm.getAlarmID()));	
			msg.append("该告警已经被处理，请刷新界面获取最新未处理告警列表！");			
			//msg.append("确认的时间: "+String.valueOf(alarmTmp.getManagerTime().toString())+",");
			
			log.info("not update the Security Alarm for it has been already processed!");		
			result = 2;
			return ERROR;
	    }
	    else
	    {
	    	msg.append("上报人ID: "+String.valueOf(alarm.getUserID())+",");		
			msg.append("上报的时间: "+String.valueOf(alarm.getManagerTime().toString())+",");
			if(alarm.getIsReal()==1){
				msg.append("实警");
			}else{
				msg.append("虚警");
			}
			if(alarm.getReport()==1){
				logmsg.append("，上报分公司");
			}else if(alarm.getReport()==2){
				logmsg.append("，上报总公司");
			}
			log.info("update Security Alarm!");		
			seAlarmDao.update(alarm);
			SecurityAlarm alarm1=(SecurityAlarm)seAlarmDao.findAlarm(alarm.getAlarmID());
			alarmUIPush.pullWarningEvent2GIS(session.getOrganizationId(), "del", "securityAlarm", alarm.getAlarmID().toString());//清除GIS告警
			alarmUIPush.Execute(alarm1); //告警推送
			msg.append("--成功。"); 	
			result = 1;
			return SUCCESS;
	    }
		
	}
	catch(DataAccessException e)

    {
		log.error(e.getMessage(),e);
		result = 0;
		msg.append("--失败，数据访问异常！");
        throw e;

    }

    catch(RuntimeException e)

    {
    	log.error(e.getMessage(),e);
    	result = 0;
    	msg.append("--失败，运行期异常！");
        throw e;

    }		
	catch(SQLException e){
		  log.error(e.getMessage(),e);
		  result = 0;
		  msg.append("--失败，数据访问异常！");
		  return ERROR;
	  }
	catch(Exception e){
		 log.error(e.getMessage(),e);
		  result = 0;
		  msg.append("--失败，其他异常！");
		  return ERROR;
	 }
	finally{		
		ajaxObject = new AjaxObject(result, msg.toString());
		operationLogService.createOperationLog("confirmSecurityAlarm", logmsg.toString(), 
				result, msg.toString());
	}
	}
	
	
	@SuppressWarnings("finally")
	public String confirmSecurityAlarmSimple() throws IOException{
		//分公司和总公司确认告警
		
		if(alarmID==null){
			log.error("alarmID 告警赋值错误");
			return null;
		}
		int result = 0;
		StringBuffer msg = new StringBuffer("简单确认告警");	
		StringBuffer logmsg = new StringBuffer("");
		SecurityAlarm alarmTmp2;
		try{		
			ActionContext ctx = ActionContext.getContext();
			Session session = (Session)ctx.getSession().get("session");
			SecurityAlarm alarmtmp=new SecurityAlarm();
			alarmtmp.setAlarmID(alarmID);
			alarmtmp.setAlarmStatus(1);
			alarmtmp.setReport(Integer.parseInt(session.getLev()));
			
			logmsg.append("告警ID: "+String.valueOf(alarmID));
			
			alarmTmp2 = (SecurityAlarm) seAlarmDao.findAlarm(alarmID);
		    
			if(alarmTmp2.getReport()!=Integer.parseInt(session.getLev()) || alarmTmp2.getAlarmStatus()!=0)
		    {
				msg.append("告警ID: "+String.valueOf(alarmID));	
				msg.append("该告警已经被处理，请刷新界面获取最新未处理告警列表！");			
				//msg.append("确认的时间: "+String.valueOf(alarmTmp.getManagerTime().toString())+",");
				
				log.info("not update the Security Alarm for it has been already processed!");		
				result = 2;
				return ERROR;
		    }
		    else
		    {
		    	msg.append("确认人ID: "+session.getId());				
				msg.append("确认的时间: "+String.valueOf(new Date()));
				log.info("update Security Alarm!");		
				seAlarmDao.updateSimple(alarmtmp);			
				msg.append("--成功。"); 			
				alarmUIPush.pullWarningEvent2GIS(session.getOrganizationId(), "del", "securityAlarm", alarmID.toString());//清除GIS告警                 //增加GIS推送消除告警
				result = 1;
				return SUCCESS;
		    }
			
			
		}
		catch(DataAccessException e)

	    {
			log.error(e.getMessage(),e);
			result = 0;
			msg.append("--失败，数据访问异常！");
	        throw e;

	    }

	    catch(RuntimeException e)

	    {
	    	log.error(e.getMessage(),e);
	    	result = 0;
	    	msg.append("--失败，运行期异常！");
	        throw e;

	    }		
		catch(SQLException e){
			  log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("--失败，数据访问异常！");
			  return ERROR;
		  }
		catch(Exception e){
			 log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("--失败，其他异常！");
			  return ERROR;
		 }
		finally{
			
			ajaxObject = new AjaxObject(result, msg.toString());
			operationLogService.createOperationLog("confirmSecurityAlarm", logmsg.toString(), 
					result, msg.toString());
			
		}
	}
	
	@SuppressWarnings("finally")
	public String reportSecurityAlarmSimple() throws IOException{
		//分公司上报告警
		
		if(alarmID==null){
			log.error("alarmID 告警赋值错误");
			return null;
		}
		
	int result = 0;
	StringBuffer msg = new StringBuffer("分公司上报告警");	
	StringBuffer logmsg = new StringBuffer("");	
	SecurityAlarm alarmTmp2;
	
	try{
		ActionContext ctx = ActionContext.getContext();
		Session session = (Session)ctx.getSession().get("session");
		SecurityAlarm alarmtmp=new SecurityAlarm();
		alarmtmp.setAlarmID(alarmID);
		alarmtmp.setAlarmStatus(0);
		alarmtmp.setReport(Integer.parseInt(session.getLev())-1);
		
		logmsg.append("告警ID: "+String.valueOf(alarmID));
		
		alarmTmp2 = (SecurityAlarm) seAlarmDao.findAlarm(alarmID);
	    
		if(alarmTmp2.getReport()!=Integer.parseInt(session.getLev()) || alarmTmp2.getAlarmStatus()!=0)
	    {
			msg.append("告警ID: "+String.valueOf(alarmID));	
			msg.append("该告警已经被处理，请刷新界面获取最新未处理告警列表！");			
			//msg.append("确认的时间: "+String.valueOf(alarmTmp.getManagerTime().toString())+",");
			
			log.info("not update the Security Alarm for it has been already processed!");		
			result = 2;
			return ERROR;
	    }
	    else
	    {
	    	logmsg.append("上报人ID: "+session.getId());				
			logmsg.append("上报的时间: "+String.valueOf(new Date()));
			log.info("update Security Alarm!");		
			seAlarmDao.updateSimple(alarmtmp);
			SecurityAlarm alarm1=(SecurityAlarm)seAlarmDao.findAlarm(alarmID);
			alarmUIPush.pullWarningEvent2GIS(session.getOrganizationId(), "del", "securityAlarm", alarmID.toString());//清除GIS告警                 //增加GIS推送消除告警
			alarmUIPush.Execute(alarm1); //告警推送给上一级
			msg.append("--成功。");
			result = 1;
			return SUCCESS;
	    }
		
	}
	catch(DataAccessException e)

    {
		log.error(e.getMessage(),e);
		result = 0;
		msg.append("--失败，数据访问异常！");
        throw e;

    }

    catch(RuntimeException e)

    {
    	log.error(e.getMessage(),e);
    	result = 0;
    	msg.append("--失败，运行期异常！");
        throw e;

    }		
	catch(SQLException e){
		  log.error(e.getMessage(),e);
		  result = 0;
		  msg.append("--失败，数据访问异常！");
		  return ERROR;
	  }
	catch(Exception e){
		 log.error(e.getMessage(),e);
		  result = 0;
		  msg.append("--失败，其他异常！");
		  return ERROR;
	 }
	finally{		
		ajaxObject = new AjaxObject(result, msg.toString());
		operationLogService.createOperationLog("confirmSecurityAlarm", logmsg.toString(), 
				result, msg.toString());
	}
	}
	
	
	

	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public static String systemConfig(String key)
	{
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("system", Locale.ENGLISH);
			return bundle.getString(key);
		} catch (Exception e) {
			return "";
		}
	}

}
