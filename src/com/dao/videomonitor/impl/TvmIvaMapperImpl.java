package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TvmIvaMapper;

import com.entity.videomonitor.TVmIva;

public class TvmIvaMapperImpl implements TvmIvaMapper {
	
	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	@Override
	public int addIVA(TVmIva iva) throws Exception {
		
		int res = 1;
		try {
			sqlmapclienttemplate.insert("insertIvaSelective", iva);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public int deleteIvaById(String id) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.delete("deleteIvaById",id);
	}

	@Override
	public int updateIvaByPrimaryKeySelective(TVmIva ivaToAddOrUpdate) {
		int res = 1;
		try {
			sqlmapclienttemplate.update("updateIvaByPrimaryKeySelective",ivaToAddOrUpdate);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}



	@Override
	public TVmIva selectIvaById(String ivaID) {
		// TODO Auto-generated method stub
		return (TVmIva)sqlmapclienttemplate.queryForObject("getIvaById", ivaID);
		
	}

	@Override
	public List<TVmIva> selectIvaByPage(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectIvaByPage", cond);
		
	}

	@Override
	public int selectIvaByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		Object res = sqlmapclienttemplate.queryForObject("selectIvaByPageRowCount",cond);
		int count = 0;
		if(res != null && Integer.class.isInstance(res)){
			count = Integer.parseInt(res.toString());
		}
		return count;
	}

	public int testIvaExistById(String ivaId) {
		int count = 0;
		try {
			Object cid = sqlmapclienttemplate.queryForObject("testIvaExistById", ivaId);
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
