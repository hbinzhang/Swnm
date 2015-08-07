package com.service.zone;

import java.util.List;
import java.util.Map;

import com.common.page.Page;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.DefenceZoneMapBean;
import com.entity.zone.DeviceMapInterface;
import com.entity.zone.IntegrationZoneMapBean;
import com.entity.zone.PositionZoneMapBean;
import com.entity.zone.PulseZoneMapBean;
import com.entity.zone.ZoneBean;

public interface IZoneService {
	
	void addDeviceInfo(ZoneBean zoneBean,DeviceMapInterface device,List<IpcZoneMap> ipcZoneMaps,boolean flag)throws Exception;
	
	void delFenceMapInfo(ZoneBean zoneBean,DeviceMapInterface device)throws Exception;
	
	void delZone(ZoneBean zoneBean)throws Exception;
	
	void modZone(ZoneBean zoneBean,DeviceMapInterface device,List<IpcZoneMap> ipcZoneMaps)throws Exception;
	
	void updateFenceMapInfo(ZoneBean zoneBean,DeviceMapInterface device)throws Exception;
	
	void zoneCtrl(ZoneBean zoneInfo)throws Exception;
	
	List<Integer> getAllZoneID()throws Exception;
	
	void addDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)throws Exception;
	
	void addPositionZoneMap(PositionZoneMapBean positionZoneMapBean)throws Exception;
	
	void addIntegrationZoneMap(IntegrationZoneMapBean integrationZoneMapBean)throws Exception;
	
	void addPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)throws Exception;
	
	ZoneBean getZoneByID(Integer zoneID)throws Exception;
	PulseZoneMapBean getPulseZoneMapByZoneID(Integer zoneID)throws Exception;
	IntegrationZoneMapBean getIntegrationZoneMapByZoneID(Integer zoneID)throws Exception;
	DefenceZoneMapBean getDefenceZoneMapByZoneID(Integer zoneID)throws Exception;
	PositionZoneMapBean getPositionZoneMapByZoneID(Integer zoneID)throws Exception;
	List<IpcZoneMap> getIpcZoneMapsByZoneID(Integer zoneID)throws Exception;
	void zoneCtrl(Integer zoneID,Integer status)throws Exception;
	Page<ZoneBean> queryZoneByPage(Page<ZoneBean> page)throws Exception;
	void addMainZoneInfo(ZoneBean zoneBean)throws Exception;
	void modMainZoneInfo(ZoneBean zoneBean)throws Exception;
	void addIpcZoneMap(IpcZoneMap ipcZoneMap)throws Exception;
	void addSoundZoneMap(SoundZoneMap soundZoneMap)throws Exception;
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
	void handleRemoteJmsMsg(String message)throws Exception;
	List<String> findHostIDsByFenceType(Map paramMap)throws Exception;
	String addIpcInfo(IpcZoneMap ipcZoneMap)throws Exception;
	void delIpcMapInfo(IpcZoneMap ipcZoneMap)throws Exception;
	void updateIpcMapInfo(IpcZoneMap ipcZoneMap) throws Exception;

	String getFenceHostIdByZoneId(Integer zoneID)throws Exception;

	List<String> getIpcIDsByOrgID(Map paramMap)throws Exception;

	void delZoneBasicInfo(Integer zoneID);

	int getCountMainIpcByZoneId(Map<String, Integer> param);

	int getCountIpcByIpcIdAndPreset(Map<String, Object> param);
}
