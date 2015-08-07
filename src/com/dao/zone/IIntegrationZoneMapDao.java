package com.dao.zone;

import java.util.List;
import com.entity.zone.IntegrationZoneMapBean;

public interface IIntegrationZoneMapDao {
	
	void addIntegrationZoneMap(IntegrationZoneMapBean integrationZoneMapBean)throws Exception;
	void delIntegrationZoneMap(IntegrationZoneMapBean integrationZoneMapBean)throws Exception;
	void modIntegrationZoneMap(IntegrationZoneMapBean integrationZoneMapBean)throws Exception;
	void delIntegrationZoneMapByZoneID(Integer zoneID)throws Exception;
	void delIntegrationZoneMapByHostID(String hostID)throws Exception;
	IntegrationZoneMapBean getIntegrationZoneMapByZoneID(Integer zoneID)throws Exception;
	List<Integer> getZoneIDsByHostID(String hostID)throws Exception;

}
