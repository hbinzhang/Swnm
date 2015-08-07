package com.dao.linkagectl.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.linkagectl.ISoundSetDao;




public class SoundSetDaoOracleImp implements ISoundSetDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;
	

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public boolean delete(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.delete("deleteSoundSet",o.toString());
		return true;
	}

	public List findall() throws SQLException{
		// TODO Auto-generated method stub		
		return (List)sqlmapclienttemplate.queryForList("getAllSoundSet",null);
	}
	
	public Object findSoundSetFromAlarmCode(Object o)throws SQLException{
		return sqlmapclienttemplate.queryForObject("getSoundSetFromAlarmCode",o.toString());
	}
	public Object findSoundSet(Object o)throws SQLException{
		return sqlmapclienttemplate.queryForObject("getSoundSet",o.toString());
	}
	public boolean save(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.insert("insertSoundSet",o);
		return true;
	}

	public boolean update(Object o) throws SQLException{
		// TODO Auto-generated method stub
		sqlmapclienttemplate.update("updateSoundSet",o);
		return true;
	}

		

	

}
