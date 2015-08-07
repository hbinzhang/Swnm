package com.dao.zone.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.common.page.Page;
import com.dao.zone.IZoneDao;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.DefenceZoneMapBean;
import com.entity.zone.IntegrationZoneMapBean;
import com.entity.zone.PositionZoneMapBean;
import com.entity.zone.PulseZoneMapBean;
import com.entity.zone.ZoneBean;

@SuppressWarnings("unchecked")
public class ZoneDaoImpl implements IZoneDao{
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	
	
	@Override
	public int getCountIpcByIpcIdAndPreset(Map<String, Object> param) {
		return (Integer)sqlmapclienttemplate.queryForObject("getCountIpcByIpcIdAndPreset", param);
	}

	@Override
	public int getCountMainIpcByZoneId(Map<String, Integer> param) {
		// TODO Auto-generated method stub
		return (Integer)sqlmapclienttemplate.queryForObject("getCountMainIpcByZoneId", param);
	}

	public void addZone(ZoneBean zoneBean) throws Exception {
		
		sqlmapclienttemplate.insert("insertZone", zoneBean);
		
	}

	public void delZone(Integer zoneID) throws Exception {
		sqlmapclienttemplate.delete("deleteZone", zoneID);
	}

	public void modZone(ZoneBean zoneBean) throws Exception {
		sqlmapclienttemplate.update("modZone", zoneBean);
	}
	public ZoneBean getZoneByZoneID(Integer zoneID) throws Exception {
		return (ZoneBean)sqlmapclienttemplate.queryForObject("getZoneByZoneID", zoneID);
	}

	public void zoneCtrl(ZoneBean zoneInfo) throws Exception {
		sqlmapclienttemplate.update("updateStatus", zoneInfo);
	}

	public List<Integer> getAllZoneID() throws Exception {
		
		return sqlmapclienttemplate.queryForList("getAllZoneID");
	}

	public Page<ZoneBean> queryZoneByPage(Page<ZoneBean> page) throws Exception {
		
		List<ZoneBean> zones = (List<ZoneBean>)sqlmapclienttemplate.queryForList("queryZoneByPage",page);
		int totalCount = (Integer)sqlmapclienttemplate.queryForObject("findZoneByCount",page);
		
		Page<ZoneBean> p = new Page<ZoneBean>(page.getOffset(), page.getSize(), (long)totalCount);
		p.setDatas(zones);
		
		return p;
	}
	public List<AlarmDTO> queryAlarmsByZoneID(Integer zoneID) throws Exception {
		
		return (List<AlarmDTO>)sqlmapclienttemplate.queryForList("queryAlarmsByZoneID", zoneID);
		
	}
	public List<String> getHostIDs(FenceBean fenceBean) throws Exception {
		
		return (List<String>)sqlmapclienttemplate.queryForList("getHostIDs", fenceBean);
		
	}
	@Override
	public List<String> getIpcIDsByOrgID(Map paramMap) throws Exception {
		
		return (List<String>)sqlmapclienttemplate.queryForList("getIpcIDsByOrgID", paramMap);
		
	}
	@Override
	public DefenceZoneMapBean getDefenceZoneMapByZoneIdAndHostId(
			DefenceZoneMapBean defenceZoneMapBean) throws Exception {
		
		return (DefenceZoneMapBean)sqlmapclienttemplate.queryForObject("getDefenceZoneMapByZoneIdAndHostId", defenceZoneMapBean);
		
	}
	@Override
	public PositionZoneMapBean getPositionZoneMapByZoneIdAndHostId(
			PositionZoneMapBean positionZoneMapBean) throws Exception {
		
		return (PositionZoneMapBean)sqlmapclienttemplate.queryForObject("getPositionZoneMapByZoneIdAndHostId", positionZoneMapBean);
		
	}
	@Override
	public IntegrationZoneMapBean getIntegrationZoneMapByZoneIdAndHostId(
			IntegrationZoneMapBean integrationZoneMapBean) throws Exception {
		
		return (IntegrationZoneMapBean)sqlmapclienttemplate.queryForObject("getIntegrationZoneMapByZoneIdAndHostId", integrationZoneMapBean);
	}
	@Override
	public PulseZoneMapBean getPulseZoneMapByZoneIdAndHostId(
			PulseZoneMapBean pulseZoneMapBean) throws Exception {
		
		return (PulseZoneMapBean)sqlmapclienttemplate.queryForObject("getPulseZoneMapByZoneIdAndHostId", pulseZoneMapBean);
	}
	@Override
	public IpcZoneMap getIpcZoneMapByZoneIdAndIpcId(IpcZoneMap ipcZoneMap)
			throws Exception {
		
		return (IpcZoneMap)sqlmapclienttemplate.queryForObject("getIpcZoneMapByZoneIdAndIpcId", ipcZoneMap);
	
	}
	@Override
	public SoundZoneMap getSoundZoneMapByZoneIdAndAudioId(
			SoundZoneMap soundZoneMap) throws Exception {
		return (SoundZoneMap)sqlmapclienttemplate.queryForObject("getSoundZoneMapByZoneIdAndAudioId", soundZoneMap);
	}
	@Override
	public List<ZoneBean> getZonesByBranchIdOrMngId(ZoneBean zoneBean)
			throws Exception {
		
		return sqlmapclienttemplate.queryForList("getZonesByBranchIdOrMngId", zoneBean);
		
	}

	@Override
	public List<String> findHostIDsByFenceType(Map paramMap) throws Exception {
		
		return (List<String>)sqlmapclienttemplate.queryForList("findHostIDsByFenceType", paramMap);
		
	}
	@Override
	public String getFenceHostIdByZoneId(Integer zoneID) throws Exception {
		
		return (String)sqlmapclienttemplate.queryForObject("getFenceHostIdByZoneId", zoneID);
	}

	@Override
	public void updateZoneStatusByMngID(Map paramMap) throws Exception {
		sqlmapclienttemplate.update("updateZoneStatusByMngID", paramMap);
	}
}
