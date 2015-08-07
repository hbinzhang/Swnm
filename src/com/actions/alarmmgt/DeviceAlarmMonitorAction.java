package com.actions.alarmmgt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmQueryService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.linkagectl.impl.AlarmUIPushImp;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;
import common.page.AjaxObject;

/**
 * 设备告警监视
 * @author liyinghui
 *
 */
public class DeviceAlarmMonitorAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4552125577836636646L;

	private static final String LOGGER_NAME = DeviceAlarmMonitorAction.class
			.getName();
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：查询设备告警
	 * 含义：设备告警list
	 */
	private List<DeviceAlarm> deviceAlarmList;

	private IAlarmQueryService alarmQueryService;	
	private IOrganizationManagerService organManagerService;
	private IOperationLogService operationLogService;

    /**
	 * 来源：客户端下发给服务器
	 * 操作：处理设备告警
	 * 含义：告警编号
	 */
	private int alarmId;
	
	private Log log = LogFactory.getLog(this.getClass());

	private CommonBean commonBean;
	
	private AlarmUIPushImp alarmUiPushImp;
	
	private String alarmStrs;

	private AjaxObject ajaxObject;
		
	/**
	 * 查询未处理的设备告警
	 * @return String
	 */
	public String queryDeviceAlarm() {
		log.info("start queryDeviceAlarm!");
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		try{
			// 从session获取用户
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String userLevel = session.getLev();
				String organizationId = session.getOrganizationId();
				log.info("queryDeviceAlarm, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					DeviceAlarm dAlarm = new DeviceAlarm();
					// 如果用户是总公司用户，查询所有设备告警，如果是分公司用户，查询分公司下的告警
					// 如果是管理处用户，查询管理处下的告警
					if(!userLevel.equals(AlarmConstants.USER_HEADQUARTERS) &&
							!userLevel.equals(AlarmConstants.USER_BRANCH) &&
							!userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						message = "用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}else{				
						if(userLevel.equals(AlarmConstants.USER_BRANCH)){
							dAlarm.setBranchId(organizationId);	
							deviceAlarmList = (List<DeviceAlarm>)alarmQueryService.queryBranchDevAlarmByStatus(
									dAlarm);
						}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
							dAlarm.setDepartmentId(organizationId);	
							deviceAlarmList = (List<DeviceAlarm>)alarmQueryService.queryDepDevAlarmByStatus(
									dAlarm);
						}else{
							deviceAlarmList = (List<DeviceAlarm>)alarmQueryService.queryCompDevAlarmByStatus(
									dAlarm);
						}					
						String userId = session.getId();
						log.info("queryDeviceAlarm, userId = " + userId);
						log.info("start find orgName!");
						if(null != userId){
							List<Organization> orgList = organManagerService.queryOrganizationsByAccountId(userId);
							Map<String, String> orgMap = new HashMap<String, String>();						
							for(int j=0; j<orgList.size(); j++){
								orgMap.put(orgList.get(j).getOrgId(), orgList.get(j).getOrgNm());
							}
							for(DeviceAlarm deviceAlarm : deviceAlarmList){
								deviceAlarm.setBranchName(AlarmUtil.formatString(orgMap.get(
										deviceAlarm.getBranchId())));
								deviceAlarm.setDepartmentName(AlarmUtil.formatString(orgMap.get(
										deviceAlarm.getDepartmentId())));
							}
						}
						log.info("end find orgName!");
					}	
				}										
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}		
		}catch(Exception e){
			log.error("queryDeviceAlarm error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		if(result == AlarmConstants.RESULT_FAIL){
			message = "监视设备告警失败，" + message;
		}
		log.info("end queryDeviceAlarm!");
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}	
	
	/**
	 * 处理设备告警
	 * @return String
	 */
	/*public String handleDeviceAlarm() {
		log.info("start handleDeviceAlarm! alarmId = " + alarmId);
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		// 判断当前告警是否已经被处理
		DeviceAlarm dAlarm = (DeviceAlarm)alarmQueryService.queryDeviceAlarmById(alarmId);
		if(dAlarm.getAlarmStatus() == AlarmConstants.STATUS_HANDLED){
			message = "该条数据已经处理，请不要重复操作！";
			result = 2;
		}else{
			try{
				Session session = AlarmUtil.getLoginSession();
				if(null == session){
					message = "获取当前登录会话异常。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					DeviceAlarm deviceAlarm = new DeviceAlarm();
					deviceAlarm.setAlarmId(alarmId);
					deviceAlarm.setAlarmStatus(AlarmConstants.STATUS_HANDLED);
					deviceAlarm.setManagerTime(new Date());			
					deviceAlarm.setPeopleId(session.getId());
					log.info("handleDeviceAlarm, deviceAlarm = " + deviceAlarm);
					alarmQueryService.updateDeviceAlarm(deviceAlarm);
					log.info("start handleDeviceAlarm pullWarningEvent2GIS!");
					alarmUiPushImp.pullWarningEvent2GIS(session.getOrganizationId(),"del",
							"deviceAlarm", String.valueOf(alarmId));
					log.info("end handleDeviceAlarm pullWarningEvent2GIS!");
				}			
			}catch(Exception e){
				log.error("handleDeviceAlarm error!",e);
				message = "数据库异常。";
				result = AlarmConstants.RESULT_FAIL;
			}
			log.info("handleDeviceAlarm start createlog!");
			operationLogService.createOperationLog("handleDeviceAlarm", String.valueOf(
					alarmId), result, message);
			log.info("handleDeviceAlarm end createlog!");
			if(result == AlarmConstants.RESULT_FAIL){
				message = "处理设备告警失败，" + message;
			}
		}	
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}*/

	/**
	 * 批量处理设备告警
	 * @return String
	 */
	public String handleDeviceAlarm() {
		log.info("start handleDeviceAlarm4List! alarmStrs = " + alarmStrs);
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		StringBuffer operationObjects = new StringBuffer();
		List<Integer> handledList = new ArrayList<Integer>();
		StringBuffer failInfo = new StringBuffer();
		String[] strs = alarmStrs.split(",");
		if(strs != null && strs.length > 0 ){
			for(int i=0; i < strs.length; i++){
				String aId = strs[i];
				// 判断当前告警是否已经被处理
				DeviceAlarm dAlarm = (DeviceAlarm)alarmQueryService.queryDeviceAlarmById(
						Integer.parseInt(aId));
				if(dAlarm.getAlarmStatus() == AlarmConstants.STATUS_HANDLED){
					handledList.add(Integer.parseInt(aId));	
					failInfo.append(aId).append(",");
				}
			}
		}				
		log.info("handleDeviceAlarm4List! handledList = " + handledList);
		if(handledList.size() > 0 ){
			failInfo.deleteCharAt(failInfo.length()-1);
			message = "设备告警"+failInfo.toString()+"已经被处理，请不要重复操作！";
			result = 2;
		}else{						
			Session session = AlarmUtil.getLoginSession();
			if(null == session){
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}else{
				if(strs != null && strs.length > 0 ){
					for(int i=0; i < strs.length; i++){
						String aId = strs[i];
						try{
							DeviceAlarm deviceAlarm = new DeviceAlarm();
							deviceAlarm.setAlarmId(Integer.parseInt(aId));
							deviceAlarm.setAlarmStatus(AlarmConstants.STATUS_HANDLED);
							deviceAlarm.setManagerTime(new Date());			
							deviceAlarm.setPeopleId(session.getId());
						//	log.info("handleDeviceAlarm4List, deviceAlarm = " + deviceAlarm);
							alarmQueryService.updateDeviceAlarm(deviceAlarm);
						//	log.info("start handleDeviceAlarm4List pullWarningEvent2GIS!");
							alarmUiPushImp.pullWarningEvent2GIS(session.getOrganizationId(),"del",
									"deviceAlarm", String.valueOf(Integer.parseInt(aId)));
						//	log.info("end handleDeviceAlarm4List pullWarningEvent2GIS!");
							message = "处理成功！";
							operationObjects.append(aId).append(",");						
						}catch(Exception e){
							log.error("handleDeviceAlarm4List error! araId = " + aId, e);
							message = "数据库异常。";
							result = AlarmConstants.RESULT_FAIL;
						}
					}
				}
				if(operationObjects.length() > 0){
					operationObjects.deleteCharAt(operationObjects.length()-1);
				}
				operationLogService.createOperationLog("handleDeviceAlarm", operationObjects.toString(),
						result, message);
			}					
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "处理设备告警失败，" + message;
		}
		ajaxObject=new AjaxObject(result, message);
		log.info("end handleDeviceAlarm4List!");
		if(result == AlarmConstants.RESULT_SUC || result == 2){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public void setAlarmStrs(String alarmStrs) {
		this.alarmStrs = alarmStrs;
	}

	public List<DeviceAlarm> getDeviceAlarmList() {
		return deviceAlarmList;
	}

	public void setDeviceAlarmList(List<DeviceAlarm> deviceAlarmList) {
		this.deviceAlarmList = deviceAlarmList;
	}

	public IAlarmQueryService getAlarmQueryService() {
		return alarmQueryService;
	}

	public void setAlarmQueryService(IAlarmQueryService alarmQueryService) {
		this.alarmQueryService = alarmQueryService;
	}

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
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

	public AlarmUIPushImp getAlarmUiPushImp() {
		return alarmUiPushImp;
	}

	public void setAlarmUiPushImp(AlarmUIPushImp alarmUiPushImp) {
		this.alarmUiPushImp = alarmUiPushImp;
	}

	public String getAlarmStrs() {
		return alarmStrs;
	}

	public void setAlarmStrst(String alarmStrs) {
		this.alarmStrs = alarmStrs;
	}

}
