package com.dao.zone;

import java.util.List;
import com.entity.zone.PositionZoneMapBean;

public interface IPositionZoneMapDao {
	
	void addPositionZoneMap(PositionZoneMapBean positionZoneMapBean)throws Exception;
	void delPositionZoneMap(PositionZoneMapBean positionZoneMapBean)throws Exception;
	void modPositionZoneMap(PositionZoneMapBean positionZoneMapBean)throws Exception;
	void delPositionZoneMapByZoneID(Integer zoneID)throws Exception;
	void delPositionZoneMapByHostID(String hostID)throws Exception;
	PositionZoneMapBean getPositionZoneMapByZoneID(Integer zoneID)throws Exception;
	List<Integer> getZoneIDsByHostID(String hostID)throws Exception;
}
