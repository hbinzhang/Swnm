package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.IIpcConfigMapper;
import com.entity.alarmmgt.WarnInfo;
import com.entity.videomonitor.IpcConfigDTO;

public class IpcConfigMapperImpl implements IIpcConfigMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	@Override
	public List<IpcConfigDTO> getIpcInfoByIp(String ip) throws Exception{
		return sqlmapclienttemplate.queryForList("getIpcInfoByIp",ip);
	}

	@Override
	public void delIpcConfigByIp(String ip) throws Exception {
		sqlmapclienttemplate.delete("delIpcConfigByIp", ip);
		
	}

	@Override
	public WarnInfo getAlarmByIpAndPreset(Map<String, Object> paramMap)
			throws Exception {
		return (WarnInfo)sqlmapclienttemplate.queryForObject("getAlarmByIpAndPreset",paramMap);
	}

	@Override
	public WarnInfo getAlarmByAlarmCode(Integer alarmCode) throws Exception {
		return (WarnInfo)sqlmapclienttemplate.queryForObject("getAlarmByAlarmCode",alarmCode);
	}

}
