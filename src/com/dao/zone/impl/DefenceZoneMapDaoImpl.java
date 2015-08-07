package com.dao.zone.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.zone.IDefenceZoneMapDao;
import com.entity.zone.DefenceZoneMapBean;

public class DefenceZoneMapDaoImpl implements IDefenceZoneMapDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void addDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)
			throws Exception {
		
		sqlmapclienttemplate.insert("insertDefenceZoneMap", defenceZoneMapBean);
	}

	public void delDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.delete("delDefenceZoneMap", defenceZoneMapBean);
	}

	public void modDefenceZoneMap(DefenceZoneMapBean defenceZoneMapBean)
			throws Exception {
		sqlmapclienttemplate.update("modDefenceZoneMap", defenceZoneMapBean);
	}

	public void delDefenceZoneMapByZoneID(Integer zoneID) throws Exception {
		
		sqlmapclienttemplate.delete("delDefenceZoneMapByZoneID", zoneID);
		
	}

	public void delDefenceZoneMapByHostID(String hostID) throws Exception {
		
		sqlmapclienttemplate.delete("delDefenceZoneMapByHostID", hostID);
		
	}

	public DefenceZoneMapBean getDefenceZoneMapByZoneID(Integer zoneID)
			throws Exception {
		return (DefenceZoneMapBean)sqlmapclienttemplate.queryForObject("getDefenceZoneMapByZoneID", zoneID);
	}

	@Override
	public List<Integer> getZoneIDsByHostID(String hostID) throws Exception {
		return (List<Integer>)sqlmapclienttemplate.queryForList("getZoneIDsByHostID", hostID);
	}
}
