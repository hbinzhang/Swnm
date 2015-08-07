package com.actions.securityinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.entity.securityinfo.RewardPunishment;
import com.entity.securityinfo.RewardPunishmentCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.service.securityinfo.IRewardPunishmentService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;

/**
 * 目标考核——奖惩管理
 * @author liyinghui
 *
 */
public class RewardPunishmentAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7489917302218136867L;

	private Log log = LogFactory.getLog(this.getClass());
	
	private CommonBean commonBean;
	
	private IRewardPunishmentService rewardPunishmentService;

	private IOrganizationManagerService organManagerService;
	
	private AjaxObject ajaxObject;

	private IOperationLogService operationLogService;
	
	private List<CommonBean> belongToOrgList;

	private RewardPunishmentCondition rewardPunishmentCondition;
	
	private List<RewardPunishment> rewardPunishList;
	
	private RewardPunishment rewardPunishment;
	
	private String name;
	
	private String belongToOrgId;
	
	private String tip;
	
	public String queryRewardPunishment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{	
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String oId = session.getOrganizationId();
				log.info("queryRewardPunishment, session = " + session);
				if(oId == null ){
					message = "会话相关信息不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					// 所属机构为当前登陆用户所属机构和直接下一级的机构
					belongToOrgList = getBelongToOrgListBySession(session);	
					if(null == rewardPunishmentCondition){
						rewardPunishmentCondition = new RewardPunishmentCondition();
					}
					if(rewardPunishmentCondition.getBelongToOrgId() == null){
						rewardPunishmentCondition.setBelongToOrgId(oId);
					}
					rewardPunishList = rewardPunishmentService.queryRewardPunishment(
							rewardPunishmentCondition);
					List<Organization> orgList = organManagerService.queryOrganizationsByAccountId(
							session.getId());
					Map<String, String> orgMap = new HashMap<String, String>();						
					for(int j=0; j<orgList.size(); j++){
						orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
					}
					for(RewardPunishment rewardPunishment : rewardPunishList){
						rewardPunishment.setBelongToOrgName(AlarmUtil.formatString(orgMap.get(
								rewardPunishment.getBelongToOrgId())));
					}
				}	
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}				
		}catch(Exception e){
			log.error("queryRewardPunishment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询奖惩记录失败，" + message;
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
	
	public String entryCreateRewardPunish(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				rewardPunishment = new RewardPunishment();
				rewardPunishment.setBelongToOrgId(session.getOrganizationId());
				rewardPunishment.setBelongToOrgName((String)organManagerService.
						getOrgNmByOrgId(session.getOrganizationId()));
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("entryCreateRewardPunish error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化创建奖惩记录界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String createRewardPunishment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createRewardPunish, rewardPunishment = " + rewardPunishment);					
			String name = rewardPunishment.getName();
			String description = rewardPunishment.getDescription();
			String note = rewardPunishment.getNote();
			String implementOrg = rewardPunishment.getImplementOrg();
			String rplevel = rewardPunishment.getRplevel();
			String rpOrg = rewardPunishment.getRpOrg();
			RewardPunishment rp = new RewardPunishment();
			rp.setBelongToOrgId(rewardPunishment.getBelongToOrgId());
			rp.setDescription(description.trim());
			rp.setImplementOrg(implementOrg.trim());
			rp.setName(name.trim());
			rp.setNote(note.trim());
			rp.setRplevel(rplevel.trim());
			rp.setRpOrg(rpOrg.trim());
			rp.setTime(rewardPunishment.getTime());
			log.info("createRewardPunish, rp = " + rp);					
			rewardPunishmentService.createRewardPunishment(rp);
		}catch(Exception e){
			log.error("createRewardPunish error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建奖惩记录失败，" + message;
		}
		operationLogService.createOperationLog("createRewardPunish",  "奖惩记录名称:"+rewardPunishment.getName().trim()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(rewardPunishment.getBelongToOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String entryUpdateRewardPunish(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){				
				RewardPunishment param = new RewardPunishment();
				param.setName(rewardPunishment.getName().trim());
				param.setBelongToOrgId(rewardPunishment.getBelongToOrgId());
				rewardPunishment = (RewardPunishment)rewardPunishmentService.
						queryRewardPunishByName(param);
				if(null != rewardPunishment){
					rewardPunishment.setBelongToOrgName((String)organManagerService.getOrgNmByOrgId(
							rewardPunishment.getBelongToOrgId()));
				}else{
					message = "该奖惩记录已不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}						
			}
		}catch(Exception e){
			log.error("entryUpdateRewardPunish error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化编辑奖惩记录界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String updateRewardPunishment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("updateRewardPunishment, rewardPunishment = " + rewardPunishment);		
			String name = rewardPunishment.getName();
			String description = rewardPunishment.getDescription();
			String note = rewardPunishment.getNote();
			String implementOrg = rewardPunishment.getImplementOrg();
			String rplevel = rewardPunishment.getRplevel();
			String rpOrg = rewardPunishment.getRpOrg();
			RewardPunishment rp = new RewardPunishment();
			rp.setBelongToOrgId(rewardPunishment.getBelongToOrgId());
			rp.setDescription(description.trim());
			rp.setImplementOrg(implementOrg.trim());
			rp.setName(name.trim());
			rp.setNote(note.trim());
			rp.setRplevel(rplevel.trim());
			rp.setRpOrg(rpOrg.trim());
			rp.setTime(rewardPunishment.getTime());			
			log.info("updateRewardPunishment, rp = " + rp);					
			rewardPunishmentService.updateRewardPunishment(rp);
		}catch(Exception e){
			log.error("updateRewardPunishment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "修改奖惩记录失败，" + message;
		}	
		operationLogService.createOperationLog("createRewardPunish",  "奖惩记录名称:"+rewardPunishment.getName()
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(rewardPunishment.getBelongToOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String deleteRewardPunishment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			RewardPunishment rp = new RewardPunishment();
			rp.setName(name);
			rp.setBelongToOrgId(belongToOrgId);
			log.info("deleteRewardPunishment, rp = " + rp);					
			rewardPunishmentService.deleteRewardPunishment(rp);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteRewardPunishment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除奖惩记录失败，" + message;
		}	
		operationLogService.createOperationLog("createRewardPunish",  "奖惩记录名称:"+name
				+"，所属机构:"+(String)organManagerService.getOrgNmByOrgId(belongToOrgId)
					, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String checkRPNameExist(){
		String res = SUCCESS;	
		try {
			RewardPunishment param = new RewardPunishment();	
			param.setName(rewardPunishment.getName().trim());
			param.setBelongToOrgId(rewardPunishment.getBelongToOrgId());	
			log.info("checkRPNameExist, param = " + param);					
			RewardPunishment rp = (RewardPunishment)rewardPunishmentService.
					queryRewardPunishByName(param);
			if(null != rp){
				tip = "奖惩记录已存在。";
			}else{
				tip = "";
			}			
		} catch (Exception e) {
			log.error("checkRPNameExist error!", e);
		}
		return res;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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

	public RewardPunishment getRewardPunishment() {
		return rewardPunishment;
	}

	public void setRewardPunishment(RewardPunishment rewardPunishment) {
		this.rewardPunishment = rewardPunishment;
	}

	public RewardPunishmentCondition getRewardPunishmentCondition() {
		return rewardPunishmentCondition;
	}

	public void setRewardPunishmentCondition(
			RewardPunishmentCondition rewardPunishmentCondition) {
		this.rewardPunishmentCondition = rewardPunishmentCondition;
	}

	public List<RewardPunishment> getRewardPunishList() {
		return rewardPunishList;
	}

	public void setRewardPunishList(List<RewardPunishment> rewardPunishList) {
		this.rewardPunishList = rewardPunishList;
	}

	public List<CommonBean> getBelongToOrgList() {
		return belongToOrgList;
	}

	public void setBelongToOrgList(List<CommonBean> belongToOrgList) {
		this.belongToOrgList = belongToOrgList;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public IRewardPunishmentService getRewardPunishmentService() {
		return rewardPunishmentService;
	}

	public void setRewardPunishmentService(
			IRewardPunishmentService rewardPunishmentService) {
		this.rewardPunishmentService = rewardPunishmentService;
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
