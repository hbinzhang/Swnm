package com.actions.authmgt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.entity.CommonBean;
import com.entity.authmgt.Account;
import com.entity.authmgt.OrgLevAndIdNames;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IAccountManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;

import common.page.AjaxObject;

public class AccountManagerAction extends ActionSupport {

	private static final java.lang.String LOGGER_NAME = AccountManagerAction.class
			.getName();

	private Log log = LogFactory.getLog(this.getClass());

	// 下面的属性值由页面传入//
	private Account account;
	private String id;// 工号
	private String password;//
	private List<String> seledRoles;

	// 下面的属性值提供给页面
	private OrgLevAndIdNames orgLevAndIdNames;
	private List<String> allowedRoles;
	private List<Account> accounts;
	private String oldpassword;//
	// 执行结果。用于页面显示
	private CommonBean commonBean;
	private AjaxObject ajaxObject;

	private IAccountManagerService accountManagerService = null;

	private IOperationLogService operationLogService;

	private String oldPasswd;

	private String rePassword;

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */

	public String entryCreateAccount() {
		boolean isSuccess = true;
		String result = "0";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");

		if (session != null) {
			try {
				account = new Account();
				account.setBirthday(null);
				seledRoles = new ArrayList<String>();
				orgLevAndIdNames = accountManagerService
						.getOrgLevAndIdNamesByAccountId(session.getId());
				allowedRoles = accountManagerService
						.getAllowedRolesByAccountId();
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
			}
		} else
			result = "-2";
		StringBuffer msg = new StringBuffer("查询机构和角色");
		switch (Integer.parseInt(result)) {
		case -2:
			msg.append("失败，无Session对象！");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String queryAccountByAccountId() {
		boolean isSuccess = true;
		String result = "0";

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				account = (Account) accountManagerService
						.queryAccountByAccountId(session.getId());
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
			}
		} else
			result = "-2";
		StringBuffer msg = new StringBuffer("查询帐号");
		switch (Integer.parseInt(result)) {
		case -2:
			msg.append("失败，无Session对象！");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String queryAccountsByAccountId() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = "0";

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				accounts = accountManagerService
						.queryAccountsByAccountId(session.getId());
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		// operationLogService.createOperationLog("queryAccountsByAccountId",
		// "",
		// resultForLog, message);
		StringBuffer msg = new StringBuffer("查询帐号");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:1--已存在；0：成功；-1：异常
	public String createAccount() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		
		String homeAddress = account.getHomeAddress();
		String homeTel = account.getHomeTel();
		String id = account.getId();
		String mail = account.getMail();
		String offTel = account.getOffTel();
		String phone = account.getPhone();
		String position = account.getPosition();
		String positionDesc = account.getPositionDesc();
		String remark = account.getRemark();
		String userId = account.getUserId();
		String userName = account.getUserName();

		account.setHomeAddress(homeAddress.trim());
		account.setHomeTel(homeTel.trim());
		account.setId(id.trim());
		account.setMail(mail.trim());
		account.setOffTel(offTel.trim());
		account.setPhone(phone.trim());
		account.setPosition(position.trim());
		account.setPositionDesc(positionDesc.trim());
		account.setRemark(remark.trim());
		account.setUserId(userId.trim());
		account.setUserName(userName.trim());
		account.setType(1);
		account.setPassword(id.trim());
		account.setRoles(transListToString(seledRoles));
		try {
			result = String.valueOf(accountManagerService
					.createAccount(account));
			orgLevAndIdNames = accountManagerService
					.getOrgLevAndIdNamesByAccountId(session.getId());
			allowedRoles = accountManagerService
					.getAllowedRolesByAccountId();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("createAccount",
				account.getId(), resultForLog, message);
		StringBuffer msg = new StringBuffer("创建帐号").append(account.getId());
		switch (Integer.parseInt(result)) {
		case 1:
			msg.append("失败，相同工号的帐号已经存在。");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private String transListToString(List<String> opNames) {
		StringBuffer authorizedOpIdsTmp = new StringBuffer();
		if (opNames != null) {
			for (int i = 0; i < opNames.size(); i++) {
				authorizedOpIdsTmp.append(opNames.get(i));
				if (i < (opNames.size() - 1)) {
					authorizedOpIdsTmp.append(",");
				}
			}
		}
		return authorizedOpIdsTmp.toString();
	}

	public String editAccount() {
		boolean isSuccess = true;
		String result = "0";
		try {
			account = (Account) accountManagerService
					.queryAccountByAccountId(id);
			String roleTmp = account.getRoles();
			if (roleTmp != null && roleTmp.length() != 0) {
				String[] roleStrings = roleTmp.split(",");
				seledRoles = new ArrayList<String>(Arrays.asList(roleStrings));
			}
			orgLevAndIdNames = accountManagerService
					.getOrgLevAndIdNamesByAccountId(id);
			allowedRoles = accountManagerService
					.getAllowedRolesByAccountId();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
		}
		StringBuffer msg = new StringBuffer("查询机构和角色");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:2--不存在；0：成功；-1：异常
	public String updateAccount() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;

		String homeAddress = account.getHomeAddress();
		String homeTel = account.getHomeTel();
		String id = account.getId();
		String mail = account.getMail();
		String offTel = account.getOffTel();
		String phone = account.getPhone();
		String position = account.getPosition();
		String positionDesc = account.getPositionDesc();
		String remark = account.getRemark();
		String userId = account.getUserId();
		String userName = account.getUserName();

		account.setHomeAddress(homeAddress.trim());
		account.setHomeTel(homeTel.trim());
		account.setId(id.trim());
		account.setMail(mail.trim());
		account.setOffTel(offTel.trim());
		account.setPhone(phone.trim());
		account.setPosition(position.trim());
		account.setPositionDesc(positionDesc.trim());
		account.setRemark(remark.trim());
		account.setUserId(userId.trim());
		account.setUserName(userName.trim());

		account.setType(1);//
		account.setPassword(account.getId());
		account.setRoles(transListToString(seledRoles));

		try {
			result = String.valueOf(accountManagerService
					.updateAccount(account));
			orgLevAndIdNames = accountManagerService
					.getOrgLevAndIdNamesByAccountId(id.trim());
			allowedRoles = accountManagerService
					.getAllowedRolesByAccountId();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("updateAccount",
				account.getId(), resultForLog, message);

		StringBuffer msg = new StringBuffer("修改帐号").append(account.getId());
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，帐号已经不存在。");
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:2--不存在；0：成功；-1：异常
	public String updateAccountPassword() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = "0";
		int resultForDel = -1;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			id = session.getId();
			try {
				if (!password.equals(rePassword.trim())) {
					result = "3";
				} else {
					result = String.valueOf(accountManagerService
							.updateAccountPassword(id, oldPasswd.trim(),
									password.trim()));
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		operationLogService.createOperationLog("updateAccountPassword", id,
				resultForLog, message);

		StringBuffer msg = new StringBuffer("修改帐号密码").append(id);
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，帐号密码已经不存在。");
			isSuccess = false;
			resultForDel = 0;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			resultForDel = 0;
			break;
		case 3:
			msg.append("失败，两次密码输入不一致！");
			isSuccess = false;
			resultForDel = 0;
			break;
		default:
			msg.append("成功。");
			resultForDel = 1;
			isSuccess = true;
			break;
		}
		ajaxObject = new AjaxObject(resultForDel, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String editAccountPassword() {
		boolean isSuccess = true;
		String result = "0";

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				Account account = (Account) accountManagerService
						.queryAccountByAccountId(session.getId());
				if (account != null)
					oldpassword = account.getPassword();
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
			}
		}
		StringBuffer msg = new StringBuffer("查询账号");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			break;
		}
		commonBean = new CommonBean(result, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// result:2--不存在；0：成功；-1：异常
	public String resetAccountPassword() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;
		int resultForDel = -1;

		try {
			result = String.valueOf(accountManagerService
					.resetAccountPassword(id));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("resetAccountPassword", id,
				resultForLog, message);

		StringBuffer msg = new StringBuffer("重置密码").append(id);
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，帐号已经不存在。");
			resultForDel = 0;
			isSuccess = false;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			resultForDel = 0;
			break;
		default:
			msg.append("成功。");
			resultForDel = 1;
			isSuccess = true;
			break;
		}
		// commonBean = new CommonBean(result, msg.toString());
		ajaxObject = new AjaxObject(resultForDel, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	// public String editAccountRoles() {
	// boolean isSuccess = true;
	// String result = "0";
	// try {
	// account = (Account) accountManagerService
	// .queryAccountByAccountId(id);
	// String[] roleStrings = account.getRoles().split(",");
	// seledRoles = new ArrayList<String>(Arrays.asList(roleStrings));
	// // orgLevAndIdNames =
	// // accountManagerService.getOrgLevAndIdNamesByAccountId(id);
	// allowedRoles = accountManagerService.getAllowedRolesByAccountId(id);
	// } catch (Exception ex) {
	// // LoggerOperator.err(LOGGER_NAME, "addUser error", e);
	// Logger log = Logger.getLogger(LOGGER_NAME);
	// log.error(ex.getMessage(), ex);
	// result = "-1";
	// }
	// StringBuffer msg = new StringBuffer("查询账号");
	// switch (Integer.parseInt(result)) {
	// case -1:
	// msg.append("失败，数据库异常！");
	// isSuccess = false;
	// break;
	// default:
	// msg.append("成功。");
	// isSuccess = true;
	// break;
	// }
	// commonBean = new CommonBean(result, msg.toString());
	// if (isSuccess) {
	// return SUCCESS;
	// } else {
	// return ERROR;
	// }
	// }

	// result:2--不存在；0：成功；-1：异常
	// public String updateAccountRoles() {
	// int resultForLog = AlarmConstants.RESULT_SUC;
	// String message = "";
	// boolean isSuccess = true;
	// String result = null;
	//
	// try {
	// result = String.valueOf(accountManagerService.updateAccountRoles(
	// id, transListToString(seledRoles)));
	// } catch (Exception ex) {
	// // LoggerOperator.err(LOGGER_NAME, "addUser error", e);
	// Logger log = Logger.getLogger(LOGGER_NAME);
	// log.error(ex.getMessage(), ex);
	// result = "-1";
	// message = "数据库异常";
	// resultForLog = AlarmConstants.RESULT_FAIL;
	// }
	// operationLogService.createOperationLog("updateAccountRoles", id,
	// resultForLog, message);
	//
	// StringBuffer msg = new StringBuffer("修改帐号").append(account.getId());
	// switch (Integer.parseInt(result)) {
	// case 2:
	// msg.append("失败，帐号已经不存在。");
	// isSuccess = false;
	// break;
	// case -1:
	// msg.append("失败，数据库异常！");
	// isSuccess = false;
	// break;
	// default:
	// msg.append("成功。");
	// isSuccess = true;
	// break;
	// }
	// commonBean = new CommonBean(result, msg.toString());
	// if (isSuccess) {
	// return SUCCESS;
	// } else {
	// this.addActionError(msg.toString());
	// return ERROR;
	// }
	// }

	// result:2--不存在；0：成功；-1：异常
	public String deleteAccount() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;
		int resultForDel = -1;
		try {
			result = String.valueOf(accountManagerService.deleteAccount(id));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("deleteAccount", id,
				resultForLog, message);

		StringBuffer msg = new StringBuffer("删除帐号").append(id);
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，帐号已经不存在。");
			isSuccess = false;
			resultForDel = 0;
			break;
		case -1:
			msg.append("失败，数据库异常！");
			isSuccess = false;
			resultForDel = 0;
			break;
		default:
			msg.append("成功。");
			isSuccess = true;
			resultForDel = 1;
			break;
		}
		ajaxObject = new AjaxObject(resultForDel, msg.toString());
		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public IAccountManagerService getAccountManagerService() {
		return accountManagerService;
	}

	public void setAccountManagerService(
			IAccountManagerService accountManagerService) {
		this.accountManagerService = accountManagerService;
	}

	public String getId() {
		return id;
	}

	public void setId(String accountId) {
		this.id = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public OrgLevAndIdNames getOrgLevAndIdNames() {
		return orgLevAndIdNames;
	}

	public void setOrgLevAndIdNames(OrgLevAndIdNames orgLevAndIdNames) {
		this.orgLevAndIdNames = orgLevAndIdNames;
	}

	public List<String> getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(List<String> allowedRoles) {
		this.allowedRoles = allowedRoles;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public List<String> getSeledRoles() {
		return seledRoles;
	}

	public void setSeledRoles(List<String> seledRoles) {
		this.seledRoles = seledRoles;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getRePassword() {
		return rePassword;
	}

}
