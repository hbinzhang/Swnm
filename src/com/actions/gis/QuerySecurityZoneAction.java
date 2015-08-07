/**
 * 
 */
package com.actions.gis;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.authmgt.Session;
import com.entity.gis.SecurityZoneInfo;
import com.entity.zone.ZoneBean;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.zone.IZoneService;

/**
 * @author wyj
 *
 */
public class QuerySecurityZoneAction extends ActionSupport {

	private static final long serialVersionUID = -8430562428807364611L;
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	public QuerySecurityZoneAction() {
		// TODO Auto-generated constructor stub
	}

	private IZoneService securityZoneService;//
	private IOrganizationManagerService orgService;//

	private InputStream resultJsonStream;//
	private List<SecurityZoneInfo> securityZoneList4Ajax;

	// private AjaxObject errorObj;

	public String querySecurityZoneByOrgID() {

		ArrayList<SecurityZoneInfo> queryResultArray = new ArrayList<SecurityZoneInfo>();// ���صĲ�ѯ���

		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");
			// Session session = null;
			// String userLevel = session.getLev();
			String userID = session.getId();
			String orgId = session.getOrganizationId();
			// String organizationId = "1";
			// String userLevel = AlarmConstants.USER_BRANCH;

			log.info("QuerySecurityZoneAction, userID = " + userID);

			ZoneBean paramZoneBean = null;

			if (orgService.isCompanyByAccountId(userID)) {// 分公司
				paramZoneBean = new ZoneBean();
				paramZoneBean.setBranchID(orgId);
			} else if (orgService.isManagementByAccountId(userID)) {// 管理处
				paramZoneBean = new ZoneBean();
				paramZoneBean.setMgtID(orgId);
			} else {// 总公司，参数置空
			}
			log.info("QuerySecurityZoneAction, param = " + paramZoneBean);
			List<ZoneBean> securityZoneList = (List<ZoneBean>) securityZoneService
					.getZonesByBranchIdOrMngId(paramZoneBean);
			for (ZoneBean zoneObj : securityZoneList) {
				SecurityZoneInfo tmpZone = new SecurityZoneInfo(zoneObj);
				if (tmpZone.getStartLat() != 0 && tmpZone.getStartLon() != 0)
					tmpZone.setMgtName((String)orgService.getOrgNmByOrgId(zoneObj.getMgtID()));
					queryResultArray.add(tmpZone);
			}
			securityZoneList4Ajax = queryResultArray;
			// String jsonStr = JSON.toJSONString(queryResultArray);
			// resultJsonStream = new ByteArrayInputStream(jsonStr.getBytes());
			log.info("QuerySecurityZoneAction, count of mached data = "
					+ securityZoneList4Ajax.size());
			return SUCCESS;
		} catch (Exception e) {
			log.error("QuerySecurityZoneAction error!", e);
			securityZoneList4Ajax = new ArrayList<SecurityZoneInfo>();
//			errorObj = new AjaxObject(0, "message:error");
			return ERROR;
		}
	}

	public IZoneService getSecurityZoneService() {
		return securityZoneService;
	}

	public void setSecurityZoneService(IZoneService securityZoneService) {
		this.securityZoneService = securityZoneService;
	}

	public IOrganizationManagerService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganizationManagerService orgService) {
		this.orgService = orgService;
	}

	public void setResultJsonStream(InputStream resultJsonStream) {
		this.resultJsonStream = resultJsonStream;
	}

	public InputStream getResultJsonStream() {
		return resultJsonStream;
	}

	public List<SecurityZoneInfo> getSecurityZoneList4Ajax() {
		return securityZoneList4Ajax;
	}

	public void setSecurityZoneList4Ajax(
			List<SecurityZoneInfo> securityZoneList4Ajax) {
		this.securityZoneList4Ajax = securityZoneList4Ajax;
	}
	//
	// public AjaxObject getErrorObj() {
	// return errorObj;
	// }
	//
	// public void setErrorObj(AjaxObject errorObj) {
	// this.errorObj = errorObj;
	// }

}
/*
<action name="text-result" class="actions.QuerySecurityZone">
<result type="stream">
<param name="contentType">text/html</param>
<param name="securityZone">resultJsonStream</param>
</result>
</action>
*/