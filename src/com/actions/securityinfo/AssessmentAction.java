package com.actions.securityinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.entity.securityinfo.Assessment;
import com.entity.securityinfo.CheckInfo;
import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.impl.AccountManagerServiceImp;
import com.service.logmgt.IOperationLogService;
import com.service.securityinfo.IAssessmentService;
import com.service.securityinfo.ITaskBookService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;


/**
 * 目标考核——考核指标
 * @author liyinghui
 *
 */
public class AssessmentAction  extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7766914383311838313L;

	private Log log = LogFactory.getLog(this.getClass());
	
	private CommonBean commonBean;
	
	private IAssessmentService assessmentService;

	private IOrganizationManagerService organManagerService;

	private AccountManagerServiceImp accountManagerService;
	
	private AjaxObject ajaxObject;

	private IOperationLogService operationLogService;
	
	/**
	 * 考核机构下拉列表
	 */
	private List<CommonBean> orgList;
	
	private List<Assessment> assessmentList;
	
	/**
	 * 考核机构id
	 */
	private String orgId;
	
	private Assessment assessment;
	
	/**
	 * 考核机构责任人下拉列表
	 */
	private List<CommonBean> orgPersonList;
	
	/**
	 * 考核人员下拉列表
	 */
	private List<CommonBean> assessPersonList;
	
	/**
	 * 指标目标下拉列表
	 */
	private List<TaskBook> assessIndexList;
	
	private ITaskBookService taskBookService;
	
	private String name;
	
	/**
	 * 机构考核成绩列表
	 */
	private List<Assessment> orgAssessmentList;

	/**
	 * 机构汇总考核成绩
	 */
	private float orgResult;
	
	/**
	 * 考核机构名称
	 */
	private String orgName;
	
	private List<CheckInfo> checkInfoList;
	
	private CheckInfo checkInfo;
	
	private String time;
	
	private String indexName;
	
	private String tip;
	
	private String assessTip;
	
	private int checkInfoSize;
	
	private String checkOrgId;

	public String queryAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{				
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String oId = session.getOrganizationId();
				String userId = session.getId();
				log.info("queryAssessment, session = " + session);
				if(null == userId || oId == null ){
					message = "会话相关信息不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					if(null == orgId){
						orgId = oId;
					}
					orgList = getOrgListBySession(session);		
					List<Organization> list = organManagerService.queryOrganizationsByAccountId(
							userId);
					Map<String, String> orgMap = new HashMap<String, String>();						
					for(int j=0; j<list.size(); j++){
						orgMap.put(list.get(j).getOrgId(), list.get(j).getOrgNm());
					}
					assessmentList = (List<Assessment>)assessmentService.queryAssessment(orgId);
					for(Assessment assess:assessmentList){
						assess.setOrgName(AlarmUtil.formatString(orgMap.get(
								assess.getOrgId())));
						assess.setOrgPersonName(((Account)accountManagerService.
								queryAccountByAccountId(assess.getOrgPerson())).getUserName());
						assess.setReviewerName(((Account)accountManagerService.
								queryAccountByAccountId(assess.getReviewer())).getUserName());
					}
				
				}	
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}				
		}catch(Exception e){
			log.error("queryAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询考核指标失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	public String entryCreateAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				log.info("entryCreateAssessment, session = " + session);
				orgList = getOrgListBySession(session);			
				checkOrgId = session.getOrganizationId();
				orgPersonList = (List<CommonBean>)accountManagerService.
						getAccountIdAndUserNamesByOrgId(checkOrgId);	
				TaskBookCondition taskBookCondition = new TaskBookCondition();
				taskBookCondition.setPlanOrgId(checkOrgId);
				taskBookCondition.setInChargeOrgId(checkOrgId);
				log.info("entryCreateAssessment, taskBookCondition = " + taskBookCondition);
				assessIndexList = taskBookService.queryTbByIssueTime(taskBookCondition);
				assessPersonList = (List<CommonBean>)accountManagerService.getAccountIdAndUserNamesByOrgId(
						checkOrgId);										
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("entryCreateAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化创建考核指标界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	private List<CommonBean> getOrgListBySession(Session session){
		List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
		List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();	
		List<CommonBean> assessOrgList = new ArrayList<CommonBean>();
		assessOrgList.add(new CommonBean(session.getOrganizationId(),(String)organManagerService.
				getOrgNmByOrgId(session.getOrganizationId())));
		// 根据用户级别决定界面考核机构下拉列表
		if(session.getLev().equals(AlarmConstants.USER_HEADQUARTERS)){
			// 如果用户是总公司，考核机构为总公司+分公司		
			assessOrgList.addAll(branchList);	
		}else if(session.getLev().equals(AlarmConstants.USER_BRANCH)){				
			// 如果用户是分公司级别, 考核机构为分公司+下级管理处	
			assessOrgList.addAll(departmentList);	
		}else if(session.getLev().equals(AlarmConstants.USER_DEPARTMENT)){
			// 如果用户是管理处级别, 考核机构为管理处		
		}	
		return assessOrgList;
	}
	
	public String createAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createAssessment, assessment = " + assessment);					
			String target = assessment.getTarget();
			int percent = assessment.getPercent();
			Assessment param = new Assessment();
			param.setTarget(target.trim());
			param.setPercent(percent);	
			param.setName(assessment.getName());
			param.setOrgId(assessment.getOrgId());
			param.setOrgPerson(assessment.getOrgPerson());
			param.setReviewer(assessment.getReviewer());			
			log.info("createAssessment, param = " + param);					
			assessmentService.createAssessment(param);
		}catch(Exception e){
			log.error("createAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建考核指标失败，" + message;
		}	
		operationLogService.createOperationLog("createAssessment", "指标名称:"+assessment.getName().trim()
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(assessment.getOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String entryUpdateAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){				
				Assessment param = new Assessment();
				param.setName(assessment.getName().trim());
				param.setOrgId(assessment.getOrgId());
				assessment = (Assessment)assessmentService.queryAssessmentByName(param);
				if(null != assessment){
					assessment.setOrgName((String)organManagerService.getOrgNmByOrgId(
							assessment.getOrgId()));
					orgPersonList = (List<CommonBean>)accountManagerService.
							getAccountIdAndUserNamesByOrgId(assessment.getOrgId());	
					assessPersonList = (List<CommonBean>)accountManagerService.
							getAccountIdAndUserNamesByOrgId(session.getOrganizationId());		
				}else{
					message = "该考核指标已不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}						
			}
		}catch(Exception e){
			log.error("entryUpdateAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化编辑考核指标界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String updateAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("updateAssessment, assessment = " + assessment);					
			String target = assessment.getTarget();
			Assessment param = new Assessment();
			param.setTarget(target.trim());
			param.setPercent(assessment.getPercent());	
			param.setName(assessment.getName());
			param.setOrgId(assessment.getOrgId());
			param.setOrgPerson(assessment.getOrgPerson());
			param.setReviewer(assessment.getReviewer());			
			log.info("updateAssessment, param = " + param);					
			assessmentService.updateAssessment(param);
		}catch(Exception e){
			log.error("updateAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "修改考核指标失败，" + message;
		}	
		operationLogService.createOperationLog("updateAssessment", "指标名称:"+assessment.getName().trim()
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(assessment.getOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String entryEvaluation(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{			
			Assessment param = new Assessment();
			param.setName(assessment.getName().trim());
			param.setOrgId(assessment.getOrgId());
			assessment = (Assessment)assessmentService.queryAssessmentByName(param);
			if(null != assessment){
				assessment.setOrgName((String)organManagerService.getOrgNmByOrgId(
						assessment.getOrgId()));
			}else{
				message = "该考核指标已不存在。";
				result = AlarmConstants.RESULT_FAIL;
			}									
		}catch(Exception e){
			log.error("entryEvaluation error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化评价考核指标界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String evaluateAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("evaluateAssessment, assessment = " + assessment);					
			String note = assessment.getNote();
			Assessment param = new Assessment();
			param.setNote(note.trim());
			param.setResult(assessment.getResult());	
			param.setName(assessment.getName());
			param.setOrgId(assessment.getOrgId());
			log.info("evaluateAssessment, param = " + param);					
			assessmentService.evaluateAssessment(param);
		}catch(Exception e){
			log.error("evaluateAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "评价考核指标失败，" + message;
		}	
		operationLogService.createOperationLog("evaluateAssessment", "指标名称:"+assessment.getName().trim()
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(assessment.getOrgId())
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String deleteAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Assessment param = new Assessment();
			param.setName(name);
			param.setOrgId(orgId);
			log.info("deleteAssessment, param = " + param);					
			assessmentService.deleteAssessment(param);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除考核指标失败，" + message;
		}	
		operationLogService.createOperationLog("deleteAssessment", "指标名称:"+name
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(orgId)
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String noticeAssessment(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Assessment param = new Assessment();
			param.setName(name);
			param.setOrgId(orgId);
			message = "通知成功！";
			log.info("noticeAssessment, param = " + param);					
		}catch(Exception e){
			log.error("noticeAssessment error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "通知考核指标失败，" + message;
		}	
		operationLogService.createOperationLog("noticeAssessment", "指标名称:"+name
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(orgId)
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String queryAssessResult4Org(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				TaskBookCondition condition = new TaskBookCondition();
				condition.setInChargeOrgId(orgId);
				condition.setPlanOrgId(session.getOrganizationId());
				log.info("queryAssessResult4Org, condition = " + condition);	
				orgAssessmentList = assessmentService.queryAssessResult4Org(condition);
				orgResult = calculateOrgResult(orgAssessmentList);
				orgName = (String)organManagerService.getOrgNmByOrgId(orgId);
			}
		}catch(Exception e){
			log.error("queryAssessResult4Org error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查看机构考核成绩失败，" + message;
		}		
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
	}
	
	private float calculateOrgResult(List<Assessment> orgAssessmentList) {
		//  Score = R1 * (P1 /(P1 + P2 + P3 + 。。。 + PN)) + R2 * (P2 /(P1 + P2 + P3 + 。。。 + PN))
		// + RN * (PN /( P1 + P2 + P3 + 。。。 + PN))，“指标权重”分别为P1、PN，考评结果分别为R1、RN，
		float score = 0;
		int totalPercent = 0;
		log.info("calculateOrgResult, orgAssessmentList = " + orgAssessmentList);	
		for(Assessment assess : orgAssessmentList){
			totalPercent = totalPercent + assess.getPercent();
		}
		if(totalPercent != 0){
			for(Assessment ass : orgAssessmentList){
				float percent = ass.getPercent();
				float result = ass.getResult();
				score = score + result*(percent/totalPercent);
			}
		}	
		log.info("calculateOrgResult, score = " + score);	
		return score;
	}

	public String entryCheckInfo(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				CheckInfo param = new CheckInfo();
				param.setIndexName(assessment.getName().trim());
				param.setOrgId(assessment.getOrgId());										
				checkInfoList = assessmentService.queryCheckInfoByName(param);	
				String orgName = (String)organManagerService.getOrgNmByOrgId(
						assessment.getOrgId());
				String recordPersonName = ((Account)accountManagerService.
						queryAccountByAccountId(session.getId())).getUserName();
				for(CheckInfo checkInfo : checkInfoList){
					checkInfo.setOrgName(orgName);
					checkInfo.setRecordPersonName(recordPersonName);
				}
				checkInfo = new CheckInfo();
				checkInfo.setIndexName(assessment.getName().trim());
				checkInfo.setOrgId(assessment.getOrgId());
				checkInfo.setOrgName(orgName);
				checkInfo.setRecordPerson(session.getId());
				checkInfo.setRecordPersonName(recordPersonName);
				checkInfoSize = checkInfoList.size();
			}
		}catch(Exception e){
			log.error("entryCheckInfo error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化检查情况界面失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	public String createCheckInfo(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			log.info("createCheckInfo, checkInfo = " + checkInfo);					
			String note = checkInfo.getNote();
			CheckInfo param = new CheckInfo();
			param.setNote(note.trim());	
			param.setIndexName(checkInfo.getIndexName());
			param.setOrgId(checkInfo.getOrgId());
			param.setRecordPerson(checkInfo.getRecordPerson());
			param.setTime(checkInfo.getTime());
			log.info("createCheckInfo, param = " + param);					
			assessmentService.createCheckInfo(param);
		}catch(Exception e){
			log.error("createCheckInfo error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "创建检查情况失败，" + message;
		}
		operationLogService.createOperationLog("createCheckInfo", "指标名称:"+checkInfo.getIndexName()
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(checkInfo.getOrgId())
				+"，检查时间:"+checkInfo.getTime()
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String queryCheckInfoByName(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				CheckInfo param = new CheckInfo();			
				param.setIndexName(checkInfo.getIndexName().trim());
				param.setOrgId(checkInfo.getOrgId());											
				checkInfoList = assessmentService.queryCheckInfoByName(param);	
				String orgName = (String)organManagerService.getOrgNmByOrgId(
						checkInfo.getOrgId());
				String recordPersonName = ((Account)accountManagerService.
						queryAccountByAccountId(session.getId())).getUserName();
				for(CheckInfo checkInfo : checkInfoList){
					checkInfo.setOrgName(orgName);
					checkInfo.setRecordPersonName(recordPersonName);
				}
				checkInfo.setOrgName(orgName);
				checkInfo.setRecordPerson(session.getId());
				checkInfo.setRecordPersonName(recordPersonName);
				checkInfoSize = checkInfoList.size();
			}
		}catch(Exception e){
			log.error("queryCheckInfo error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询检查情况失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}	
	
	public String deleteCheckInfo(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";				
		try{			
			CheckInfo param = new CheckInfo();			
			param.setIndexName(indexName);
			param.setOrgId(orgId);	
			param.setTime(AlarmUtil.string2Date(time, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss")));
			log.info("deleteCheckInfo, param = " + param);					
			assessmentService.deleteCheckInfoByName(param);
			message = "删除成功！";
		}catch(Exception e){
			log.error("deleteCheckInfo error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "删除检查情况失败，" + message;
		}	
		operationLogService.createOperationLog("deleteCheckInfo", "指标名称:"+indexName
				+"，考核机构:"+(String)organManagerService.getOrgNmByOrgId(orgId)
				+"，检查时间:"+time
				, result, message);
		ajaxObject=new AjaxObject(result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String checkCheckInfoExist(){
		String res = SUCCESS;	
		try {
			CheckInfo param = new CheckInfo();	
			param.setIndexName(checkInfo.getIndexName().trim());
			param.setOrgId(checkInfo.getOrgId());	
			param.setTime(checkInfo.getTime());
			log.info("checkCheckInfoExist, param = " + param);					
			CheckInfo t = (CheckInfo)assessmentService.queryCheckInfoByNmAndTime(
					param);
			if(null != t){
				tip = "检查情况已存在。";
			}else{
				tip = "";
			}			
		} catch (Exception e) {
			log.error("checkCheckInfoExist error!", e);
		}
		return res;
	}
	
	public String checkAssessmentExist(){
		String res = SUCCESS;	
		try {
			Assessment param = new Assessment();	
			param.setName(assessment.getName().trim());
			param.setOrgId(assessment.getOrgId());
			log.info("checkAssessmentExist, param = " + param);					
			Assessment t = (Assessment)assessmentService.queryAssessmentByName(
					param);
			if(null != t){
				assessTip = "考核指标已存在。";
			}else{
				assessTip = "";
			}				
		} catch (Exception e) {
			log.error("checkAssessmentExist error!", e);
		}
		return res;
	}	

	/**
	 * 根据考核机构联动查询考核机构责任人
	 * @return
	 */
	public String queryOrgPersonByOrg(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";	
		log.info("queryOrgPersonByOrg, orgId = " + orgId);					
		try{		
			if(null != orgId){
				orgPersonList = (List<CommonBean>)accountManagerService.
						getAccountIdAndUserNamesByOrgId(orgId);										
			}					
		}catch(Exception e){
			log.error("queryOrgPersonByOrg error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询考核机构责任人失败，" + message;
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(orgPersonList);
		ajaxObject.setMessage(message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/**
	 * 根据考核机构联动查询指标名称
	 * @return
	 */
	public String queryAssessNameByOrg(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";	
		log.info("queryAssessNameByOrg, orgId = " + orgId);					
		try{	
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				if(null != orgId){
					TaskBookCondition taskBookCondition = new TaskBookCondition();
					taskBookCondition.setPlanOrgId(session.getOrganizationId());
					taskBookCondition.setInChargeOrgId(orgId);
					log.info("queryAssessNameByOrg, taskBookCondition = " + taskBookCondition);
					assessIndexList = taskBookService.queryTbByIssueTime(taskBookCondition);								
				}
			}
		}catch(Exception e){
			log.error("queryAssessNameByOrg error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询指标名称失败，" + message;
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(assessIndexList);
		ajaxObject.setMessage(message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String getCheckOrgId() {
		return checkOrgId;
	}

	public void setCheckOrgId(String checkOrgId) {
		this.checkOrgId = checkOrgId;
	}

	public int getCheckInfoSize() {
		return checkInfoSize;
	}

	public void setCheckInfoSize(int checkInfoSize) {
		this.checkInfoSize = checkInfoSize;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public CheckInfo getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(CheckInfo checkInfo) {
		this.checkInfo = checkInfo;
	}

	public List<CheckInfo> getCheckInfoList() {
		return checkInfoList;
	}

	public void setCheckInfoList(List<CheckInfo> checkInfoList) {
		this.checkInfoList = checkInfoList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public float getOrgResult() {
		return orgResult;
	}

	public void setOrgResult(float orgResult) {
		this.orgResult = orgResult;
	}

	public List<Assessment> getOrgAssessmentList() {
		return orgAssessmentList;
	}

	public void setOrgAssessmentList(List<Assessment> orgAssessmentList) {
		this.orgAssessmentList = orgAssessmentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CommonBean> getAssessPersonList() {
		return assessPersonList;
	}

	public void setAssessPersonList(List<CommonBean> assessPersonList) {
		this.assessPersonList = assessPersonList;
	}

	public List<CommonBean> getOrgPersonList() {
		return orgPersonList;
	}

	public void setOrgPersonList(List<CommonBean> orgPersonList) {
		this.orgPersonList = orgPersonList;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
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

	public AccountManagerServiceImp getAccountManagerService() {
		return accountManagerService;
	}

	public void setAccountManagerService(
			AccountManagerServiceImp accountManagerService) {
		this.accountManagerService = accountManagerService;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public IAssessmentService getAssessmentService() {
		return assessmentService;
	}

	public void setAssessmentService(IAssessmentService assessmentService) {
		this.assessmentService = assessmentService;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public List<CommonBean> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<CommonBean> orgList) {
		this.orgList = orgList;
	}

	public List<Assessment> getAssessmentList() {
		return assessmentList;
	}

	public void setAssessmentList(List<Assessment> assessmentList) {
		this.assessmentList = assessmentList;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<TaskBook> getAssessIndexList() {
		return assessIndexList;
	}

	public void setAssessIndexList(List<TaskBook> assessIndexList) {
		this.assessIndexList = assessIndexList;
	}

	public ITaskBookService getTaskBookService() {
		return taskBookService;
	}

	public void setTaskBookService(ITaskBookService taskBookService) {
		this.taskBookService = taskBookService;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getAssessTip() {
		return assessTip;
	}

	public void setAssessTip(String assessTip) {
		this.assessTip = assessTip;
	}

}
