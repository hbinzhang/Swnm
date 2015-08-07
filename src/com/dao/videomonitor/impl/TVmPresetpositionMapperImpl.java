package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmPresetpositionMapper;
import com.entity.videomonitor.TVmPresetposition;

public class TVmPresetpositionMapperImpl implements TVmPresetpositionMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int insertPreset(TVmPresetposition preset) {
		int res = 0;
		Object id = sqlmapclienttemplate.insert("insertPreset",preset);
		if(id != null && Integer.class.isInstance(id)){
			res = Integer.parseInt(id.toString());
		}
		return res;
	}

	@Override
	public int updatePreset(TVmPresetposition preset) {
		int res = 0;
		res = sqlmapclienttemplate.update("updatePreset", preset);
		return res;
	}

	@Override
	public int deletePresetByPrimaryKey(int pointid) {
		int res = 0;
		res = sqlmapclienttemplate.delete("deletePresetByPrimaryKey", pointid);
		return res;
	}

	@Override
	public List<TVmPresetposition> selectPresetByPage(Map<String, Object> cond) {
		List<TVmPresetposition> res = sqlmapclienttemplate.queryForList("selectPresetByPage", cond);
		return res;
	}

	@Override
	public TVmPresetposition selectPresetByPrimaryKey(int id) {
		TVmPresetposition res = (TVmPresetposition)sqlmapclienttemplate.queryForObject("selectPresetByPrimaryKey", id);
		return res;
	}

}
