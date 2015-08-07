package com.dao.videomonitor.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.common.page.Page;
import com.dao.videomonitor.IIvaConfigMapper;
import com.entity.videomonitor.TVmIva;
import com.entity.videomonitor.TVmIvaIpcMap;

public class IvaConfigMapperImpl implements IIvaConfigMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	
	@Override
	public void addIvaConfig(TVmIvaIpcMap tvmIvaIpcMap) throws Exception {
		sqlmapclienttemplate.insert("addIvaConfig",tvmIvaIpcMap);
	}

	@Override
	public void batchAddIvaConfig(List<TVmIvaIpcMap> tvmIvaIpcMaps)
			throws Exception {
		sqlmapclienttemplate.insert("batchAddIvaConfig",tvmIvaIpcMaps);
	}

	@Override
	public void delIvaConfig(String ivaID) throws Exception {
		sqlmapclienttemplate.delete("delIvaConfig",ivaID);
	}

	@Override
	public TVmIva getIvaById(String ivaID) throws Exception {
		
		return (TVmIva)sqlmapclienttemplate.queryForObject("getIvaById",ivaID);
		
	}

	@Override
	public Page<TVmIva> queryIvaByPage(Page<TVmIva> page) throws Exception {
		List<TVmIva> tvmIvas = (List<TVmIva>)sqlmapclienttemplate.queryForList("queryTvmIvaByPage",page);
		int totalCount = (Integer)sqlmapclienttemplate.queryForObject("findTvmIvaByCount",page);
		
		Page<TVmIva> p = new Page<TVmIva>(page.getOffset(), page.getSize(), (long)totalCount);
		p.setDatas(tvmIvas);
		
		return p;
	}

}
