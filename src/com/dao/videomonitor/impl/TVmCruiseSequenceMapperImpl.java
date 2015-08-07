package com.dao.videomonitor.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmCruiseSequenceMapper;
import com.entity.videomonitor.TVmCruiseSequence;

public class TVmCruiseSequenceMapperImpl implements TVmCruiseSequenceMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	
	@Override
	public int insertCruiseSequence(TVmCruiseSequence cruiseSequence) {
		int res = 0;
		Object id = sqlmapclienttemplate.insert("insertCruiseSequence", cruiseSequence);
		if(id != null && Integer.class.isInstance(id)){
			res = Integer.parseInt(id.toString());
		}
		return res;
	}

	@Override
	public int deleteCruiseSequenceByCruise(int cruiseid) {
		int res = 0;
		Object rows = sqlmapclienttemplate.delete("deleteCruiseSequenceByCruise", cruiseid);
		if(rows != null && Integer.class.isInstance(rows)){
			res = Integer.parseInt(rows.toString());
		}
		return res;
	}

}
