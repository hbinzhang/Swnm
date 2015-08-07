package com.actions.videomonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.authmgt.Session;
import com.entity.videomonitor.*;
import com.opensymphony.xwork2.ActionSupport;
import com.service.logmgt.IOperationLogService;
import com.service.videomonitor.DeviceManageIPCService;
import com.service.videomonitor.DeviceManageNVRService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

public class DeviceManageNVRAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DeviceManageNVRAction.class);
	private DeviceManageNVRService nvrService;
	private DeviceManageIPCService ipcService;
	private TVmNvr nvrToAddOrUpdate;
	private String nvridToDelete;
	private String nvridToLoadIpcs;
	private List<TVmNvr> nvrs;
	private List<TVmManufacturer> vendors;
	private List<Map<String,String>> branchs;
	private Map<String,List<Map<String, String>>> managementagencies;
	private List<String> ipcchecked;
	private List<String> ipcnochecked;
	private String branch;
	private String managementagency;
	private String ipcsjson;
	private String tip;
	private boolean isadd;
	private int pageNo = 1;
	private int pageSize = 15;
	private int rowCount = 0;
	private int pageCount = 1;
	private String searchBranch;
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

	private String searchManagement;
	private String searchAttachType;
	private String searchAttachValue;
	
	public String getNvridToLoadIpcs() {
		return nvridToLoadIpcs;
	}

	public void setNvridToLoadIpcs(String nvridToLoadIpcs) {
		this.nvridToLoadIpcs = nvridToLoadIpcs;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public boolean isIsadd() {
		return isadd;
	}

	public void setIsadd(boolean isadd) {
		this.isadd = isadd;
	}

	public List<String> getIpcchecked() {
		return ipcchecked;
	}

	public void setIpcchecked(List<String> ipcchecked) {
		this.ipcchecked = ipcchecked;
	}
	
	public List<String> getIpcnochecked() {
		return ipcnochecked;
	}

	public void setIpcnochecked(List<String> ipcnochecked) {
		this.ipcnochecked = ipcnochecked;
	}

	public DeviceManageNVRService getNvrService() {
		return nvrService;
	}

	public void setNvrService(DeviceManageNVRService deviceManageService) {
		this.nvrService = deviceManageService;
	}
	
	public DeviceManageIPCService getIpcService() {
		return ipcService;
	}

	public void setIpcService(DeviceManageIPCService ipcService) {
		this.ipcService = ipcService;
	}
	
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getManagementagency() {
		return managementagency;
	}

	public void setManagementagency(String managementagency) {
		this.managementagency = managementagency;
	}

	public String getIpcsjson() {
		return ipcsjson;
	}

	public void setIpcsjson(String ipcsjson) {
		this.ipcsjson = ipcsjson;
	}


	public TVmNvr getNvrToAddOrUpdate() {
		return nvrToAddOrUpdate;
	}

	public void setNvrToAddOrUpdate(TVmNvr nvrToAddOrUpdate) {
		this.nvrToAddOrUpdate = nvrToAddOrUpdate;
	}

	public String getNvridToDelete() {
		return nvridToDelete;
	}

	public void setNvridToDelete(String nvridToDelete) {
		this.nvridToDelete = nvridToDelete;
	}

	public List<TVmNvr> getNvrs() {
		return nvrs;
	}

	public void setNvrs(List<TVmNvr> nvrs) {
		this.nvrs = nvrs;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public List<Map<String, String>> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Map<String, String>> branchs) {
		this.branchs = branchs;
	}
	
	public List<TVmManufacturer> getVendors() {
		return vendors;
	}

	public void setVendors(List<TVmManufacturer> vendors) {
		this.vendors = vendors;
	}

	public Map<String, List<Map<String, String>>> getManagementagencies() {
		return managementagencies;
	}

	public void setManagementagencies(
			Map<String, List<Map<String, String>>> managementagencies) {
		this.managementagencies = managementagencies;
	}

	public String loadNVR() {
		String res = SUCCESS;
		try {
			Session session = AlarmUtil.getLoginSession();
			String lev = session.getLev();
			setBranchs(nvrService.getBranches());
			setManagementagencies(nvrService.getManagementagencies());
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
			}else if(lev.equals(AlarmConstants.USER_DEPARTMENT)){
				if(managementagencies != null && branchId != "" && managementagencies.containsKey(branchId) && managementagencies.get(branchId).size() > 0){
					String managementagencyId = getManagementagencies().get(branchId).get(0).get("id");
					params.put("managementagency", managementagencyId);
				}
			}
			if(searchAttachType != null && searchAttachValue != null && !searchAttachValue.equals("")){
				params.put("atype", searchAttachType);
				params.put("avalue", searchAttachValue);
			}
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("cond", params);
			List<TVmNvr> tempNvrs = nvrService.searchByPage(cond);
			setNvrs(tempNvrs);
			setRowCount(nvrService.searchByPageRowCount(cond));
			setPageCount((int)Math.ceil((double)rowCount / pageSize));
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String loadAddNVR(){
		String res = SUCCESS;
		try {
			setVendors(nvrService.getManufactures());
			setBranchs(nvrService.getBranches());
			setManagementagencies(nvrService.getManagementagencies());
			TVmNvr nvr = new TVmNvr();
			nvr.setVersion("3.0");
			nvr.setDevtype("hk10001");
			setNvrToAddOrUpdate(nvr);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String checkNvrIdExist(){
		String res = SUCCESS;
		try {
			int idcount = nvrService.testNvrExistById(nvrToAddOrUpdate.getNvrid());
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
	
	public String addNVR() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("添加" + "Nvr：" );
		try {
			if(nvrToAddOrUpdate == null){
				return INPUT;
			}
			if (nvrService.addNVR(nvrToAddOrUpdate,ipcchecked,ipcnochecked) != 1) {
				setNvrToAddOrUpdate(null);
				res = "inputAdd";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("addNvr", nvrToAddOrUpdate.getNvrid(), opres, msg.toString());
		return res;
	}
	
	public String updateNVR() {
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("修改" + "Nvr：" );
		try {
			if(nvrToAddOrUpdate == null){
				return INPUT;
			}
			if (nvrService.modifyNVR(nvrToAddOrUpdate,ipcchecked,ipcnochecked) != 1) {
				res = INPUT;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("updateNvr", nvrToAddOrUpdate.getNvrid(), opres, msg.toString());
		return res;
	}
	
	public String loadUpdateNVR(){
		String res = SUCCESS;
		try {
			setVendors(nvrService.getManufactures());
			setBranchs(nvrService.getBranches());
			setManagementagencies(nvrService.getManagementagencies());
			TVmNvr nvr = nvrService.searchById(nvrToAddOrUpdate.getNvrid());
			nvr.setVersion("3.0");
			nvr.setDevtype("hk10001");
			setNvrToAddOrUpdate(nvr);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
	
	public String deleteNVR(){
		int opres = 0;
		String res = SUCCESS;
		StringBuffer msg = new StringBuffer("删除Nvr：");
		try {
			List<String> nvrs = new ArrayList<String>();
			if(nvridToDelete != null){
				String[] nvrsToDelete = nvridToDelete.split("\\,");
				for(String nvrid:nvrsToDelete){
					if(nvrid == null || nvrid.equals("")){
						continue;
					}
					nvrs.add(nvrid);
				}
			}
			if (nvrs.size() == 0 || nvrService.deleteNVRs(nvrs) != nvrs.size()) {
				res = ERROR;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		opres = res == SUCCESS ? 1 : 0;
		msg.append(res == SUCCESS ? "成功！" : "失败！");
		operationLogService.createOperationLog("deleteNvr", nvridToDelete, opres, msg.toString());
		return res;
	}
	
	public String getIpcsByOrgId(){
		String res = SUCCESS;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			if(getManagementagency() != null && !getManagementagency().equals(""))
			{
				params.put("managementagency", getManagementagency());
			}
			if(getBranch() != null && !getBranch().equals("")){
				params.put("branch", getBranch());
			}
			if(getNvridToLoadIpcs() != null && !getNvridToLoadIpcs().equals("")){
				params.put("nvrid", getNvridToLoadIpcs());
			}
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("cond", params);
			List<TVmIpc> ipcs = ipcService.getAllIpcByOrgIdNvrId(cond);
			JSONObject jo = new JSONObject();
			jo.put("ipcs", JSONArray.fromObject(ipcs));
			JSONArray ja = new JSONArray();
			ja.add(jo);
			setIpcsjson(ja.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
		}
		return res;
	}
}
