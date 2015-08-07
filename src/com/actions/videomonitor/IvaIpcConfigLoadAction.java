package com.actions.videomonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.BaseAction;
import com.common.page.Page;
import com.entity.authmgt.Session;
import com.entity.efence.FenceBean;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIpcDTO;
import com.entity.videomonitor.TVmIva;
import com.service.videomonitor.DeviceManageIPCService;
import com.service.videomonitor.IIvaConfigService;
import common.page.AjaxObject;

public class IvaIpcConfigLoadAction extends BaseAction {

	private final static Log log = LogFactory
			.getLog(IvaIpcConfigLoadAction.class);
	private TVmIva tvmIva;
	private String ivaID;
	private AjaxObject ajaxObject;
	private Page<TVmIva> page;		private String pluginVersion;

	public String getPluginVersion() {		return pluginVersion;	}	public void setPluginVersion(String pluginVersion) {		this.pluginVersion = pluginVersion;	}	private DeviceManageIPCService ipcService;
	private IIvaConfigService ivaConfigService;

	public String queryIvaByPage() {
		int level = 0;
		// 1.获取用户信息，不同的用户显示不同的信息
		Session s = (Session) getRequest().getSession().getAttribute("session");
		try {

			if (page == null) {
				page = new Page<TVmIva>(pageNow, pageSize);
			}
			// 权限
			TVmIva tvmIvaCondition = new TVmIva();

			if (s != null) {
				level = Integer.valueOf(s.getLev());
				switch (level) {
				case 0:
					break;
				case 1:
					// 参数：分公司ID
					String branchID = s.getOrgIdAndNames().getSubCompanys()
							.get(0).getId();
					tvmIvaCondition.setBranch(branchID);
					break;
				case 2:
					// 参数：管理处ID
					String mID = s.getOrgIdAndNames().getManagements().get(0)
							.getId();
					tvmIvaCondition.setManagementagency(mID);
					break;
				}
			}
			/*tvmIvaCondition.setBranch("010100");
			tvmIvaCondition.setManagementagency("010101");*/
			page.setObjCondition(tvmIvaCondition);
			page = ivaConfigService.queryIvaByPage(page);
		} catch (Exception e) {
			log.fatal("queryIvaByPage failed:"+e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadIvaServerInfo() {

		try {
			tvmIva = ivaConfigService.getIvaById(ivaID);
		} catch (Exception e) {
			log.fatal("loadIvaServerInfo failed:" + e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String loadIpcInfo() {
		// 1.加载管理处下的所有ipc信息
		// 获取用户信息，不同的用户显示不同的信息
		int level = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		Session s = (Session) getRequest().getSession().getAttribute("session");
		if (s != null) {
			level = Integer.valueOf(s.getLev());
		}
		switch (level) {
		case 0:
			break;
		case 1:
			// 参数：分公司ID
			String branchID = s.getOrgIdAndNames().getSubCompanys().get(0)
					.getId();
			param.put("branch", branchID);
			break;
		case 2:
			// 参数：管理处ID
			String mID = s.getOrgIdAndNames().getManagements().get(0).getId();
			param.put("managementagency", mID);
			break;
		}
		/*
		 * param.put("branch", "010400"); param.put("managementagency",
		 * "010401"); ivaID="E1A001C";
		 */
		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("cond", param);
			// 存放未分配智能分析仪的ipc信息集合
			List<TVmIpc> tempConfIpcs = new ArrayList<TVmIpc>();
			// 机构下的所有IPC信息
			List<TVmIpc> allIpcs = ipcService.getAllIpcByOrgIdNvrId(cond);
			if (allIpcs.size() > 0) {
				// 加载已分配智能分析仪的ipc信息
				List<TVmIpcDTO> confIpcs = ipcService.getAllIpcByIvaID(ivaID);
				// 数据处理
				Map<String, TVmIpc> map = new HashMap<String, TVmIpc>();
				for (TVmIpc ipc : allIpcs) {
					map.put(ipc.getIpcid(), ipc);
				}
				for (TVmIpcDTO tvmIpcDTO : confIpcs) {
					if (map.get(tvmIpcDTO.getIpcid()) != null) {
						tempConfIpcs.add(map.get(tvmIpcDTO.getIpcid()));
					}
				}
				if (tempConfIpcs.size() > 0) {
					allIpcs.removeAll(tempConfIpcs);
				}
				// ajax返回页面
				Map<String, List> retData = new HashMap<String, List>();
				retData.put("unConfIpc", allIpcs);
				retData.put("confIpc", confIpcs);
				ajaxObject = new AjaxObject(1, retData);
				return SUCCESS;

			} else {
				ajaxObject = new AjaxObject(0, "该机构没有摄像头");
				return ERROR;
			}

		} catch (Exception e) {
			log.fatal("loadIpcInfo failed:" + e.getMessage());
			ajaxObject = new AjaxObject(0, "信息加载失败");
			return ERROR;
		}

	}

	/******************************* setter/getter *********************************/
	public TVmIva getTvmIva() {
		return tvmIva;
	}

	public void setTvmIva(TVmIva tvmIva) {
		this.tvmIva = tvmIva;
	}

	public String getIvaID() {
		return ivaID;
	}

	public void setIvaID(String ivaID) {
		this.ivaID = ivaID;
	}

	public DeviceManageIPCService getIpcService() {
		return ipcService;
	}

	public void setIpcService(DeviceManageIPCService ipcService) {
		this.ipcService = ipcService;
	}

	public IIvaConfigService getIvaConfigService() {
		return ivaConfigService;
	}

	public void setIvaConfigService(IIvaConfigService ivaConfigService) {
		this.ivaConfigService = ivaConfigService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public Page<TVmIva> getPage() {
		return page;
	}

	public void setPage(Page<TVmIva> page) {
		this.page = page;
	}

}
