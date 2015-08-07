package com.dao.zone.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.zone.IPositionZoneMapDao;
import com.entity.zone.PositionZoneMapBean;

public class PositionZoneMapDaoImpl implements IPositionZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	public void addPositionZoneMap(PositionZoneMapBean positionZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.insert("insertPositionZoneMap", positionZoneMapBean);
	}

	public void delPositionZoneMap(PositionZoneMapBean positionZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.delete("delPositionZoneMap", positionZoneMapBean);
	}

	public void modPositionZoneMap(PositionZoneMapBean positionZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.update("modPositionZoneMap", positionZoneMapBean);
	}

	public void delPositionZoneMapByZoneID(Integer zoneID) throws Exception {
		
		sqlmapclienttemplate.delete("delPositionZoneMapByZoneID", zoneID);
		
	}

	public void delPositionZoneMapByHostID(String hostID) throws Exception {
		
		sqlmapclienttemplate.delete("delPositionZoneMapByHostID",hostID);
		
	}

	public PositionZoneMapBean getPositionZoneMapByZoneID(Integer zoneID)
			throws Exception {
		
		return (PositionZoneMapBean)sqlmapclienttemplate.queryForObject("getPositionZoneMapByZoneID", zoneID);
	}

	@Override
	public List<Integer> getZoneIDsByHostID(String hostID) throws Exception {
		
		return (List<Integer>)sqlmapclienttemplate.queryForList("getZoneIDsByHostID02", hostID);
		
	}
}
