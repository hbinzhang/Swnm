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
import com.entity.efence.FenceBean;
import com.entity.gis.FenceInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.efence.IFenceService;

/**
 * @author wyj
 *
 */
public class QueryFenceAction extends ActionSupport {

	private static final long serialVersionUID = -2748163869645840825L;
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	public QueryFenceAction() {
		// TODO Auto-generated constructor stub
	}

	private InputStream resultJsonStream;// ���ؽ���� json �����

	private IFenceService fenceService;//
	private List<FenceInfo> fenceList4Ajax;
	// private AjaxObject errorObj;

	private IOrganizationManagerService orgService;//
	// private

	public String queryFenceByOrgID() {
		ArrayList<FenceInfo> queryResultArray = new ArrayList<FenceInfo>();
		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");
			String userID = session.getId();
			String orgId = session.getOrganizationId();
			// String userID = "1";
			// String orgId = "010000";

			log.info("QueryFenceAction, userID = " + userID);

			FenceBean paramFenceBean = null;

			if (orgService.isCompanyByAccountId(userID)) {// 分公司
				paramFenceBean = new FenceBean();
				paramFenceBean.setSubComID(orgId);
			} else if (orgService.isManagementByAccountId(userID)) {// 管理处
				paramFenceBean = new FenceBean();
				paramFenceBean.setMntMentID(orgId);
			} else {// 总公司，参数置空
			}
			log.info("QueryFenceAction, param = " + paramFenceBean);
			List<FenceBean> fenceList = (List<FenceBean>) fenceService
					.getFencesBySubComIdOrMngId(paramFenceBean);
			for (FenceBean fenceObj : fenceList) {
				FenceInfo tmpFence = new FenceInfo(fenceObj);
				tmpFence.setMntMentName((String) orgService
						.getOrgNmByOrgId(fenceObj.getMntMentID()));
				tmpFence.setBranchName((String) orgService
						.getOrgNmByOrgId(fenceObj.getSubComID()));
				if (tmpFence.getHostLat() != 0 && tmpFence.getHostLon() != 0)
					queryResultArray.add(tmpFence);
			}
			log.info("QueryFenceAction, count of mached data = "
					+ queryResultArray.size());

			// String jsonStr = JSON.toJSONString(queryResultArray);
			// resultJsonStream = new ByteArrayInputStream(jsonStr.getBytes());
			fenceList4Ajax = queryResultArray;
			// jsonStr./
			return SUCCESS;
		} catch (Exception e) {
			log.error("QueryFenceAction error!", e);
			fenceList4Ajax = new ArrayList<FenceInfo>();
			// errorObj = new AjaxObject(0, "");
			return ERROR;
		}
	}


	public InputStream getResultJsonStream() {
		return resultJsonStream;
	}

	public IFenceService getFenceService() {
		return fenceService;
	}

	public void setFenceService(IFenceService fenceService) {
		this.fenceService = fenceService;
	}

	public List<FenceInfo> getFenceList4Ajax() {
		return fenceList4Ajax;
	}

	public void setFenceList4Ajax(List<FenceInfo> fenceList4Ajax) {
		this.fenceList4Ajax = fenceList4Ajax;
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
	//
	// public AjaxObject getErrorObj() {
	// return errorObj;
	// }
	//
	// public void setErrorObj(AjaxObject errorObj) {
	// this.errorObj = errorObj;
	// }

}
