package com.actions.alarmmgt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.entity.alarmmgt.AlarmKnowledge;
import com.entity.alarmmgt.AlarmQueryCondition;
import com.entity.alarmmgt.DeviceAlarm;
import com.entity.alarmmgt.SecurityAlarm;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmKnowledgeService;
import com.service.alarmmgt.IAlarmQueryService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;
import com.util.alarmmgt.AlarmUtil;

import common.page.AjaxObject;
import common.page.Pager;

/**
 * 告警查询
 * @author liyinghui
 *
 */
public class AlarmQueryAction extends ActionSupport{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8435131083178594514L;
	private IAlarmQueryService alarmQueryService;
	private IAlarmKnowledgeService alarmKnowledgeService;
	private IOrganizationManagerService organManagerService;
	
	private IOperationLogService operationLogService;

	
	private Log log = LogFactory.getLog(this.getClass());

	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化查询告警界面
	 * 含义：分公司list(下拉列表)，CommonBean中id是标识，name是名称，
	 * 如果是总公司用户，则界面下拉列表带“全部”，否则默认为返回的list的值（就一个值），不带“全部”
	 */
	private List<CommonBean> branchList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：根据分公司id查询管理处
	 * 含义：管理处list(下拉列表) 
	 * 如果是总公司或分公司用户，则界面下拉列表带“全部”，否则默认为返回的list的值（就一个值），不带“全部”
	 */
	private List<CommonBean> departmentList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：根据管理处id查询防区
	 * 含义： 防区list(下拉列表) 
	 */
	private List<CommonBean> zoneList;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：根据设备类型id查询告警名称
	 * 含义：告警名称list(下拉列表) 
	 */
	private List<String> alarmNameList;
	
	/**
	 * 来源：服务器返回给客户端或客户端下发给服务器
	 * 操作：初始化告警查询界面或查询告警
	 * 含义：是否有事件视频，0无视频，1 有视频
	 */
	private int hasEventVideo;

	/**
	 * 来源：服务器返回给客户端或客户端下发给服务器
	 * 操作：初始化查询告警界面或查询告警
	 * 含义：告警类型 ，1 安防告警，2 设备告警
	 */
	private int type;
	
	/**
	 * 来源：客户端下发给服务器
	 * 操作：查询告警
	 * 含义：管理处id，-1为全部
	 */
	private String departmentId;
	
	/**
	 * 来源：客户端下发给服务器
	 * 操作：查询告警
	 * 含义：设备类型id，-1为全部
	 * 1 高压脉冲主机
	 * 2一体化探测主机
	 * 3 防区型振动光纤
	 * 4 定位型振动光纤
	 * 5 NVR
	 * 6 IPC
	 * 7 智能视频服务器
	 */
	private int deviceTypeId = -1;
	
	/**
	 * 来源：客户端下发给服务器
	 * 操作：查询告警
	 * 含义：分公司id，-1为全部
	 */
	private String branchId;
	
	/**
	 * 来源：服务器返回给客户端
	 * 操作：初始化告警查询界面
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

	private AjaxObject ajaxObject;
	
	private CommonBean commonBean;

	private int alarmId;
		
	private List<String> pictureUrl;
	
	private SecurityAlarm pictureAlarm;
	
	private SecurityAlarm videoAlarm;
	
	/**
	 * 初始化查询告警条件
	 */
	public String initAlarm(){	
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		type = AlarmConstants.TYPE_SECURITY_ALARM;	
		// 无事件视频
		hasEventVideo = 0;		
		try{	
			// 从session获取用户
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				userLevel = session.getLev();
				log.info("initAlarm, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						// 如果用户是总公司, 获取所有分公司，有全部			
						branchList = session.getOrgIdAndNames().getSubCompanys();
					}else if(userLevel.equals( AlarmConstants.USER_BRANCH)){				
						// 如果用户是分公司级别, 获取用户所在分公司和分公司下的所有管理处	
						// 界面的分公司下拉列表（无全部）默认为用户的分公司，管理处有全部
						branchList = session.getOrgIdAndNames().getSubCompanys();
						departmentList = session.getOrgIdAndNames().getManagements();
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						// 如果用户是管理处级别, 获取用户所在分公司和所在管理处，所有防区
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
			log.error("initAlarm error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		if(result == AlarmConstants.RESULT_FAIL){
			message = "初始化告警查询条件失败，" + message;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/**
	 * 根据分公司id查询管理处
	 * @return
	 */
	public String queryDepartByBranch(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		List<CommonBean> departmentList = new ArrayList<CommonBean>();	
		log.info("queryDepartByBranch, branchId = " + branchId);
		try{
			Session session = AlarmUtil.getLoginSession();
			if(null != session){
				String userLevel = session.getLev();
				log.info("queryDepartByBranch, userLevel = " + userLevel);
				if(null == userLevel){
					message = "用户所属机构级别不存在。";
					result = AlarmConstants.RESULT_FAIL;
				}else{
					if(userLevel.equals(AlarmConstants.USER_HEADQUARTERS)){
						departmentList = (List<CommonBean>)organManagerService.getOrgIdAndOrgNmsFor2ByPOrgId(
								branchId);	
					}else if(userLevel.equals( AlarmConstants.USER_BRANCH)){				
						departmentList = session.getOrgIdAndNames().getManagements();					
					}else if(userLevel.equals(AlarmConstants.USER_DEPARTMENT)){
						departmentList = session.getOrgIdAndNames().getManagements();			
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
			log.error("queryDepartByBranch error!",e);
			message = "数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}	
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(departmentList);
		if(null != message && message.length() > 0){
			ajaxObject.setMessage("根据分公司查询管理处失败，" + message);
		}
		log.info("queryDepartByBranch, ajaxObject = " + ajaxObject);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 根据管理处id查询防区
	 * @return
	 */
	public String queryZoneByDepart(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		List<CommonBean> zoneList = new ArrayList<CommonBean>();	
		log.info("queryZoneByDepart, departmentId = " + departmentId);
		try{
			zoneList = (List<CommonBean>)alarmQueryService.queryZoneByDepartId(departmentId);			
		}catch(Exception e){
			log.error("queryZoneByDepart error!",e);
			result = AlarmConstants.RESULT_FAIL;
			message = "根据管理处查询防区失败，数据库异常。";
		}
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(zoneList);
		ajaxObject.setMessage(message);	
		log.info("queryZoneByDepart, ajaxObject = " + ajaxObject);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
	}
	
	/**
	 * 根据设备类型id查询告警名称
	 * @return
	 */
	public String queryAlarmNameByDevType(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		List<String> alarmNameList = new ArrayList<String>();		
		log.info("queryAlarmNameByDevType, deviceTypeId = " + deviceTypeId);
		if(deviceTypeId != -1){
			try{
				AlarmKnowledge alarmKnowledge = new AlarmKnowledge();
				alarmKnowledge.setDeviceType(deviceTypeId);
				List<AlarmKnowledge> knowledgeList = (List<AlarmKnowledge>)alarmKnowledgeService.queryAlaKnowByKnowledge(
						alarmKnowledge);
				for(AlarmKnowledge knowledge : knowledgeList){
					alarmNameList.add(knowledge.getAlarmName());
				}					
			}catch(Exception e){
				log.error("queryAlarmNameByDevType error!",e);
				message = "根据设备类型查询告警名称失败，数据库异常。";
				result = AlarmConstants.RESULT_FAIL;
			}
		}	
		ajaxObject = new AjaxObject();
		ajaxObject.setResult(result);
		ajaxObject.setObject(alarmNameList);
		ajaxObject.setMessage(message);
		log.info("queryAlarmNameByDevType, ajaxObject = " + ajaxObject);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}
	}
	
	private void setPageInfo(Pager pager, int totalCount, AlarmQueryCondition alarmQueryCondition){
		int start = 0;
		int end = 0;
		int totalPage = 0;
		int offset = alarmQueryCondition.getOffset();
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
		alarmQueryCondition.setStart(start);
		alarmQueryCondition.setEnd(end);
		pager.setOffset(offset);
		pager.setPages(totalPage);
		pager.setTotal(totalCount);
		pager.setSize(step);
	}
	
	/**
	 * 查询告警
	 */
	@SuppressWarnings("unchecked")
	public String queryAlarm(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		AlarmQueryCondition alarmQueryCondition = JSON.parseObject(jsonStr,
				AlarmQueryCondition.class);  
		log.info("queryAlarm, alarmQueryCondition = " + alarmQueryCondition);
		Pager pager = new Pager();
		int totalCount = 0;
		List<Organization> branchList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_BRANCH);
		List<Organization> departmentList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_DEPARTMENT);
		List<CommonBean> zoneList = (List<CommonBean>)alarmQueryService.queryZoneInfos();
		if(alarmQueryCondition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){
			try{
				totalCount = alarmQueryService.getSecurityAlarmCount(alarmQueryCondition);
				setPageInfo(pager, totalCount, alarmQueryCondition);	
				List<SecurityAlarm> securityAlarmList = (List<SecurityAlarm>)alarmQueryService.querySecurityAlarm(
						alarmQueryCondition);
				// 根据分公司id，管理处id查询分公司名称，管理处名称，权限接口提供
				for(SecurityAlarm securityAlarm : securityAlarmList){
					for(Organization cb:branchList){
						if(cb.getOrgId().equals(securityAlarm.getBranchId())){
							securityAlarm.setBranchName(AlarmUtil.formatString(cb.getOrgNm()));
							break;
						}
					}
					for(Organization cb:departmentList){
						if(cb.getOrgId().equals(securityAlarm.getDepartmentId())){
							securityAlarm.setDepartmentName(AlarmUtil.formatString(cb.getOrgNm()));
							break;
						}
					}
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
				pager.setDatas(securityAlarmList);
			}catch(Exception e){
				log.error("querySecurityAlarm error!",e);
				message = "数据库异常。";
				result = AlarmConstants.RESULT_FAIL;
			}				
		}else if(alarmQueryCondition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			try{
				totalCount = alarmQueryService.getDeviceAlarmCount(alarmQueryCondition);
				setPageInfo(pager, totalCount, alarmQueryCondition);
				List<DeviceAlarm> deviceAlarmList = (List<DeviceAlarm>)alarmQueryService.queryDeviceAlarm(
						alarmQueryCondition);
				for(DeviceAlarm deviceAlarm : deviceAlarmList){
					for(Organization cb:branchList){
						if(cb.getOrgId().equals(deviceAlarm.getBranchId())){
							deviceAlarm.setBranchName(AlarmUtil.formatString(cb.getOrgNm()));
							break;
						}
					}
					for(Organization cb:departmentList){
						if(cb.getOrgId().equals(deviceAlarm.getDepartmentId())){
							deviceAlarm.setDepartmentName(AlarmUtil.formatString(cb.getOrgNm()));
							break;
						}
					}
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
				pager.setDatas(deviceAlarmList);
			}catch(Exception e){
				log.error("queryDeviceAlarm error!",e);
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
			ajaxObject.setMessage("查询告警失败，" + message);
		}
		log.info("queryAlarm, ajaxObject = " + ajaxObject);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}

/*	private AlarmQueryCondition getCondition(){
		AlarmQueryCondition alarmQueryCondition = new AlarmQueryCondition();
		Calendar cal = Calendar.getInstance();
		condition_startTime = new Date();
		condition_endTime = new Date();
		condition_startTime.setHours(1);
		condition_startTime.setMinutes(1);
		condition_startTime.setSeconds(1);
		condition_startTime.setDate(1);

		cal.setTime(condition_startTime);
		Calendar cal2 = Calendar.getInstance();
		condition_endTime.setHours(1);
		condition_endTime.setMinutes(1);
		condition_endTime.setSeconds(3);
		condition_endTime.setDate(3);

		cal2.setTime(condition_endTime);
		zoneId = -1;
		hasEventVideo = 0;
//		departmentId = "3";
		status = -1;
		branchId = "1";
		deviceId = null;
		levelId = -1;
		deviceTypeId = -1;
		alarmQueryCondition.setAlarmName(alarmName);
		alarmQueryCondition.setAlarmStatus(status);
	//	alarmQueryCondition.setBeginTime(condition_startTime);
	//	alarmQueryCondition.setEndTime(condition_endTime);
		alarmQueryCondition.setBranchId(branchId);
		alarmQueryCondition.setDepartmentId(departmentId);
		alarmQueryCondition.setDeviceId(deviceId);
		alarmQueryCondition.setDeviceTypeId(deviceTypeId);
		alarmQueryCondition.setHasEventVideo(hasEventVideo);
		alarmQueryCondition.setZoneId(zoneId);
		alarmQueryCondition.setType(type);
		alarmQueryCondition.setLevelId(levelId);
		alarmQueryCondition.setHasEventVideo(hasEventVideo);
		return alarmQueryCondition;
	}*/
	
	private String writeExcel03(AlarmQueryCondition condition){
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
		List<Organization> branchList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_BRANCH);
		List<Organization> departmentList = (List<Organization>)organManagerService.getOrganizationsByLev(
				AlarmConstants.USER_DEPARTMENT);
		List<CommonBean> zoneList = (List<CommonBean>)alarmQueryService.queryZoneInfos();
		if(condition.getType() == AlarmConstants.TYPE_SECURITY_ALARM){		
			util.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
			        "告警编号");   
			util.createCell(row, 1, style, HSSFCell.CELL_TYPE_STRING,
			       	"告警码");
			util.createCell(row, 2, style, HSSFCell.CELL_TYPE_STRING,
			        "告警名称");
			util.createCell(row, 3, style, HSSFCell.CELL_TYPE_STRING,
					"告警时间");
			util.createCell(row, 4, style, HSSFCell.CELL_TYPE_STRING,
			        "告警级别");
			util.createCell(row, 5, style, HSSFCell.CELL_TYPE_STRING,
			        "设备类型");
			util.createCell(row, 6, style, HSSFCell.CELL_TYPE_STRING,
			        "设备ID");		
			util.createCell(row, 7, style, HSSFCell.CELL_TYPE_STRING,
			        "分公司");
			util.createCell(row, 8, style, HSSFCell.CELL_TYPE_STRING,
			        "管理处");
			util.createCell(row, 9, style, HSSFCell.CELL_TYPE_STRING,
			        "防区");
			util.createCell(row, 10, style, HSSFCell.CELL_TYPE_STRING,
			        "处理状态");
			util.createCell(row, 11, style, HSSFCell.CELL_TYPE_STRING,
			        "附加信息");
			util.createCell(row, 12, style, HSSFCell.CELL_TYPE_STRING,
			        "处理人");
			util.createCell(row, 13, style, HSSFCell.CELL_TYPE_STRING,
			        "处理时间");
		    util.createCell(row, 14, style, HSSFCell.CELL_TYPE_STRING,
			        "事件原因");
			util.createCell(row, 15, style, HSSFCell.CELL_TYPE_STRING,
			        "事件过程");
			util.createCell(row, 16, style, HSSFCell.CELL_TYPE_STRING,
			        "责任人");
			util.createCell(row, 17, style, HSSFCell.CELL_TYPE_STRING,
			        "虚实警");
			util.createCell(row, 18, style, HSSFCell.CELL_TYPE_STRING,
			        "上报情况");
			util.createCell(row, 19, style, HSSFCell.CELL_TYPE_STRING,
			        "复核手段");
			util.createCell(row, 20, style, HSSFCell.CELL_TYPE_STRING,
			        "防区事件视频");
			util.createCell(row, 21, style, HSSFCell.CELL_TYPE_STRING,
			        "左防区事件视频");
			util.createCell(row, 22, style, HSSFCell.CELL_TYPE_STRING,
			        "右防区事件视频");
			util.createCell(row, 23, style, HSSFCell.CELL_TYPE_STRING,
			        "事件图片");
			util.createCell(row, 24, style, HSSFCell.CELL_TYPE_STRING,
			        "持续时间");
			util.createCell(row, 25, style, HSSFCell.CELL_TYPE_STRING,
					"次数");
			
			int m = 1;
			long allCount = alarmQueryService.getSecurityAlarmCount(condition);
			log.info("writeExcel03, allCount = " + allCount);
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
					List<SecurityAlarm>	securityAlarmList = alarmQueryService.querySecurityAlarm(condition);
					if(null != securityAlarmList && securityAlarmList.size() > 0){
						//下面的是根据list  进行遍历循环 向下面的单元格 塞值
						for (int i = 0; i < securityAlarmList.size(); i++) {            
							SecurityAlarm alarm =(SecurityAlarm) securityAlarmList.get(i);
							HSSFRow row1 = null;
							if(j != 0){
							    row1 = sheet.createRow(j+i);// 建立新行
							}else{
							    row1 = sheet.createRow(i+1);// 建立新行
							}
						    util.createCell(row1, 0, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getAlarmId())));    	      
						    util.createCell(row1, 1, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getAlarmCode())));
						    util.createCell(row1, 2, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getAlarmName()));
						    util.createCell(row1, 3, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.date2String(alarm.getAlarmTime(), df));
						    util.createCell(row1, 4, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.levelMap,
						       				alarm.getCheckLevel()));
						    util.createCell(row1, 5, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.devAlarmDeviceTypeMap,
						       				alarm.getDeviceType()));
						    util.createCell(row1, 6, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getDeviceId())));															
						    for(Organization cb:branchList){
								if(cb.getOrgId().equals(alarm.getBranchId())){
								    util.createCell(row1, 7, style, HSSFCell.CELL_TYPE_STRING,
								            AlarmUtil.formatString(cb.getOrgNm()));
									break;
								}
							}
							for(Organization cb:departmentList){
								if(cb.getOrgId().equals(alarm.getDepartmentId())){
									util.createCell(row1, 8, style, HSSFCell.CELL_TYPE_STRING,
									        AlarmUtil.formatString(cb.getOrgNm()));
									break;
								}
							}	
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
						    util.createCell(row1, 9, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(zoneName));
						    
						    util.createCell(row1, 10, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.statusMap,
						       				alarm.getAlarmStatus()));
						    util.createCell(row1, 11, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getInfo()));
						    util.createCell(row1, 12, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(alarm.getUserId()));
						    util.createCell(row1, 13, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.date2String(alarm.getManagerTime(), df));
						    util.createCell(row1, 14, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getReason()));
						    util.createCell(row1, 15, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getDescription()));
						    util.createCell(row1, 16, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(alarm.getPeopleId()));
						    util.createCell(row1, 17, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.isRealMap,
						       				alarm.getIsReal()));
						    util.createCell(row1, 18, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.reportMap,
						       				alarm.getReport()));
						    util.createCell(row1, 19, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.checkMethodMap,
						       				alarm.getCheckMothod()));
						    util.createCell(row1, 20, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getVidioUrl()));
						    util.createCell(row1, 21, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getLeftVidioUrl()));
						    util.createCell(row1, 22, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getRightVidioUrl()));
						    util.createCell(row1, 23, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getPictureUrl()));
						    util.createCell(row1, 24, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getLastTime())));
						    util.createCell(row1, 25, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getAlarmCount())));     
						    m++;
						 }        
					}			
				}
				if (allCount > totalCount) {
					util.createCell(sheet.createRow(m), 0, style, HSSFCell.CELL_TYPE_STRING,
							 "共" + allCount + "条数据，显示"+totalCount+"条，数据太多，后面数据没有显示。");
				}
			}
		}else if(condition.getType() == AlarmConstants.TYPE_DEVICE_ALARM){
			util.createCell(row, 0, style, HSSFCell.CELL_TYPE_STRING,
				    "告警编号");   
			util.createCell(row, 1, style, HSSFCell.CELL_TYPE_STRING,
					"告警码");
			util.createCell(row, 2, style, HSSFCell.CELL_TYPE_STRING,
				    "告警名称");
			util.createCell(row, 3, style, HSSFCell.CELL_TYPE_STRING,
				    "告警时间");
			util.createCell(row, 4, style, HSSFCell.CELL_TYPE_STRING,
				    "告警级别");
			util.createCell(row, 5, style, HSSFCell.CELL_TYPE_STRING,
				    "设备类型");
			util.createCell(row, 6, style, HSSFCell.CELL_TYPE_STRING,
				    "设备ID");		
			util.createCell(row, 7, style, HSSFCell.CELL_TYPE_STRING,
				    "分公司");
			util.createCell(row, 8, style, HSSFCell.CELL_TYPE_STRING,
				    "管理处");
			util.createCell(row, 9, style, HSSFCell.CELL_TYPE_STRING,
				    "防区");	
			util.createCell(row, 10, style, HSSFCell.CELL_TYPE_STRING,
				    "处理状态");
			util.createCell(row, 11, style, HSSFCell.CELL_TYPE_STRING,
				    "附加信息");
			util.createCell(row, 12, style, HSSFCell.CELL_TYPE_STRING,
				    "处理人");
			util.createCell(row, 13, style, HSSFCell.CELL_TYPE_STRING,
				    "处理时间");
			util.createCell(row, 14, style, HSSFCell.CELL_TYPE_STRING,
				    "可能原因");
			util.createCell(row, 15, style, HSSFCell.CELL_TYPE_STRING,
				    "可能后果");
			util.createCell(row, 16, style, HSSFCell.CELL_TYPE_STRING,
				    "建议操作");
			util.createCell(row, 17, style, HSSFCell.CELL_TYPE_STRING,
					"是否影响业务");

			int m = 1;
			long allCount = alarmQueryService.getDeviceAlarmCount(condition);
			log.info("writeExcel03, allCount = " + allCount);
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
					List<DeviceAlarm> deviceAlarmList = alarmQueryService.queryDeviceAlarm(condition);
					if(null != deviceAlarmList && deviceAlarmList.size() > 0){
						//下面的是根据list  进行遍历循环 向下面的单元格 塞值
						for (int i = 0; i < deviceAlarmList.size(); i++) {            
							DeviceAlarm alarm =(DeviceAlarm) deviceAlarmList.get(i);
							HSSFRow row1 = null;
							if(j != 0){
							    row1 = sheet.createRow(j+i);// 建立新行
							}else{
							    row1 = sheet.createRow(i+1);// 建立新行
							}
						    util.createCell(row1, 0, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getAlarmId())));    	      
						    util.createCell(row1, 1, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(String.valueOf(alarm.getAlarmCode())));
						    util.createCell(row1, 2, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getAlarmName()));
						    util.createCell(row1, 3, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.date2String(alarm.getAlarmTime(), df));
						    util.createCell(row1, 4, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.levelMap,
						       				alarm.getAlarmLevel()));
						    util.createCell(row1, 5, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.devAlarmDeviceTypeMap,
						       				alarm.getDeviceType()));
						    util.createCell(row1, 6, style, HSSFCell.CELL_TYPE_STRING,
						    		 AlarmUtil.formatString(String.valueOf(alarm.getDeviceId())));						
						    for(Organization cb:branchList){
								if(cb.getOrgId().equals(alarm.getBranchId())){
								    util.createCell(row1, 7, style, HSSFCell.CELL_TYPE_STRING,
								            AlarmUtil.formatString(cb.getOrgNm()));
									break;
								}
							}
							for(Organization cb:departmentList){
								if(cb.getOrgId().equals(alarm.getDepartmentId())){
									util.createCell(row1, 8, style, HSSFCell.CELL_TYPE_STRING,
									        AlarmUtil.formatString(cb.getOrgNm()));
									break;
								}
							}
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
						    util.createCell(row1, 9, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(zoneName));
						    
						    util.createCell(row1, 10, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.getNameByKey(AlarmUtil.statusMap,
						       				alarm.getAlarmStatus()));
						     util.createCell(row1, 11, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getInfo()));
						     util.createCell(row1, 12, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.formatString(alarm.getPeopleId()));
						     util.createCell(row1, 13, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.date2String(alarm.getManagerTime(), df));
						     util.createCell(row1, 14, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getCause()));
						     util.createCell(row1, 15, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getResult()));
						     util.createCell(row1, 16, style, HSSFCell.CELL_TYPE_STRING,
						            AlarmUtil.formatString(alarm.getOperation()));
						     util.createCell(row1, 17, style, HSSFCell.CELL_TYPE_STRING,
						    		AlarmUtil.getNameByKey(AlarmUtil.affectMap,
						       				alarm.getIsAffect()));	            	
						     m++;
						 }        
					}
				}
				if (allCount > totalCount) {
					 util.createCell(sheet.createRow(m), 0, style, HSSFCell.CELL_TYPE_STRING,
							 "共" + allCount + "条数据，显示"+totalCount+"条，数据太多，后面数据没有显示。");
				}
			}
		}	
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		try{   
			wb.write(os);
			os.flush();
	    	fileName = new String("告警查询.xls".getBytes(), "ISO8859-1");   
			byte[] content = os.toByteArray();   		     
			inputStream = new ByteArrayInputStream(content);  
			os.close();
		}catch(IOException e)   {   
			log.error("writeExcel03 error!",e);
			message = "导出异常";
			result = AlarmConstants.RESULT_FAIL;
		}   
		operationLogService.createOperationLog("exportAlarm", "", 
				result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "导出告警失败，" + message+ "。";
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/*private String writeExcel07(AlarmQueryCondition condition){
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
       if(type == AlarmConstants.TYPE_SECURITY_ALARM){		
	       util.createCell(row, 0, style, XSSFCell.CELL_TYPE_STRING,
	       			"告警编号");   
	       util.createCell(row, 1, style, XSSFCell.CELL_TYPE_STRING,
	       			"告警码");
	       util.createCell(row, 2, style, XSSFCell.CELL_TYPE_STRING,
	          		"告警名称");
	       util.createCell(row, 3, style, XSSFCell.CELL_TYPE_STRING,
	          		"告警时间");
	       util.createCell(row, 4, style, XSSFCell.CELL_TYPE_STRING,
	          		"告警级别");
	       util.createCell(row, 5, style, XSSFCell.CELL_TYPE_STRING,
	          		"设备类型");
	       util.createCell(row, 6, style, XSSFCell.CELL_TYPE_STRING,
	          		"设备ID");
	       util.createCell(row, 7, style, XSSFCell.CELL_TYPE_STRING,
	          		"防区");
	       util.createCell(row, 8, style, XSSFCell.CELL_TYPE_STRING,
	          		"管理处");
	       util.createCell(row, 9, style, XSSFCell.CELL_TYPE_STRING,
	          		"分公司");
	       util.createCell(row, 10, style, XSSFCell.CELL_TYPE_STRING,
	          		"处理状态");
	       util.createCell(row, 11, style, XSSFCell.CELL_TYPE_STRING,
	          		"附加信息");
	       util.createCell(row, 12, style, XSSFCell.CELL_TYPE_STRING,
	          		"处理人");
	       util.createCell(row, 13, style, XSSFCell.CELL_TYPE_STRING,
	          		"处理时间");
	       util.createCell(row, 14, style, XSSFCell.CELL_TYPE_STRING,
	          		"事件原因");
	       util.createCell(row, 15, style, XSSFCell.CELL_TYPE_STRING,
	          		"事件过程");
	       util.createCell(row, 16, style, XSSFCell.CELL_TYPE_STRING,
	          		"责任人");
	       util.createCell(row, 17, style, XSSFCell.CELL_TYPE_STRING,
	          		"虚实警");
	       util.createCell(row, 18, style, XSSFCell.CELL_TYPE_STRING,
	         		"上报情况");
	       util.createCell(row, 19, style, XSSFCell.CELL_TYPE_STRING,
	          		"复核手段");
	       util.createCell(row, 20, style, XSSFCell.CELL_TYPE_STRING,
	         		"防区事件视频");
	       util.createCell(row, 21, style, XSSFCell.CELL_TYPE_STRING,
	         		"左防区事件视频");
	       util.createCell(row, 22, style, XSSFCell.CELL_TYPE_STRING,
	         		"右防区事件视频");
	       util.createCell(row, 23, style, XSSFCell.CELL_TYPE_STRING,
	         		"事件图片");
	       util.createCell(row, 24, style, XSSFCell.CELL_TYPE_STRING,
	         		"持续时间");
	       util.createCell(row, 25, style, XSSFCell.CELL_TYPE_STRING,
	         		"次数");
	
	       int m = 1;
		   long allCount = alarmQueryService.getSecurityAlarmCount(condition);
		   log.info("writeExcel07, allCount = " + allCount);
		   if(allCount == 0){
			   util.createCell(sheet.createRow(1), 0, style, XSSFCell.CELL_TYPE_STRING, "查无资料");
		   }else{
			   long totalCount = allCount > 50000 ? 50000 : allCount;
			   final int step = 1000;
			   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   for (int j = 0; m <= totalCount; j += step) {
					// 分段写入，防止数据量过大卡死
					condition.setStart(j);
					condition.setEnd(j+step);
					List<SecurityAlarm>	securityAlarmList = alarmQueryService.querySecurityAlarm(condition);
					if(null != securityAlarmList && securityAlarmList.size() > 0){
						//下面的是根据list  进行遍历循环 向下面的单元格 塞值
						for (int i = 0; i < securityAlarmList.size(); i++) {            
						   SecurityAlarm alarm =(SecurityAlarm) securityAlarmList.get(i);
			               XSSFRow row1 = sheet.createRow((short) (i + 1));// 建立新行
			               util.createCell(row1, 0, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(String.valueOf(alarm.getAlarmId())));    	      
			               util.createCell(row1, 1, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(String.valueOf(alarm.getAlarmCode())));
			               util.createCell(row1, 2, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getAlarmName()));
			               util.createCell(row1, 3, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.date2String(alarm.getAlarmTime(), df));
			               util.createCell(row1, 4, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.levelMap,
			       						alarm.getCheckLevel()));
			               util.createCell(row1, 5, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.secAlarmDeviceTypeMap,
			       						alarm.getDeviceType()));
			               util.createCell(row1, 6, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(String.valueOf(alarm.getDeviceId())));
			               String zoneName = String.valueOf(alarmQueryService.getZoneNameById(
			            		   alarm.getZoneId()));
			               util.createCell(row1, 7, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(zoneName));
			       		   String departmentName = String.valueOf(organManagerService.getOrgNmByOrgId(
			       				   alarm.getDepartmentId()));	
			               util.createCell(row1, 8, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(departmentName));
			       		   String branchName = String.valueOf(organManagerService.getOrgNmByOrgId(
			       				   alarm.getBranchId()));
			               util.createCell(row1, 9, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(branchName));
			               util.createCell(row1, 10, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.statusMap,
			       						alarm.getAlarmStatus()));
			               util.createCell(row1, 11, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getInfo()));
			               util.createCell(row1, 12, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getUserId()));
			               util.createCell(row1, 13, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.date2String(alarm.getManagerTime(), df));
			               util.createCell(row1, 14, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getReason()));
			               util.createCell(row1, 15, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getDescription()));
			               util.createCell(row1, 16, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getPeopleId()));
			               util.createCell(row1, 17, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.isRealMap,
			       						alarm.getIsReal()));
			               util.createCell(row1, 18, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.reportMap,
			       						alarm.getReport()));
			               util.createCell(row1, 19, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.getNameByKey(AlarmUtil.checkMethodMap,
			       						alarm.getCheckMothod()));
			               util.createCell(row1, 20, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getVidioUrl()));
			               util.createCell(row1, 21, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getLeftVidioUrl()));
			               util.createCell(row1, 22, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getRightVidioUrl()));
			               util.createCell(row1, 23, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(alarm.getPictureUrl()));
			               util.createCell(row1, 24, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(String.valueOf(alarm.getLastTime())));
			               util.createCell(row1, 25, style, XSSFCell.CELL_TYPE_STRING,
			            		   AlarmUtil.formatString(String.valueOf(alarm.getAlarmCount())));     
			               m++;
			           }        
					}			
			    }
				if (allCount > totalCount) {
					 util.createCell(sheet.createRow(m), 0, style, XSSFCell.CELL_TYPE_STRING,
							 "共" + allCount + "条数据，显示"+totalCount+"条，数据太多，后面数据没有显示。");
				}
		    }
		}else if(type == AlarmConstants.TYPE_DEVICE_ALARM){
			  util.createCell(row, 0, style, XSSFCell.CELL_TYPE_STRING,
		       			"告警编号");   
		       util.createCell(row, 1, style, XSSFCell.CELL_TYPE_STRING,
		       			"告警码");
		       util.createCell(row, 2, style, XSSFCell.CELL_TYPE_STRING,
		          		"告警名称");
		       util.createCell(row, 3, style, XSSFCell.CELL_TYPE_STRING,
		          		"告警时间");
		       util.createCell(row, 4, style, XSSFCell.CELL_TYPE_STRING,
		          		"告警级别");
		       util.createCell(row, 5, style, XSSFCell.CELL_TYPE_STRING,
		          		"设备类型");
		       util.createCell(row, 6, style, XSSFCell.CELL_TYPE_STRING,
		          		"设备ID");
		       util.createCell(row, 7, style, XSSFCell.CELL_TYPE_STRING,
		          		"防区");
		       util.createCell(row, 8, style, XSSFCell.CELL_TYPE_STRING,
		          		"管理处");
		       util.createCell(row, 9, style, XSSFCell.CELL_TYPE_STRING,
		          		"分公司");
		       util.createCell(row, 10, style, XSSFCell.CELL_TYPE_STRING,
		          		"处理状态");
		       util.createCell(row, 11, style, XSSFCell.CELL_TYPE_STRING,
		          		"附加信息");
		       util.createCell(row, 12, style, XSSFCell.CELL_TYPE_STRING,
		          		"处理人");
		       util.createCell(row, 13, style, XSSFCell.CELL_TYPE_STRING,
		          		"处理时间");
		       util.createCell(row, 14, style, XSSFCell.CELL_TYPE_STRING,
		          		"可能原因");
		       util.createCell(row, 15, style, XSSFCell.CELL_TYPE_STRING,
		          		"可能后果");
		       util.createCell(row, 16, style, XSSFCell.CELL_TYPE_STRING,
		          		"建议操作");
		       util.createCell(row, 17, style, XSSFCell.CELL_TYPE_STRING,
		          		"是否影响业务");

		       int m = 1;
			   long allCount = alarmQueryService.getDeviceAlarmCount(condition);
			   log.info("writeExcel07, allCount = " + allCount);
			   if(allCount == 0){
				   util.createCell(sheet.createRow(1), 0, style, XSSFCell.CELL_TYPE_STRING, "查无资料");
			   }else{
				   long totalCount = allCount > 50000 ? 50000 : allCount;
				   final int step = 1000;
				   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   for (int j = 0; m <= totalCount; j += step) {
						// 分段写入，防止数据量过大卡死
						condition.setStart(j);
						condition.setEnd(j+step);
						List<DeviceAlarm> deviceAlarmList = alarmQueryService.queryDeviceAlarm(condition);
						if(null != deviceAlarmList && deviceAlarmList.size() > 0){
							//下面的是根据list  进行遍历循环 向下面的单元格 塞值
							for (int i = 0; i < deviceAlarmList.size(); i++) {            
							   DeviceAlarm alarm =(DeviceAlarm) deviceAlarmList.get(i);
				               XSSFRow row1 = sheet.createRow((short) (i + 1));// 建立新行
				               util.createCell(row1, 0, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(String.valueOf(alarm.getAlarmId())));    	      
				               util.createCell(row1, 1, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(String.valueOf(alarm.getAlarmCode())));
				               util.createCell(row1, 2, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getAlarmName()));
				               util.createCell(row1, 3, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.date2String(alarm.getAlarmTime(), df));
				               util.createCell(row1, 4, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.getNameByKey(AlarmUtil.levelMap,
				       						alarm.getAlarmLevel()));
				               util.createCell(row1, 5, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.getNameByKey(AlarmUtil.secAlarmDeviceTypeMap,
				       						alarm.getDeviceType()));
				               util.createCell(row1, 6, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(String.valueOf(alarm.getDeviceId())));
				               String zoneName = String.valueOf(alarmQueryService.getZoneNameById(
				            		   alarm.getZoneId()));
				               util.createCell(row1, 7, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(zoneName));
				       		   String departmentName = String.valueOf(organManagerService.getOrgNmByOrgId(
				       				   alarm.getDepartmentId()));
				               util.createCell(row1, 8, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(departmentName));
				       		   String branchName = String.valueOf(organManagerService.getOrgNmByOrgId(
				       				   alarm.getBranchId()));
				               util.createCell(row1, 9, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(branchName));
				               util.createCell(row1, 10, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.getNameByKey(AlarmUtil.statusMap,
				       						alarm.getAlarmStatus()));
				               util.createCell(row1, 11, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getInfo()));
				               util.createCell(row1, 12, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getPeopleId()));
				               util.createCell(row1, 13, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.date2String(alarm.getManagerTime(), df));
				               util.createCell(row1, 14, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getCause()));
				               util.createCell(row1, 15, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getResult()));
				               util.createCell(row1, 16, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.formatString(alarm.getOperation()));
				               util.createCell(row1, 17, style, XSSFCell.CELL_TYPE_STRING,
				            		   AlarmUtil.getNameByKey(AlarmUtil.affectMap,
				       						alarm.getIsAffect()));	            	
				               m++;
				           }        
						}
				   }
				   if (allCount > totalCount) {
						 util.createCell(sheet.createRow(m), 0, style, XSSFCell.CELL_TYPE_STRING,
								 "共" + allCount + "条数据，显示"+totalCount+"条，数据太多，后面数据没有显示。");
				   }
			 }
		}	
        ByteArrayOutputStream os = new ByteArrayOutputStream();   
		try{   
			wb.write(os);
			os.flush();
	    	fileName = new String("告警查询.xlsx".getBytes(), "ISO8859-1");   
			byte[] content = os.toByteArray();   		     
			inputStream = new ByteArrayInputStream(content);  
			os.close();
		}catch(IOException e)   {   
			log.error("writeExcel07 error!",e);
			message = "导出异常";
			result = AlarmConstants.RESULT_FAIL;
		}   
		operationLogService.createOperationLog("exportAlarm", "", 
				result, message);
		if(result == AlarmConstants.RESULT_FAIL){
			message = "导出告警失败，" + message+ "。";
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}*/

	
	/**
	 * 导出告警
	 */
	public String exportAlarm(){
		AlarmQueryCondition condition = JSON.parseObject(jsonStr,AlarmQueryCondition.class);
		log.info("exportAlarm ,alarmQueryCondition = " + condition);
		return writeExcel03(condition);
	}

	/**
	 * 获得安防告警图片路径
	 * @return
	 */
	public String queryAlarmPictureUrl(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		pictureUrl = new ArrayList<String>();
		try{	
			pictureAlarm = (SecurityAlarm)alarmQueryService.querySecAlarmById(
					alarmId);
			if(null != pictureAlarm){
				String urls = pictureAlarm.getPictureUrl();
				if(urls!=null&&urls.length()>0)
				{
					pictureUrl = Arrays.asList(urls.split(","));
				}
			}
		}catch(Exception e){
			log.error("getAlarmPicUrl error!",e);
			message = "获得安防告警图片路径失败，数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}

	/**
	 * 获得安防告警视频路径
	 * @return
	 */
	public String queryAlarmVideoUrl(){
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		/*videoUrls = new ArrayList<String>();
		leftVideoUrls = new ArrayList<String>();
		rightVideoUrls = new ArrayList<String>();*/
		try{	
			videoAlarm = (SecurityAlarm)alarmQueryService.querySecAlarmById(
					alarmId);
			/*if(null != sAlarm){
				if(videoType == 1){
					String vUrls = sAlarm.getVidioUrl();
					if(vUrls!=null&&vUrls.length()>0)
					{
						videoUrls = Arrays.asList(vUrls.split(","));
					}
				}else if(videoType == 2){
					String lvUrls = sAlarm.getLeftVidioUrl();
					if(lvUrls!=null&&lvUrls.length()>0)
					{
						leftVideoUrls = Arrays.asList(lvUrls.split(","));
					}
				}else if(videoType == 3){
					String rvUrls = sAlarm.getRightVidioUrl();
					if(rvUrls!=null&&rvUrls.length()>0)
					{
						rightVideoUrls = Arrays.asList(rvUrls.split(","));
					}
				}
				
			}*/
		}catch(Exception e){
			log.error("getAlarmPicUrl error!",e);
			message = "获得安防告警视频路径失败，数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public IAlarmQueryService getAlarmQueryService() {
		return alarmQueryService;
	}

	public void setAlarmQueryService(IAlarmQueryService alarmQueryService) {
		this.alarmQueryService = alarmQueryService;
	}

	public int getHasEventVideo() {
		return hasEventVideo;
	}

	public void setHasEventVideo(int hasEventVideo) {
		this.hasEventVideo = hasEventVideo;
	}

	public IAlarmKnowledgeService getAlarmKnowledgeService() {
		return alarmKnowledgeService;
	}

	public void setAlarmKnowledgeService(
			IAlarmKnowledgeService alarmKnowledgeService) {
		this.alarmKnowledgeService = alarmKnowledgeService;
	}

	public List<String> getAlarmNameList() {
		return alarmNameList;
	}

	public void setAlarmNameList(List<String> alarmNameList) {
		this.alarmNameList = alarmNameList;
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

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public List<String> getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(List<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public SecurityAlarm getPictureAlarm() {
		return pictureAlarm;
	}

	public void setPictureAlarm(SecurityAlarm pictureAlarm) {
		this.pictureAlarm = pictureAlarm;
	}

	public SecurityAlarm getVideoAlarm() {
		return videoAlarm;
	}

	public void setVideoAlarm(SecurityAlarm videoAlarm) {
		this.videoAlarm = videoAlarm;
	}
	
}
