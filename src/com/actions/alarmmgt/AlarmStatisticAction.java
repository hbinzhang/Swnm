package com.actions.alarmmgt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.entity.CommonBean;
import com.entity.alarmmgt.AlarmStatisticCondition;
import com.entity.alarmmgt.AlarmStatisticInfo;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmQueryService;
import com.service.alarmmgt.IAlarmStatisticService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;
import common.page.Pager;

/**
 * 告警统计
 * @author liyinghui
 *
 */
public class AlarmStatisticAction  extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -113381156634119646L;

	private IAlarmStatisticService alarmStatisticService;
	private IAlarmQueryService alarmQueryService;
	private IOrganizationManagerService organManagerService;
	private IOperationLogService operationLogService;

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 来源：服务器返回给客户端
	 * 操作：统计告警
	 * 含义：分公司list(下拉列表)，CommonBean中id是标识，name是名称，
	 * 如果是总公司用户，则界面下拉列表带“全部”，否则默认为返回的list的值（就一个值），不带“全部”
	 */
	private List<CommonBean> branchList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：统计告警
	 * 含义：管理处list(下拉列表) 
	 * 如果是总公司或分公司用户，则界面下拉列表带“全部”，否则默认为返回的list的值（就一个值），不带“全部”
	 */
	private List<CommonBean> departmentList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：统计告警
	 * 含义： 防区list(下拉列表) 
	 */
	private List<CommonBean> zoneList;
	
	/**
	 * 来源：服务器返回给客户端或客户端下发给服务器
	 * 操作：初始化告警统计界面或统计告警
	 * 含义：告警类型 ，1 安防告警，2 设备告警
	 */
	private int type;
	
	/**
	 * 来源：客户端下发给服务器
	 * 操作：统计告警
	 * 含义：时间粒度，必选一个
	 * DD 天
	 * DY 周
	 * MM 月
	 * Q  季度
	 * YY 年
	 */
	private String timeGranularity;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化告警统计界面
	 * 含义：用户所属机构级别
	 * 0 总公司
	 * 1 分公司
	 * 2 管理处
	 */
	private String userLevel;	
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：导出告警
	 * 含义：文件名，如www.xls或www.xlsx
	 */
	private String fileName;	
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：导出告警
	 * 含义：文件流
	 */
	private InputStream inputStream;
	
	/**
	 * 来源：客户端传给服务器
	 * 操作：导出告警
	 * 含义：文件版本
	 * 1:2003版本
	 * 2:2007版本
	 */
//	private int fileVer;
	
	private String jsonStr;
	
	public static final int step = 10;

	private CommonBean commonBean;


	private AjaxObject ajaxObject;
	
	private AlarmStatisticCondition alarmStatisticCondition;

	
	/**
	 * 初始化告警统计条件
	 */
	public String initStatisticAlarm(){	
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		type = AlarmConstants.TYPE_SECURITY_ALARM;
		timeGranularity = AlarmConstants.TIME_GRANU_DATE;		
		try{
			// 从session获取用户
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				userLevel = session.getLev();
				log.info("initStatisticAlarm, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						// 如果用户是总公司, 获取所有分公司，管理处，有全部			
						branchList = session.getOrgIdAndNames().getSubCompanys();
					}else if(userLevel.equals( AlarmConstants.USER_BRANCH)){				
						// 如果用户是分公司级别, 获取用户所在分公司和分公司下的所有管理处	
						// 界面的分公司下拉列表（无全部）默认为用户的分公司，管理处有全部
						branchList = session.getOrgIdAndNames().getSubCompanys();
						departmentList = session.getOrgIdAndNames().getManagements();
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果用户是管理处级别, 获取用户所在分公司和所在管理处，
						// 界面的分公司下拉列表默认为用户的分公司，管理处下拉列表默认为用户管理处，都无全部
						branchList = session.getOrgIdAndNames().getSubCompanys();
						departmentList = session.getOrgIdAndNames().getManagements();
						zoneList = (List<CommonBean>)alarmQueryService.queryZoneByDepartId(departmentList.
								get(0).getId());	
					}else{
						message = "获取用户所属机构级别错误。";
						result = AlarmConstants.RESULT_FAIL;
					}				
				}
			}else{
				message = "获取当前登录会话异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}catch(Exception e){
			log.error("initStatisticAlarm error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化告警统计分析条件失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
	private void setPageInfo(Pager pager, int totalCount, AlarmStatisticCondition alarmStatisticCondition){
		int start = 0;
		int end = 100;
		int totalPage = 0;
		int offset = alarmStatisticCondition.getOffset();
		if (totalCount == 0) {
			totalPage = 0;
		} else {		
			// 计算总页数
			totalPage = (totalCount + step - 1) / step;
			// 如果当前页为负数或0，则按第一页处理，超过总页数时，按最后一页处理
			if (offset <= 0) {
				offset = 1;
			} else if (offset > totalPage) {
				offset = totalPage;
			}
			start = (offset-1)*step + 1;
			end = start + step;
		}
		log.info("setPageInfo, start = " + start + 
				", end = " + end);
		alarmStatisticCondition.setStart(start);
		alarmStatisticCondition.setEnd(end);
		pager.setOffset(offset);
		pager.setPages(totalPage);
		pager.setSize(step);
		pager.setTotal(totalCount);
	}
	
	/**
	 * 告警统计分析
	 */
	public String statisticAlarm(){		
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		/*AlarmStatisticCondition alarmStatisticCondition = JSON.parseObject(jsonStr,
				AlarmStatisticCondition.class);  */
		alarmStatisticCondition.setGroupByStr(getGroupStr(alarmStatisticCondition));
		alarmStatisticCondition.setColumnStr(getColumnStr(alarmStatisticCondition));
		alarmStatisticCondition.setWhereStr(getWhereStr(alarmStatisticCondition));
		alarmStatisticCondition.setSqlStr(getSqlStr(alarmStatisticCondition));
		log.info("statisticAlarm, alarmStatisticCondition = "  + alarmStatisticCondition);
		Pager pager = new Pager();
		int totalCount = 0;
		List<Organization> branchList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_BRANCH);
		List<Organization> departmentList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_DEPARTMENT);
		List<CommonBean> zoneList = (List<CommonBean>)alarmQueryService.queryZoneInfos();
		List<AlarmStatisticInfo> alarmStatisticList = new ArrayList<AlarmStatisticInfo>();
		if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
			try{
				totalCount = alarmStatisticService.getStatisSecAlarmCount(alarmStatisticCondition);
				setPageInfo(pager, totalCount, alarmStatisticCondition);
				alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticSecurityAlarm(
						alarmStatisticCondition);
				// 根据分公司id，管理处id，防区id查询分公司名称，管理处，防区名称，权限接口提供
				for(AlarmStatisticInfo securityAlarm : alarmStatisticList){
					List<Integer> staticList = alarmStatisticCondition.getStaticGranularity();
					if(null != staticList && staticList.size() > 0){
						for(int staticGran : staticList){
							if(staticGran == AlarmConstants.STATIC_GRANU_BRANCH){
								for(Organization cb:branchList){
									if(cb.getOrgId().equals(securityAlarm.getBranchId())){
										securityAlarm.setBranchName(AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}
							if(staticGran == AlarmConstants.STATIC_GRANU_ZONE){
								String zoneName = "";
								if(null != securityAlarm.getZoneId()){
									for(CommonBean cb:zoneList){
										if(cb.getId().equals(String.valueOf(securityAlarm.getZoneId()))){
											zoneName = cb.getName();
											break;
										}
									}
									// 如果防区表里没有告警的防区，则显示防区id
									if("".equals(zoneName)){
										zoneName = String.valueOf(securityAlarm.getZoneId());
									}
								}
								securityAlarm.setZoneName(zoneName);
							}
							if(staticGran == AlarmConstants.STATIC_GRANU_DEPARTMENT){
								for(Organization cb:departmentList){
									if(cb.getOrgId().equals(securityAlarm.getDepartmentId())){
										securityAlarm.setDepartmentName(AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}	
						}	
					}											
				}
				pager.setDatas(alarmStatisticList);
			}catch(Exception e){
				log.error("statisticAlarm error!",e);
				message = "数据库异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			try{
				totalCount = alarmStatisticService.getStatisDevAlarmCount(alarmStatisticCondition);
				setPageInfo(pager, totalCount, alarmStatisticCondition);
				alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticDeviceAlarm(
						alarmStatisticCondition);
				for(AlarmStatisticInfo deviceAlarm : alarmStatisticList){
					List<Integer> staticList = alarmStatisticCondition.getStaticGranularity();
					if(null != staticList && staticList.size() > 0){
						for(int staticGran : staticList){
							if(staticGran == AlarmConstants.STATIC_GRANU_BRANCH){
								for(Organization cb:branchList){
									if(cb.getOrgId().equals(deviceAlarm.getBranchId())){
										deviceAlarm.setBranchName(AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}
							if(staticGran == AlarmConstants.STATIC_GRANU_ZONE){
								String zoneName = "";
								if(null != deviceAlarm.getZoneId()){
									for(CommonBean cb:zoneList){
										if(cb.getId().equals(String.valueOf(deviceAlarm.getZoneId()))){
											zoneName = cb.getName();
											break;
										}
									}
									// 如果防区表里没有告警的防区，则显示防区id
									if("".equals(zoneName)){
										zoneName = String.valueOf(deviceAlarm.getZoneId());
									}
								}
								deviceAlarm.setZoneName(zoneName);
							}
							if(staticGran == AlarmConstants.STATIC_GRANU_DEPARTMENT){
								for(Organization cb:departmentList){
									if(cb.getOrgId().equals(deviceAlarm.getDepartmentId())){
										deviceAlarm.setDepartmentName(AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}	
						}		
					}
				}
				pager.setDatas(alarmStatisticList);
			}catch(Exception e){
				log.error("statisticAlarm error!",e);
				message = "数据库异常。";
				result = AlarmConstants.RESULT_FAIL;
			}	
		}else{
			message = "告警类型错误。";
			result = AlarmConstants.RESULT_FAIL;
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(pager);
		if(null != message && message.length() > 0){
			ajaxObject.setMessage("统计分析告警失败，" + message);
		}
		log.info("statisticAlarm, ajaxObject = " + ajaxObject);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}		
	}
	
/*	private AlarmStatisticCondition getCondition(){
		AlarmStatisticCondition alarmStatisticCondition = new  AlarmStatisticCondition();
		Calendar cal = Calendar.getInstance();
		condition_startTime = new Date();
		condition_endTime = new Date();
		condition_startTime.setDate(1);
		condition_startTime.setHours(1);
		condition_startTime.setMinutes(1);
		condition_startTime.setSeconds(1);
		condition_startTime.setMonth(0);
		cal.setTime(condition_startTime);
		Calendar cal2 = Calendar.getInstance();
		condition_endTime.setHours(1);
		condition_endTime.setMinutes(1);
		condition_endTime.setSeconds(1);
		condition_endTime.setDate(1);
		condition_endTime.setMonth(3);

		cal2.setTime(condition_endTime);
		zoneId = -1;
		departmentId = null;
		branchId = null;
		levelId = -1;
		deviceTypeId = 1;
		statisticGranuList = new ArrayList();
		statisticGranuList.add(AlarmConstants.STATIC_GRANU_BRANCH);
		statisticGranuList.add(AlarmConstants.STATIC_GRANU_DEVICE_TYPE);
		statisticGranuList.add(AlarmConstants.STATIC_GRANU_ALARM_LEVEL);

		timeGranularity = AlarmConstants.TIME_GRANU_DATE;
		
	//	alarmStatisticCondition.setGroupByStr(getGroupStr());
	//	alarmStatisticCondition.setColumnStr(getColumnStr());
	//	alarmStatisticCondition.setWhereStr(getWhereStr());
	//	alarmStatisticCondition.setSqlStr(getSqlStr());
	//	alarmStatisticCondition.setBranchId(branchId);
	//	alarmStatisticCondition.setDepartmentId(departmentId);
	//	alarmStatisticCondition.setDeviceTypeId(deviceTypeId);
		alarmStatisticCondition.setBeginTime(condition_startTime);
		alarmStatisticCondition.setEndTime(condition_endTime);
	//	alarmStatisticCondition.setLevelId(levelId);
		alarmStatisticCondition.setTimeGranularity(timeGranularity);
	//	alarmStatisticCondition.setZoneId(zoneId);
		alarmStatisticCondition.setType(type);
		return alarmStatisticCondition;
	}*/
	
	private String getColumnStr(AlarmStatisticCondition alarmStatisticCondition){
		List<Integer> statisticGranuList = alarmStatisticCondition.getStaticGranularity();
		StringBuffer s = new StringBuffer();
		s.append(" b.BEGINTIME,");
		s.append(" b.ENDTIME,");
		if(null != statisticGranuList && statisticGranuList.size() > 0){		
			for(int statisticGranu : statisticGranuList){
				if(statisticGranu == AlarmConstants.STATIC_GRANU_BRANCH){
					s.append(" b.BRANCHID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ZONE){
					s.append(" b.ZONEID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
					s.append(" b.DEVICETYPE,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
					s.append(" b.DEPARTMENTID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){	
					if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
						s.append(" b.CHECKLEVEL ALARMLEVEL,");					
					}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
						s.append(" b.ALARMLEVEL,");					
					}
				}
			}
		}
		if(!s.toString().contains("BRANCHID")){
			s.append(" 0 BRANCHID,");
		}
		if(!s.toString().contains("ZONEID")){
			s.append(" 1 ZONEID,");
		}
		if(!s.toString().contains("DEVICETYPE")){
			s.append(" 2 DEVICETYPE,");
		}
		if(!s.toString().contains("DEPARTMENTID")){
			s.append(" 3 DEPARTMENTID,");
		}
		if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
			if(!s.toString().contains("CHECKLEVEL")){
				s.append(" 4 ALARMLEVEL,");
			}
		}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			if(!s.toString().contains("ALARMLEVEL")){
				s.append(" 5 ALARMLEVEL,");
			}
		}	
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}	
	
	private String getWhereStr(AlarmStatisticCondition alarmStatisticCondition){
		StringBuffer s = new StringBuffer();
		List<Integer> statisticGranuList = alarmStatisticCondition.getStaticGranularity();
		if(null != statisticGranuList && statisticGranuList.size() > 0){		
			for(int statisticGranu : statisticGranuList){
				if(statisticGranu == AlarmConstants.STATIC_GRANU_BRANCH){
					s.append(" AND b.BRANCHID=c.BRANCHID(+) ");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ZONE){
					s.append(" AND (NVL(b.ZONEID, -1)= NVL(c.ZONEID(+), -1)) ");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
					s.append(" AND b.DEVICETYPE=c.DEVICETYPE(+) ");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
					s.append(" AND b.DEPARTMENTID=c.DEPARTMENTID(+) ");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){	
					if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
						s.append(" AND b.CHECKLEVEL=c.CHECKLEVEL(+) ");					
					}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
						s.append(" AND b.ALARMLEVEL=c.ALARMLEVEL(+) ");					
					}
				}
			}
		}
		return s.toString();
	}	
	
	private String getSqlStr(AlarmStatisticCondition alarmStatisticCondition){
		StringBuffer s = new StringBuffer();
		List<Integer> statisticGranuList = alarmStatisticCondition.getStaticGranularity();
		String branchId = alarmStatisticCondition.getBranchId();
		String departmentId = alarmStatisticCondition.getDepartmentId();
		int zoneId = alarmStatisticCondition.getZoneId();
		int levelId = alarmStatisticCondition.getLevelId();
		int deviceTypeId = alarmStatisticCondition.getDeviceTypeId();	
		if(null != statisticGranuList && statisticGranuList.size() > 0){	
			// 分组只有分公司
			if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID from T_ALM_DEVALARM)ra");
					}
				}
			}else if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分组是分公司，管理处
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,DEPARTMENTID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,DEPARTMENTID  from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,DEPARTMENTID  from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,DEPARTMENTID  from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,DEPARTMENTID  from T_ALM_DEVALARM)ra");
					}
				}
			}else if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分组是分公司，管理处，防区
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,DEPARTMENTID,ZONEID from T_ALM_DEVALARM)ra");
					}	
				}			
			}else if(!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分组是管理处
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct DEPARTMENTID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct DEPARTMENTID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct DEPARTMENTID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct DEPARTMENTID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct DEPARTMENTID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct DEPARTMENTID from T_ALM_DEVALARM)ra");
					}
				}
			}else if(!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
						statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
						statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分组是管理处，防区
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct DEPARTMENTID,ZONEID from T_ALM_DEVALARM)ra");
					}
				}
			}else if(!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分组防区
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct ZONEID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct ZONEID from T_ALM_DEVALARM)ra");
					}
				}
			}else if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_BRANCH) &&
					!statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEPARTMENT)&&
					statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ZONE)){
				// 分公司，防区分组
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){				
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,ZONEID from T_ALM_SECURITYALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,ZONEID from T_ALM_SECURITYALARM)ra");
					}
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(null != branchId && !branchId.equals("")){	
						if(null != departmentId && !departmentId.equals("")){
							if(zoneId != -1){
								// 分公司有值，管理处有值，防区有值
								s.append(", (select distinct BRANCHID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("' and ZONEID = ").append(zoneId).append(")ra");
							}else{
								// 分公司有值，管理处有值，防区没值
								s.append(", (select distinct BRANCHID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("'" +
										" and DEPARTMENTID = ").append("'").append(departmentId).append("')ra");
							}
						}else{
							// 分公司有值，管理处没值，防区没值
							s.append(", (select distinct BRANCHID,ZONEID from T_ALM_DEVALARM where BRANCHID = ").append("'").append(branchId).append("')ra");
						}
					}else{
						// 分公司没值，管理处没值，防区没值
						s.append(", (select distinct BRANCHID,ZONEID from T_ALM_DEVALARM)ra");
					}
				}
			}
			// 只有设备类型，无告警级别
			if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEVICE_TYPE) && !statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ALARM_LEVEL)){
				if(deviceTypeId != -1){
					s.append(", (select distinct DEVICETYPE from T_ALM_KNOWLEDGE where DEVICETYPE = ").append(deviceTypeId).append(") rd");
				}else{
					s.append(", (select distinct DEVICETYPE from T_ALM_KNOWLEDGE) rd");
				}
			}
			// 只有告警级别，无设备类型
			if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ALARM_LEVEL) && !statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEVICE_TYPE)){	
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
					if(levelId != -1){
						s.append(", (select distinct CHECKLEVEL from T_ALM_SECURITYALARM where CHECKLEVEL = ").append(levelId).append(") re");

					}else{
						s.append(", (select distinct CHECKLEVEL from T_ALM_SECURITYALARM) re");
					}					
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(levelId != -1){
						s.append(", (select distinct ALARMLEVEL from T_ALM_KNOWLEDGE where ALARMLEVEL = ").append(levelId).append(") re");

					}else{
						s.append(", (select distinct ALARMLEVEL from T_ALM_KNOWLEDGE) re");
					}	
				}
			}	
			// 既有告警级别，又有设备类型
			if(statisticGranuList.contains(AlarmConstants.STATIC_GRANU_DEVICE_TYPE) && statisticGranuList.contains(AlarmConstants.STATIC_GRANU_ALARM_LEVEL)){
				if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
					if(deviceTypeId != -1){
						s.append(", (select distinct DEVICETYPE from T_ALM_KNOWLEDGE where DEVICETYPE = ").append(deviceTypeId).append(") rd");
					}else{
						s.append(", (select distinct DEVICETYPE from T_ALM_KNOWLEDGE) rd");
					}
					if(levelId != -1){
						s.append(", (select distinct CHECKLEVEL from T_ALM_SECURITYALARM where CHECKLEVEL = ").append(levelId).append(") re");
					}else{
						s.append(", (select distinct CHECKLEVEL from T_ALM_SECURITYALARM) re");
					}					
				}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					if(levelId != -1 && deviceTypeId != -1){
						s.append(", (select distinct DEVICETYPE, ALARMLEVEL from T_ALM_KNOWLEDGE where ALARMLEVEL is not null and ALARMTYPE=2 and ALARMLEVEL = ").append(levelId).append(
								" and DEVICETYPE = ").append(deviceTypeId).append(") re");
					}else if (levelId != -1 && deviceTypeId == -1){
						s.append(", (select distinct DEVICETYPE, ALARMLEVEL from T_ALM_KNOWLEDGE where ALARMLEVEL is not null and ALARMTYPE=2 and ALARMLEVEL = ").append(levelId).append(") re");
					}else if (levelId == -1 && deviceTypeId != -1){
						s.append(", (select distinct DEVICETYPE, ALARMLEVEL from T_ALM_KNOWLEDGE where ALARMLEVEL is not null and ALARMTYPE=2 and DEVICETYPE = ").append(deviceTypeId).append(") re");
					}else{
						s.append(", (select distinct DEVICETYPE, ALARMLEVEL from T_ALM_KNOWLEDGE where ALARMLEVEL is not null and ALARMTYPE=2) re");
					}
				}
			}
		}
		return s.toString();
	}	
	
	private String getGroupStr(AlarmStatisticCondition alarmStatisticCondition){
		List<Integer> statisticGranuList = alarmStatisticCondition.getStaticGranularity();
		StringBuffer s = new StringBuffer();
		s.append(" BEGINTIME,");
		s.append(" ENDTIME,");
		if(null != statisticGranuList && statisticGranuList.size() > 0){		
			for(int statisticGranu : statisticGranuList){
				if(statisticGranu == AlarmConstants.STATIC_GRANU_BRANCH){
					s.append(" BRANCHID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ZONE){
					s.append(" ZONEID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
					s.append(" DEVICETYPE,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
					s.append(" DEPARTMENTID,");
				}
				if(statisticGranu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){	
					if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
						s.append(" CHECKLEVEL,");					
					}else if(alarmStatisticCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
						s.append(" ALARMLEVEL,");					
					}
				}
			}
		}
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}	
				
	private String writeExcel03(AlarmStatisticCondition condition){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		 // 创建一个HSSFWorkbook
		HSSFWorkbook wb = new HSSFWorkbook();
        // 由HSSFWorkbook创建一个HSSFSheet
        HSSFSheet sheet = wb.createSheet("Sheet1");     
        // 由HSSFSheet创建HSSFRow
        HSSFRow row = sheet.createRow(0);
        // 设置列宽
  //    this.setSheetColumnWidth(sheet);
        // 获取样式
        AlarmUtil util = new AlarmUtil();
        HSSFCellStyle style = util.createTitleStyle(wb);        
        util.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
        		"开始时间");   
        util.createCell(row, 1, style, HSSFCell.CELL_TYPE_STRING,
        		"结束时间");
        List<Integer> statisticGranuList = condition.getStaticGranularity();
        List<Organization> branchList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_BRANCH);
		List<Organization> departmentList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_DEPARTMENT);
		List<CommonBean> zoneList = (List<CommonBean>)alarmQueryService.queryZoneInfos();
        if(null != statisticGranuList && statisticGranuList.size() > 0){
            for(int m=0; m<statisticGranuList.size(); m++){
    			int granu = statisticGranuList.get(m);
    			if(granu == AlarmConstants.STATIC_GRANU_BRANCH){
    				util.createCell(row, m+2, style, HSSFCell.CELL_TYPE_STRING,
    		        		"分公司");
    			}
    			if(granu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
    				util.createCell(row, m+2, style, HSSFCell.CELL_TYPE_STRING,
    		        		"管理处");				
    			}
    			if(granu == AlarmConstants.STATIC_GRANU_ZONE){
    				util.createCell(row, m+2, style, HSSFCell.CELL_TYPE_STRING,
    		        		"防区");	
    			}
    			if(granu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
    				util.createCell(row, m+2, style, HSSFCell.CELL_TYPE_STRING,
    		        		"设备类型");	
    			}					
    			if(granu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){
    				util.createCell(row, m+2, style, HSSFCell.CELL_TYPE_STRING,
    		        		"告警级别");	
    			}
            } 
        }
        util.createCell(row, statisticGranuList.size()+2, style, HSSFCell.CELL_TYPE_STRING,
        		"告警个数");
        long allCount = 0;
		int m = 1;		
    	List<AlarmStatisticInfo> alarmStatisticList = new ArrayList<AlarmStatisticInfo>();
		if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
			allCount = alarmStatisticService.getStatisSecAlarmCount(condition);
		}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			allCount = alarmStatisticService.getStatisDevAlarmCount(condition);
		}
		log.info("writeExcel03, allCount " + allCount);
		if(allCount == 0){
			util.createCell(sheet.createRow(1), 0, style, HSSFCell.CELL_TYPE_STRING, "查无资料");
		}else{
			long totalCount = allCount > 50000 ? 50000 : allCount;
			int step = 1000;			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int j = 0; m < totalCount; j += step) {
				// 分段写入，防止数据量过大卡死
				condition.setStart(j);
				if(j+step == 50000){
					condition.setEnd(50001);
				}else{
					condition.setEnd(j+step);
				}
				if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
					alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticSecurityAlarm(
							condition);		
				}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
					alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticDeviceAlarm(
							condition);
				}
				if(null != alarmStatisticList && alarmStatisticList.size() > 0){
					//下面的是根据list  进行遍历循环 向下面的单元格 塞值
					for (int i = 0; i < alarmStatisticList.size(); i++) {            
		            	AlarmStatisticInfo alarm =(AlarmStatisticInfo) alarmStatisticList.get(i);
		            	HSSFRow row1 = null;
						if(j != 0){
						    row1 = sheet.createRow(j+i);// 建立新行
						}else{
						    row1 = sheet.createRow(i+1);// 建立新行
						}
		                util.createCell(row1, 0, style, HSSFCell.CELL_TYPE_STRING,
		        				AlarmUtil.date2String(alarm.getBeginTime(), df));    	      
		                util.createCell(row1, 1, style, HSSFCell.CELL_TYPE_STRING,
		        				AlarmUtil.date2String(alarm.getEndTime(), df));
		        		for(int k=0; k<statisticGranuList.size(); k++){
							int granu = statisticGranuList.get(k);
							if(granu == AlarmConstants.STATIC_GRANU_BRANCH){
								for(Organization cb:branchList){
									if(cb.getOrgId().equals(alarm.getBranchId())){
										util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
												AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}
							if(granu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
								for(Organization cb:departmentList){
									if(cb.getOrgId().equals(alarm.getDepartmentId())){
										util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
												AlarmUtil.formatString(cb.getOrgNm()));
										break;
									}
								}
							}
							if(granu == AlarmConstants.STATIC_GRANU_ZONE){
								String zoneName = "";
								if(null != alarm.getZoneId()){
									for(CommonBean cb:zoneList){
										if(cb.getId().equals(String.valueOf(alarm.getZoneId()))){
											zoneName = cb.getName();
											break;
										}
									}
									// 如果防区表里没有告警的防区，则显示防区id
									if("".equals(zoneName)){
										zoneName = String.valueOf(alarm.getZoneId());
									}
								}
								util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
										AlarmUtil.formatString(zoneName));
							}
							if(granu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
							//	if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
									util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
											AlarmUtil.getNameByKey(AlarmUtil.
													devAlarmDeviceTypeMap, alarm.getDeviceType()));															
								/*}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
									util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
											AlarmUtil.getNameByKey(AlarmUtil
													.devAlarmDeviceTypeMap, alarm.getDeviceType()));						
								}		*/		
							}							
							if(granu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){
								util.createCell(row1, k+2, style, HSSFCell.CELL_TYPE_STRING,
										AlarmUtil.getNameByKey(AlarmUtil.levelMap,
												alarm.getAlarmLevel()));																			
							}
						}       		
		        		util.createCell(row1, statisticGranuList.size()+2, style, HSSFCell.CELL_TYPE_NUMERIC,
		                		alarm.getAlarmNum()); 
		        		m++;
					}  
				}
			}
			if (allCount > totalCount) {
				util.createCell(sheet.createRow(m), 0, style, HSSFCell.CELL_TYPE_STRING,
						"共" + allCount + "条数据，显示"+totalCount+"条，数据太多，后面数据没有显示。");
			}		
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		try{   
			wb.write(os);
			os.flush();
	    	fileName = new String("告警统计.xls".getBytes(), "ISO8859-1");   
			byte[] content = os.toByteArray();   		     
			inputStream = new ByteArrayInputStream(content);  
			os.close();
		}catch(IOException e){   
			log.error("writeExcel03 error!",e);
			message = "导出异常";
			result = AlarmConstants.RESULT_FAIL;
		}   
		operationLogService.createOperationLog("exportAlarm", "", 
				result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "导出告警统计分析结果失败，" + message+ "。";
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/*private String writeExcel07(AlarmStatisticCondition condition){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		 // 创建一个HSSFWorkbook
		XSSFWorkbook wb = new XSSFWorkbook();		
        // 由HSSFWorkbook创建一个HSSFSheet
		XSSFSheet sheet = wb.createSheet("Sheet1");     
        // 由HSSFSheet创建HSSFRow
		XSSFRow row = sheet.createRow(0);
        // 设置列宽
  //    this.setSheetColumnWidth(sheet);
        // 获取样式
		AlarmUtil util = new AlarmUtil();
        XSSFCellStyle style = util.createTitleStyle(wb);        
        util.createCell(row, 0, style, XSSFCell.CELL_TYPE_STRING,
        		"开始时间");   
        util.createCell(row, 1, style, XSSFCell.CELL_TYPE_STRING,
        		"结束时间");
        List<Integer> statisticGranuList = condition.getStaticGranularity();
        if(null != statisticGranuList && statisticGranuList.size() > 0){
	       for(int m=0; m<statisticGranuList.size(); m++){
				int granu = statisticGranuList.get(m);
				if(granu == AlarmConstants.STATIC_GRANU_BRANCH){
					util.createCell(row, m+2, style, XSSFCell.CELL_TYPE_STRING,
			        		"分公司");
				}
				if(granu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
					util.createCell(row, m+2, style, XSSFCell.CELL_TYPE_STRING,
			        		"管理处");				
				}
				if(granu == AlarmConstants.STATIC_GRANU_ZONE){
					util.createCell(row, m+2, style, XSSFCell.CELL_TYPE_STRING,
			        		"防区");	
				}
				if(granu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
					util.createCell(row, m+2, style, XSSFCell.CELL_TYPE_STRING,
			        		"设备类型");	
				}					
				if(granu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){
					util.createCell(row, m+2, style, XSSFCell.CELL_TYPE_STRING,
			        		"告警级别");	
				}
	        }     
        }
        util.createCell(row, statisticGranuList.size()+2, style, HSSFCell.CELL_TYPE_STRING,
        		"告警个数");
    	List<AlarmStatisticInfo> alarmStatisticList = new ArrayList<AlarmStatisticInfo>();
		if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
			alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticSecurityAlarm(
					condition);
		}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			alarmStatisticList = (List<AlarmStatisticInfo>)alarmStatisticService.statisticDeviceAlarm(
					condition);
		}
		log.info("writeExcel07, alarmStatisticList.size() = " + alarmStatisticList.size());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != alarmStatisticList && alarmStatisticList.size() > 0){
			//下面的是根据list  进行遍历循环 向下面的单元格 塞值
			for (int i = 0; i < alarmStatisticList.size(); i++) {            
            	AlarmStatisticInfo alarm =(AlarmStatisticInfo) alarmStatisticList.get(i);
                XSSFRow row1 = sheet.createRow((short) (i + 1));// 建立新行
                util.createCell(row1, 0, style, XSSFCell.CELL_TYPE_STRING,
        				AlarmUtil.date2String(alarm.getBeginTime(), df));    	      
                util.createCell(row1, 1, style, XSSFCell.CELL_TYPE_STRING,
        				AlarmUtil.date2String(alarm.getEndTime(), df));
        		for(int m=0; m<statisticGranuList.size(); m++){
					int granu = statisticGranuList.get(m);
					if(granu == AlarmConstants.STATIC_GRANU_BRANCH){
						String branchName = String.valueOf(organManagerService.getOrgNmByOrgId(
								alarm.getBranchId()));
						util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
								AlarmUtil.formatString(branchName));
					}
					if(granu == AlarmConstants.STATIC_GRANU_DEPARTMENT){
						String departmentName = String.valueOf(organManagerService.getOrgNmByOrgId(
								alarm.getDepartmentId()));
						util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
								AlarmUtil.formatString(departmentName));
					}
					if(granu == AlarmConstants.STATIC_GRANU_ZONE){
						String zoneName = String.valueOf(alarmQueryService.getZoneNameById(
								alarm.getZoneId()));
						util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
								AlarmUtil.formatString(zoneName));
					}
					if(granu == AlarmConstants.STATIC_GRANU_DEVICE_TYPE){
						if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
							util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
									AlarmUtil.getNameByKey(AlarmUtil.
											secAlarmDeviceTypeMap, alarm.getDeviceType()));															
						}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
							util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
									AlarmUtil.getNameByKey(AlarmUtil
											.devAlarmDeviceTypeMap, alarm.getDeviceType()));						
						}				
					}							
					if(granu == AlarmConstants.STATIC_GRANU_ALARM_LEVEL){
						if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){	
							util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
									AlarmUtil.getNameByKey(AlarmUtil.levelMap,
											alarm.getCheckLevel()));														
						}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
							util.createCell(row1, m+2, style, XSSFCell.CELL_TYPE_STRING,
									AlarmUtil.getNameByKey(AlarmUtil.levelMap,
											alarm.getAlarmLevel()));							
						}							
					}
				}       		
        		util.createCell(row1, statisticGranuList.size()+2, style, XSSFCell.CELL_TYPE_NUMERIC,
                		alarm.getAlarmNum());               
            }        
		}else {
			util.createCell(sheet.createRow(0), 0, style,
			XSSFCell.CELL_TYPE_STRING, "查无资料");
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		try{   
			wb.write(os);
			os.flush();
	    	fileName = new String("告警统计.xlsx".getBytes(), "ISO8859-1");   
			byte[] content = os.toByteArray();   		     
			inputStream = new ByteArrayInputStream(content);  
			os.close();
		}catch(IOException e)   {   
			log.error("writeExcel07 error!",e);
			message = "导出异常";
			result = AlarmConstants.RESULT_FAIL;
		}   
		operationLogService.createOperationLog("exportStatisticAlarm", "", 
				result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "导出告警统计分析结果失败，" + message+ "。";
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}*/

	/**
	 * 导出告警统计结果
	 */
	public String exportStatisticAlarm() { 
		alarmStatisticCondition = JSON.parseObject(jsonStr,AlarmStatisticCondition.class);
		alarmStatisticCondition.setGroupByStr(getGroupStr(alarmStatisticCondition));
		alarmStatisticCondition.setColumnStr(getColumnStr(alarmStatisticCondition));
		alarmStatisticCondition.setWhereStr(getWhereStr(alarmStatisticCondition));
		alarmStatisticCondition.setSqlStr(getSqlStr(alarmStatisticCondition));
		log.info("exportStatisticAlarm, alarmStatisticCondition = " + alarmStatisticCondition);
		return writeExcel03(alarmStatisticCondition);
	}
	
	public IAlarmStatisticService getAlarmStatisticService() {
		return alarmStatisticService;
	}

	public void setAlarmStatisticService(
			IAlarmStatisticService alarmStatisticService) {
		this.alarmStatisticService = alarmStatisticService;
	}

	public List<CommonBean> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<CommonBean> branchList) {
		this.branchList = branchList;
	}

	public List<CommonBean> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<CommonBean> departmentList) {
		this.departmentList = departmentList;
	}

	public List<CommonBean> getZoneList() {
		return zoneList;
	}

	public void setZoneList(List<CommonBean> zoneList) {
		this.zoneList = zoneList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTimeGranularity() {
		return timeGranularity;
	}

	public void setTimeGranularity(String timeGranularity) {
		this.timeGranularity = timeGranularity;
	}

	/*public List<Integer> getStatisticGranuList() {
		return statisticGranuList;
	}

	public void setStatisticGranuList(List<Integer> statisticGranuList) {
		this.statisticGranuList = statisticGranuList;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}*/

	public IAlarmQueryService getAlarmQueryService() {
		return alarmQueryService;
	}

	public void setAlarmQueryService(IAlarmQueryService alarmQueryService) {
		this.alarmQueryService = alarmQueryService;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public CommonBean getCommonBean() {
		return commonBean;
	}

	public void setCommonBean(CommonBean commonBean) {
		this.commonBean = commonBean;
	}

	public AlarmStatisticCondition getAlarmStatisticCondition() {
		return alarmStatisticCondition;
	}

	public void setAlarmStatisticCondition(
			AlarmStatisticCondition alarmStatisticCondition) {
		this.alarmStatisticCondition = alarmStatisticCondition;
	}
	
}
