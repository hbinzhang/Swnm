package com.actions.securityinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.impl.AccountManagerServiceImp;
import com.service.logmgt.IOperationLogService;
import com.service.securityinfo.ITaskBookService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;
import com.util.securityinfo.SecurityInfoConstants;
import common.page.AjaxObject;

/**
 * 任务书管理
 * @author liyinghui
 *
 */
public class TaskBookAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2478701170118286474L;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private ITaskBookService taskBookService;
	
	private TaskBookCondition taskBookCondition;
	
	private CommonBean commonBean;
	
	private List<CommonBean> inChargeOrgList;
	
	private List<CommonBean> inChargeOrgPersonList;
	
	private List<TaskBook> taskBookList;
	
	private TaskBook taskBook;
	
	private IOrganizationManagerService organManagerService;

	private String name;
	
	private AccountManagerServiceImp accountManagerService;
	
	private List<CommonBean> planOrgList;
	
	private IOperationLogService operationLogService;
	
	private AjaxObject ajaxObject;
	
	private String planOrgId;

	private String tip;
	
	private String inChargeOrgId;
	
	private String zerenOrgId;
	
	public String queryTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			if(null == taskBookCondition){
				taskBookCondition = new TaskBookCondition();
				taskBookCondition.setQueryTaskBookFlag(1);
				taskBookCondition.setType(-1);
				taskBookCondition.setState(-1);
			}
			log.info("queryTaskBook, taskBookCondition = " + taskBookCondition);					
				Session session = AlarmUtil.getLoginSession();
				if(null != session){
					String userLevel = session.getLev();
					String orgId = session.getOrganizationId();
					String userId = session.getId();
					log.info("queryTaskBook, userLevel = " + userLevel);
					if(null == userLevel || null == userId || orgId == null ){
						message = "会话相关信息不存在。";
						result = AlarmConstants.RESULT_FAIL;
					}else{
						List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
						List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();					
						planOrgList = new ArrayList<CommonBean>();
					//	String subPlanOrgId = "";
						// 根据用户级别决定界面任务责任机构下拉列表
						if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
							// 如果用户是总公司，任务责任机构为总公司+分公司		
							CommonBean company = session.getOrgIdAndNames().getCompany();
							planOrgList.add(company);
							planOrgList.addAll(branchList);	
							planOrgList.addAll(departmentList);	
					//		subPlanOrgId = orgId.substring(0,3);
						}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){				
							// 如果用户是分公司级别, 任务责任机构为分公司+下级管理处	
							planOrgList.addAll(branchList);	
							planOrgList.addAll(departmentList);	
				//			subPlanOrgId = orgId.substring(0,4);
						}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
							// 如果用户是管理处级别, 任务责任机构为管理处		
							planOrgList.addAll(departmentList);	
				//			subPlanOrgId = orgId;
						}else{
							message = "获取用户所属机构级别错误。";
							result = AlarmConstants.RESULT_FAIL;
						}
				//		taskBookCondition.setSubPlanOrgId(subPlanOrgId);
						if(taskBookCondition.getPlanOrgId() == null){
							taskBookCondition.setPlanOrgId(orgId);
						}
						if(taskBookCondition.getPlanOrgId() != null){
							inChargeOrgList = getInChargeOrgByPlanOrgId(taskBookCondition.getPlanOrgId());														
						}
						if(taskBookCondition.getInChargeOrgId() == null){
							taskBookCondition.setInChargeOrgId(inChargeOrgList.get(0).getId());
						}
						if(taskBookCondition.getQueryTaskBookFlag() == 1){
							// 按任务发布时间查询
							taskBookList = (List<TaskBook>)taskBookService.queryTbByIssueTime(taskBookCondition);
						}else if(taskBookCondition.getQueryTaskBookFlag() == 2){
							// 按任务启动时间查询
							taskBookList = (List<TaskBook>)taskBookService.queryTbByStartTime(taskBookCondition);
						}else{
							// 按任务结束时间查询
							taskBookList = (List<TaskBook>)taskBookService.queryTbByEndTime(taskBookCondition);
						}
						List<Organization> orgList = organManagerService.queryOrganizationsByAccountId(
								userId);
						Map<String, String> orgMap = new HashMap<String, String>();						
						for(int j=0; j<orgList.size(); j++){
							orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
						}
						for(TaskBook tb:taskBookList){
							tb.setPlanOrgName(AlarmUtil.formatString(orgMap.get(
									tb.getPlanOrgId())));
							tb.setInChargeOrgName(AlarmUtil.formatString(orgMap.get(
									tb.getInChargeOrgId())));
							tb.setInChargeOrgPersonName(((Account)accountManagerService.
									queryAccountByAccountId(tb.getInChargeOrgPerson())).getUserName());
							tb.setPlanOrgPersonName(((Account)accountManagerService.
									queryAccountByAccountId(tb.getPlanOrgPerson())).getUserName());
						}
					}	
				}else{
					message = "获取当前登录会话异常。";
					result = AlarmConstants.RESULT_FAIL;
				}				
		}catch(Exception e){
			log.error("queryTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询安防任务失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String entryCreateTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String userLevel = session.getLev();
				log.info("entryCreateTaskBook, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					taskBook = new TaskBook();
					taskBook.setPlanOrgPerson(session.getId());
					taskBook.setPlanOrgName((String)organManagerService.getOrgNmByOrgId(
							session.getOrganizationId()));
					taskBook.setPlanOrgId(session.getOrganizationId());
					taskBook.setPlanTime(new Date());
					taskBook.setState(SecurityInfoConstants.TASKBOOK_STATE_NOTPUBLISH);
					taskBook.setPlanOrgPersonName(((Account)accountManagerService.
							queryAccountByAccountId(session.getId())).getUserName());
					List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
					List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();
					inChargeOrgList = new ArrayList<CommonBean>();
					// 根据用户级别决定界面任务责任机构下拉列表
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						// 如果用户是总公司，任务责任机构为总公司+分公司		
						CommonBean company = session.getOrgIdAndNames().getCompany();
						inChargeOrgList.add(company);
						inChargeOrgList.addAll(branchList);		
					}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){				
						// 如果用户是分公司级别, 任务责任机构为分公司+下级管理处	
						inChargeOrgList.addAll(branchList);		
						inChargeOrgList.addAll(departmentList);
						/*inChargeOrgPersonList = (List<Account>)accountManagerService.queryAccountsByAccountId(
								session.getId());*/
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果用户是管理处级别, 任务责任机构为管理处		
						inChargeOrgList.addAll(departmentList);
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}				
					inChargeOrgPersonList = (List<CommonBean>)accountManagerService.getAccountIdAndUserNamesByOrgId(
							session.getOrganizationId());	
					zerenOrgId = inChargeOrgList.get(0).getId();
				}	
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("entryCreateTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化创建安防任务界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String checkTaskNameExist(){
		String res = SUCCESS;	
		try {
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				// 判断同一个制定机构是否有重名的任务
				TaskBook param = new TaskBook();
				param.setName(taskBook.getName().trim());
				param.setPlanOrgId(session.getOrganizationId());
				log.info("checkTaskNameExist, param = " + param);					
				TaskBook t = (TaskBook)taskBookService.queryTaskBookByName(param);
				if(null != t){
					tip = "任务名称已存在。";
				}else{
					tip = "";
				}
			}else{
				tip = "获取当前登录会话异常。";
			}	
		} catch (Exception e) {
			log.error("checkTaskNameExist error!", e);
		}
		return res;
	}
	
	public String createTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createTaskBook, taskBook = " + taskBook);					
			String name = taskBook.getName();
			String planOrgId = taskBook.getPlanOrgId();
			String content = taskBook.getContent();
			String inChargeOrgId = taskBook.getInChargeOrgId();
			String inChargeOrgPerson = taskBook.getInChargeOrgPerson();
			String note = taskBook.getNote();
			String target = taskBook.getTarget();		
			String stateStr = taskBook.getStateStr();
			int type = taskBook.getType();
			TaskBook tb = new TaskBook();
			tb.setContent(content.trim());
			tb.setInChargeOrgId(inChargeOrgId);
			tb.setInChargeOrgPerson(inChargeOrgPerson);
			tb.setName(name.trim());
			tb.setNote(note.trim());
			tb.setPlanOrgId(planOrgId);
			tb.setTarget(target.trim());
			tb.setType(type);
			tb.setPlanTime(taskBook.getPlanTime());
			tb.setStartTime(taskBook.getStartTime());
			tb.setEndTime(taskBook.getEndTime());
			tb.setPlanOrgPerson(taskBook.getPlanOrgPerson());
			tb.setState(getStateByStr(stateStr));
			log.info("createTaskBook, tb = " + tb);					
			taskBookService.createTaskBook(tb);
		}catch(Exception e){
			log.error("createTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建安防任务失败，" + message;
		}
		operationLogService.createOperationLog("createTaskBook",  "任务名称:"+taskBook.getName().trim()
				+"，制定机构:"+(String)organManagerService.getOrgNmByOrgId(taskBook.getPlanOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	private int getStateByStr(String stateStr){
		if(stateStr.equals("未发布")){
			return SecurityInfoConstants.TASKBOOK_STATE_NOTPUBLISH;
		}
		if(stateStr.equals("已发布")){
			return SecurityInfoConstants.TASKBOOK_STATE_PUBLISHED;
		}
		return SecurityInfoConstants.TASKBOOK_STATE_CLOSED;
	}

	public String entryUpdateTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String userLevel = session.getLev();
				log.info("entryCreateTaskBook, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					TaskBook param = new TaskBook();
					param.setName(taskBook.getName().trim());
					param.setPlanOrgId(taskBook.getPlanOrgId());
					taskBook = (TaskBook)taskBookService.queryTaskBookByName(param);
					if(null != taskBook){
						taskBook.setPlanOrgName((String)organManagerService.getOrgNmByOrgId(
								session.getOrganizationId()));
						taskBook.setPlanTime(new Date());
						taskBook.setPlanOrgPersonName(((Account)accountManagerService.
								queryAccountByAccountId(taskBook.getPlanOrgPerson())).getUserName());
						List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
						List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();
						inChargeOrgList = new ArrayList<CommonBean>();
						// 根据用户级别决定界面任务责任机构下拉列表
						if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
							// 如果用户是总公司，任务责任机构为总公司+分公司		
							CommonBean company = session.getOrgIdAndNames().getCompany();
							inChargeOrgList.add(company);
							inChargeOrgList.addAll(branchList);		
						}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){				
							// 如果用户是分公司级别, 任务责任机构为分公司+下级管理处	
							inChargeOrgList.addAll(branchList);		
							inChargeOrgList.addAll(departmentList);
						}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
							// 如果用户是管理处级别, 任务责任机构为管理处		
							inChargeOrgList.addAll(departmentList);
						}else{
							message = "获取用户所属机构级别错误。";
							result = AlarmConstants.RESULT_FAIL;
						}
						inChargeOrgPersonList = (List<CommonBean>)accountManagerService.getAccountIdAndUserNamesByOrgId(
								session.getOrganizationId());	
						zerenOrgId = inChargeOrgList.get(0).getId();
					}else{
						message = "该任务已不存在。";
						result = AlarmConstants.RESULT_FAIL;
					}			
				}
			}
		}catch(Exception e){
			log.error("entryUpdateTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化编辑安防任务界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String updateTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("updateTaskBook, taskBook = " + taskBook);					
			String content = taskBook.getContent();
			String inChargeOrgId = taskBook.getInChargeOrgId();
			String inChargeOrgPerson = taskBook.getInChargeOrgPerson();
			String name = taskBook.getName();
			String note = taskBook.getNote();
			String planOrgId = taskBook.getPlanOrgId();
			String target = taskBook.getTarget();		
			int type = taskBook.getType();
			String stateStr = taskBook.getStateStr();
			TaskBook tb = new TaskBook();
			tb.setContent(content.trim());
			tb.setInChargeOrgId(inChargeOrgId);
			tb.setInChargeOrgPerson(inChargeOrgPerson);
			tb.setName(name.trim());
			tb.setNote(note.trim());
			tb.setPlanOrgId(planOrgId);
			tb.setTarget(target.trim());
			tb.setType(type);
			tb.setPlanTime(taskBook.getPlanTime());
			tb.setStartTime(taskBook.getStartTime());
			tb.setEndTime(taskBook.getEndTime());
			tb.setState(getStateByStr(stateStr));
			log.info("updateTaskBook, tb = " + tb);					
			taskBookService.updateTaskBook(tb);
		}catch(Exception e){
			log.error("updateTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "编辑安防任务失败，" + message;
		}
		operationLogService.createOperationLog("updateTaskBook", "任务名称:"+taskBook.getName().trim()
				+"，制定机构:"+(String)organManagerService.getOrgNmByOrgId(taskBook.getPlanOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String publishTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			TaskBook tb = new TaskBook();
			tb.setName(name.trim());
			tb.setPlanOrgId(planOrgId);
			tb.setIssueTime(new Date());
			tb.setState(SecurityInfoConstants.TASKBOOK_STATE_PUBLISHED);
			log.info("publishTaskBook, tb = " + tb);					
			taskBookService.publishTaskBook(tb);
			// 需要发消息给制定机构负责人和责任机构负责人
			message = "发布成功！";
		}catch(Exception e){
			log.error("publishTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		operationLogService.createOperationLog("publishTaskBook", "任务名称:"+name.trim() 
				+"，制定机构:"+(String)organManagerService.getOrgNmByOrgId(planOrgId)
				, result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "发布安防任务失败，" + message;
		}
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}			
	}
	
	public String deleteTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			TaskBook tb = new TaskBook();
			tb.setName(name.trim());
			tb.setPlanOrgId(planOrgId);
			log.info("deleteTaskBook, tb = " + tb);					
			taskBookService.deleteTaskBookByName(tb);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		operationLogService.createOperationLog("deleteTaskBook", "任务名称:"+name.trim()
				+"，制定机构:"+(String)organManagerService.getOrgNmByOrgId(planOrgId)
				, result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除安防任务失败，" + message;
		}
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}			
	}
	
	public String closeTaskBook(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			TaskBook tb = new TaskBook();
			tb.setName(name.trim());
			tb.setPlanOrgId(planOrgId);
			tb.setState(SecurityInfoConstants.TASKBOOK_STATE_CLOSED);
			log.info("closeTaskBook, tb = " + tb);					
			taskBookService.closeTaskBook(tb);
			message = "关闭成功！";
		}catch(Exception e){
			log.error("closeTaskBook error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "关闭安防任务失败，" + message;
		}
		operationLogService.createOperationLog("closeTaskBook", "任务名称:"+name.trim()
				+"，制定机构:"+(String)organManagerService.getOrgNmByOrgId(planOrgId)
				,result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}			
	}
	
	private List<CommonBean> getInChargeOrgByPlanOrgId(String pOrgId){
		List<CommonBean> list = new ArrayList<CommonBean>();
		list.add(new CommonBean(pOrgId, (String)organManagerService.
				getOrgNmByOrgId(pOrgId)));
		String level = (String)organManagerService.getLevByOrgId(pOrgId);
		if(null != level){
			// 根据用户级别决定界面任务责任机构下拉列表
			if(level.equals(AlarmConstants.USER_HEADQUARTERS)){
				// 如果用户是总公司，任务责任机构为总公司+分公司		
				List<Organization> orgList = organManagerService.getOrganizationsFor1ByPOrgId(
						pOrgId);
				for(Organization org : orgList){
					list.add(new CommonBean(org.getOrgId(),org.getOrgNm()));
				}							
			}else if(level.equals(AlarmConstants.USER_BRANCH)){				
				// 如果用户是分公司级别, 任务责任机构为分公司+下级管理处	
				List<Organization> orgList = organManagerService.getOrganizationsFor2ByPOrgId(
						pOrgId);
				for(Organization org : orgList){
					list.add(new CommonBean(org.getOrgId(),org.getOrgNm()));
				}	
			}
		}
		return list;
	}
	
	/**
	 * 根据制定机构联动查询责任机构
	 * @return
	 */
	public String queryInChargeOrgsByPorg(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";	
		log.info("queryInChargeOrgsByPorg, planOrgId = " + planOrgId);					
		try{		
			if(null != planOrgId){
				inChargeOrgList = getInChargeOrgByPlanOrgId(planOrgId);									
			}					
		}catch(Exception e){
			log.error("queryInChargeOrgsByPorg error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询责任机构失败，" + message;
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(inChargeOrgList);
		ajaxObject.setMessage(message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/**
	 * 根据责任机构联动查询责任机构负责人
	 * @return
	 */
	public String queryInChargeOrgPersonByOrg(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";	
		log.info("queryInChargeOrgPersonByOrg, inChargeOrgId = " + inChargeOrgId);					
		try{		
			if(null != inChargeOrgId){
				inChargeOrgPersonList = (List<CommonBean>)accountManagerService.
						getAccountIdAndUserNamesByOrgId(inChargeOrgId);										
			}					
		}catch(Exception e){
			log.error("queryInChargeOrgPersonByOrg error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询责任机构负责人失败，" + message;
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(inChargeOrgPersonList);
		ajaxObject.setMessage(message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String getInChargeOrgId() {
		return inChargeOrgId;
	}

	public void setInChargeOrgId(String inChargeOrgId) {
		this.inChargeOrgId = inChargeOrgId;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public TaskBook getTaskBook() {
		return taskBook;
	}

	public void setTaskBook(TaskBook taskBook) {
		this.taskBook = taskBook;
	}

	public ITaskBookService getTaskBookService() {
		return taskBookService;
	}

	public void setTaskBookService(ITaskBookService taskBookService) {
		this.taskBookService = taskBookService;
	}

	public TaskBookCondition getTaskBookCondition() {
		return taskBookCondition;
	}

	public void setTaskBookCondition(TaskBookCondition taskBookCondition) {
		this.taskBookCondition = taskBookCondition;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public List<CommonBean> getInChargeOrgList() {
		return inChargeOrgList;
	}

	public void setInChargeOrgList(List<CommonBean> inChargeOrgList) {
		this.inChargeOrgList = inChargeOrgList;
	}

	public List<TaskBook> getTaskBookList() {
		return taskBookList;
	}

	public void setTaskBookList(List<TaskBook> taskBookList) {
		this.taskBookList = taskBookList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CommonBean> getInChargeOrgPersonList() {
		return inChargeOrgPersonList;
	}

	public void setInChargeOrgPersonList(List<CommonBean> inChargeOrgPersonList) {
		this.inChargeOrgPersonList = inChargeOrgPersonList;
	}

	public AccountManagerServiceImp getAccountManagerService() {
		return accountManagerService;
	}

	public void setAccountManagerService(
			AccountManagerServiceImp accountManagerService) {
		this.accountManagerService = accountManagerService;
	}

	public List<CommonBean> getPlanOrgList() {
		return planOrgList;
	}

	public void setPlanOrgList(List<CommonBean> planOrgList) {
		this.planOrgList = planOrgList;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public String getPlanOrgId() {
		return planOrgId;
	}

	public void setPlanOrgId(String planOrgId) {
		this.planOrgId = planOrgId;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getZerenOrgId() {
		return zerenOrgId;
	}

	public void setZerenOrgId(String zerenOrgId) {
		this.zerenOrgId = zerenOrgId;
	}
	
}
