package com.dao.common.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.common.IManufacturerDao;
import com.entity.Manufacturer;

public class ManufacturerDaoImpl implements IManufacturerDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}
	@Override
	public List<Manufacturer> findAllManufacturer() throws Exception {
		return (List<Manufacturer>)sqlmapclienttemplate.queryForList("findAllManufacturer");
	}

	@Override
	public Manufacturer getManufacturerById(String vendorID) throws Exception {
		return (Manufacturer)sqlmapclienttemplate.queryForObject("getManufacturerById",vendorID);
	}

	@Override
	public String getSubComIDByMngID(String mngID) throws Exception {
		return (String)sqlmapclienttemplate.queryForObject("getSubComIDByMngID",mngID);
	}

}
