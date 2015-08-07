package com.dao.zone;

import java.util.List;
import com.entity.zone.PulseZoneMapBean;

public interface IPulseZoneMapDao {
	
	void addPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)throws Exception;
	void delPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)throws Exception;
	void modPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)throws Exception;
	void delPulseZoneMapByZoneID(Integer zoneID)throws Exception;
	void delPulseZoneMapByHostID(String hostID)throws Exception;
	PulseZoneMapBean getPulseZoneMapByZoneID(Integer zoneID)throws Exception;
	List<Integer> getZoneIDsByHostID(String hostID)throws Exception;
	
}
