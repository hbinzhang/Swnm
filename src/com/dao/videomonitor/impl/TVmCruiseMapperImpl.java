package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmCruiseMapper;
import com.entity.videomonitor.TVmCruise;

public class TVmCruiseMapperImpl implements TVmCruiseMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int insertCruise(TVmCruise cruise) {
		int res = 0;
		Object id = sqlmapclienttemplate.insert("insertCruise", cruise);
		if(id != null && Integer.class.isInstance(id)){
			res = Integer.parseInt(id.toString());
		}
		return res;
	}

	@Override
	public int updateCruise(TVmCruise cruise) {
		int res = 0;
		Object rows = sqlmapclienttemplate.update("updateCruise", cruise);
		if(rows != null && Integer.class.isInstance(rows)){
			res = Integer.parseInt(rows.toString());
		}
		return res;
	}

	@Override
	public List<TVmCruise> selectCruiseByIpcid(Map<String, Object> cond) {
		List<TVmCruise> res = sqlmapclienttemplate.queryForList("selectCruiseByIpcid",cond);
		return res;
	}

	@Override
	public int deleteCruiseById(int cruiseid) {
		int res = sqlmapclienttemplate.delete("deleteCruiseById",cruiseid);
		return res;
	}

	@Override
	public TVmCruise selectCruiseByRoute(Map<String,Object> cond) {
		TVmCruise res = (TVmCruise)sqlmapclienttemplate.queryForObject("selectCruiseByRoute", cond);
		return res;
	}
}
