package com.util.logmgt;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.entity.logmgt.OperationLog;
import com.entity.logmgt.OperationLogCondition;
import com.util.alarmmgt.AlarmConstants;

public class LogUtil {

	public static Map<Integer, String> operationResultMap = new HashMap<Integer, String>();

	public static Map<Integer, String> securityResultMap = new HashMap<Integer, String>();

	public static Map<String, String> sortedOptionNameMap = new HashMap<String, String>();
	
	public static Map<String, String> sortedOptionAscendMap = new HashMap<String, String>();

	public static Map<String, String> organizationMap = new TreeMap<String, String>();
	

	static {
		initNameMap();
	}
	
	public static void initNameMap() {
		operationResultMap.put(OperationLog.SUCCESS, "成功");
		operationResultMap.put(OperationLog.FAILED, " 失败");
		operationResultMap.put(OperationLog.PART_FAILED, "部分失败");
		operationResultMap.put(OperationLog.EXECUTING, "正在执行");
		
		securityResultMap.put(OperationLog.SUCCESS, "成功");
		securityResultMap.put(OperationLog.FAILED, " 失败");
		
		sortedOptionNameMap.put(OperationLogCondition.ORDER_BY_TIME, "产生时间");
		sortedOptionNameMap.put(OperationLogCondition.ORDER_BY_ORGANIZATIONID,"机构标识");
		sortedOptionNameMap.put(OperationLogCondition.ORDER_BY_ACCOUNTID, "操作员");
		
		/*sortedOptionAscendMap.put(OperationLogCondition.ORDER_BY_NONE, "不排序");
		sortedOptionAscendMap.put(OperationLogCondition.ORDER_BY_ASC,"升序");
		sortedOptionAscendMap.put(OperationLogCondition.ORDER_BY_DESC, "降序");*/
		
		organizationMap.put(AlarmConstants.USER_HEADQUARTERS, "总公司");
		organizationMap.put(AlarmConstants.USER_BRANCH, "分公司");
		organizationMap.put(AlarmConstants.USER_DEPARTMENT,"管理处");
	}
}
