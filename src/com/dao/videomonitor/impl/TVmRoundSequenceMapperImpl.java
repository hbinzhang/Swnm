package com.dao.videomonitor.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmRoundSequenceMapper;
import com.entity.videomonitor.TVmRoundSequence;

public class TVmRoundSequenceMapperImpl implements TVmRoundSequenceMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int insertRoundSequence(TVmRoundSequence roundSequence) {
		int res = 0;
		Object id = sqlmapclienttemplate.insert("insertRoundSequence", roundSequence);
		if(id != null && Integer.class.isInstance(id)){
			res = Integer.parseInt(id.toString());
		}
		return res;
	}

	@Override
	public int deleteRoundSequenceByRound(int roundid) {
		int res = 0;
		Object rows = sqlmapclienttemplate.delete("deleteRoundSequenceByRound", roundid);
		if(rows != null && Integer.class.isInstance(rows)){
			res = Integer.parseInt(rows.toString());
		}
		return res;
	}
}
