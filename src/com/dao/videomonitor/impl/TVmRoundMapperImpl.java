package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmRoundMapper;
import com.entity.videomonitor.TVmRound;

public class TVmRoundMapperImpl implements TVmRoundMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int insertRound(TVmRound round) {
		int res = 0;
		Object id = sqlmapclienttemplate.insert("insertRound", round);
		if(id != null && Integer.class.isInstance(id)){
			res = Integer.parseInt(id.toString());
		}
		return res;
	}

	@Override
	public int updateRound(TVmRound round) {
		int res = 0;
		Object rows = sqlmapclienttemplate.update("updateRound", round);
		if(rows != null && Integer.class.isInstance(rows)){
			res = Integer.parseInt(rows.toString());
		}
		return res;
	}

	@Override
	public int deleteRoundById(int roundid) {
		int res = sqlmapclienttemplate.delete("deleteRoundByPrimaryKey",roundid);
		return res;
	}

	@Override
	public List<TVmRound> selectRound(Map<String, Object> cond) {
		List<TVmRound> res = sqlmapclienttemplate.queryForList("selectRound",cond);
		return res;
	}
}
