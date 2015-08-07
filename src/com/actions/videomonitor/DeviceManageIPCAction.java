package com.actions.videomonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.entity.authmgt.Session;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmManufacturer;
import com.entity.videomonitor.TVmNvr;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.videomonitor.DeviceManageIPCService;
import com.service.videomonitor.DeviceManageNVRService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class DeviceManageIPCAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DeviceManageIPCAction.class);

	private DeviceManageIPCService ipcService;
	
	public DeviceManageIPCService getIpcService() {
		return ipcService;
	}

	public void setIpcService(DeviceManageIPCService ipcService) {
		this.ipcService = ipcService;
	}

	private DeviceManageNVRService nvrService;
	
	public DeviceManageNVRService getNvrService() {
		return nvrService;
	}

	public void setNvrService(DeviceManageNVRService nvrService) {
		this.nvrService = nvrService;
	}

	private String tip;
	private String uploadContentType;
	private String uploadFileName;
	//private File upload;
	private TVmIpc ipcToAddOrUpdate;
	private String ipcidToDelete;
	private List<TVmIpc> ipcs;
	private int pageNo = 1;
	private int pageSize = 15;
	private int rowCount = 0;
	private int pageCount = 1;
	private String ipcsjson;
	private String ajaxIpcSearchCond;
	private List<TVmNvr> nvrs; 
	private List<TVmManufacturer> vendors;
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

	public List<TVmManufacturer> getVendors() {
		return vendors;
	}

	public void setVendors(List<TVmManufacturer> vendors) {
		this.vendors = vendors;
	}

	public List<TVmNvr> getNvrs() {
		return nvrs;
	}

	public void setNvrs(List<TVmNvr> nvrs) {
		this.nvrs = nvrs;
	}

	public String getAjaxIpcSearchCond() {
		return ajaxIpcSearchCond;
	}

	public void setAjaxIpcSearchCond(String ajaxIpcSearchCond) {
		this.ajaxIpcSearchCond = ajaxIpcSearchCond;
	}

	public String getIpcsjson() {
		return ipcsjson;
	}

	public void setIpcsjson(String ipcsjson) {
		this.ipcsjson = ipcsjson;
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

	public List<TVmIpc> getIpcs() {
		return ipcs;
	}

	public void setIpcs(List<TVmIpc> ipcs) {
		this.ipcs = ipcs;
	}

	public TVmIpc getIpcToAddOrUpdate() {
		return ipcToAddOrUpdate;
	}

	public void setIpcToAddOrUpdate(TVmIpc ipcToAddOrUpdate) {
		this.ipcToAddOrUpdate = ipcToAddOrUpdate;
	}
	
	public String getIpcidToDelete() {
		return ipcidToDelete;
	}

	public void setIpcidToDelete(String ipcidToDelete) {
		this.ipcidToDelete = ipcidToDelete;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/*public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}*/
	
	public String checkIpcIdExist(){
		String res = SUCCESS;
		try {
			int idcount = ipcService.testIpcExistById(ipcToAddOrUpdate.getIpcid());
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

	public String addIPC() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("添加" + "IPC：" );
		try {
			if(ipcToAddOrUpdate == null){
				return INPUT;
			}
			if (ipcService.addIPC(ipcToAddOrUpdate) != 1) {
				setIpcToAddOrUpdate(null);
				res = "inputAdd";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("addIpc", ipcToAddOrUpdate.getIpcid(), opres, msg.toString());
		return res;
	}
	
	public String updateIPC() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("修改" + "IPC：" );
		try {
			if(ipcToAddOrUpdate == null){
				return INPUT;
			}
			if (ipcService.modifyIPC(ipcToAddOrUpdate) != 1) {
				res = INPUT;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("updateIpc", ipcToAddOrUpdate.getIpcid(), opres, msg.toString());
		return res;
	}
	
	public String deleteIPC(){
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("删除IPC：");
		try {
			List<String> ipcs = new ArrayList<String>();
			if(ipcidToDelete != null){
				String[] ipcsToDelete = ipcidToDelete.split("\\,");
				for(String ipcid:ipcsToDelete){
					if(ipcid == null || ipcid.equals("")){
						continue;
					}
					ipcs.add(ipcid);
				}
			}
			if (ipcs.size() == 0 || (tip = ipcService.deleteIPCs(ipcs)) != "") {
				tip = "id为["+tip+"]的ipc删除失败,因为它们与音频设备有关联！";
				res = ERROR;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			tip = "删除出现异常:" + e.getMessage();
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("deleteIpc", ipcidToDelete, opres, msg.toString());
		return res;
	}
	
	public String loadUpdateIPC(){
		String res = SUCCESS;
		try {
			setVendors(ipcService.getManufactures());
			setBranchs(ipcService.getBranches());
			setManagementagencies(ipcService.getManagementagencies());
			loadNvrsByRole();
			TVmIpc ipc = ipcService.searchById(ipcToAddOrUpdate.getIpcid());
			ipc.setVersion("3.0");
			ipc.setDevtype("hk10001");
			setIpcToAddOrUpdate(ipc);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadAddIPC(){
		String res = SUCCESS;
		try {
			setVendors(ipcService.getManufactures());
			setBranchs(ipcService.getBranches());
			setManagementagencies(ipcService.getManagementagencies());
			loadNvrsByRole();
			TVmIpc ipc = new TVmIpc();
			ipc.setVersion("3.0");
			ipc.setDevtype("hk10001");
			setIpcToAddOrUpdate(ipc);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	private void loadNvrsByRole(){
		try {
			Session session = AlarmUtil.getLoginSession();
			String lev = session.getLev();
			Map<String,Object> params = new HashMap<String,Object>();
			if(!lev.equals(AlarmConstants.USER_HEADQUARTERS)){
				params.put("branch", getBranchs().get(0).get("id"));
			}
			if(lev.equals(AlarmConstants.USER_DEPARTMENT)){
				params.put("managementagency", getManagementagencies().get(getBranchs().get(0).get("id")).get(0).get("id"));
			}
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("cond", params);
			setNvrs(nvrService.selectNvrByRole(cond));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public String loadIPC() {
		String res = SUCCESS;
		try {
			Session session = AlarmUtil.getLoginSession();
			String lev = session.getLev();
			setBranchs(ipcService.getBranches());
			setManagementagencies(ipcService.getManagementagencies());
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
			List<TVmIpc> tempIpcs = ipcService.searchByPage(cond);
			setIpcs(tempIpcs);
			setRowCount(ipcService.searchByPageRowCount(cond));
			setPageCount((int)Math.ceil((double)rowCount / pageSize));
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadIPCAjax(){
		String res = SUCCESS;
		loadIPC();
		JSONObject jo = new JSONObject();
		jo.put("ipcs", JSONArray.fromObject(ipcs));
		jo.put("managements", JSONObject.fromObject(managementagencies));
		jo.put("branchs", JSONArray.fromObject(branchs));
		JSONArray ja = new JSONArray();
		ja.add(jo);
		setIpcsjson(ja.toString());
		return res;
	}
}
