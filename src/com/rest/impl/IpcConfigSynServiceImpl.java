package com.rest.impl;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.dao.videomonitor.IIpcConfigMapper;
import com.entity.videomonitor.IpcConfigDTO;
import com.rest.IIpcConfigSynService;

@Path("ipc")
public class IpcConfigSynServiceImpl implements IIpcConfigSynService {
	
	private final static Log log = LogFactory
			.getLog(IpcConfigSynServiceImpl.class);
	
	private IIpcConfigMapper ipcConfigMapper;

	@Override
	@Path("/getIpcConfigByIp.res")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getIpcConfigByIp(String ip) {
		
		log.info("rest invoke getIpcConfigByIp begin,param:"+ip);
		
		List<IpcConfigDTO> ipcs=null;
		String ipcJson="";
		try {
			ipcs = ipcConfigMapper.getIpcInfoByIp(ip);
		} catch (Exception e) {
			log.fatal("rest invoke getIpcConfigByIp error:"+e.getMessage());
			return ipcJson;
		}
		if(ipcs!=null&&ipcs.size()>0){
			
			ipcJson = JSON.toJSONString(ipcs);
		}
		log.info("rest invoke getIpcConfigByIp end,result:"+ipcJson);
		
		return ipcJson;
	}
	
	@Path("/configResult.res")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public void configResult(String result) {
		
		if(!result.equals("success")){
			try {
				ipcConfigMapper.delIpcConfigByIp(result);
			} catch (Exception e) {
				log.info("configResult:delete database ipcConfig error:"+e.getMessage());
			}
		}else{
			log.info("=======================configResult:"+result);
		}
		
	}
	/***************************setter/getter*********************************/

	public IIpcConfigMapper getIpcConfigMapper() {
		return ipcConfigMapper;
	}

	public void setIpcConfigMapper(IIpcConfigMapper ipcConfigMapper) {
		this.ipcConfigMapper = ipcConfigMapper;
	}

}
