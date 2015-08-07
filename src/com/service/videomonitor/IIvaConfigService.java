package com.service.videomonitor;

import java.util.List;

import com.common.page.Page;
import com.entity.videomonitor.TVmIva;
import com.entity.videomonitor.TVmIvaIpcMap;

public interface IIvaConfigService {

	void addIvaConfig(List<TVmIvaIpcMap> tvmIvaIpcMaps)throws Exception;

	void delIvaConfig(String ivaID)throws Exception;

	TVmIva getIvaById(String ivaID)throws Exception;

	Page<TVmIva> queryIvaByPage(Page<TVmIva> page)throws Exception;

	void delIvaConfig(String ip, Integer port)throws Exception;

}
