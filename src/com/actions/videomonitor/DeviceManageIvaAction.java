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
import com.entity.videomonitor.TVmIva;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.videomonitor.DeviceManageDecoderService;
import com.service.videomonitor.DeviceManageIVAService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class DeviceManageIvaAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DeviceManageDecoderAction.class);

	private DeviceManageIVAService ivaService;
	
	public DeviceManageIVAService getIvaService() {
		return ivaService;
	}

	public void setIvaService(DeviceManageIVAService ivaService) {
		this.ivaService = ivaService;
	}

	private String tip;
	//private File upload;
	private TVmIva ivaToAddOrUpdate;
	private String ivaidToDelete;
	private List<TVmIva> ivas;
	private int pageNo = 1;
	private int pageSize = 15;
	private int rowCount = 0;
	private String ivasjson;
	private String ajaxIvaSearchCond;
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

	public String getAjaxIvaSearchCond() {
		return ajaxIvaSearchCond;
	}

	public void setAjaxIvaSearchCond(String ajaxIvaSearchCond) {
		this.ajaxIvaSearchCond = ajaxIvaSearchCond;
	}

	public String getIvasjson() {
		return ivasjson;
	}

	public void setIvasjson(String ivasjson) {
		this.ivasjson = ivasjson;
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

	public List<TVmIva> getIvas() {
		return ivas;
	}

	public void setIvas(List<TVmIva> ivas) {
		this.ivas = ivas;
	}

	public TVmIva getIvaToAddOrUpdate() {
		return ivaToAddOrUpdate;
	}

	public void setIvaToAddOrUpdate(TVmIva ivaToAddOrUpdate) {
		this.ivaToAddOrUpdate = ivaToAddOrUpdate;
	}
	
	public String getIvaidToDelete() {
		return ivaidToDelete;
	}

	public void setIvaidToDelete(String ivaidToDelete) {
		this.ivaidToDelete = ivaidToDelete;
	}
	
	public String checkIvaIdExist(){
		String res = SUCCESS;
		try {
			int idcount = ivaService.testIvaExistById(ivaToAddOrUpdate.getIvaID());
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

	public String addIva() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("添加" + "IVA：" );
		try {
			if(ivaToAddOrUpdate == null){
				return INPUT;
			}
			if (ivaService.addIVA(ivaToAddOrUpdate) != 1) {
				setIvaToAddOrUpdate(null);
				res = "inputAdd";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("addIva", ivaToAddOrUpdate.getIvaID(), opres, msg.toString());
		return res;
	}
	
	public String updateIva() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("修改" + "Decoder：" );
		try {
			if(ivaToAddOrUpdate == null){
				return INPUT;
			}
			if (ivaService.modifyIva(ivaToAddOrUpdate) != 1) {
				res = INPUT;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("updateDecoder", ivaToAddOrUpdate.getIvaID(), opres, msg.toString());
		return res;
	}
	
	public String deleteIva(){
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("删除Iva：");
		try {
			List<String> ivas = new ArrayList<String>();
			if(ivaidToDelete != null){
				String[] ivasToDelete = ivaidToDelete.split("\\,");
				for(String ivaid:ivasToDelete){
					if(ivaid == null || ivaid.equals("")){
						continue;
					}
					ivas.add(ivaid);
				}
			}
			if (ivas.size() == 0 || ivaService.deleteIvas(ivas) != ivas.size()) {
				res = ERROR;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("deleteIva", ivaidToDelete, opres, msg.toString());
		return res;
	}
	
	public String loadUpdateIva(){
		String res = SUCCESS;
		try {
			setBranchs(ivaService.getBranches());
			setManagementagencies(ivaService.getManagementagencies());
			TVmIva iva = ivaService.searchById(ivaToAddOrUpdate.getIvaID());
			//iva.setDevictype("hk10001");
			setIvaToAddOrUpdate(iva);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadAddIva(){
		String res = SUCCESS;
		try {
			setBranchs(ivaService.getBranches());
			setManagementagencies(ivaService.getManagementagencies());
			TVmIva iva = new TVmIva();
			//decoder.setDevictype("hk10001");
			setIvaToAddOrUpdate(iva);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}

	public String loadIva() {
		String res = SUCCESS;
		try {
			Session session = AlarmUtil.getLoginSession();
			String lev = session.getLev();
			setBranchs(ivaService.getBranches());
			setManagementagencies(ivaService.getManagementagencies());
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
			List<TVmIva> tempIvas = ivaService.searchByPage(cond);
			setIvas(tempIvas);
			setRowCount(ivaService.searchByPageRowCount(cond));
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadIvaAjax(){
		String res = SUCCESS;
		loadIva();
		JSONArray ja = JSONArray.fromObject(ivas);
		setIvasjson(ja.toString());
		return res;
	}
}
