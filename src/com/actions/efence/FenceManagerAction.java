package com.actions.efence;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.BaseAction;
import com.common.page.Page;
import com.entity.Manufacturer;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.authmgt.Organization;
import com.entity.authmgt.Session;
import com.entity.common.ExcelConfigInfo;
import com.entity.efence.FenceBean;
import com.service.common.IManufacturerService;
import com.service.efence.IFenceService;
import com.service.logmgt.IOperationLogService;
import com.util.fence.ExcelUtil;
import com.util.fence.FenceConvertUtil;
import common.page.AjaxObject;

@SuppressWarnings("serial")
public class FenceManagerAction extends BaseAction{
	
	private final static Log log = LogFactory
			.getLog(FenceManagerAction.class);
	
	private IFenceService fenceManagerService;
	private IManufacturerService manufacturerService;
	private IOperationLogService operationLogService;
	
	//数据
	private FenceBean fenceBean;
	//查询条件
	private FenceBean fenceCondition;
	
	private String hostIDs;
	private String subComID;
	private Organization organization;
	private List<Manufacturer> manufacturers;
	private List<FenceBean> fenceBeans;
	private File fence;
	private String fenceFileName;
	private String fenceContentType;
	private List<AlarmDTO> alarms;
	private Page<FenceBean> page;
	
	private AjaxObject ajaxObject;
 
	//存放excel文件出错信息（行数以及格式）
	Map<String,Object> errorMessage;
	/**
	 * 选择了管理处，联动分公司 ajax
	 */
	/*public String findBranchInfoByMngID(){
		
		try {
			subComID = fenceManagerService.findSubComByMngID(fenceBean);
			returnJSON(subComID, "text");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	/**
	 * 跳转查询页面
	 * @return
	 */
	public String goQueryFencePage(){
		return SUCCESS;
	}
	/**
	 * 跳转新增页面
	 */
	public String goAddPage(){
		//查询所有厂商信息
		try {
			manufacturers = manufacturerService.findAllManufacturer();
		} catch (Exception e) {
			log.info("goAddPage findAllManufacturer failed:"+e.getMessage());
		}
		return SUCCESS;
		
	}
	/**
	 * 查看告警信息
	 */
	public String findAlarmInfo(){
		
		try {
			//通过id查询围栏信息
			//fenceBean = fenceManagerService.getFenceByID(fenceBean);
			//通过设备ID查询告警信息
			alarms = fenceManagerService.findAlarmsByDeviceID(fenceBean.getHostID());
			
			ajaxObject=new AjaxObject(1,alarms);
			
		} catch (Exception e) {
			ajaxObject=new AjaxObject(0,null);
			log.info("efence findAlarmInfo failed:"+e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 根据id查询围栏详细信息
	 */
	/*public String queryDetailByID(){
		
		try {
			fenceBean = fenceManagerService.getFenceByID(fenceBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}*/
	
	/**
	 * 查询页面
	 */
	public String queryFence(){
		int level = 0;
		//1.获取用户信息，不同的用户显示不同的信息
		Session s = (Session)getRequest().getSession().getAttribute("session");
		/**
		 * 2.根据用户信息查询公司信息和管理处信息：
		 * 总公司用户：查询所有分公司和所有管理处
		 * 分公司用户：查询分公司和分公司所属所有管理处
		 * 管理处用户：管理处所在分公司和本管理处
		 */
		//3.查询围栏信息
		
	     try {
	    	 
	    	if(page==null){
	    		page = new Page<FenceBean>(pageNow,pageSize); 
	    	}
	    	//权限
	    	if(fenceCondition==null){
	    		
	    		fenceCondition=new FenceBean();
	    		
	    		if(s!=null){
	    			level = Integer.valueOf(s.getLev());
	    		}
				switch (level) {
				case 0:
					break;
				case 1:
					//参数：分公司ID
					String branchID = s.getOrgIdAndNames().getSubCompanys().get(0).getId();
					fenceCondition.setSubComID(branchID);
					break;
				case 2:
					//参数：管理处ID
					String mID = s.getOrgIdAndNames().getManagements().get(0).getId();
					fenceCondition.setMntMentID(mID);
					break;
				}
	    	}
	    	fenceCondition.init();
	 		page.setObjCondition(fenceCondition);
	 		
	 		page = fenceManagerService.queryFenceByPage(page);
	 		} catch (Exception e) {
	 			log.info("queryFence failed:"+e.getMessage());
	 		}
		
		return SUCCESS;
	}
	/**
	 * 判断围栏ID是否已经存在
	 * ajax请求
	 */
	public String isIdRepeat(){
		
		try {
			FenceBean fb = fenceManagerService.getFenceByID(fenceBean);
			if(fb==null){
				ajaxObject=new AjaxObject(1,null);
				return SUCCESS;
			}else{
				ajaxObject=new AjaxObject(0,null);
				return ERROR;
			}
		} catch (Exception e) {
			log.error("efence isIdRepeat:"+e.getMessage());
			ajaxObject=new AjaxObject(0,null);
			return ERROR;
		}
	}
	/**
	 * 新增
	 * @return
	 */
	public String addFence(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("增加围栏：");
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		
		fenceBean.setInstallTime(sdf.format(new Date()));
		int flag;
		try {
			flag = fenceManagerService.addFence(fenceBean);
		} catch (Exception e) {
			
			log.error("FenceManagerAction addFence failed:"+e);
			
			ajaxObject=new AjaxObject(0,"管理处异常");
			return ERROR;
		}
		if(flag==0){
			
			msg.append("成功！");
			//记录操作日志
			try {
				operationLogService.createOperationLog("addFence", fenceBean.getHostID(), 
						result, msg.toString());
			} catch (Exception e) {
				log.error("createOperationLog failed when addFence:"+e.getMessage());
			}
			
			ajaxObject=new AjaxObject(1,null);
			return SUCCESS;
		}else{
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("addFence", fenceBean.getHostID(), 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(0,"添加失败");
			return ERROR;
		}
	}
	/**
	 * 删除
	 * @return
	 */
	public String delFence(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("删除围栏：");
		
		StringBuffer retMsg = null;
		
		int flag=0;
		String[] hs = hostIDs.split(",");
		for(String hID:hs){
			try {
				
				flag = fenceManagerService.delFence(hID);
				
			} catch (Exception e) {
				if(retMsg==null){
					retMsg = new StringBuffer("删除围栏主机:");
				}
				retMsg.append(hID+" ");
			}
			if(flag!=0){
				if(retMsg==null){
					retMsg = new StringBuffer("删除围栏主机:");
				}
				retMsg.append(hID+" ");
			}
		}
 
		if(retMsg==null){
			
			msg.append("成功！");
			//记录操作日志
			try {
				operationLogService.createOperationLog("delFence", hostIDs, 
						result, msg.toString());
			} catch (Exception e) {
				log.error("createOperationLog failed when delFence:"+e.getMessage());
			}
			
			ajaxObject=new AjaxObject(result,"删除成功");
			return SUCCESS;
			
		}else{
			result=0;
			msg.append("失败！");
			retMsg.append("失败");
			//记录操作日志
			operationLogService.createOperationLog("delFence", hostIDs, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,retMsg.toString());
			return ERROR;
			
		}
		
	}
	/**
	 * 跳转更新页面
	 * @return
	 */
	public String goModFence(){
		
		try {
			//查询所有厂商信息
			manufacturers = manufacturerService.findAllManufacturer();
			
			fenceBean = fenceManagerService.getFenceByID(fenceBean);
			
		} catch (Exception e) {
			log.error("FenceManagerAction goModFence error:"+e.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	/**
	 * 执行更新
	 * @return
	 */
	public String doModFence(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("修改围栏：");
		
		int flag;
		try {
			flag = fenceManagerService.modFence(fenceBean);
		} catch (Exception e) {
			result = 0;
			log.error("FenceManagerAction doModFence failed:"+e.getMessage());
			ajaxObject=new AjaxObject(result,"管理处异常");
			return ERROR;
		}
		if(flag==0){
			
			msg.append("成功！");
			//记录操作日志
			try {
				operationLogService.createOperationLog("doModFence", fenceBean.getHostID(), 
						result, msg.toString());
			} catch (Exception e) {
				log.error("createOperationLog failed when doModFence"+e.getMessage());
			}
			ajaxObject=new AjaxObject(result,null);
			return SUCCESS;
		}else{
			
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("doModFence", fenceBean.getHostID(), 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,"编辑失败");
			return ERROR;
		}
		
	}
	/**
	 * 批量导入
	 * @return
	 */
	public String batchImportFence(){
		
		int result = 1;	
		StringBuffer msg = new StringBuffer("批量导入围栏：");
		
		try {
			log.info("begin excute ExcelUtil.readExcel.fileName:"+fenceFileName);
			
			errorMessage = new HashMap<String,Object>();
			//存放插入数据库时错误的行号及错误信息
			List<String> errorRowMeg = new ArrayList<String>();
			
			Map<Integer,List<Object>> fences = ExcelUtil.readExcel(fence,fenceFileName,errorMessage);
			
			/**
			 * 解析excel文件有错误则直接返回
			 * errorMessage：存放错误信息
			 * key值映射关系： FENCE_DATA_ERROR="FENCEDATAERROR" 值为内容校验错误的行号
			 *            "FILE_FORMAT_ERROR"  值为文件格式错误的该文件名称
			 */
			if(errorMessage.size()>0){
				
				result=0;
				msg.append("文件格式错误！");
				//记录操作日志
				operationLogService.createOperationLog("batchImportFence", fenceFileName, 
						result, msg.toString());
				//待定
				ajaxObject=new AjaxObject(result,errorMessage);
				return ERROR;
			}
			
			//将excel文件信息封装成FenceBean对象集合
			List<FenceBean> fenceList = null;
			if(fences!=null&&fences.size()>0){
				
				fenceList= FenceConvertUtil.getFences(fences);
			}
			
			//获取数据库中所有的hostid
			List<Integer> hostIDs = fenceManagerService.getAllFenceHostID();
			
			//判断文件传过来的hostid是否已在数据库中存在，存在则不执行添加操作。
			if(fenceList!=null){
				
				//for(FenceBean fence:fenceList){
				for(int i=0;i<fenceList.size();i++){
					FenceBean fence=fenceList.get(i);
					if(hostIDs!=null){
						if(!hostIDs.contains(fence.getHostID())){
							try {
								fenceManagerService.addFence(fence);
							} catch (Exception e) {
								e.printStackTrace();
								log.error("batchImportFence insert db error:"+e);
								errorRowMeg.add("第"+(i+2)+"行:存储异常\r\n");
							}
						}
					}else{
						try {
							fenceManagerService.addFence(fence);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("batchImportFence insert db error:"+e);
							errorRowMeg.add("第"+(i+2)+"行:存储异常\r\n");
						}
					}
				}
				//存放插入数据库时错误行号信息
				if(errorRowMeg.size()>0){
					
					result=0;
					msg.append("文件内容错误！");
					//记录操作日志
					operationLogService.createOperationLog("batchImportFence", fenceFileName, 
							result, msg.toString());
					
					errorMessage.put(ExcelConfigInfo.FENCE_DATA_ERROR, errorRowMeg);
					ajaxObject=new AjaxObject(result,errorMessage);
					return ERROR;
					
				}
			}
			
		} catch (Exception e) {
			log.error("FenceManagerAction batchImportFence failed:"+e);
			result=0;
			msg.append("失败！");
			//记录操作日志
			operationLogService.createOperationLog("batchImportFence", fenceFileName, 
					result, msg.toString());
			
			ajaxObject=new AjaxObject(result,errorMessage);
			return ERROR;
		}
		
		msg.append("成功！");
		//记录操作日志
		try {
			operationLogService.createOperationLog("batchImportFence", fenceFileName, 
					result, msg.toString());
		} catch (Exception e) {
			log.error("createOperationLog failed when batchImportFence:"+e.getMessage());
		}
		
		ajaxObject=new AjaxObject(result,null);
		return SUCCESS;
	}
	
 
	public IFenceService getFenceManagerService() {
		return fenceManagerService;
	}

	public void setFenceManagerService(IFenceService fenceManagerService) {
		this.fenceManagerService = fenceManagerService;
	}

	public FenceBean getFenceBean() {
		return fenceBean;
	}

	public void setFenceBean(FenceBean fenceBean) {
		this.fenceBean = fenceBean;
	}
	public List<FenceBean> getFenceBeans() {
		return fenceBeans;
	}
	public void setFenceBeans(List<FenceBean> fenceBeans) {
		this.fenceBeans = fenceBeans;
	}
	public File getFence() {
		return fence;
	}
	public void setFence(File fence) {
		this.fence = fence;
	}
	public String getFenceFileName() {
		return fenceFileName;
	}
	public void setFenceFileName(String fenceFileName) {
		this.fenceFileName = fenceFileName;
	}
	public String getFenceContentType() {
		return fenceContentType;
	}
	public void setFenceContentType(String fenceContentType) {
		this.fenceContentType = fenceContentType;
	}
	public Map<String, Object> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(Map<String, Object> errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<AlarmDTO> getAlarms() {
		return alarms;
	}
	public void setAlarms(List<AlarmDTO> alarms) {
		this.alarms = alarms;
	}
	public Page<FenceBean> getPage() {
		return page;
	}
	public void setPage(Page<FenceBean> page) {
		this.page = page;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(List<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public IManufacturerService getManufacturerService() {
		return manufacturerService;
	}

	public void setManufacturerService(IManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}
	public String getSubComID() {
		return subComID;
	}
	public void setSubComID(String subComID) {
		this.subComID = subComID;
	}
	public String getHostIDs() {
		return hostIDs;
	}
	public void setHostIDs(String hostIDs) {
		this.hostIDs = hostIDs;
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
	public FenceBean getFenceCondition() {
		return fenceCondition;
	}
	public void setFenceCondition(FenceBean fenceCondition) {
		this.fenceCondition = fenceCondition;
	}
	
}
