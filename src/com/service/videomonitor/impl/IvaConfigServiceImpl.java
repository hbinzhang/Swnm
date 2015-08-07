package com.service.videomonitor.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.page.Page;
import com.dao.videomonitor.IIvaConfigMapper;
import com.entity.videomonitor.TVmIva;
import com.entity.videomonitor.TVmIvaIpcMap;
import com.rest.impl.IpcConfigSynServiceImpl;
import com.service.videomonitor.IIvaConfigService;
import com.service.videomonitor.IVAConfigSocketService;

public class IvaConfigServiceImpl implements IIvaConfigService {
	
	private final static Log log = LogFactory
			.getLog(IvaConfigServiceImpl.class);
	private IIvaConfigMapper ivaConfigMapper;
	private IVAConfigSocketService ivaConfigSocketService;

	@Override
	public void addIvaConfig(List<TVmIvaIpcMap> tvmIvaIpcMaps) throws Exception {
		
		try {
			String ip = tvmIvaIpcMaps.get(0).getIp();
			Integer port = tvmIvaIpcMaps.get(0).getPort();
			for(TVmIvaIpcMap tvmIvaIpcMap:tvmIvaIpcMaps){
				
				ivaConfigMapper.addIvaConfig(tvmIvaIpcMap);
				
			}
			//boolean result=true;
			boolean result =ivaConfigSocketService.notifyIVAServer(ip, port, "ok");
			if(!result){
				log.fatal("==============addIvaConfig socket error=================");
				throw new Exception();
			}
			
		} catch (Exception e) {
			log.fatal("addIvaConfig insert local database error:"+e.getMessage());
			throw e;
		}
	}
	@Override
	public void delIvaConfig(String ip, Integer port) throws Exception {
		boolean result =ivaConfigSocketService.notifyIVAServer(ip, port, "delete");
		for(int i=0;i<3;i++){
			if(result){
				break;
			}
			result =ivaConfigSocketService.notifyIVAServer(ip, port, "delete");
		}
	}
	@Override
	public void delIvaConfig(String ivaID) throws Exception {
		ivaConfigMapper.delIvaConfig(ivaID);
	}
	@Override
	public TVmIva getIvaById(String ivaID) throws Exception {
		return ivaConfigMapper.getIvaById(ivaID);
	}
	@Override
	public Page<TVmIva> queryIvaByPage(Page<TVmIva> page) throws Exception {
		return ivaConfigMapper.queryIvaByPage(page);
	}
	/*********************************getter/setter***********************************/
	public IIvaConfigMapper getIvaConfigMapper() {
		return ivaConfigMapper;
	}

	public void setIvaConfigMapper(IIvaConfigMapper ivaConfigMapper) {
		this.ivaConfigMapper = ivaConfigMapper;
	}
	public IVAConfigSocketService getIvaConfigSocketService() {
		return ivaConfigSocketService;
	}
	public void setIvaConfigSocketService(
			IVAConfigSocketService ivaConfigSocketService) {
		this.ivaConfigSocketService = ivaConfigSocketService;
	}
}
