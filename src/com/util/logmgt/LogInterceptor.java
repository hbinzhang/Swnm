/*package com.util.logmgt;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.entity.authmgt.Session;
import com.entity.logmgt.CommandInfo;
import com.entity.logmgt.OperationLog;
import com.entity.logmgt.SecurityLog;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.service.logmgt.IOperationLogService;
import com.service.logmgt.ISecurityLogService;
import com.util.alarmmgt.AlarmUtil;

public class LogInterceptor extends AbstractInterceptor{

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	private IOperationLogService operationLogService;
	private ISecurityLogService securityLogService;
	private String hostIp = "";
	private String hostName = ""; 
	private String organizationId = "";
	private String uid = "";
	private ApplicationContext ac = null;
	private ActionSupport action = null;
	private String operationObjects;
	private Log log = LogFactory.getLog(this.getClass());

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		action = (ActionSupport)actionInvocation.getAction();
		action.clearErrorsAndMessages();
		// 上下文
		ActionContext actionContext = actionInvocation.getInvocationContext();   
		Map paramsMap = actionContext.getParameters();	
		if(null != paramsMap){
			// 属性对应的是数组 
			String[] cmdId = (String[])paramsMap.get("commandId"); 
			String[] objects = (String[])paramsMap.get("operationObjects");
			if(null != objects && objects.length > 0){
				operationObjects = String.valueOf(objects[0]); 
			}
			if(null != cmdId && cmdId.length > 0){			
				// 登陆用户
				HttpServletRequest request= (HttpServletRequest) actionContext.get(
							StrutsStatics.HTTP_REQUEST);	
				Session session = AlarmUtil.getLoginSession();
				uid = session.getId();  
				hostIp = session.getLoginHostIp();
				hostName = session.getLoginHostName();
				organizationId = session.getOrganizationId();
				isAjaxRequest(request);
				Map session = actionContext.getSession();   
				uid = "12233";
				InetAddress netAddress = getInetAddress();
				hostIp = getHostIp(netAddress);
				hostName = getHostName(netAddress);		
			 	hostIp = "2.2.3.2";
				hostName = "ddd";
				organizationId = "eew";			
		
				ServletContext sc = request.getSession().getServletContext(); 				
				ac = WebApplicationContextUtils.getRequiredWebApplicationContext(
						sc);  
				String actionResult = "";
				int commandId = Integer.parseInt(cmdId[0]);
				CommandInfo commandInfo = CommandParser.getInstance().getDetail(commandId);
				log.info("intercept, commandInfo = " + commandInfo);
				if(null != commandInfo){
					if(commandId == 103050001 || commandId == 103050002){
						actionResult = interceptSecurityLog(commandInfo, actionInvocation);
					}else{
						actionResult = interceptOperationLog(commandInfo, actionInvocation);
					}
					return actionResult;
				}else{
					return "error";
				}
			}else{
				return "error";
			}
		}	
		return "error";
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {  
		   String header = request.getHeader("X-Requested-With");  
		   if (header != null && "XMLHttpRequest".equals(header))  
		       return true;  
		   else  
	        return false;  
	} 
	
	private String interceptOperationLog(CommandInfo commandInfo, ActionInvocation 
			actionInvocation) {
		int logType = commandInfo.getLogType();
		int id = 0;
		try{
			if(logType != 0){
				operationLogService = (IOperationLogService)ac.getBean(
						"operationlogservice");				
				id = operationLogService.getOperationLogId();
			}
		}catch(Exception e){
			log.error("interceptOperationLog error!",e);
			return "error";
		}
		OperationLog operationLog = new OperationLog();	
		operationLog.setOlsId(id);
		operationLog.setAccountId(uid);					
		operationLog.setHostIp(hostIp);					
		operationLog.setHostName(hostName);
		operationLog.setCommandId(commandInfo.getCommandId());
		if(commandInfo.getModuleId() != -1){
			operationLog.setModuleId(commandInfo.getModuleId());		
		}
		operationLog.setOrganizationId(organizationId);
		operationLog.setOpTime(new Date());
		if(null != operationObjects && operationObjects.length() > 0){	
			operationLog.setOperationObjects(operationObjects);
		}
		// 如果是异步操作，生成关联号，作为各业务的主键
		if(commandInfo.getIsAsynch().equals("true")){
			Collection<String> ids = new ArrayList<String>();
			ids.add(String.valueOf(id));
			action.setActionMessages(ids);
		}
		try{
			String actionResultString = actionInvocation.invoke();
			log.info("interceptOperationLog, actionResultString = " + actionResultString);
			if(logType != 0){		
				if(actionResultString.equalsIgnoreCase("ERROR")|| actionResultString.
						equalsIgnoreCase("INPUT") || actionResultString.equalsIgnoreCase(
								"LOGIN")){
					if(action.hasActionErrors()){
						// 记录失败结果，详细信息
						Collection<String> msgCol = action.getActionErrors();
						operationLog.setOpDetail(msgCol.toString());
					}
					operationLog.setOpResult(OperationLog.FAILED);
				}else if(actionResultString.equalsIgnoreCase("SUCCESS")||
						actionResultString.equalsIgnoreCase("NONE")) {
					if(commandInfo.getIsAsynch().equals("true")){
						operationLog.setOpResult(OperationLog.EXECUTING);
					}else{
						operationLog.setOpResult(OperationLog.SUCCESS);
					}
				}
				log.info("interceptOperationLog, operationLog = " + operationLog);
				// 写数据库日志
		//	    operationLogService.createOperationLog(operationLog);
				log.info("记录操作日志完成 !");
			}
			return actionResultString;		
		}catch(Exception ex){
			log.error("interceptOperationLog error!",ex);
			if(logType != 0){
				if(action.hasActionErrors()){
					// 记录失败结果，详细信息
					Collection<String> msgCol = action.getActionErrors();
					operationLog.setOpDetail(msgCol.toString());
				}
				operationLog.setOpResult(OperationLog.FAILED); 	
				log.info("执行异常，operationLog = " + operationLog);
		//		operationLogService.createOperationLog(operationLog);	
				log.info("记录操作日志完成 !");
			}
			return "error"; 
		}
	}

	private String interceptSecurityLog(CommandInfo commandInfo, ActionInvocation 
			actionInvocation) {
		int logType = commandInfo.getLogType();
		int id = 0;
		try{
			if(logType != 0){
				securityLogService = (ISecurityLogService)ac.getBean(
						"securitylogservice");	
				id = securityLogService.getSecurityLogId();
			}
		}catch(Exception e){
			log.error("interceptSecurityLog error!",e);
			return "error";
		}	
		// 登录或退出，记录安全日志
		SecurityLog securityLog = new SecurityLog();	
		securityLog.setSlsId(id);
		securityLog.setAccountId(uid);
		securityLog.setCommandId(commandInfo.getCommandId());
		securityLog.setHostIp(hostIp);
		securityLog.setHostName(hostName);
		securityLog.setOpTime(new Date());
		securityLog.setOrganizationId(organizationId);
		try{
			String actionResultString = actionInvocation.invoke();
			log.info("interceptSecurityLog, actionResultString = " + actionResultString);
			if(logType != 0){
				if(actionResultString.equalsIgnoreCase("ERROR")|| actionResultString.
						equalsIgnoreCase("INPUT") || actionResultString.equalsIgnoreCase(
								"LOGIN")){
					if(action.hasActionErrors()){
						// 记录失败结果，详细信息
						Collection<String> msgCol = action.getActionErrors();
						securityLog.setOpDetail(msgCol.toString());
					}
					securityLog.setOpResult(SecurityLog.FAILED);
				}else if(actionResultString.equalsIgnoreCase("SUCCESS")||
					actionResultString.equalsIgnoreCase("NONE")) {
					securityLog.setOpResult(SecurityLog.SUCCESS);
				}
				log.info("interceptSecurityLog, securityLog = " + securityLog);
			//	securityLogService.createSecurityLog(securityLog);
				log.info("记录安全日志完成 !");
			}
			return actionResultString;		
		}catch(Exception ex){
			log.error("interceptSecurityLog error!",ex);
			if(logType != 0){
				if(action.hasActionErrors()){
					// 记录失败结果，详细信息
					Collection<String> msgCol = action.getActionErrors();
					securityLog.setOpDetail(msgCol.toString());
				}
				securityLog.setOpResult(SecurityLog.FAILED); 
				log.info("执行异常，securityLog = " + securityLog);
		//		securityLogService.createSecurityLog(securityLog);	
				log.info("记录安全日志完成 !");	
			}
			return "error"; 
		}		
	}
	
	private InetAddress getInetAddress(){   	   
		try{   
			return InetAddress.getLocalHost();   
		}catch(UnknownHostException e){   
		    System.out.println("unknown host!");   
		}   
		return null;     
	} 
	 
	private String getHostName(InetAddress netAddress){   
		if(null == netAddress){   
			return null;   
		}   
		String name = netAddress.getHostName();  
		return name;   
	}   
	 
	private String getHostIp(InetAddress netAddress){   
		if(null == netAddress){   
			return null;   
		}   		       
		String ip = netAddress.getHostAddress(); 
	    return ip;   
	} 	 
	
}
*/