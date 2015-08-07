package com.actions.securityinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.entity.securityinfo.Event;
import com.entity.securityinfo.EventCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;

/**
 * 目标考核——督查管理
 * @author liyinghui
 *
 */
public class InspectionAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 347915678944339605L;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private CommonBean commonBean;

	private IOrganizationManagerService organManagerService;
	
	private AjaxObject ajaxObject;

	private IOperationLogService operationLogService;
	
	private List<CommonBean> belongToOrgList;
	
	public String queryInspection(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{	
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String oId = session.getOrganizationId();
				log.info("queryEvent, session = " + session);
				if(oId == null ){
					message = "会话相关信息不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					// 所属机构为当前登陆用户所属机构和直接下一级的机构
					belongToOrgList = getBelongToOrgListBySession(session);	
					/*if(null == eventCondition){
						eventCondition = new EventCondition();
					}
					if(eventCondition.getBelongToOrgId() == null){
						eventCondition.setBelongToOrgId(oId);
					}
					eventList = eventService.queryEvent(
							eventCondition);
					List<Organization> orgList = organManagerService.queryOrganizationsByAccountId(
							session.getId());
					Map<String, String> orgMap = new HashMap<String, String>();						
					for(int j=0; j<orgList.size(); j++){
						orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
					}
					for(Event event : eventList){
						event.setBelongToOrgName(AlarmUtil.formatString(orgMap.get(
								event.getBelongToOrgId())));
					}*/
				}	
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}				
		}catch(Exception e){
			log.error("queryEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询安全事件失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public List<CommonBean> getBelongToOrgList() {
		return belongToOrgList;
	}

	public void setBelongToOrgList(List<CommonBean> belongToOrgList) {
		this.belongToOrgList = belongToOrgList;
	}


	private List<CommonBean> getBelongToOrgListBySession(Session session){
		List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
		List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();	
		List<CommonBean> belongToOrgList = new ArrayList<CommonBean>();
		belongToOrgList.add(new CommonBean(session.getOrganizationId(),(String)organManagerService.
				getOrgNmByOrgId(session.getOrganizationId())));
		// 根据用户级别决定界面所属机构下拉列表
		if(session.getLev().equals(AlarmConstants.USER_HEADQUARTERS)){
			// 如果用户是总公司，所属机构为总公司+分公司		
			belongToOrgList.addAll(branchList);	
		}else if(session.getLev().equals(AlarmConstants.USER_BRANCH)){				
			// 如果用户是分公司级别, 所属机构为分公司+下级管理处	
			belongToOrgList.addAll(departmentList);	
		}else if(session.getLev().equals(AlarmConstants.USER_DEPARTMENT)){
			// 如果用户是管理处级别, 所属机构为管理处		
		}	
		return belongToOrgList;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	
	
}
