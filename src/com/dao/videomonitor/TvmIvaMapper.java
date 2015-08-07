package com.dao.videomonitor;

import java.util.List;
import java.util.Map;
import com.entity.videomonitor.TVmIva;

public interface TvmIvaMapper {
	
	int addIVA(TVmIva iva)throws Exception;	
	int deleteIvaById(String id);	
	int updateIvaByPrimaryKeySelective(TVmIva ivaToAddOrUpdate);	
	//TVmIva searchById(String ivaID) ;	
	TVmIva selectIvaById(String ivaID);	
	List<TVmIva> selectIvaByPage(Map<String, Object> cond);	
	int selectIvaByPageRowCount(Map<String, Object> cond);	
	int testIvaExistById(String ivaID);
}
