package com.service.linkagectl;
import java.util.List;
import java.util.Map;

import com.entity.linkagectl.UIMCSecurityAlarm;

public interface IGetSeAlarm {
	
	public List<UIMCSecurityAlarm> findAllUIMcAlarmInfo(String orgID, String userID);
	
	public int  getActiveAlarmCountByDevId(String devID);
	
	public List <Map>  getActiveAlarmCountByOrgid(String userID,String orgID);

}