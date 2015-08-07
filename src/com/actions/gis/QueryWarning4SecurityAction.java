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

import com.entity.alarmmgt.DeviceAlarm;
import com.entity.authmgt.Session;
import com.entity.gis.WarningInfo4Security;
import com.entity.linkagectl.UIMCSecurityAlarm;
import com.entity.zone.ZoneBean;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.efence.IFenceService;
import com.service.linkagectl.IGetSeAlarm;
import com.service.zone.IZoneService;
import com.util.alarmmgt.AlarmConstants;

/**
 * @author wyj
 *
 */
public class QueryWarning4SecurityAction extends ActionSupport {

	private static final long serialVersionUID = 7917364017281206511L;
	
	private Log log = LogFactory.getLog(this.getClass());

	public QueryWarning4SecurityAction() {
		// TODO Auto-generated constructor stub
	}


	private IGetSeAlarm warningService;
	private IOrganizationManagerService orgService;//
	private IZoneService securityZoneService;//
	private IFenceService fenceService;//

	private InputStream resultJsonStream;
	private ArrayList<WarningInfo4Security> securityAlarmList4Ajax;

	// private AjaxObject errorObj;

	public String queryWarningNotProcessed() {
		ArrayList<WarningInfo4Security> queryResultArray = new ArrayList<WarningInfo4Security>();

		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");

			String userID = session.getId();
			String orgId = session.getOrganizationId();
			// String userID = "1";
			// String orgId = "010000";

			log.info("QueryWarning4SecurityAction, userID = " + userID);

			DeviceAlarm dAlarm = new DeviceAlarm();
			dAlarm.setAlarmStatus(AlarmConstants.STATUS_NOT_HANDLED);
			// 如果用户是总公司用户，查询所有设备告警，如果是分公司用户，查询分公司下的告警
			// 如果是管理处用户，查询管理处下的告警
			if (orgService.isCompanyByAccountId(userID)) {
				dAlarm.setBranchId(orgId);
			} else if (orgService.isManagementByAccountId(userID)) {
				dAlarm.setDepartmentId(orgId);
			} else {// 总公司，参数置空
				dAlarm.setBranchId(null);
				dAlarm.setDepartmentId(null);
			}
			log.info("QueryWarning4SecurityAction, deviceAlarm = " + dAlarm);
			List<UIMCSecurityAlarm> securityAlarmList = (List<UIMCSecurityAlarm>) warningService
					.findAllUIMcAlarmInfo(orgId, userID);
			
			// 查询结果映射
			WarningInfo4Security tmp = null;
			ZoneBean tmpZoneInfo = null;
			for (UIMCSecurityAlarm alarmObj : securityAlarmList) {
				tmp = new WarningInfo4Security(alarmObj);

				// 查找防区坐标
				tmpZoneInfo = securityZoneService.getZoneByID(tmp
						.getAlarmZoneID());
				if (tmpZoneInfo != null) {
					try {
						if (tmpZoneInfo.getStartLon() != null) {
							tmp.setZoneStartLon(Double.parseDouble(tmpZoneInfo
									.getStartLon()));
						}
						if (tmpZoneInfo.getStartLat() != null) {
							tmp.setZoneStartLat(Double.parseDouble(tmpZoneInfo
									.getStartLat()));
						}
						if (tmpZoneInfo.getEndLon() != null) {
							tmp.setZoneEndLon(Double.parseDouble(tmpZoneInfo
									.getEndLon()));
						}
						if (tmpZoneInfo.getEndLat() != null) {
							tmp.setZoneEndLat(Double.parseDouble(tmpZoneInfo
									.getEndLat()));
						}
					} catch (NumberFormatException nfe) {
						log.error("安防告警,编号：" + alarmObj.getAlarmID() + "，告警关联的设备，编号："+ alarmObj.getAlarmZoneID() + "的地理坐标数据不是数字！");
					}

				}
				// 清除无效数据
				if (tmp.getZoneStartLat() != 0 && tmp.getZoneStartLon() != 0 && tmp.getZoneEndLat() != 0 && tmp.getZoneEndLon() != 0)
					queryResultArray.add(tmp);
			}
			securityAlarmList4Ajax = queryResultArray;
			// String jsonStr = JSON.toJSONString(queryResultArray);
			// resultJsonStream = new ByteArrayInputStream(jsonStr.getBytes());
			// jsonStr./
			log.info("QueryWarning4SecurityAction, count of mached data = "
					+ securityAlarmList4Ajax.size());

			return SUCCESS;
		} catch (Exception e) {
			log.error("QueryWarning4SecurityAction error!", e);
			securityAlarmList4Ajax = new ArrayList<WarningInfo4Security>();
			// errorObj = new AjaxObject(0, "");
			// this.addActionError("数据库异常");
			return ERROR;
		}
	}

	public IGetSeAlarm getWarningService() {
		return warningService;
	}

	public void setWarningService(IGetSeAlarm warningService) {
		this.warningService = warningService;
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

	//
	// public AjaxObject getErrorObj() {
	// return errorObj;
	// }
	//
	// public void setErrorObj(AjaxObject errorObj) {
	// this.errorObj = errorObj;
	// }

	public IZoneService getSecurityZoneService() {
		return securityZoneService;
	}

	public void setSecurityZoneService(IZoneService securityZoneService) {
		this.securityZoneService = securityZoneService;
	}

	public ArrayList<WarningInfo4Security> getSecurityAlarmList4Ajax() {
		return securityAlarmList4Ajax;
	}

	public void setSecurityAlarmList4Ajax(
			ArrayList<WarningInfo4Security> securityAlarmList4Ajax) {
		this.securityAlarmList4Ajax = securityAlarmList4Ajax;
	}

	public IFenceService getFenceService() {
		return fenceService;
	}

	public void setFenceService(IFenceService fenceService) {
		this.fenceService = fenceService;
	}

}
