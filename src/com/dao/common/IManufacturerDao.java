package com.dao.common;

import java.util.List;

import com.entity.Manufacturer;

public interface IManufacturerDao {
	
	public List<Manufacturer> findAllManufacturer() throws Exception;

	public Manufacturer getManufacturerById(String vendorID)throws Exception;
	
	public String getSubComIDByMngID(String mngID)throws Exception;

}
