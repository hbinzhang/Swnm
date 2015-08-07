package com.actions.alarmmgt;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.CommonBean;
import com.entity.alarmmgt.AlarmKnowledge;
import com.opensymphony.xwork2.ActionSupport;
import com.service.alarmmgt.IAlarmKnowledgeService;
import com.service.logmgt.IOperationLogService;
import com.util.alarmmgt.AlarmConstants;
 
/**
 * 告警知识库
 * @author liyinghui
 *
 */
public class AlarmKnowledgeAction extends ActionSupport {
	
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 5717484386721769845L;

	/**
	 * 来源：服务器返回给客户端
	 * 操作：查询告警知识库
	 * 含义：知识库list
	 */
	private List<AlarmKnowledge> alarmKnowledgeList;
	
	private IAlarmKnowledgeService alarmKnowledgeService;
	private IOperationLogService operationLogService;

	/**
	 * 来源：界面下发
	 * 操作：修改告警知识库
	 * 含义：告警码
	 */
	private int alarmCode;
	
	private CommonBean commonBean;
	
	private AlarmKnowledge alarmKnowledge;
	
	
//	private String str;

//	private AjaxObject ajaxObject;

	public String queryAllAlarmKnowledge() {
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		try{
			alarmKnowledgeList = (List<AlarmKnowledge>)alarmKnowledgeService.queryAllAlarmKnowledge();
		}catch(Exception e){
			log.error("queryAllAlarmKnowledge error!",e);
			message = "查询告警知识库失败，数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	public String goupdateAlarmKnowledge() {
		int result = AlarmConstants.RESULT_SUC;
		String message = "";
		try{
			alarmKnowledge = (AlarmKnowledge)alarmKnowledgeService.queryAlarmKnowledgeByCode(alarmCode);
		}catch(Exception e){
			log.error("goupdateAlarmKnowledge error!",e);
			message = "获取告警知识库编辑信息失败，数据库异常。";
			result = AlarmConstants.RESULT_FAIL;
		}
		commonBean = new CommonBean(String.valueOf(result), message);
		if(result == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}

	public String updateAlarmKnowledge() {
		int opResult = AlarmConstants.RESULT_SUC;
		String message = "";
		log.info("updateAlarmKnowledge, alarmKnowledge = " + alarmKnowledge);
		try{
			if(null != alarmKnowledge){
				AlarmKnowledge param = new AlarmKnowledge();
				param.setAlarmCode(alarmKnowledge.getAlarmCode());
				param.setCause(alarmKnowledge.getCause());
				param.setInfo(alarmKnowledge.getInfo());
				param.setOperation(alarmKnowledge.getOperation());
				param.setResult(alarmKnowledge.getResult());
				alarmKnowledgeService.updateAlarmKnowledge(param);
			}		
		}catch(Exception e){
			log.error("updateAlarmKnowledge error!",e);
			message = "数据库异常";
			opResult = AlarmConstants.RESULT_FAIL;
		}
		operationLogService.createOperationLog("updateAlarmKnowledge", String.valueOf(
				alarmCode), opResult, message);
		if(opResult == AlarmConstants.RESULT_FAIL){
			message = "修改告警基本信息失败，" + message+ "。";
		}
		commonBean = new CommonBean(String.valueOf(opResult), message);
		if(opResult == AlarmConstants.RESULT_SUC){
			return "success";
		}else{
			return "error";
		}	
	}
	
	/* public String validateLength() {  
        String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";  
        boolean rs = false;
    	if(str == null || str.length()==0){
	     	ajaxObject = new AjaxObject(0,rs);			
    	}else{
    		 rs = matcher(validateStr, str);  
	         if (rs) {  
	            int strLenth = getStrLength(str);  
	            if (strLenth > 200) {  
	                rs = true;
	                ajaxObject = new AjaxObject(1, rs);
	            } 
	            else{
	            	rs = false;
	            	ajaxObject = new AjaxObject(0,rs);
	            }
	        }  
    	}    
		return "success";
	 }  

	*//** 
	  * 获取字符串的长度，对双字符（包括汉字）按两位计数 
	  *  
	  * @param value 
	  * @return 
	  *//*  
	 public static int getStrLength(String value) {  
	     int valueLength = 0;  
	     String chinese = "[\u0391-\uFFE5]";  
	     for (int i = 0; i < value.length(); i++) {  
	         String temp = value.substring(i, i + 1);  
	         if (temp.matches(chinese)) {  
	             valueLength += 2;  
	         } else {  
	             valueLength += 1;  
	         }  
	     }  
	     return valueLength;  
	 }  
	
	 private static boolean matcher(String reg, String string) {  
	
	     boolean tem = false;  
	     Pattern pattern = Pattern.compile(reg);  
	     Matcher matcher = pattern.matcher(string);  
	     tem = matcher.matches();  
	     return tem;  
	 }  */

	public List<AlarmKnowledge> getAlarmKnowledgeList() {
		return alarmKnowledgeList;
	}

	public void setAlarmKnowledgeList(List<AlarmKnowledge> alarmKnowledgeList) {
		this.alarmKnowledgeList = alarmKnowledgeList;
	}

	public IAlarmKnowledgeService getAlarmKnowledgeService() {
		return alarmKnowledgeService;
	}

	public void setAlarmKnowledgeService(
			IAlarmKnowledgeService alarmKnowledgeService) {
		this.alarmKnowledgeService = alarmKnowledgeService;
	}

	public int getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(int alarmCode) {
		this.alarmCode = alarmCode;
	}

	/*public String addAlarmFlow(){
		CommonBean person1 = new CommonBean("1","fastjson");  
		String jsonString = JSON.toJSONString(person1);  
		System.out.println(jsonString);  
		JSONObject p =JSON.parseObject(jsonString);  

		CommonBean person2 =JSON.parseObject(jsonString,CommonBean.class);  
		String name = (String) p.get("id");
	    System.out.println(person2.toString());  
		         
		// 将JavaBean转换为JSONObject或者JSONArray,JSONArray：相当于List<Object>,JSONObject：相当于Map<String, Object>
	    //		Object a = JSON.toJSON(alarmKnowledgeList); 
		HttpServletResponse response= (HttpServletResponse) ActionContext.getContext().get(
				StrutsStatics.HTTP_RESPONSE);		         
	    try {
		    PrintWriter out = response.getWriter();
		    out.print(jsonString);
		    out.flush();
		    out.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return "success";
	}*/

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

	public AlarmKnowledge getAlarmKnowledge() {
		return alarmKnowledge;
	}

	public void setAlarmKnowledge(AlarmKnowledge alarmKnowledge) {
		this.alarmKnowledge = alarmKnowledge;
	}

/*	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}*/

/*	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}*/
	
}
