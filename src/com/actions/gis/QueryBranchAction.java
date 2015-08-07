/**
 * 
 */
package com.actions.gis;

import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.authmgt.Session;
import com.entity.gis.BranchInfo;
import com.entity.gis.WarningInfo4Security;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;

/**
 * 依据用户ID和机构ID，查询管理处所辖地理范围，决定用户登录时装载的地图范围
 * 
 * @author wyj
 * 
 */
public class QueryBranchAction extends ActionSupport {

	private static final long serialVersionUID = 7917364017281206511L;
	
	private Log log = LogFactory.getLog(this.getClass());

	public QueryBranchAction() {
		// TODO Auto-generated constructor stub
	}

	private IOrganizationManagerService orgService;//
	private InputStream resultJsonStream;

	private BranchInfo branchInfo;

	public String queryBranchLocationByOrgID() {
		ArrayList<WarningInfo4Security> queryResultArray = new ArrayList<WarningInfo4Security>();

		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");
			// String userLevel = session.getLev();
			String userID = session.getId();
			String orgId = session.getOrganizationId();
			// String organizationId = "1";
			// String userLevel = AlarmConstants.USER_BRANCH;
			log.info("QueryBranchAction, userID = " + userID);

			if (orgService.isCompanyByAccountId(userID)) {
				// branchInfo.setBranchId(orgId);
			} else if (orgService.isManagementByAccountId(userID)) {
				// branchInfo.setDepartmentId(orgId);
			} else {// 总公司，参数置空
				// branchInfo.setBranchId(null);
				// branchInfo.setDepartmentId(null);
			}
			log.info("QueryBranchAction, branch posi = "
					+ branchInfo.toString());
			
			// 查询结果映射
			branchInfo = null;
			branchInfo.setXmin(70.374747);
			branchInfo.setYmin(0.903097);
			branchInfo.setXmax(138.053125);
			branchInfo.setYmax(56.038121);

			
			return SUCCESS;
		} catch (Exception e) {
			log.error("QueryWarning4SecurityAction error!", e);
			// this.addActionError("数据库异常");
			return ERROR;
		}
	}



	public InputStream getResultJsonStream() {
		return resultJsonStream;
	}

	public void setResultJsonStream(InputStream resultJsonStream) {
		this.resultJsonStream = resultJsonStream;
	}

	public IOrganizationManagerService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganizationManagerService orgService) {
		this.orgService = orgService;
	}

}
