package com.actions.videomonitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actions.BaseAction;
import com.actions.efence.FenceManagerAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.videomonitor.TVmIvaIpcMap;
import com.service.videomonitor.IIvaConfigService;

public class IvaIpcConfigAddAction extends BaseAction {
	
	private final static Log log = LogFactory
			.getLog(IvaIpcConfigAddAction.class);
	private String ivaConfig;
	private IIvaConfigService ivaConfigService;
	
	public String execute(){
		/*ivaConfig="{\"ivaID\":\"IVA001\",\"ip\":\"12.3.20.45\",\"port\":30000,\"ipcs\":"
				
				+"[{\"ipcID\":\"IPCID001\",\"alarms\":{\"highTossAct\":1,\"flotage\":1}},{\"ipcID\":\"IPCID002\",\"alarms\":{\"highTossAct\":1,\"flotage\":1}}]}";*/
		List<TVmIvaIpcMap> tvmIvaIpcMaps = new ArrayList<TVmIvaIpcMap>();
		//解析页面json数据
		JSONObject jb = JSON.parseObject(ivaConfig);
		String ivaID = jb.getString("ivaID");
		String ip = jb.getString("ip");
		Integer port = jb.getIntValue("port");
		JSONArray jarr=null;
		if(ivaID!=null){
			jarr = jb.getJSONArray("ipcs");
		}
		if(jarr!=null){
			for(int i=0;i<jarr.size();i++){
				TVmIvaIpcMap tim = new TVmIvaIpcMap();
				JSONObject jobj = jarr.getJSONObject(i);
				tim.setIvaID(ivaID);
				tim.setIp(ip);
				tim.setPort(port);
				tim.setIpcID(jobj.getString("ipcID"));
				tim.setPoint(jobj.getInteger("point"));
				JSONObject jobj1=jobj.getJSONObject("alarms");
				tim.setCordon(jobj1.getIntValue("cordon"));
				tim.setFlotage(jobj1.getIntValue("flotage"));
				tim.setHighTossAct(jobj1.getIntValue("highTossAct"));
				tim.setTravelToTrack(jobj1.getIntValue("travelToTrack"));
				tvmIvaIpcMaps.add(tim);
			}
		}
		try {
			//1.先删除该ivaID是否已存在摄像头映射
			ivaConfigService.delIvaConfig(ivaID);
			//2.增加新的配置
			if(tvmIvaIpcMaps.size()>0){
				ivaConfigService.addIvaConfig(tvmIvaIpcMaps);
			}else{
				ivaConfigService.delIvaConfig(ip,port);
			}
		} catch (Exception e) {
			log.fatal("IvaIpcConfigAddAction addIvaConfig failed:"+e.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
		
	}
/*******************************setter/getter*********************************/
	public String getIvaConfig() {
		return ivaConfig;
	}
	public void setIvaConfig(String ivaConfig) {
		this.ivaConfig = ivaConfig;
	}
	public IIvaConfigService getIvaConfigService() {
		return ivaConfigService;
	}
	public void setIvaConfigService(IIvaConfigService ivaConfigService) {
		this.ivaConfigService = ivaConfigService;
	}
	
	

}
