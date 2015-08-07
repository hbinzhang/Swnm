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
import com.entity.efence.FenceBean;
import com.entity.gis.WarningInfo4Device;
import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmQueryService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.efence.IFenceService;
import com.service.zone.IZoneService;
import com.util.alarmmgt.AlarmConstants;


/**
 * @author wyj
 * 
 *         用户登录时初始化GIS，查询设备告警
 * 
 */
public class QueryWarning4DeviceAction extends ActionSupport {

	private static final long serialVersionUID = -6176613947499836697L;

	private Log log = LogFactory.getLog(this.getClass());

	public QueryWarning4DeviceAction() {
		// TODO Auto-generated constructor stub
	}

	private IZoneService securityZoneService;//
	private IAlarmQueryService warningService;//
	private IOrganizationManagerService orgService;//
	// private ICameraService cameraService;
	private IFenceService fenceService;//

	private InputStream resultJsonStream;//
	private List<WarningInfo4Device> deviceAlarmList4Ajax;

	// private AjaxObject errorObj;
	public String queryWarningNotProcessed() {

		ArrayList<WarningInfo4Device> queryResultArray = new ArrayList<WarningInfo4Device>();//

		try {
			// 从session获取用户
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession httpSession = request.getSession();
			Session session = (Session) httpSession.getAttribute("session");
			// String userLevel = session.getLev();
			String userID = session.getId();
			String orgId = session.getOrganizationId();
			// String userID = "1";
			// String orgId = "010000";

			log.info("QueryWarning4DeviceAction, userID = " + userID);

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
			log.info("queryDeviceAlarm, deviceAlarm = " + dAlarm);
			@SuppressWarnings("unchecked")
			List<DeviceAlarm> deviceAlarmList = (List<DeviceAlarm>) warningService
					.queryDeviceAlarmByStatus(dAlarm);

			// 查询结果映射
			WarningInfo4Device tmp = null;
			for(DeviceAlarm alarmObj:deviceAlarmList){
				tmp = new WarningInfo4Device(alarmObj);
				/* 1 高压电子围栏 2 振动光纤 3 一体化 4 智能分析服务器 */
				// 补充查找设备信息
				switch (alarmObj.getDeviceType().intValue()) {
				case 1:
				case 2:
				case 3: {
					FenceBean paramFenceBean = new FenceBean();
					paramFenceBean.setHostID(alarmObj.getDeviceId());
					FenceBean resultFenceBean = fenceService
							.getFenceByID(paramFenceBean);
					if (resultFenceBean != null) {
						try{
							if (resultFenceBean.getEfLatitude() != null) {
								tmp.setLat(Double.parseDouble(resultFenceBean
										.getEfLatitude()));
							}
							if (resultFenceBean.getEfLongitude() != null) {
								tmp.setLon(Double.parseDouble(resultFenceBean
										.getEfLongitude()));
							}
						}catch(NumberFormatException nfe){
							log.error("设备告警,编号：" + alarmObj.getDeviceId()
									+ "，告警关联的设备，编号："
									+ resultFenceBean.getDevID()
									+ "的地理坐标数据不是数字！");
						}
					}
					break;
				}
				}// eof switch
				if (tmp.getLat() != 0 && tmp.getLon() != 0)
					queryResultArray.add(tmp);
			}
			deviceAlarmList4Ajax = queryResultArray;
			// String jsonStr = JSON.toJSONString(queryResultArray);
			// resultJsonStream = new ByteArrayInputStream(jsonStr.getBytes());
			log.info("QueryWarning4DeviceAction, count of mached data = "
					+ deviceAlarmList4Ajax.size());

			return SUCCESS;
		} catch (Exception e) {
			log.error("QueryWarning4DeviceAction error!", e);
			deviceAlarmList4Ajax = new ArrayList<WarningInfo4Device>();
			// errorObj = new AjaxObject(0, "");
			// this.addActionError("数据库异常");
			return ERROR;
		}
	}

	public String queryWarningNotProcessedByID(String warningID) {
		return SUCCESS;
	}

	public IAlarmQueryService getWarningService() {
		return warningService;
	}

	public void setWarningService(IAlarmQueryService warningService) {
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

	public List<WarningInfo4Device> getDeviceAlarmList4Ajax() {
		return deviceAlarmList4Ajax;
	}

	public void setDeviceAlarmList4Ajax(
			List<WarningInfo4Device> deviceAlarmList4Ajax) {
		this.deviceAlarmList4Ajax = deviceAlarmList4Ajax;
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

	public IFenceService getFenceService() {
		return fenceService;
	}

	public void setFenceService(IFenceService fenceService) {
		this.fenceService = fenceService;
	}

}
