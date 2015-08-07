package com.actions.devmgt.sound;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.actions.devmgt.sound.util.ContextUtil;
import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.devmgt.sound.SoundDev;
import com.entity.devmgt.sound.SoundDevQueryCondition;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IOrganizationManagerService;
import com.service.logmgt.IOperationLogService;
import com.service.sounddev.database.ISoundDevservice;
import com.util.alarmmgt.AlarmConstants;

import common.page.AjaxObject;
import common.page.Pager;

/**
 * 音频按界面下发条件查询
 * 
 * @author maming 2015-4-10上午8:59:16
 * 
 */
public class SoundDevQueryByConditionAction extends ActionSupport {

	public static final String SOUNDDEVQUERYCONDITION = "sounddevquerycondition";

	public static final String SOUNDDEVQUERYRESULT = "sounddevqueryresult";

	private IOrganizationManagerService organManagerService;

	private ISoundDevservice soundDevService = null;

	private IOperationLogService operationLogService = null;

	private Pager<SoundDev> page = null;

	private SoundDevQueryCondition queryCondition = null;

	private List<CommonBean> mgts = null;

	private int elseCondition = 1;

	public int getElseCondition() {
		return elseCondition;
	}

	public void setElseCondition(int elseCondition) {
		this.elseCondition = elseCondition;
	}

	public SoundDevQueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(SoundDevQueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public AjaxObject getAjaxObject() {
		return ajaxObject;
	}

	public void setAjaxObject(AjaxObject ajaxObject) {
		this.ajaxObject = ajaxObject;
	}

	private AjaxObject ajaxObject = null;

	public void setPage(Pager<SoundDev> page) {
		this.page = page;
	}

	public Pager<SoundDev> getPage() {
		return page;
	}

	public List<CommonBean> getMgts() {
		return mgts;
	}

	public void setMgts(List<CommonBean> mgts) {
		this.mgts = mgts;
	}

	public void setOrganManagerService(
			IOrganizationManagerService organManagerService) {
		this.organManagerService = organManagerService;
	}

	public IOrganizationManagerService getOrganManagerService() {
		return organManagerService;
	}

	/**
	 * 返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;

	private Log log = LogFactory.getLog(this.getClass());

	public IOperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(IOperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public ISoundDevservice getSoundDevService() {
		return soundDevService;
	}

	public void setSoundDevService(ISoundDevservice soundDevService) {
		this.soundDevService = soundDevService;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// 条件里有当前第几页，每页多少条数据
		// queryCondition = (SoundDevQueryCondition) request
		// .getSession().getAttribute(SOUNDDEVQUERYCONDITION);
		// 音频服务器不属于任何管理处，管理处条件去掉
		if (null != queryCondition.devType
				&& queryCondition.devType.equals("音频服务器")) {
			queryCondition.mgt = null;
		}
//		request.getSession().removeAttribute(SOUNDDEVQUERYCONDITION);
		String message = "";
		int result = RESULT_SUC;
		queryCondition.init();
		try {
			if (queryCondition.getBrench() != null
					&& queryCondition.getBrench().length() > 0) {
				mgts = organManagerService
						.getOrgIdAndOrgNmsFor2ByPOrgId(queryCondition
								.getBrench());
			} else {
				mgts = null;
			}
		} catch (Exception e) {
		}
		try {
			queryCondition.initMgt(mgts);
			page = (Pager<SoundDev>) soundDevService.query(queryCondition);
			page.setOffset(queryCondition.getOffset());
		} catch (Exception e) {
			log.error("SoundDevQueryByConditionAction error!", e);
			message = "数据库异常。";
			result = RESULT_FAIL;
		}

		// operationLogService.createOperationLog("querySoundDev", "",
		// result, message);
		ajaxObject = new AjaxObject(result, message.toString());

//		request.getSession().setAttribute(SOUNDDEVQUERYRESULT, page);
		//二〇一五年五月十八日 14:24:50 增加管理处名称字段，前台音频页面上管理处ID要修改为管理处的名称
		List<SoundDev> retValue = page.getDatas();
		for (SoundDev soundDev : retValue) {
			if(null != soundDev.getMgtCode())
			soundDev.setMgtName((String)organManagerService.getOrgNmByOrgId(soundDev.getMgtCode()));
		}
		page.setDatas(retValue);
		test(page.getDatas());
		if (result == RESULT_SUC)
			return "success";
		return "error";
	}

	private void test(List<SoundDev> soundDevList2) {
		log.info("音频设备查询");
		log.info("devId------devName------factoryName------ipAddress------devType------ownerName------ownerIp------mgtCode");
		for (SoundDev dev : soundDevList2) {
			log.info(dev.id + "------" + dev.name + "------" + dev.vendorName
					+ "------" + dev.ipAddress + "------" + dev.devType
					+ "------" + dev.ownerdev + "------" + dev.ownerIp
					+ "------" + dev.mgtCode + "------");
		}
		log.info("-----------------query()-----------------");
	}

	public String enter() {
		Session session = ContextUtil.getLoginSession();
		if (null != session) {
			String userLevel = session.getLev();
			if (userLevel.equals(AlarmConstants.USER_HEADQUARTERS)) {
				// 如果用户是总公司
				this.setQueryCondition(new SoundDevQueryCondition());
				return execute();
			} else if (userLevel.equals(AlarmConstants.USER_BRANCH)) {
				SoundDevQueryCondition condition = new SoundDevQueryCondition();
				// 如果用户是分公司级别, 获取用户所在分公司
				List<CommonBean> branchList  = session.getOrgIdAndNames().getSubCompanys();
				condition.setBrench(branchList.get(0).getId());
				this.setQueryCondition(condition);
				return execute();
			} else if (userLevel.equals(AlarmConstants.USER_DEPARTMENT)) {
				// 如果用户是管理处级别, 获取用户所在分公司和所在管理处
				SoundDevQueryCondition condition = new SoundDevQueryCondition();
				List<CommonBean> branchList   = session.getOrgIdAndNames().getSubCompanys();
				List<CommonBean> departmentList = session.getOrgIdAndNames().getManagements();
				condition.setBrench(branchList.get(0).getId());
				condition.setMgt(departmentList.get(0).getId());
				this.setQueryCondition(condition);
				return execute();
			}
		}
		return execute();
	}
}
