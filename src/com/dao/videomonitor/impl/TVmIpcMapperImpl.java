package com.dao.videomonitor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.videomonitor.TVmIpcMapper;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIpcDTO;
import com.entity.videomonitor.TVmIpcExample;

public class TVmIpcMapperImpl implements TVmIpcMapper {

	private SqlMapClientTemplate sqlmapclienttemplate;
	
	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public int countByExample(TVmIpcExample example) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public int deleteByExample(TVmIpcExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String ipcid) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.delete("deleteByPrimaryKey", ipcid);
	}

	@Override
	public int insert(TVmIpc record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TVmIpc record) {
		// TODO Auto-generated method stub
		int res = 1;
		try {
			sqlmapclienttemplate.insert("insertSelective",record);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public List<TVmIpc> selectByExample(TVmIpcExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TVmIpc selectByPrimaryKey(String ipcid) {
		// TODO Auto-generated method stub
		return (TVmIpc)sqlmapclienttemplate.queryForObject("selectByPrimaryKey", ipcid);
	}

	@Override
	public int updateByExampleSelective(TVmIpc record, TVmIpcExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(TVmIpc record, TVmIpcExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(TVmIpc record) {
		// TODO Auto-generated method stub
		int res = 1;
		try {
			sqlmapclienttemplate.update("updateByPrimaryKeySelective",record);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = 0;
		}
		return res;
	}

	@Override
	public int updateByPrimaryKey(TVmIpc record) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public List<TVmIpc> selectByPage(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectByPage", cond);
	}

	@Override
	public int selectByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		Object res = sqlmapclienttemplate.queryForObject("selectByPageRowCount",cond);
		int count = 0;
		if(res != null && Integer.class.isInstance(res)){
			count = Integer.parseInt(res.toString());
		}
		return count;
	}

	@Override
	public Map<String, ?> selectByPrimaryKeyEx(TVmIpc ipc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateNvrByIpcIds(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.update("updateNvrByIpcId", cond);
	}

	@Override
	public int testIpcExistById(String ipcid) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			Object cid = sqlmapclienttemplate.queryForObject("testIpcExistById", ipcid);
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
	public List<TVmIpc> selectIpcsByIds(String ipcids) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("selectIpcsByIds", ipcids);
	}

	@Override
	public List<TVmIpc> getAllIpcByOrgIdNvrId(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("getAllIpcByOrgIdNvrId", cond);
	}

	@Override
	public List<TVmIpc> getIPCsByOrg(Map<String, String> org) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForList("getIPCsFromOrg", org);
	}

	@Override
	public int clearIpcByNvrIds(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.update("clearIpcByNvrIds",cond);
	}

	@Override
	public List<TVmIpcDTO> getAllIpcByIvaID(String ivaID) {
		return sqlmapclienttemplate.queryForList("getAllIpcByIvaID", ivaID);
	}

	@Override
	public int checkIpcUsedByAudio(String ipcid) {
		Object res = sqlmapclienttemplate.queryForObject("checkIpcUsedByAudio", ipcid);
		int count = 0;
		if(res != null && Integer.class.isInstance(res)){
			count = Integer.parseInt(res.toString());
		}
		return count;
	}
}
