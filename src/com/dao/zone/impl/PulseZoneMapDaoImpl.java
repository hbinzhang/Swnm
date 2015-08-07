package com.dao.zone.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.zone.IPulseZoneMapDao;
import com.entity.zone.PulseZoneMapBean;

public class PulseZoneMapDaoImpl implements IPulseZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	public void addPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.insert("insertPulseZoneMap", pulseZoneMapBean);
	}

	public void delPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)
			throws Exception {
		
		sqlmapclienttemplate.delete("delPulseZoneMap", pulseZoneMapBean);
		
	}

	public void modPulseZoneMap(PulseZoneMapBean pulseZoneMapBean)
			throws Exception {
		
		sqlmapclienttemplate.update("modPulseZoneMap", pulseZoneMapBean);

	}

	public void delPulseZoneMapByZoneID(Integer zoneID) throws Exception {
		
		sqlmapclienttemplate.delete("delPulseZoneMapByZoneID", zoneID);
		
		
	}

	public void delPulseZoneMapByHostID(String hostID) throws Exception {
		
		sqlmapclienttemplate.delete("delPulseZoneMapByHostID", hostID);
		
	}

	public PulseZoneMapBean getPulseZoneMapByZoneID(Integer zoneID)
			throws Exception {
		
		return (PulseZoneMapBean)sqlmapclienttemplate.queryForObject("getPulseZoneMapByZoneID", zoneID);
		
	}

	@Override
	public List<Integer> getZoneIDsByHostID(String hostID) throws Exception {
		return (List<Integer>)sqlmapclienttemplate.queryForList("getZoneIDsByHostID03", hostID);
	}

}
