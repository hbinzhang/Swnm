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
import com.service.securityinfo.IEventService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;

/**
 * 安全事件管理
 * @author liyinghui
 *
 */
public class EventAction  extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099326598453553584L;

	private Log log = LogFactory.getLog(this.getClass());
	
	private CommonBean commonBean;
	
	private IEventService eventService;

	private IOrganizationManagerService organManagerService;
	
	private AjaxObject ajaxObject;

	private IOperationLogService operationLogService;
	
	private String tip;
	
	private List<CommonBean> belongToOrgList;
	
	private Event event;
	
	private EventCondition eventCondition;
	
	private List<Event> eventList;
	
	private String name;
	
	private String belongToOrgId;

	public String queryEvent(){
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
					if(null == eventCondition){
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
					}
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
	
	public String entryCreateEvent(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				event = new Event();
				event.setBelongToOrgId(session.getOrganizationId());
				event.setBelongToOrgName((String)organManagerService.
						getOrgNmByOrgId(session.getOrganizationId()));
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("entryCreateEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化创建安全事件界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String createEvent(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createEvent, event = " + event);					
			String name = event.getName();
	
			String recificationResult = event.getResult();
			Event rf = new Event();
			rf.setBelongToOrgId(event.getBelongToOrgId());
			
			rf.setName(name.trim());
			
			rf.setTime(event.getTime());	
			rf.setResult(recificationResult.trim());
			log.info("createEvent, rf = " + rf);					
			eventService.createEvent(rf);
		}catch(Exception e){
			log.error("createEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建安全事件失败，" + message;
		}
		/*operationLogService.createOperationLog("createEvent",  "安全事件名称:"+event.getName().trim()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(event.getBelongToOrgId())
				, result, message);*/
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String entryUpdateEvent(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){				
				Event param = new Event();
				param.setName(event.getName().trim());
				param.setBelongToOrgId(event.getBelongToOrgId());
				event = (Event)eventService.
						queryEventByName(param);
				if(null != event){
					event.setBelongToOrgName((String)organManagerService.getOrgNmByOrgId(
							event.getBelongToOrgId()));
				}else{
					message = "该安全事件已不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}						
			}
		}catch(Exception e){
			log.error("entryUpdateEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化编辑安全事件界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String updateEvent(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("updateEvent, event = " + event);		
			String name = event.getName();
	
			String recificationResult = event.getResult();
			Event rf = new Event();
			rf.setBelongToOrgId(event.getBelongToOrgId());
			
			rf.setTime(event.getTime());	
			rf.setResult(recificationResult.trim());
			log.info("updateEvent, rf = " + rf);					
			eventService.updateEvent(rf);
		}catch(Exception e){
			log.error("updateEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "修改安全事件失败，" + message;
		}	
		/*operationLogService.createOperationLog("createEvent",  "安全事件名称:"+event.getName()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(event.getBelongToOrgId())
				, result, message);*/
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String deleteEvent(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Event event = new Event();
			event.setName(name);
			event.setBelongToOrgId(belongToOrgId);
			log.info("deleteEvent, event = " + event);					
			eventService.deleteEvent(event);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteEvent error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除奖惩记录失败，" + message;
		}	
		/*operationLogService.createOperationLog("createEvent",  "安全事件名称:"+name
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(belongToOrgId)
					, result, message);*/
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String checkEventNameExist(){
		String res = SUCCESS;	
		try {
			Event param = new Event();	
			param.setName(event.getName().trim());
			param.setBelongToOrgId(event.getBelongToOrgId());	
			log.info("checkEventNameExist, param = " + param);					
			Event rp = (Event)eventService.
					queryEventByName(param);
			if(null != rp){
				tip = "安全事件已存在。";
			}else{
				tip = "";
			}			
		} catch (Exception e) {
			log.error("checkEventNameExist error!", e);
		}
		return res;
	}
	
	public EventCondition getEventCondition() {
		return eventCondition;
	}

	public void setEventCondition(EventCondition eventCondition) {
		this.eventCondition = eventCondition;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBelongToOrgId() {
		return belongToOrgId;
	}

	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public IEventService getEventService() {
		return eventService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<CommonBean> getBelongToOrgList() {
		return belongToOrgList;
	}

	public void setBelongToOrgList(List<CommonBean> belongToOrgList) {
		this.belongToOrgList = belongToOrgList;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
