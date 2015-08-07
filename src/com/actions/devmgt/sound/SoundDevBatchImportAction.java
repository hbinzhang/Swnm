package com.actions.devmgt.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.devmgt.sound.util.ExcelObjectConvertUtil;
import com.actions.devmgt.sound.util.ExcelUtil;
import com.entity.common.ExcelConfigInfo;
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.entity.efence.FenceBean;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.DefenceZoneMapBean;
import com.entity.zone.IntegrationZoneMapBean;
import com.entity.zone.PositionZoneMapBean;
import com.entity.zone.PulseZoneMapBean;
import com.entity.zone.ZoneBean;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import com.util.zone.ExcelMutilSheetUtil;

import common.page.AjaxObject;

/**
 * Excel文件形式批量导入音频设备
 * 
 * @author maming 2015-4-3上午10:10:07
 * 
 */
public class SoundDevBatchImportAction extends ActionSupport {
	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

	private ISoundDevservice soundDevService = null;

	private IOperationLogService operationLogService = null;

	private AjaxObject ajaxObject = null;

	private String sounddevFileName;

	private File sounddev;

	// 存放excel文件出错的行数
	private Map<String, Object> errorMessage;

	private Log log = LogFactory.getLog(this.getClass());
	
	private String sounddevContentType ; 


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

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public Map<String, Object> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Map<String, Object> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String execute() {
		try {
			//构造数据
//			test();
			log.info("begin excute SoundDevBatchImportAction:"
					+ sounddevFileName);
			errorMessage = new HashMap<String, Object>();
			// excel解析容器
			// 存放所有表数据大集合
			Map<String, List<List<Object>>> soundDevInfos = ExcelUtil
					.readExcel(sounddev, sounddevFileName, errorMessage);

			// 解析excel文件有错误则直接返回

				//解析excel文件有错误则直接返回
				if(errorMessage.size()>0){
					ajaxObject = new AjaxObject(0,errorMessage);
					return ERROR;
				}

			// 音频终端对象集合

			List<List<Object>> termList = soundDevInfos
					.get(ExcelUtil.SOUNDTERMINALTABLE);

			List<AudioAdapter> soundAdapters = null;// 音频终端对象集合

			if (termList != null && termList.size() > 0) {

				soundAdapters = ExcelObjectConvertUtil
						.getSoundTerminals(termList);

			}

			// IO控制器表对象集合

			List<List<Object>> ioconList = soundDevInfos
					.get(ExcelUtil.IOCONTROLLERTABLE);

			List<IOCtroller> ioControllers = null;// IO控制器表对象集合

			if (ioconList != null && ioconList.size() > 0) {

				ioControllers = ExcelObjectConvertUtil
						.getIOControllers(ioconList);

			}

			// 音频服务器表对象集合

			List<List<Object>> serverList = soundDevInfos
					.get(ExcelUtil.SOUNDSERVERTABLE);

			List<AudioServer> audioServerBeans = null;// 一体化映射对象集合

			if (serverList != null && serverList.size() > 0) {

				audioServerBeans = ExcelObjectConvertUtil
						.getAudioServers(serverList);

			}

			// 音频厂商表对象集合

			List<List<Object>> manufacturerList = soundDevInfos
					.get(ExcelUtil.MANUFACTURERTABLE);

			List<SoundDevManufacturer> manufacturerBeans = null;// 音频厂商表对象集合

			if (manufacturerList != null && manufacturerList.size() > 0) {

				manufacturerBeans = ExcelObjectConvertUtil
						.getManufacturers(manufacturerList);

			}

			// 判断文件传过来的hostid是否已在数据库中存在，存在则不执行添加操作。

			if (soundAdapters != null) {

				// 获取终端id集合
				List<String> termIds = soundDevService.queryAllAudioAdapterID();
				for (AudioAdapter audioAdapter : soundAdapters) {
					if (termIds != null) {
						if (!termIds.contains(audioAdapter.getAudioId().trim())) {
							soundDevService.addAdapter(audioAdapter);
						}

					} else {

						soundDevService.addAdapter(audioAdapter);

					}

				}

			}

			if (ioconList != null) {
				// 获取控制器id集合
				List<String> ioconIds = soundDevService
						.queryAllIOControllerID();

				for (IOCtroller ioCtroller : ioControllers) {

					if (ioconIds != null) {

						if (!ioconIds.contains(ioCtroller.getID().trim())) {

							soundDevService.addIOController(ioCtroller);
						}
					} else {
						soundDevService.addIOController(ioCtroller);
					}
				}
			}
			if (audioServerBeans != null) {

				// 获取服务器id集合
				List<String> serverIds = soundDevService.queryAllServerID();

				for (AudioServer serverBean : audioServerBeans) {

					if (serverIds != null) {

						if (!serverIds
								.contains(serverBean.getSERVERID().trim())) {

							soundDevService.addAudioServer(serverBean);
						}
					} else {
						soundDevService.addAudioServer(serverBean);
					}
				}
			}
			if (manufacturerBeans != null) {
				// 获取厂商id集合
				List<String> manufacturerIds = soundDevService
						.queryAllmanufacturerID();
				for (SoundDevManufacturer manufacturerBean : manufacturerBeans) {
					if (manufacturerIds != null) {
						if (!manufacturerIds.contains(manufacturerBean
								.getVendorId().trim())) {
							soundDevService.addManufacturer(manufacturerBean);
						}
					} else {
						soundDevService.addManufacturer(manufacturerBean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ZoneManagerAction batchImportFence failed:" + e);
		}
		ajaxObject = new AjaxObject(1,null);
		return SUCCESS;
	}

	public String getSounddevFileName() {
		return sounddevFileName;
	}

	public void setSounddevFileName(String sounddevFileName) {
		this.sounddevFileName = sounddevFileName;
	}

	public File getSounddev() {
		return sounddev;
	}

	public void setSounddev(File sounddev) {
		this.sounddev = sounddev;
	}

	public String getSounddevContentType() {
		return sounddevContentType;
	}

	public void setSounddevContentType(String sounddevContentType) {
		this.sounddevContentType = sounddevContentType;
	}

//	private void test() {
//		soundDevFileFileName = "C://音频设备导入表.xlsx";
//		soundDevFile = new File(soundDevFileFileName);
//	}
}
