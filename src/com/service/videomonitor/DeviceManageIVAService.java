package com.service.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIva;

public interface DeviceManageIVAService extends DeviceManageService{
	//
	int addIVA(TVmIva iva)throws Exception;

	int testIvaExistById(String ivaID);

	int deleteIvas(List<String> ivas);

	Map<String, List<Map<String, String>>> getManagementagencies();

	TVmIva searchById(String ivaID);

	int searchByPageRowCount(Map<String, Object> cond);

	List<TVmIva> searchByPage(Map<String, Object> cond);

	int modifyIva(TVmIva ivaToAddOrUpdate);
	
}
