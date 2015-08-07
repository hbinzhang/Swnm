package com.actions.linkagectl;

import java.sql.SQLException;

import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.dao.linkagectl.ISoundSetDao;
import com.dao.linkagectl.ISoundZoneMapDao;
import com.entity.linkagectl.SoundSet;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.thirdsection.SoundDevServProvider;

import common.page.AjaxObject;


public class SpeakVoiceAction extends ActionSupport {
	
	private ISoundZoneMapDao soundZoneMapDao=null;	
	private Log log = LogFactory.getLog(this.getClass());	
	private List<String> listSoundID=null;
	private AjaxObject ajaxObject;
	private IOperationLogService operationLogService;
	private int zoneID;
	private String fileName;
	private String soundfilepath;
	
	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}	
	
	
	public int getZoneID() {
		return zoneID;
	}

	public void setZoneID(int zoneID) {
		this.zoneID = zoneID;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}


	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}


	public ISoundZoneMapDao getSoundZoneMapDao() {
		return soundZoneMapDao;
	}


	public void setSoundZoneMapDao(ISoundZoneMapDao soundZoneMapDao) {
		this.soundZoneMapDao = soundZoneMapDao;
	}



	public List<String> getListSoundID() {
		return listSoundID;
	}


	public void setListSoundID(List<String> listSoundID) {
		this.listSoundID = listSoundID;
	}

	 public SpeakVoiceAction() {
		super();
		soundfilepath = this.getClass().getClassLoader().getResource("/").getPath();        
		soundfilepath = soundfilepath.substring(1,soundfilepath.indexOf("WEB-INF"))+"/resources/sound/"; 
		// TODO Auto-generated constructor stub
	}

	
@SuppressWarnings({ "unchecked", "finally" })
	public String SpeakVoice(){  //根据防区查找喇叭列表，人工喊话
		int result = 0;	
		StringBuffer msg = new StringBuffer("人工喊话");
		try{		
			setListSoundID(soundZoneMapDao.findallSoundID(zoneID));
			if(listSoundID.size()>0){  
			 
//				SoundDevServProvider.getService().devPlay(listSoundID,soundfilepath+fileName);//调用音频喊话接口
				SoundDevServProvider.getService().devPlay(listSoundID,soundfilepath+fileName,100);
				log.info("SpeakVoice sucess!");
				msg.append("成功。");
				listSoundID=null;		
				// return re;  	
				result = 1;
				return SUCCESS;
			}
			result = 0;
			msg.append("防区没有配置音频设备");			
			return ERROR;
			}
		catch(DataAccessException e)

	    {
			log.error(e.getMessage(),e);
			result = 0;
			msg.append("失败，数据访问异常！");
	        throw e;

	    }

	    catch(RuntimeException e)

	    {
	    	log.error(e.getMessage(),e);
	    	result = 0;
	    	msg.append("失败，运行期异常！");
	        throw e;

	    }		
		catch(SQLException e){
			  log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("失败，数据访问异常！");
			  return ERROR;
		  }
		catch(ServletException e){
			 log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("失败，其他异常！");
			  return ERROR;
		 }
		finally{		
			ajaxObject = new AjaxObject(result, msg.toString());
			operationLogService.createOperationLog("speakVoice", "", 
					result, msg.toString());
			
		}
			
		}
		

}
