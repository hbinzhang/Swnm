package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmDecoderMapper;
import com.entity.videomonitor.TVmDecoder;

public class TVmDecoderMapperImpl implements TVmDecoderMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int deleteDecoderByPrimaryKey(String decoderid) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.delete("deleteDecoderByPrimaryKey",decoderid);
	}

	@Override
	public int insertDecoder(TVmDecoder record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertDecoderSelective(TVmDecoder record) {
		// TODO Auto-generated method stub
		int res = 1;
		try {
			sqlmapclienttemplate.insert("insertDecoderSelective",record);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public int updateDecoderByPrimaryKeySelective(TVmDecoder record) {
		int res = 1;
		try {
			sqlmapclienttemplate.update("updateDecoderByPrimaryKeySelective",record);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public int updateDecoderByPrimaryKey(TVmDecoder record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TVmDecoder> selectDecoderByPage(Map<String, Object> cond) {
		return sqlmapclienttemplate.queryForList("selectDecoderByPage", cond);
	}

	@Override
	public int selectDecoderByPageRowCount(Map<String, Object> cond) {
		Object res = sqlmapclienttemplate.queryForObject("selectDecoderByPageRowCount",cond);
		int count = 0;
		if(res != null && Integer.class.isInstance(res)){
			count = Integer.parseInt(res.toString());
		}
		return count;
	}

	@Override
	public int testDecoderExistById(String decoderid) {
		int count = 0;
		try {
			Object cid = sqlmapclienttemplate.queryForObject("testDecoderExistById", decoderid);
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

	@Override
	public TVmDecoder selectDecoderByPrimaryKey(String decoderid) {
		// TODO Auto-generated method stub
		return (TVmDecoder)sqlmapclienttemplate.queryForObject("selectDecoderByPrimaryKey", decoderid);
	}
}
