package com.actions.devmgt.sound.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.service.authmgt.IOrganizationManagerService;
import com.util.alarmmgt.AlarmConstants;

/**
 * 工具類
 * 
 * @author maming
 * 
 */
public class ContextUtil {

	public static List<CommonBean> getDepartmentsCanBeSeen(
			IOrganizationManagerService organManagerService) {
		List<CommonBean> branchList = new ArrayList<CommonBean>();
		List<CommonBean> departmentList = new ArrayList<CommonBean>();
		Session session = getLoginSession();
		if (null != session) {
			String userLevel = session.getLev();
			if (userLevel.equals(AlarmConstants.USER_HEADQUARTERS)) {
				// 如果用户是总公司, 获取所有分公司，有全部
				branchList = session.getOrgIdAndNames().getSubCompanys();
				for (CommonBean commonBean : branchList) {
					List<CommonBean> tempdepartmentList = organManagerService
							.getOrgIdAndOrgNmsFor2ByPOrgId(commonBean.getId());
					departmentList.addAll(tempdepartmentList);
				}
			} else if (userLevel.equals(AlarmConstants.USER_BRANCH)) {
				// 如果用户是分公司级别, 获取用户所在分公司和分公司下的所有管理处
				// 界面的分公司下拉列表（无全部）默认为用户的分公司，管理处有全部
				branchList = session.getOrgIdAndNames().getSubCompanys();
				departmentList = session.getOrgIdAndNames().getManagements();
			} else if (userLevel.equals(AlarmConstants.USER_DEPARTMENT)) {
				// 如果用户是管理处级别, 获取用户所在分公司和所在管理处，所有防区
				// 界面的分公司下拉列表默认为用户的分公司，管理处下拉列表默认为用户管理处，都无全部
				branchList = session.getOrgIdAndNames().getSubCompanys();
				departmentList = session.getOrgIdAndNames().getManagements();
			}
		}
		return departmentList;
	}

	public static Session getLoginSession() {
		Session session = null;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			session = (Session) httpSession.getAttribute("session");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return session;
	}

	/**
	 * 当前用户是否是总公司用户
	 * 
	 * @return
	 */
	public static boolean isHeadquarters() {
		Session session = getLoginSession();
		if (null != session) {
			String userLevel = session.getLev();
			if (userLevel.equals(AlarmConstants.USER_HEADQUARTERS)) {
				return true;
			}
		}
		return false;
	}
}
