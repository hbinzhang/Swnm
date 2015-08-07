package com.dao.videomonitor;

import java.util.List;

import com.common.page.Page;
import com.entity.videomonitor.TVmIva;
import com.entity.videomonitor.TVmIvaIpcMap;

public interface IIvaConfigMapper {

	void batchAddIvaConfig(List<TVmIvaIpcMap> tvmIvaIpcMaps)throws Exception;
	
	void addIvaConfig(TVmIvaIpcMap tvmIvaIpcMap)throws Exception;

	void delIvaConfig(String ivaID)throws Exception;

	TVmIva getIvaById(String ivaID)throws Exception;

	Page<TVmIva> queryIvaByPage(Page<TVmIva> page)throws Exception;

}
