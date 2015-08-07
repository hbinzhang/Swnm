package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmNvrMapper;
import com.entity.videomonitor.TVmNvr;
import com.entity.videomonitor.TVmNvrExample;

public class TVmNvrMapperImpl implements TVmNvrMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int countByExample(TVmNvrExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(TVmNvrExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String nvrid) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.delete("deleteNvrByPrimaryKey", nvrid);
	}

	@Override
	public int insert(TVmNvr record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TVmNvr record) {
		// TODO Auto-generated method stub
		int res = 1;
		try {
			sqlmapclienttemplate.insert("insertNvrSelective", record);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public List<TVmNvr> selectByExample(TVmNvrExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TVmNvr selectByPrimaryKey(String nvrid) {
		// TODO Auto-generated method stub
		return (TVmNvr)sqlmapclienttemplate.queryForObject("selectNvrByPrimaryKey", nvrid);
	}

	@Override
	public int updateByExampleSelective(TVmNvr record, TVmNvrExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(TVmNvr record, TVmNvrExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(TVmNvr record) {
		// TODO Auto-generated method stub
		int res = sqlmapclienttemplate.update("updateNvrByPrimaryKeySelective", record);
		return res;
	}

	@Override
	public int updateByPrimaryKey(TVmNvr record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TVmNvr> selectByPage(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectNvrByPage", cond);
	}

	@Override
	public int selectByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		Object res = sqlmapclienttemplate.queryForObject("selectNvrByPageRowCount",cond);
		int count = 0;
		if(res != null && Integer.class.isInstance(res)){
			count = Integer.parseInt(res.toString());
		}
		return count;
	}

	@Override
	public List<TVmNvr> selectNvrByRole(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectNvrByRole",cond);
	}

	@Override
	public int testNvrExistById(String nvrid) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			Object cid = sqlmapclienttemplate.queryForObject("testNvrExistById", nvrid);
			if(cid != null && Integer.class.isInstance(cid)){
				count = Integer.parseInt(cid.toString());
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
