package com.actions.logmgt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.SecurityLog;
import com.entity.logmgt.SecurityLogCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.service.logmgt.ISecurityLogService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;
import com.util.logmgt.CommandParser;
import com.util.logmgt.LogUtil;

import common.page.AjaxObject;
import common.page.Pager;

/**
 * 安全日志管理
 * @author liyinghui
 *
 */
public class SecurityLogAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5244037489206052265L;

	private ISecurityLogService securityLogService;
	private IOrganizationManagerService organManagerService;
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 来源：客户端下发给服务器
	 * 操作：查询安全日志
	 * 含义：工号
	 */
	private String accountId;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：查询安全日志
	 * 含义：安全日志list
	 */
	private List<SecurityLog> securityLogList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化安全日志界面
	 * 含义：分公司list
	 */
	private List<CommonBean> branchList;

	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化安全日志界面
	 * 含义：管理处list
	 */
	private List<CommonBean> departmentList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化安全日志界面
	 * 含义：总公司list
	 */
	private List<CommonBean> companyList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化安全日志界面
	 * 含义：机构级别list
	 */
	private List<CommonBean> organizationList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：查询安全日志
	 * 含义：查询条件
	 */
	private SecurityLogCondition securityLogCondition;	
	
	/**
	 * 来源：服务器返回给客户端
	 * 含义：用户所属机构级别
	 * 0 总公司
	 * 1 分公司
	 * 2 管理处
	 */
	private String userLevel;	
		
	public static final int step = 10;
	
	private int offset;
	
	private Pager pager;
	
	private AjaxObject ajaxObject;

	private CommonBean commonBean;
	
	private IOperationLogService operationLogService;

	public static int max = 1000000;
	
	public static int min = 1;
	
	/**
	 * 1 按机构查
	 * 2 按帐号查
	 */
//	private int flag;
	
	public String initSecurityLog(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		accountId = "";		
	//	flag = 1;
		securityLogCondition = new SecurityLogCondition();
		securityLogCondition.setFlag(1);
		securityLogCondition.setOpResult(-1);
		securityLogCondition.setCommandId(-1);
		pager = new Pager();
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				userLevel = session.getLev();
				log.info("initSecurityLog, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					departmentList = session.getOrgIdAndNames().getManagements();
					List<CommonBean> list = AlarmUtil.getListByMap(LogUtil.organizationMap);
					// 根据用户级别决定界面机构级别下拉列表
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						// 如果用户是总公司，界面级别为总公司，分公司，管理处，机构名称都无全部			
						organizationList = AlarmUtil.getSubList(list, 0, 2);
						// 获取所有总公司，分公司，管理处
						companyList = (List<CommonBean>)organManagerService.getOrgIdAndOrgNmsByLev(
								AlarmConstants.USER_HEADQUARTERS);			
						branchList = session.getOrgIdAndNames().getSubCompanys();
					}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){				
						// 如果用户是分公司级别, 界面级别为分公司，管理处	
						organizationList = AlarmUtil.getSubList(list, 1, 2);
						branchList = session.getOrgIdAndNames().getSubCompanys();
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果用户是管理处级别, 界面级别为管理处		
						organizationList = AlarmUtil.getSubList(list, 2, 2);
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}			
				}
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("initSecurityLog error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化安全日志查询条件失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}			
	}

	/*private boolean judgeUser(){
		if(null == accountId || accountId.length() == 0){
			return true;
		}
		// 输入用户级别
		String userLevel = String.valueOf(organManagerService.getLevByAccountId(accountId));
		Session session = AlarmUtil.getLoginSession();
		// 登陆用户级别
		String loginUserId = session.getId();  
		String loginUserLevel = session.getLev();	
		if(null == userLevel){
			this.addActionError("工号不存在");
			return false;
		}
		if(null == loginUserLevel){
			this.addActionError("登录用户不存在");
			return false;
		}	
		// 如果输入用户级别高于登陆用户级别
		if(Integer.parseInt(userLevel) < Integer.parseInt(loginUserLevel)){
			this.addActionError("工号级别应等于或低于登录用户级别");
			return false;
		}
		// 登陆用户机构id			
		String loginUserOrgaId = session.getOrganizationId();	
		// 输入用户机构id
		CommonBean cb = (CommonBean)organManagerService.getOrgIdAndOrgNmByAccountId(accountId);
		String userOrgaId = cb.getId();
		if(null == userOrgaId){
			this.addActionError("工号所属机构不存在");
			return false;
		}
		if(null == loginUserOrgaId){
			this.addActionError("登录用户所属机构不存在");
			return false;
		}
		if(loginUserLevel != AlarmConstants.USER_HEADQUARTERS){
			// 输入用户父机构id
			String userParentOrgaId = String.valueOf(organManagerService.getPOrgIdByAccountId(accountId));		
			if(null == userParentOrgaId){
				this.addActionError("工号所属父机构不存在");
				return false;
			}	
			// 如果输入用户不等于登陆用户或者输入用户父机构不等于登陆用户机构
			if((!accountId.equals(loginUserId)) && (!userParentOrgaId.equalsIgnoreCase(loginUserOrgaId))){
				this.addActionError("工号应等于登录用户或工号所属父机构等于登录用户所属机构");
				return false;
			}
		}	
		return true;
	}	*/
	
	/*private SecurityLogCondition getCondition(){
		securityLogCondition = new SecurityLogCondition();	
		Calendar cal = Calendar.getInstance();
		condition_startTime = new Date();
		condition_endTime = new Date();
		condition_startTime.setDate(23);
		condition_startTime.setHours(1);
		condition_startTime.setMinutes(1);
		condition_startTime.setSeconds(1);
		cal.setTime(condition_startTime);
		Calendar cal2 = Calendar.getInstance();
		condition_endTime.setHours(23);
		condition_endTime.setMinutes(21);
		condition_endTime.setSeconds(33);
		condition_endTime.setDate(26);

		cal2.setTime(condition_endTime);
		if(flag == 1){
			securityLogCondition.setAccountId(accountId);
		}
		securityLogCondition.setFlag(flag);
		securityLogCondition.setBeginTime(condition_startTime);
		securityLogCondition.setCommandId(cmdId);
		securityLogCondition.setEndTime(condition_endTime);
		securityLogCondition.setOrganizationId(organizationId);
		securityLogCondition.setOpResult(result);
		return securityLogCondition;
	}*/
	
	private void setPageInfo(Pager pager, int totalCount, SecurityLogCondition securityLogCondition){
		int start = 0;
		int end = 0;
		int totalPage = 0;
		if (totalCount == 0) {
			totalPage = 0;
			offset = -1;
		} else {		
			// 计算总页数
			totalPage = (totalCount + step - 1) / step;
			// 如果当前页为负数或0，则按第一页处理，超过总页数时，按最后一页处理
			if (offset <= 0) {
				offset = 1;
			} else if (offset > totalPage) {
				offset = totalPage;
			}
			start = (offset-1)*step + 1;
			end = start + step;
		}
		log.info("setPageInfo, start = " + start + 
				", end = " + end);
		securityLogCondition.setStart(start);
		securityLogCondition.setEnd(end);
		pager.setOffset(offset);
		pager.setPages(totalPage);
		pager.setTotal(totalCount);
		pager.setSize(step);
	}
	
	public String querySecurityLog(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		pager = new Pager();
		int totalCount = 0;
		log.info("querySecurityLog, securityLogCondition = " + securityLogCondition);		
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				userLevel = session.getLev();
				log.info("querySecurityLog, userLevel = " + userLevel);	
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					branchList = session.getOrgIdAndNames().getSubCompanys();
					departmentList = session.getOrgIdAndNames().getManagements();
					List<CommonBean> list = AlarmUtil.getListByMap(LogUtil.organizationMap);
					// 根据用户级别决定界面机构级别下拉列表
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						// 如果用户是总公司，界面级别为总公司，分公司，管理处，机构名称都无全部			
						organizationList = AlarmUtil.getSubList(list, 0, 2);
						// 获取所有总公司，分公司，管理处
						companyList = (List<CommonBean>)organManagerService.getOrgIdAndOrgNmsByLev(
								AlarmConstants.USER_HEADQUARTERS);			
					}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){				
						// 如果用户是分公司级别, 界面级别为分公司，管理处	
						organizationList = AlarmUtil.getSubList(list, 1, 2);	
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果用户是管理处级别, 界面级别为管理处		
						organizationList = AlarmUtil.getSubList(list, 2, 2);
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}			
				}
				if(securityLogCondition.getFlag() == 1){
					// 如果按机构查，机构id字段必有值（界面必有值）
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS) ||
							userLevel.equals(AlarmConstants.USER_BRANCH) ||
								userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						totalCount = securityLogService.getSecLogCountByOrgId(securityLogCondition);
						setPageInfo(pager, totalCount, securityLogCondition);	
						securityLogList = (List<SecurityLog>)securityLogService.querySecLogByOrgId(
								securityLogCondition);		
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}	
				}else {
					// 如果是总公司用户，可以查询所有日志
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						totalCount = securityLogService.getSecLogCountByAccId(securityLogCondition);
						setPageInfo(pager, totalCount, securityLogCondition);	
						securityLogList = (List<SecurityLog>)securityLogService.querySecLogByAccId(
								securityLogCondition);
					}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){
						// 如果输入了工号，查询   日志工号等于输入工号，并且日志机构id in (分公司和管理处id)
						List organList = new ArrayList();
						// 获取用户所属分公司
						List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
						for(CommonBean cBean :branchList){
							organList.add(cBean.getId());
						}			
						// 获取用户所属管理处
						List<CommonBean> departmentList = session.getOrgIdAndNames().getSubCompanys();
						for(CommonBean commonBean :departmentList){
							organList.add(commonBean.getId());
						}
						securityLogCondition.setOrgans(AlarmUtil.getSqlByList(organList));
						totalCount = securityLogService.getSecLogCountByOrgsAndAccId(securityLogCondition);
						setPageInfo(pager, totalCount, securityLogCondition);	
						securityLogList = (List<SecurityLog>)securityLogService.querySecLogByOrgsAndAccId(
											securityLogCondition);	
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果是管理处用户，并且输入了工号，查询  用户所属管理处的日志，并且日志工号等于输入工号
						List organList = new ArrayList();
						organList.add(session.getOrganizationId());
						securityLogCondition.setOrgans(AlarmUtil.getSqlByList(organList));
						totalCount = securityLogService.getSecLogCountByOrgsAndAccId(securityLogCondition);
						setPageInfo(pager, totalCount, securityLogCondition);	
						securityLogList = (List<SecurityLog>)securityLogService.querySecLogByOrgsAndAccId(
								securityLogCondition);			
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}	
				}				
				if(null != securityLogList && securityLogList.size() > 0){
					for(SecurityLog log : securityLogList){
						CommandInfo cInfo = CommandParser.getInstance().getDetail(
								log.getCommandId());
						if(null != cInfo){
							log.setCommandName(AlarmUtil.formatString(cInfo.getDisplayName()));						
						}
						Object o = organManagerService.getOrgNmByOrgId(log.getOrganizationId());
						if(null != o){
							String orgName = AlarmUtil.formatString(String.valueOf(o));
							if(orgName.equals("")){
								log.setOrgName(log.getOrganizationId());
							}else{
								log.setOrgName(orgName);
							}
						}						
					}
				}
				pager.setDatas(securityLogList);
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("querySecurityLog error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "查询安全日志失败，" + message;
		}
		log.info("end querySecurityLog!");	
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	/*private void setSortedOption(SecurityLogCondition securityLogCondition){
		StringBuffer orderStr = new StringBuffer();
		List<SortedOptionItem> sortedOptions = securityLogCondition.getSortedOptions();
		if(sortedOptions != null && sortedOptions.size()>0){	
			for(SortedOptionItem optionItem : sortedOptions){
				orderStr.append(optionItem.getFieldName()+" ");
				if(!optionItem.isIsAscending())
				{
					orderStr.append(" desc ");
				}
				orderStr.append(",");	
			}
			orderStr.delete(orderStr.length()-1,orderStr.length());	
		}else{
			orderStr.append(OperationLogCondition.ORDER_BY_TIME);
			orderStr.append(" desc ");
		}
		securityLogCondition.setOrderStr(orderStr.toString());
	}*/
	

	public String deleteSecurityLog(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		StringBuffer operationObjects = new StringBuffer();
		List<SecurityLog> logList = null;
		try{
			securityLogCondition.setStart(min);
			securityLogCondition.setEnd(max);	
			log.info("deleteSecurityLog, securityLogCondition = " + securityLogCondition);
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				userLevel = session.getLev();
				log.info("deleteSecurityLog, userLevel = " + userLevel);		
				if(securityLogCondition.getFlag() == 1){
					// 如果按机构查，机构id字段必有值（界面必有值）
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS) ||
							userLevel.equals(AlarmConstants.USER_BRANCH) ||
								userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						logList = (List<SecurityLog>)securityLogService.querySecLogByOrgId(
								securityLogCondition);		
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}	
				}else {
					// 如果是总公司用户，可以查询所有日志
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						logList = (List<SecurityLog>)securityLogService.querySecLogByAccId(
								securityLogCondition);
					}else if(userLevel.equals(AlarmConstants.USER_BRANCH)){
						// 如果输入了工号，查询   日志工号等于输入工号，并且日志机构id in (分公司和管理处id)
						List organList = new ArrayList();
						// 获取用户所属分公司
						List<CommonBean> branchList = session.getOrgIdAndNames().getSubCompanys();
						for(CommonBean cBean :branchList){
							organList.add(cBean.getId());
						}			
						// 获取用户所属管理处
						List<CommonBean> departmentList = session.getOrgIdAndNames().getSubCompanys();
						for(CommonBean commonBean :departmentList){
							organList.add(commonBean.getId());
						}
						securityLogCondition.setOrgans(AlarmUtil.getSqlByList(organList));
						logList = (List<SecurityLog>)securityLogService.querySecLogByOrgsAndAccId(
											securityLogCondition);	
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果是管理处用户，并且输入了工号，查询  用户所属管理处的日志，并且日志工号等于输入工号
						List organList = new ArrayList();
						organList.add(session.getOrganizationId());
						securityLogCondition.setOrgans(AlarmUtil.getSqlByList(organList));
						logList = (List<SecurityLog>)securityLogService.querySecLogByOrgsAndAccId(
								securityLogCondition);			
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}	
				}
			}
			log.info("deleteSecurityLog, logList = " + logList);
			if(null != logList && logList.size() > 0){
				for(SecurityLog log : logList){
					securityLogService.deleteSecLogById(log.getSlsId());
					operationObjects.append(log.getSlsId()).append(",");
				}
			}		
		}catch(Exception e){
			log.error("deleteSecurityLog error!",e);
			message = "删除安全日志失败，数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}		
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setMessage(message);
		log.info("deleteSecurityLog, ajaxObject = " + ajaxObject);
		if(operationObjects.length() > 0){
			operationObjects.deleteCharAt(operationObjects.length()-1);
		}
		operationLogService.createOperationLog("deleteSecurityLog", operationObjects.toString(), 
				result, message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
	}
	
	public List<CommonBean> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<CommonBean> organizationList) {
		this.organizationList = organizationList;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<CommonBean> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<CommonBean> branchList) {
		this.branchList = branchList;
	}

	public List<CommonBean> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<CommonBean> departmentList) {
		this.departmentList = departmentList;
	}

	public List<CommonBean> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CommonBean> companyList) {
		this.companyList = companyList;
	}

	public ISecurityLogService getSecurityLogService() {
		return securityLogService;
	}

	public void setSecurityLogService(ISecurityLogService securityLogService) {
		this.securityLogService = securityLogService;
	}

	public List<SecurityLog> getSecurityLogList() {
		return securityLogList;
	}

	public void setSecurityLogList(List<SecurityLog> securityLogList) {
		this.securityLogList = securityLogList;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public SecurityLogCondition getSecurityLogCondition() {
		return securityLogCondition;
	}

	public void setSecurityLogCondition(SecurityLogCondition securityLogCondition) {
		this.securityLogCondition = securityLogCondition;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
}
