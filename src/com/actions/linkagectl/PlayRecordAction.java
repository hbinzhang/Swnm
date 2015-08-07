package com.actions.linkagectl;

import java.sql.SQLException;

import java.util.List;

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


public class PlayRecordAction extends ActionSupport {
	private ISoundZoneMapDao soundZoneMapDao=null;
	private ISoundSetDao soundSetDao=null;
	private Log log = LogFactory.getLog(this.getClass());	
	private List<String> listSoundID=null;
	private AjaxObject ajaxObject;
	private IOperationLogService operationLogService;
	private int ZoneID;
	private int level;
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	
	public int getZoneID() {
		return ZoneID;
	}

	public void setZoneID(int zoneID) {
		ZoneID = zoneID;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
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


	public ISoundSetDao getSoundSetDao() {
		return soundSetDao;
	}


	public void setSoundSetDao(ISoundSetDao soundSetDao) {
		this.soundSetDao = soundSetDao;
	}


	public List<String> getListSoundID() {
		return listSoundID;
	}


	public void setListSoundID(List<String> listSoundID) {
		this.listSoundID = listSoundID;
	}

	 public PlayRecordAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	
@SuppressWarnings({ "unchecked", "finally" })
	public String PlayRecord(){  //根据防区查找喇叭列表，播放声音
		int result = 0;	
		StringBuffer msg = new StringBuffer("自动语音");
		try{		
			setListSoundID(soundZoneMapDao.findallSoundID(ZoneID));
			if(listSoundID.size()>0){    	  
						
				SoundSet soundSet=(SoundSet)soundSetDao.findSoundSet(level);
				if(soundSet==null){
					log.error("失败，获取音频文件失败！");
					 msg.append("失败，获取音频文件失败！");
					return ERROR;
				}
				log.info(soundSet.getPath());
//				SoundDevServProvider.getService().devPlay(listSoundID,soundSet.getPath());//调用视频接口
				SoundDevServProvider.getService().devPlay(listSoundID,soundSet.getPath(),2);
				log.info("playRecord sucess!");
				msg.append("成功。");
				listSoundID=null;		
				result = 1; 			
				return SUCCESS;
			}
			result = 0;
			msg.append("防区没有配置音频设备！");
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
		catch(Exception e){
			 log.error(e.getMessage(),e);
			  result = 0;
			  msg.append("失败，其他异常！");
			  return ERROR;
		 }
		finally{		
			ajaxObject = new AjaxObject(result, msg.toString());
			operationLogService.createOperationLog("playRecord", "", 
					result, msg.toString());			
		}
			
		}
		

}
