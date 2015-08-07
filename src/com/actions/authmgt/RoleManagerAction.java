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
import com.entity.authmgt.GroupAndOperation;
import com.entity.authmgt.Role;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IRoleManagerService;
import com.service.authmgt.impl.OperationAuthorizationImp;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;

import common.page.AjaxObject;

public class RoleManagerAction extends ActionSupport {

	private static final java.lang.String LOGGER_NAME = RoleManagerAction.class
			.getName();

	private Log log = LogFactory.getLog(this.getClass());

	// 下面的属性值由页面传入
	private Role role;
	private String name;
	// 操作显示名称列表
	private List<String> ops;

	// 下面的属性值提供给页面
	// 角色集
	private List<Role> roles;
	//
	private List<GroupAndOperation> groupAndOperations;
	// 执行结果。用于页面显示
	private CommonBean commonBean;
	private AjaxObject ajaxObject;

	private IRoleManagerService roleManagerService = null;

	private IOperationLogService operationLogService;

	public IRoleManagerService getRoleManagerService() {
		return roleManagerService;
	}

	public void setRoleManagerService(IRoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}

	/**
	 * 
	 * result:0：成功；-1：异常
	 * 
	 * @return SUCCESS
	 */
	public String queryAllGroupAndOperation() {
		boolean isSuccess = true;
		String result = "0";
		try {
			groupAndOperations = OperationAuthorizationImp
					.queryAllGroupAndOperation();
			log.info("groupAndOperations: " + groupAndOperations);
			role = new Role();
			ops = new ArrayList<String>();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
		}
		StringBuffer msg = new StringBuffer("查询操作组和操作");
		switch (Integer.parseInt(result)) {
		case -1:
			msg.append("失败，文件解析异常！");
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
	public String queryAllRoles() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = "0";
		List<Role> rolesTmp = null;

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		if (session != null) {
			try {
				rolesTmp = roleManagerService.getAllRoles();
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				result = "-1";
				message = "数据库异常";
				resultForLog = AlarmConstants.RESULT_FAIL;
			}
		}
		// operationLogService.createOperationLog("queryAllRoles", "",
		// resultForLog, message);
		if (rolesTmp != null) {
			roles = new ArrayList();
			for (int i = 0; i < rolesTmp.size(); i++) {
				Role role = rolesTmp.get(i);
				String[] opStrings = role.getAuthorizedOpIds().split(",");
				List<String> authorizedOpIds = new ArrayList<String>(
						Arrays.asList(opStrings));
				List<String> opsTmp = OperationAuthorizationImp
						.getOperationDisplayNames(authorizedOpIds);
				role.setAuthorizedOpsForUI(opsTmp);
				roles.add(role);
			}
		}
		StringBuffer msg = new StringBuffer("查询角色");
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
	public String createRole() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;

		role.setAuthorizedOpIds((String) roleManagerService
				.getOperationNames(ops));
		String roleName=role.getName();
		String roleDesc=role.getDesc();
		role.setName(roleName.trim());
		role.setDesc(roleDesc.trim());
		try {
			result = String.valueOf(roleManagerService.createRole(role));
			groupAndOperations = OperationAuthorizationImp
					.queryAllGroupAndOperation();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("createRole", role.getName(),
				resultForLog, message);
		StringBuffer msg = new StringBuffer("创建角色").append(role.getName());
		switch (Integer.parseInt(result)) {
		case 1:
			msg.append("失败，相同名称的角色已经存在。");
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

	public String editRole() {
		boolean isSuccess = true;
		String result = "0";
		try {
			role = roleManagerService.getRoleByName(name);
			groupAndOperations = OperationAuthorizationImp
					.queryAllGroupAndOperation();
			if (role != null) {
				String opIds = role.getAuthorizedOpIds();
				if (opIds != null&&opIds.length()!=0) {
					String[] opStrings = opIds.split(",");
					List<String> authorizedOpIds = new ArrayList<String>(
							Arrays.asList(opStrings));
					ops = OperationAuthorizationImp
							.getOperationDisplayNames(authorizedOpIds);
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
		}
		StringBuffer msg = new StringBuffer("根据角色名查询角色");
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
	public String updateRole() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;
		
		String roleDesc=role.getDesc();
		role.setDesc(roleDesc.trim());
		role.setAuthorizedOpIds((String) roleManagerService
				.getOperationNames(ops));
		try {
			result = String.valueOf(roleManagerService.updateRole(role));
			groupAndOperations = OperationAuthorizationImp
					.queryAllGroupAndOperation();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("updateRole", role.getName(),
				resultForLog, message);

		StringBuffer msg = new StringBuffer("修改角色").append(role.getName());
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，角色已经不存在。");
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
	public String deleteRole() {
		int resultForLog = AlarmConstants.RESULT_SUC;
		String message = "";
		boolean isSuccess = true;
		String result = null;
		int resultForDel = -1;
		try {
			result = String.valueOf(roleManagerService.deleteRole(name));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			result = "-1";
			message = "数据库异常";
			resultForLog = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("deleteRole", name,
				resultForLog, message);

		StringBuffer msg = new StringBuffer("删除角色").append(name);
		switch (Integer.parseInt(result)) {
		case 2:
			msg.append("失败，角色已经不存在。");
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
		// String ret=JSON.toJSONString(ajaxObject);
		/*
		 * HttpServletResponse response = ServletActionContext.getResponse();
		 * try { response.setContentType("application/json; charset=utf-8");
		 * PrintWriter out = response.getWriter(); out.print(ret); out.flush();
		 * out.close(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		if (isSuccess) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public List<GroupAndOperation> getGroupAndOperations() {
		return groupAndOperations;
	}

	public void setGroupAndOperations(List<GroupAndOperation> groupAndOperations) {
		this.groupAndOperations = groupAndOperations;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOps(List<String> ops) {
		this.ops = ops;
	}

	public List<String> getOps() {
		return ops;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

}
