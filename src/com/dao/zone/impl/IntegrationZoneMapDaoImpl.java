package com.dao.zone.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.zone.IIntegrationZoneMapDao;
import com.entity.zone.IntegrationZoneMapBean;

public class IntegrationZoneMapDaoImpl implements IIntegrationZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	public void addIntegrationZoneMap(
			IntegrationZoneMapBean integrationZoneMapBean) throws Exception {
		sqlmapclienttemplate.insert("insertIntegrationZoneMap", integrationZoneMapBean);
	}

	public void delIntegrationZoneMap(
			IntegrationZoneMapBean integrationZoneMapBean) throws Exception {
		
		sqlmapclienttemplate.delete("delIntegrationZoneMap", integrationZoneMapBean);

	}

	public void modIntegrationZoneMap(
			IntegrationZoneMapBean integrationZoneMapBean) throws Exception {
		sqlmapclienttemplate.update("modIntegrationZoneMap", integrationZoneMapBean);
	}

	public void delIntegrationZoneMapByZoneID(Integer zoneID) throws Exception {
		sqlmapclienttemplate.delete("delIntegrationZoneMapByZoneID", zoneID);
	}

	public void delIntegrationZoneMapByHostID(String hostID) throws Exception {
		sqlmapclienttemplate.delete("delIntegrationZoneMapByHostID", hostID);
	}

	public IntegrationZoneMapBean getIntegrationZoneMapByZoneID(Integer zoneID)
			throws Exception {
		return (IntegrationZoneMapBean)sqlmapclienttemplate.queryForObject("getIntegrationZoneMapByZoneID", zoneID);
	}

	@Override
	public List<Integer> getZoneIDsByHostID(String hostID) throws Exception {
		
		return (List<Integer>)sqlmapclienttemplate.queryForList("getZoneIDsByHostID01", hostID);
	}
}
