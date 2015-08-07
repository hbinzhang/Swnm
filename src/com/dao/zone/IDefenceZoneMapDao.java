package com.dao.zone;

import java.util.List;
import com.entity.zone.DefenceZoneMapBean;

public interface IDefenceZoneMapDao {
	
	void addDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)throws Exception;
	void delDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)throws Exception;
	void modDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)throws Exception;
	void delDefenceZoneMapByZoneID(Integer zoneID)throws Exception;
	void delDefenceZoneMapByHostID(String hostID)throws Exception;
	DefenceZoneMapBean getDefenceZoneMapByZoneID(Integer zoneID)throws Exception;
	List<Integer> getZoneIDsByHostID(String hostID)throws Exception;

}
