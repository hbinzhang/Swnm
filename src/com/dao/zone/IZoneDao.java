package com.dao.zone;

import java.util.List;
import java.util.Map;

import com.common.page.Page;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.DefenceZoneMapBean;
import com.entity.zone.IntegrationZoneMapBean;
import com.entity.zone.PositionZoneMapBean;
import com.entity.zone.PulseZoneMapBean;
import com.entity.zone.ZoneBean;

public interface IZoneDao {
	
	void addZone(ZoneBean zoneBean)throws Exception;
	void delZone(Integer zoneID)throws Exception;
	void modZone(ZoneBean zoneBean)throws Exception;
	ZoneBean getZoneByZoneID(Integer zoneID)throws Exception;
	void zoneCtrl(ZoneBean zoneInfo)throws Exception;
	List<Integer> getAllZoneID()throws Exception;
	Page<ZoneBean> queryZoneByPage(Page<ZoneBean> page)throws Exception;
	List<AlarmDTO> queryAlarmsByZoneID(Integer zoneID)throws Exception;
	List<String> getHostIDs(FenceBean fenceBean)throws Exception;
	DefenceZoneMapBean getDefenceZoneMapByZoneIdAndHostId(
			DefenceZoneMapBean defenceZoneMapBean)throws Exception;
	PositionZoneMapBean getPositionZoneMapByZoneIdAndHostId(
			PositionZoneMapBean positionZoneMapBean)throws Exception;
	IntegrationZoneMapBean getIntegrationZoneMapByZoneIdAndHostId(
			IntegrationZoneMapBean integrationZoneMapBean)throws Exception;
	PulseZoneMapBean getPulseZoneMapByZoneIdAndHostId(
			PulseZoneMapBean pulseZoneMapBean)throws Exception;
	IpcZoneMap getIpcZoneMapByZoneIdAndIpcId(IpcZoneMap ipcZoneMap)throws Exception;
	SoundZoneMap getSoundZoneMapByZoneIdAndAudioId(SoundZoneMap soundZoneMap)throws Exception;
	List<ZoneBean> getZonesByBranchIdOrMngId(ZoneBean zoneBean)throws Exception;
	List<String> findHostIDsByFenceType(Map paramMap)throws Exception;
	String getFenceHostIdByZoneId(Integer zoneID)throws Exception;
	void updateZoneStatusByMngID(Map paramMap)throws Exception;
	List<String> getIpcIDsByOrgID(Map paramMap)throws Exception;
	int getCountMainIpcByZoneId(Map<String, Integer> param);
	int getCountIpcByIpcIdAndPreset(Map<String, Object> param);

}
