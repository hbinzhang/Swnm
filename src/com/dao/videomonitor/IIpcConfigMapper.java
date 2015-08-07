package com.dao.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.alarmmgt.WarnInfo;
import com.entity.videomonitor.IpcConfigDTO;


public interface IIpcConfigMapper {
	
	List<IpcConfigDTO> getIpcInfoByIp(String ip)throws Exception;

	void delIpcConfigByIp(String result)throws Exception;

	WarnInfo getAlarmByIpAndPreset(Map<String, Object> paramMap)throws Exception;

	WarnInfo getAlarmByAlarmCode(Integer alarmCode)throws Exception;
}
