package com.actions.linkagectl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.dao.linkagectl.IAlarmDao;
import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.linkagectl.UIMCSecurityAlarm;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.authmgt.IAccountManagerService;
import com.service.authmgt.IOrganizationManagerService;
import com.service.linkagectl.impl.GetSeAlarmImp;

public class UISeAlarmAction extends ActionSupport {
	
	private	List <UIMCSecurityAlarm> uiAlarmList;
	
	private List<CommonBean> peopleList;

	private Session session;
	private GetSeAlarmImp getSeAlarmImp;
	private Log log = LogFactory.getLog(this.getClass());
	
	

	public List<UIMCSecurityAlarm> getUiAlarmList() {
		return uiAlarmList;
	}

	public void setUiAlarmList(List<UIMCSecurityAlarm> uiAlarmList) {
		this.uiAlarmList = uiAlarmList;
	}
	
	
	public List<CommonBean> getPeopleList() {
		return peopleList;
	}
	public void setPeopleList(List<CommonBean> peopleList) {
		this.peopleList = peopleList;
	}
		
	
	public GetSeAlarmImp getGetSeAlarmImp() {
		return getSeAlarmImp;
	}
	public void setGetSeAlarmImp(GetSeAlarmImp getSeAlarmImp) {
		this.getSeAlarmImp = getSeAlarmImp;
	}
	
	public String findAllUIMcAlarmInfo() throws Exception
    {
		
		try
		{
			String userID;
			String orgID;
			
			ActionContext ctx = ActionContext.getContext();
			session = (Session)ctx.getSession().get("session");
			
			userID = session.getId();
			orgID = session.getOrganizationId();
			
			if ((orgID == null) ||(userID == null) )
			{
				return ERROR;
			}
			uiAlarmList = getSeAlarmImp.findAllUIMcAlarmInfo(orgID, userID);
			peopleList = getSeAlarmImp.findAllOrgPeople(orgID);
			
			return SUCCESS;
		}
		catch(DataAccessException de)
        {
           log.error(de.getMessage(),de);
           throw de;
        }
        catch(RuntimeException re)
        {
            log.error(re.getMessage(),re);
            throw re;
        }
		catch(Exception e)
        {
            log.error(e.getMessage(),e);
            throw e;
        }
		
    }
	
	public String findAllOrgPeople() throws Exception
    {
		try
		{
			String orgID;

			ActionContext ctx = ActionContext.getContext();

			session = (Session)ctx.getSession().get("session");
			orgID = session.getOrganizationId();
			if(orgID == null)
			{
				return ERROR;
			}
			else
			{
				peopleList = getSeAlarmImp.findAllOrgPeople(orgID);
			}
			
			return SUCCESS;
		}
		catch(DataAccessException de)
        {
           log.error(de.getMessage(),de);
           throw de;
        }
        catch(RuntimeException re)
        {
            log.error(re.getMessage(),re);
            throw re;
        }
		catch(Exception e)
        {
            log.error(e.getMessage(),e);
            throw e;
        }
   }
	  
	public String execute() throws Exception {
		
		return SUCCESS;
	}

}
