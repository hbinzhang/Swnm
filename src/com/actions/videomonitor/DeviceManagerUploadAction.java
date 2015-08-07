package com.actions.videomonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.BaseAction;
import com.entity.common.ExcelConfigInfo;
import com.entity.videomonitor.TVmDecoder;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIva;
import com.entity.videomonitor.TVmNvr;
import com.service.logmgt.IOperationLogService;
import com.service.videomonitor.DeviceManageDecoderService;
import com.service.videomonitor.DeviceManageIPCService;
import com.service.videomonitor.DeviceManageIVAService;
import com.service.videomonitor.DeviceManageNVRService;
import com.util.video.DecoderConvertUtil;
import com.util.video.ExcelMutilSheetVideoUtil;
import com.util.video.IpcConvertUtil;
import com.util.video.IvaConvertUtil;
import com.util.video.NvrConvertUtil;
import common.page.AjaxObject;
@SuppressWarnings("serial")
public class DeviceManagerUploadAction extends BaseAction{
	
	private final static Log log = LogFactory
			.getLog(DeviceManagerUploadAction.class);
	
	private IOperationLogService operationLogService;
	private DeviceManageDecoderService decoderService;
	private DeviceManageIPCService ipcService;
	private DeviceManageNVRService nvrService;
	private DeviceManageIVAService ivaService;
 
	private File ipc;
	private String ipcFileName;
	private File nvr;
	private String nvrFileName;
	private File iva;
	private String ivaFileName;
	private File decoder;
	private String decoderFileName;
	
	private AjaxObject ajaxObject;
 
	//存放excel文件出错信息（行数以及格式）
	Map<String,Object> errorMessage;
	Map<String, Object> insertErrorMsg;
	/**
	 * 批量导入ipc
	 * @return
	 */
	public String batchImportIpc(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("批量导入视频信息：");
		
		try {
			log.info("begin excute ExcelUtil.readExcel.fileName:"+ipcFileName);
			
			errorMessage = new HashMap<String, Object>();

			// excel解析容器
			// 存放所有表数据大集合
			Map<String, List<List<Object>>> videoInfos = ExcelMutilSheetVideoUtil
					.readExcel(ipc, ipcFileName, errorMessage);
			// 解析excel文件有错误则直接返回

			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("文件格式错误！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportIpc",
						ipcFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			// 封装各个文件信息为集合对象
			// ipc信息集合，使用map剔除重复主键信息
			Map<String, List<Object>> ipcs = new LinkedHashMap<String, List<Object>>();

			List<List<Object>> list = videoInfos
					.get(ExcelConfigInfo.IPC_DATA_NAME);
			if (list != null) {
				for (List<Object> ipcList : list) {
					String ipcid = (String) ipcList.get(0);
					if(ipcid!=null && !("null".equals(ipcid))){
						ipcs.put(ipcid,ipcList);
					}
				}
			}
			List<TVmIpc> ipcBeans = null;// IPC对象集合
			if (ipcs.size() > 0) {
				ipcBeans = IpcConvertUtil.getTVmIpcs(ipcs);
			}
			// 创建容器，用于存放插入失败的行号
			List<String> ipcErrRows = new ArrayList<String>();
			//持久化操作
			if(ipcBeans!=null){
				int counter = 1;
				for(TVmIpc ipc:ipcBeans){
					counter++;
					try {
						int flag = ipcService.addIPC(ipc);
						if(flag!=1){
							ipcErrRows.add("IPC信息:第"+counter+"行:数据存储错误\r\n");
						}
					} catch (Exception e) {
						log.fatal("batch insert IPC error,this IPC id:"+ipc.getIpcid()+" failed reason:"+e.getMessage());
						ipcErrRows.add("IPC信息:第"+counter+"行:数据存储错误\r\n");
					}
				}
			}
			// 数据插入时是否有错
			if (ipcErrRows.size() > 0) {
				errorMessage.put(ExcelConfigInfo.IPC_DATA_ERROR,ipcErrRows);
			}
			
			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("批量数据插入数据库异常！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportIpc",
				ipcFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DeviceManagerUploadAction batchImportIPc failed:"+e.getMessage());
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("batchImportIpc", ipcFileName, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,errorMessage);
			return ERROR;
		}
		
		msg.append("成功！");
		//记录操作日志
		operationLogService.createOperationLog("batchImportIPc", ipcFileName, 
				result, msg.toString());
		
		ajaxObject=new AjaxObject(result,null);
		return SUCCESS;
	}
	
	/**
	 * 批量导入nvr
	 * @return
	 */
	public String batchImportNvr(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("批量导入视频信息：");
		
		try {
			log.info("begin excute ExcelUtil.readExcel.fileName:"+nvrFileName);
			
			errorMessage = new HashMap<String, Object>();

			// excel解析容器
			// 存放所有表数据大集合
			Map<String, List<List<Object>>> videoInfos = ExcelMutilSheetVideoUtil
					.readExcel(nvr, nvrFileName, errorMessage);
			// 解析excel文件有错误则直接返回

			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("文件格式错误！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportNvr",
						nvrFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			// 封装各个文件信息为集合对象
			//NVR信息集合，使用map剔除重复主键信息
			Map<String, List<Object>> nvrs = new LinkedHashMap<String, List<Object>>();

			List<List<Object>> nvrList = videoInfos
					.get(ExcelConfigInfo.NVR_DATA_NAME);
			if (nvrList != null) {
				for (List<Object> nList : nvrList) {
					String nvrid = (String) nList.get(0);
					if(nvrid!=null&&!("null".equals(nvrid))){
						nvrs.put(nvrid,nList);
					}
				}
			}
			List<TVmNvr> nvrBeans = null;// NVR对象集合
			if (nvrs.size() > 0) {
				nvrBeans = NvrConvertUtil.getTVmNvrs(nvrs);
			}
			// 创建容器，用于存放插入失败的行号
			List<String> nvrErrRows = new ArrayList<String>();
			//持久化操作
			if(nvrBeans!=null){
				int counter = 1;
				for(TVmNvr nvr:nvrBeans){
					counter++;
					try {
						int flag = nvrService.addNVR(nvr, new ArrayList<String>(), new ArrayList<String>());
						if(flag!=1){
							nvrErrRows.add("NVR信息:第"+counter+"行:数据存储错误\r\n");
						}
					} catch (Exception e) {
						log.fatal("batch insert nvr error,this nvr id:"+nvr.getNvrid()+" failed reason:"+e.getMessage());
						nvrErrRows.add("NVR信息:第"+counter+"行:数据存储错误\r\n");
					}
				}
			}
			// 数据插入时是否有错
			if (nvrErrRows.size() > 0) {
				errorMessage.put(ExcelConfigInfo.NVR_DATA_ERROR,nvrErrRows);
			}
			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("批量数据插入数据库异常！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportNvr",
				nvrFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DeviceManagerUploadAction batchImportNvr failed:"+e.getMessage());
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("batchImportNvr", nvrFileName, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,errorMessage);
			return ERROR;
		}
		
		msg.append("成功！");
		//记录操作日志
		operationLogService.createOperationLog("batchImportNvr", nvrFileName, 
				result, msg.toString());
		
		ajaxObject=new AjaxObject(result,null);
		return SUCCESS;
	}
	/**
	 * 批量导入iva
	 * @return
	 */
	public String batchImportIva(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("批量导入视频信息：");
		
		try {
			log.info("begin excute ExcelUtil.readExcel.fileName:"+ivaFileName);
			
			errorMessage = new HashMap<String, Object>();

			// excel解析容器
			// 存放所有表数据大集合
			Map<String, List<List<Object>>> videoInfos = ExcelMutilSheetVideoUtil
					.readExcel(iva, ivaFileName, errorMessage);
			// 解析excel文件有错误则直接返回

			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("文件格式错误！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportIva",
						ivaFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			// 封装各个文件信息为集合对象
			//IVA信息集合，使用map剔除重复主键信息
			Map<String, List<Object>> ivas = new LinkedHashMap<String, List<Object>>();

			List<List<Object>> ivaList = videoInfos
					.get(ExcelConfigInfo.IVA_DATA_NAME);
			if (ivaList != null) {
				for (List<Object> iList : ivaList) {
					String ivaid = (String) iList.get(0);
					if(ivaid!=null&&!("null".equals(ivaid))){
						ivas.put(ivaid,iList);
					}
				}
			}
			List<TVmIva> ivaBeans = null;// IVA对象集合
			if (ivas.size() > 0) {
				ivaBeans = IvaConvertUtil.getTVmIvas(ivas);
			}
			// 创建容器，用于存放插入失败的行号
			List<String> ivaErrRows = new ArrayList<String>();
			//持久化操作ivaService
			if(ivaBeans!=null){
				int counter = 1;
				for(TVmIva iva:ivaBeans){
					counter++;
					try {
						ivaService.addIVA(iva);
					} catch (Exception e) {
						log.fatal("batch insert iva error,this iva id:"+iva.getIvaID()+" failed reason:"+e.getMessage());
						ivaErrRows.add("IVA信息:第"+counter+"行:数据存储错误\r\n");
					}
				}
			}
			// 数据插入时是否有错
			if (ivaErrRows.size() > 0) {
				errorMessage.put(ExcelConfigInfo.IVA_DATA_ERROR, ivaErrRows);
			}
			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("批量数据插入数据库异常！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportIva",
				ivaFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DeviceManagerUploadAction batchImportIva failed:"+e.getMessage());
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("batchImportIva", ivaFileName, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,errorMessage);
			return ERROR;
		}
		
		msg.append("成功！");
		//记录操作日志
		operationLogService.createOperationLog("batchImportIva", ivaFileName, 
				result, msg.toString());
		
		ajaxObject=new AjaxObject(result,null);
		return SUCCESS;
	}
	/**
	 * 批量导入decoder
	 * @return
	 */
	public String batchImportDecoder(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("批量导入视频信息：");
		
		try {
			log.info("begin excute ExcelUtil.readExcel.fileName:"+decoderFileName);
			
			errorMessage = new HashMap<String, Object>();

			// excel解析容器
			// 存放所有表数据大集合
			Map<String, List<List<Object>>> videoInfos = ExcelMutilSheetVideoUtil
					.readExcel(decoder, decoderFileName, errorMessage);
			// 解析excel文件有错误则直接返回

			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("文件格式错误！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportDecoder",
						decoderFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			// 封装各个文件信息为集合对象
			//DECODER信息集合，使用map剔除重复主键信息
			Map<String, List<Object>> decoders = new LinkedHashMap<String, List<Object>>();

			List<List<Object>> decoderList = videoInfos
					.get(ExcelConfigInfo.DECODER_DATA_NAME);
			if (decoderList != null) {
				for (List<Object> dList : decoderList) {
					String decoderid = (String) dList.get(0);
					if(decoderid!=null&&!("null".equals(decoderid))){
						decoders.put(decoderid,dList);
					}
				}
			}
			List<TVmDecoder> decoderBeans = null;// DECODER对象集合
			if (decoders.size() > 0) {
				decoderBeans = DecoderConvertUtil.getTVmDecoders(decoders);
			}
			// 创建容器，用于存放插入失败的行号
			List<String> decoderErrRows = new ArrayList<String>();
			//持久化操作ivaService
			if(decoderBeans!=null){
				int counter = 1;
				for(TVmDecoder decoder:decoderBeans){
					counter++;
					try {
						int flag = decoderService.addDecoder(decoder);
						if(flag!=1){
							decoderErrRows.add("解码器信息:第"+counter+"行:数据存储错误\r\n");
						}
					} catch (Exception e) {
						log.fatal("batch insert decoder error,this decoder id:"+decoder.getDecoderid()+" failed reason:"+e.getMessage());
						decoderErrRows.add("解码器信息:第"+counter+"行:数据存储错误\r\n");
					}
				}
			}
 
			// 数据插入时是否有错
			if (decoderErrRows.size() > 0) {
				errorMessage.put(ExcelConfigInfo.DECODER_DATA_ERROR,decoderErrRows);
			}
			if (errorMessage.size() > 0) {
				result = 0;
				msg.append("批量数据插入数据库异常！");
				// 记录操作日志
				operationLogService.createOperationLog("batchImportDecoder",
				decoderFileName, result, msg.toString());
				ajaxObject = new AjaxObject(result, errorMessage);
				return ERROR;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DeviceManagerUploadAction batchImportDecoder failed:"+e.getMessage());
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("batchImportDecoder", decoderFileName, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,errorMessage);
			return ERROR;
		}
		
		msg.append("成功！");
		//记录操作日志
		operationLogService.createOperationLog("batchImportDecoder", decoderFileName, 
				result, msg.toString());
		
		ajaxObject=new AjaxObject(result,null);
		return SUCCESS;
	}
	/****************************************setter/getter**********************************/
	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}
	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	public File getIpc() {
		return ipc;
	}
	public void setIpc(File ipc) {
		this.ipc = ipc;
	}
	public String getIpcFileName() {
		return ipcFileName;
	}
	public void setIpcFileName(String ipcFileName) {
		this.ipcFileName = ipcFileName;
	}
	public File getNvr() {
		return nvr;
	}
	public void setNvr(File nvr) {
		this.nvr = nvr;
	}
	public String getNvrFileName() {
		return nvrFileName;
	}
	public void setNvrFileName(String nvrFileName) {
		this.nvrFileName = nvrFileName;
	}
	public File getIva() {
		return iva;
	}
	public void setIva(File iva) {
		this.iva = iva;
	}
	public String getIvaFileName() {
		return ivaFileName;
	}
	public void setIvaFileName(String ivaFileName) {
		this.ivaFileName = ivaFileName;
	}
	public File getDecoder() {
		return decoder;
	}
	public void setDecoder(File decoder) {
		this.decoder = decoder;
	}
	public String getDecoderFileName() {
		return decoderFileName;
	}
	public void setDecoderFileName(String decoderFileName) {
		this.decoderFileName = decoderFileName;
	}
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}
	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}
	public Map<String, Object> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(Map<String, Object> errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Map<String, Object> getInsertErrorMsg() {
		return insertErrorMsg;
	}
	public void setInsertErrorMsg(Map<String, Object> insertErrorMsg) {
		this.insertErrorMsg = insertErrorMsg;
	}
	public DeviceManageDecoderService getDecoderService() {
		return decoderService;
	}
	public void setDecoderService(DeviceManageDecoderService decoderService) {
		this.decoderService = decoderService;
	}
	public DeviceManageIPCService getIpcService() {
		return ipcService;
	}
	public void setIpcService(DeviceManageIPCService ipcService) {
		this.ipcService = ipcService;
	}
	public DeviceManageNVRService getNvrService() {
		return nvrService;
	}
	public void setNvrService(DeviceManageNVRService nvrService) {
		this.nvrService = nvrService;
	}
	public DeviceManageIVAService getIvaService() {
		return ivaService;
	}
	public void setIvaService(DeviceManageIVAService ivaService) {
		this.ivaService = ivaService;
	}
	
}
