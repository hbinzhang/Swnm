package com.dao.videomonitor.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmManufacturerMapper;
import com.entity.videomonitor.TVmManufacturer;
import com.entity.videomonitor.TVmManufacturerExample;

public class TVmManufacturerMapperImpl implements TVmManufacturerMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int countByExample(TVmManufacturerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(TVmManufacturerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String vendorid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TVmManufacturer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TVmManufacturer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TVmManufacturer> selectByExample(TVmManufacturerExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TVmManufacturer selectByPrimaryKey(String vendorid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(TVmManufacturer record,
			TVmManufacturerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(TVmManufacturer record,
			TVmManufacturerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(TVmManufacturer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TVmManufacturer record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TVmManufacturer> selectAll() {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectAll");
	}

}
