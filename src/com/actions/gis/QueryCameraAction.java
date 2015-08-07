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
import com.entity.gis.CameraInfo;
import com.entity.videomonitor.TVmIpc;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.videomonitor.IGetCamerasByUserID;

/**
 * @author wyj
 *
 */
public class QueryCameraAction extends ActionSupport {

	private static final long serialVersionUID = 4395188312500108659L;
	private Log log = LogFactory.getLog(this.getClass());

	private IGetCamerasByUserID cameraService;//
	private IOrganizationManagerService orgService;//

	private InputStream resultJsonStream;// ���ؽ���ￄ1�7 json ����ￄ1�7
	private List<CameraInfo> cameraList4Ajax;

	// private AjaxObject errorObj;

	/**
	 * 
	 */
	public QueryCameraAction() {
		// TODO Auto-generated constructor stub
	}

	public String queryCameraByOrgID() {
		ArrayList<CameraInfo> queryResultArray = new ArrayList<CameraInfo>();
		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");
			// String userLevel = session.getLev();
			String userID = session.getId();
			// String userID = "1";
			log.info("QueryWarning4DeviceAction, userID = " + userID);

			List<TVmIpc> resultList = cameraService.getCamerasByUserID(userID);
			if(resultList != null)
			for (TVmIpc fenceObj : resultList) {
				CameraInfo tmpCamInfo = new CameraInfo(fenceObj);
				tmpCamInfo.setMntName((String) orgService
						.getOrgNmByOrgId(fenceObj.getManagementagency()));
				tmpCamInfo.setOrgName((String) orgService
						.getOrgNmByOrgId(fenceObj.getBranch()));
				if (tmpCamInfo.getLat() != 0 && tmpCamInfo.getLon() != 0)
					queryResultArray.add(tmpCamInfo);
			}
			cameraList4Ajax = queryResultArray;
			// String jsonStr = JSON.toJSONString(queryResultArray);
			// resultJsonStream = new ByteArrayInputStream(jsonStr.getBytes());
			// jsonStr./
			log.info("QueryCameraAction, count of mached data = "
					+ cameraList4Ajax.size());

			return SUCCESS;
		} catch (Exception e) {
			log.error("QueryCameraAction error!", e);
			cameraList4Ajax = new ArrayList<CameraInfo>();
			// errorObj = new AjaxObject(0, "");
			return ERROR;
		}

	}


	public InputStream getResultJsonStream() {
		return resultJsonStream;
	}

	public IOrganizationManagerService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrganizationManagerService orgService) {
		this.orgService = orgService;
	}

	public List<CameraInfo> getCameraList4Ajax() {
		return cameraList4Ajax;
	}

	public void setCameraList4Ajax(List<CameraInfo> cameraList4Ajax) {
		this.cameraList4Ajax = cameraList4Ajax;
	}

	//
	// public AjaxObject getErrorObj() {
	// return errorObj;
	// }
	//
	// public void setErrorObj(AjaxObject errorObj) {
	// this.errorObj = errorObj;
	// }

	public void setResultJsonStream(InputStream resultJsonStream) {
		this.resultJsonStream = resultJsonStream;
	}

	public IGetCamerasByUserID getCameraService() {
		return cameraService;
	}

	public void setCameraService(IGetCamerasByUserID cameraService) {
		this.cameraService = cameraService;
	}

}
