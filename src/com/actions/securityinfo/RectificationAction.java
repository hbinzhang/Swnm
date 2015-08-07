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
import com.entity.securityinfo.Rectification;
import com.entity.securityinfo.RectificationCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.service.securityinfo.IRectificationService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;

/**
 * 整改管理
 * @author liyinghui
 *
 */
public class RectificationAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6312710453772678727L;

	private Log log = LogFactory.getLog(this.getClass());
	
	private CommonBean commonBean;
	
	private IRectificationService rectificationService;

	private IOrganizationManagerService organManagerService;
	
	private AjaxObject ajaxObject;

	private IOperationLogService operationLogService;
	
	private String tip;
	
	private List<CommonBean> belongToOrgList;
	
	private RectificationCondition rectificationCondition;
	
	private List<Rectification> rectificationList;
	
	private Rectification rectification;
	
	private String name;
	
	private String belongToOrgId;
	
	public String queryRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{	
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String oId = session.getOrganizationId();
				log.info("queryRectification, session = " + session);
				if(oId == null ){
					message = "会话相关信息不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					// 所属机构为当前登陆用户所属机构和直接下一级的机构
					belongToOrgList = getBelongToOrgListBySession(session);	
					if(null == rectificationCondition){
						rectificationCondition = new RectificationCondition();
					}
					if(rectificationCondition.getBelongToOrgId() == null){
						rectificationCondition.setBelongToOrgId(oId);
					}
					rectificationList = rectificationService.queryRectification(
							rectificationCondition);
					List<Organization> orgList = organManagerService.queryOrganizationsByAccountId(
							session.getId());
					Map<String, String> orgMap = new HashMap<String, String>();						
					for(int j=0; j<orgList.size(); j++){
						orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
					}
					for(Rectification rectification : rectificationList){
						rectification.setBelongToOrgName(AlarmUtil.formatString(orgMap.get(
								rectification.getBelongToOrgId())));
					}
				}	
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}				
		}catch(Exception e){
			log.error("queryRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询整改记录失败，" + message;
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
	
	public String entryCreateRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				rectification = new Rectification();
				rectification.setBelongToOrgId(session.getOrganizationId());
				rectification.setBelongToOrgName((String)organManagerService.
						getOrgNmByOrgId(session.getOrganizationId()));
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("entryCreateRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化创建整改记录界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String createRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createRectification, rectification = " + rectification);					
			String name = rectification.getName();
			String actions = rectification.getActions();
			String note = rectification.getNote();
			String implementOrg = rectification.getImplementOrg();
			String implementOrgPerson = rectification.getImplementPerson();
			String initiatingOrg = rectification.getInitiatingOrg();
			String recificationResult = rectification.getResult();
			Rectification rf = new Rectification();
			rf.setBelongToOrgId(rectification.getBelongToOrgId());
			rf.setActions(actions.trim());
			rf.setImplementOrg(implementOrg.trim());
			rf.setName(name.trim());
			rf.setNote(note.trim());
			rf.setImplementPerson(implementOrgPerson.trim());
			rf.setInitiatingOrg(initiatingOrg.trim());
			rf.setTime(rectification.getTime());	
			rf.setResult(recificationResult.trim());
			log.info("createRectification, rf = " + rf);					
			rectificationService.createRectification(rf);
		}catch(Exception e){
			log.error("createRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建整改记录失败，" + message;
		}
		operationLogService.createOperationLog("createRectification",  "整改记录名称:"+rectification.getName().trim()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(rectification.getBelongToOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String entryUpdateRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){				
				Rectification param = new Rectification();
				param.setName(rectification.getName().trim());
				param.setBelongToOrgId(rectification.getBelongToOrgId());
				rectification = (Rectification)rectificationService.
						queryRectificationByName(param);
				if(null != rectification){
					rectification.setBelongToOrgName((String)organManagerService.getOrgNmByOrgId(
							rectification.getBelongToOrgId()));
				}else{
					message = "该整改记录已不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}						
			}
		}catch(Exception e){
			log.error("entryUpdateRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化编辑整改记录界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String updateRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("updateRectification, rectification = " + rectification);		
			String name = rectification.getName();
			String actions = rectification.getActions();
			String note = rectification.getNote();
			String implementOrg = rectification.getImplementOrg();
			String implementOrgPerson = rectification.getImplementPerson();
			String initiatingOrg = rectification.getInitiatingOrg();
			String recificationResult = rectification.getResult();
			Rectification rf = new Rectification();
			rf.setBelongToOrgId(rectification.getBelongToOrgId());
			rf.setActions(actions.trim());
			rf.setImplementOrg(implementOrg.trim());
			rf.setName(name.trim());
			rf.setNote(note.trim());
			rf.setImplementPerson(implementOrgPerson.trim());
			rf.setInitiatingOrg(initiatingOrg.trim());
			rf.setTime(rectification.getTime());	
			rf.setResult(recificationResult.trim());
			log.info("updateRectification, rf = " + rf);					
			rectificationService.updateRectification(rf);
		}catch(Exception e){
			log.error("updateRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "修改整改记录失败，" + message;
		}	
		operationLogService.createOperationLog("createRectification",  "整改记录名称:"+rectification.getName()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(rectification.getBelongToOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String deleteRectification(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Rectification rectification = new Rectification();
			rectification.setName(name);
			rectification.setBelongToOrgId(belongToOrgId);
			log.info("deleteRectification, rectification = " + rectification);					
			rectificationService.deleteRectification(rectification);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteRectification error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除奖惩记录失败，" + message;
		}	
		operationLogService.createOperationLog("createRectification",  "整改记录名称:"+name
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(belongToOrgId)
					, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String checkRectificationNameExist(){
		String res = SUCCESS;	
		try {
			Rectification param = new Rectification();	
			param.setName(rectification.getName().trim());
			param.setBelongToOrgId(rectification.getBelongToOrgId());	
			log.info("checkRectificationNameExist, param = " + param);					
			Rectification rp = (Rectification)rectificationService.
					queryRectificationByName(param);
			if(null != rp){
				tip = "整改记录已存在。";
			}else{
				tip = "";
			}			
		} catch (Exception e) {
			log.error("checkRectificationNameExist error!", e);
		}
		return res;
	}
	
	
	public List<CommonBean> getBelongToOrgList() {
		return belongToOrgList;
	}

	public void setBelongToOrgList(List<CommonBean> belongToOrgList) {
		this.belongToOrgList = belongToOrgList;
	}

	public RectificationCondition getRectificationCondition() {
		return rectificationCondition;
	}

	public void setRectificationCondition(
			RectificationCondition rectificationCondition) {
		this.rectificationCondition = rectificationCondition;
	}

	public List<Rectification> getRectificationList() {
		return rectificationList;
	}

	public void setRectificationList(List<Rectification> rectificationList) {
		this.rectificationList = rectificationList;
	}

	public Rectification getRectification() {
		return rectification;
	}

	public void setRectification(Rectification rectification) {
		this.rectification = rectification;
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

	public IRectificationService getRectificationService() {
		return rectificationService;
	}

	public void setRectificationService(IRectificationService rectificationService) {
		this.rectificationService = rectificationService;
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
	
	
}
