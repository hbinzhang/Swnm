package com.actions.videomonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;

import com.entity.authmgt.Session;
import com.entity.videomonitor.TVmDecoder;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.videomonitor.DeviceManageDecoderService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class DeviceManageDecoderAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DeviceManageDecoderAction.class);

	private DeviceManageDecoderService decoderService;
	
	public DeviceManageDecoderService getDecoderService() {
		return decoderService;
	}

	public void setDecoderService(DeviceManageDecoderService decoderService) {
		this.decoderService = decoderService;
	}

	private String tip;
	//private File upload;
	private TVmDecoder decoderToAddOrUpdate;
	private String decoderidToDelete;
	private List<TVmDecoder> decoders;
	private int pageNo = 1;
	private int pageSize = 15;
	private int rowCount = 0;
	private int pageCount = 1;
	private String decodersjson;
	private String ajaxDecoderSearchCond;
	private List<Map<String,String>> branchs;
	private Map<String,List<Map<String, String>>> managementagencies;
	private boolean isadd;
	private String searchBranch;
	private String searchManagement;
	private String searchAttachType;
	private String searchAttachValue;
	private IOperationLogService operationLogService;
	
	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	
	public String getSearchBranch() {
		return searchBranch;
	}

	public void setSearchBranch(String searchBranch) {
		this.searchBranch = searchBranch;
	}

	public String getSearchManagement() {
		return searchManagement;
	}

	public void setSearchManagement(String searchManagement) {
		this.searchManagement = searchManagement;
	}

	public String getSearchAttachType() {
		return searchAttachType;
	}

	public void setSearchAttachType(String searchAttachType) {
		this.searchAttachType = searchAttachType;
	}

	public String getSearchAttachValue() {
		return searchAttachValue;
	}

	public void setSearchAttachValue(String searchAttachValue) {
		this.searchAttachValue = searchAttachValue;
	}

	public boolean isIsadd() {
		return isadd;
	}

	public void setIsadd(boolean isadd) {
		this.isadd = isadd;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<Map<String, String>> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Map<String, String>> branchs) {
		this.branchs = branchs;
	}

	public Map<String,List<Map<String, String>>> getManagementagencies() {
		return managementagencies;
	}

	public void setManagementagencies(Map<String,List<Map<String, String>>> managementagencies) {
		this.managementagencies = managementagencies;
	}

	public String getAjaxDecoderSearchCond() {
		return ajaxDecoderSearchCond;
	}

	public void setAjaxDecoderSearchCond(String ajaxDecoderSearchCond) {
		this.ajaxDecoderSearchCond = ajaxDecoderSearchCond;
	}

	public String getDecodersjson() {
		return decodersjson;
	}

	public void setDecodersjson(String decodersjson) {
		this.decodersjson = decodersjson;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<TVmDecoder> getDecoders() {
		return decoders;
	}

	public void setDecoders(List<TVmDecoder> decoders) {
		this.decoders = decoders;
	}

	public TVmDecoder getDecoderToAddOrUpdate() {
		return decoderToAddOrUpdate;
	}

	public void setDecoderToAddOrUpdate(TVmDecoder decoderToAddOrUpdate) {
		this.decoderToAddOrUpdate = decoderToAddOrUpdate;
	}
	
	public String getDecoderidToDelete() {
		return decoderidToDelete;
	}

	public void setDecoderidToDelete(String decoderidToDelete) {
		this.decoderidToDelete = decoderidToDelete;
	}
	
	public String checkDecoderIdExist(){
		String res = SUCCESS;
		try {
			int idcount = decoderService.testDecoderExistById(decoderToAddOrUpdate.getDecoderid());
			if(idcount == 1){
				tip = "ID已存在";
			}else{
				tip = "";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return res;
	}

	public String addDecoder() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("添加" + "Decoder：" );
		try {
			if(decoderToAddOrUpdate == null){
				return INPUT;
			}
			if (decoderService.addDecoder(decoderToAddOrUpdate) != 1) {
				setDecoderToAddOrUpdate(null);
				res = "inputAdd";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("addDecoder", decoderToAddOrUpdate.getDecoderid(), opres, msg.toString());
		return res;
	}
	
	public String updateDecoder() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("修改" + "Decoder：" );
		try {
			if(decoderToAddOrUpdate == null){
				return INPUT;
			}
			if (decoderService.modifyDecoder(decoderToAddOrUpdate) != 1) {
				res = INPUT;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("updateDecoder", decoderToAddOrUpdate.getDecoderid(), opres, msg.toString());
		return res;
	}
	
	public String deleteDecoder(){
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("删除Decoder：");
		try {
			List<String> decoders = new ArrayList<String>();
			if(decoderidToDelete != null){
				String[] decodersToDelete = decoderidToDelete.split("\\,");
				for(String decoderid:decodersToDelete){
					if(decoderid == null || decoderid.equals("")){
						continue;
					}
					decoders.add(decoderid);
				}
			}
			if (decoders.size() == 0 || decoderService.deleteDecoders(decoders) != decoders.size()) {
				res = ERROR;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("deleteDecoder", decoderidToDelete, opres, msg.toString());
		return res;
	}
	
	public String loadUpdateDecoder(){
		String res = SUCCESS;
		try {
			setBranchs(decoderService.getBranches());
			setManagementagencies(decoderService.getManagementagencies());
			TVmDecoder decoder = decoderService.searchById(decoderToAddOrUpdate.getDecoderid());
			decoder.setDevictype("hk10001");
			setDecoderToAddOrUpdate(decoder);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadAddDecoder(){
		String res = SUCCESS;
		try {
			setBranchs(decoderService.getBranches());
			setManagementagencies(decoderService.getManagementagencies());
			TVmDecoder decoder = new TVmDecoder();
			decoder.setDevictype("hk10001");
			setDecoderToAddOrUpdate(decoder);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}

	public String loadDecoder() {
		String res = SUCCESS;
		try {
			Session session = AlarmUtil.getLoginSession();
			String lev = session.getLev();
			setBranchs(decoderService.getBranches());
			setManagementagencies(decoderService.getManagementagencies());
			tip="";
			Map<String,Object> params = new HashMap<String,Object>();
			int fromRow = (this.pageNo - 1) * pageSize + 1;
			int toRow = fromRow + pageSize - 1;
			params.put("fromRow", fromRow);
			params.put("toRow", toRow);
			String branchId = "";
			if(searchBranch == null || searchBranch.equals("-1")){
				if(!lev.equals(AlarmConstants.USER_HEADQUARTERS)){
					if(branchs != null && branchs.size() > 0){ 						
						branchId = getBranchs().get(0).get("id");
						params.put("branch", branchId);
					}
				}
			}else{
				params.put("branch", searchBranch);
			}
			if(searchManagement != null && !searchManagement.equals("-1")){
				params.put("managementagency", searchManagement);
			}else{
				if(lev.equals(AlarmConstants.USER_DEPARTMENT)){
					if(managementagencies != null && branchId != "" && managementagencies.containsKey(branchId) && managementagencies.get(branchId).size() > 0){
						String managementagencyId = getManagementagencies().get(branchId).get(0).get("id");
						params.put("managementagency", managementagencyId);
					}
				}
			}
			if(searchAttachType != null && searchAttachValue != null && !searchAttachValue.equals("")){
				params.put("atype", searchAttachType);
				params.put("avalue", searchAttachValue);
			}
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("cond", params);
			List<TVmDecoder> tempDecoders = decoderService.searchByPage(cond);
			setDecoders(tempDecoders);
			setRowCount(decoderService.searchByPageRowCount(cond));
			setPageCount((int)Math.ceil((double)rowCount / pageSize));
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadDecoderAjax(){
		String res = SUCCESS;
		loadDecoder();
		JSONArray ja = JSONArray.fromObject(decoders);
		setDecodersjson(ja.toString());
		return res;
	}
}
